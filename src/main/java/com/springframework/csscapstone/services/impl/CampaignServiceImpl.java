package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.config.firebase_config.FirebaseMessageService;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.config.message.constant.MobileScreen;
import com.springframework.csscapstone.data.dao.specifications.CampaignSpecifications;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CampaignForCollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignDetailDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.NotEnoughKpiException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.CampaignMapper;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.springframework.csscapstone.data.status.CampaignStatus.APPROVAL;
import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionCatchHandler.completeSchedule;
import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionFCMHandler.fcmException;


@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final BlobUploadImages blobUploadImages;
    private final CampaignImageRepository campaignImageRepository;
    private final PrizeRepository prizeRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final FirebaseMessageService firebaseMessageService;
    @Value("${endpoint}")
    private String endpoint;

    @Value("${campaign_image_container}")
    private String campaignContainer;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final Supplier<NotEnoughKpiException> handlerNotEnoughKPIException =
            () -> new NotEnoughKpiException(MessagesUtils.getMessage(MessageConstant.KPI_NOT_ENOUGH));

    private final Supplier<CampaignNotFoundException> handlerCampaignNotFoundException =
            () -> new CampaignNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));

    private final Supplier<EntityNotFoundException> campaignNotFoundException =
            () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));


    private final Function<UUID, Supplier<EntityNotFoundException>> entityNotFoundExceptionSupplier =
            (id) -> () -> new EntityNotFoundException("The enterprise with id: " + id + " was not found!!!");

    private final Supplier<RuntimeException> weirdError = () -> new RuntimeException("Some thing went wrong in handler image");

    private final Supplier<EntityNotFoundException> notFoundException = () -> new EntityNotFoundException("The campaign was not found");

    private final Supplier<RuntimeException> noTokenException = () -> new RuntimeException("No have token belong to this account");

    private static final int INVALID_VALUE = 0;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int SHIFT_TO_ACTUAL_PAGE = 1;

    private final CacheManager cacheManager;

    private void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("findCampaignWithoutEnterpriseId")).clear();
        Objects.requireNonNull(cacheManager.getCache("listCampaignWithoutEnterpriseIdForCollaborator")).clear();
        Objects.requireNonNull(cacheManager.getCache("findCampaign")).clear();
        Objects.requireNonNull(cacheManager.getCache("campaignFindById")).clear();
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4, #p5}", value = "findCampaignWithoutEnterpriseId")
    public PageImplResDto<CampaignResDto> findCampaignWithoutEnterpriseId(
            String name, LocalDateTime date, Long kpi,
            CampaignStatus status, Integer pageNumber, Integer pageSize) {


        Specification<Campaign> condition = Specification
                .where(StringUtils.isEmpty(name) ? null : CampaignSpecifications.containsName(name))
                .and(date == null ? null : CampaignSpecifications.beforeEndDate(date))
                .and(kpi == null || kpi == 0 ? null : CampaignSpecifications.smallerKpi(kpi))
                .and(status == null ? null : CampaignSpecifications.equalsStatus(status))
                .and(CampaignSpecifications.excludeStatus(
                        CampaignStatus.CREATED,
                        CampaignStatus.DISABLED,
                        CampaignStatus.FINISHED));

        return getCampaignResDtoPageImplResDto(pageNumber, pageSize, condition);
    }


    //sort by start date
    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4, #p5}", value = "listCampaignWithoutEnterpriseIdForCollaborator")
    public PageImplResDto<CampaignForCollaboratorResDto> listCampaignWithoutEnterpriseIdForCollaborator(
            String name, LocalDateTime date,
            Long kpi, CampaignStatus status,
            Integer pageNumber, Integer pageSize) {

        //condition:

        Specification<Campaign> condition = Specification
                .where(StringUtils.isEmpty(name) ? null : CampaignSpecifications.containsName(name))
                .and(date == null ? null : CampaignSpecifications.beforeEndDate(date))
                .and(kpi == null || kpi == INVALID_VALUE ? null : CampaignSpecifications.smallerKpi(kpi))
                .and(status == null ? null : CampaignSpecifications.equalsStatus(status))
                .and(CampaignSpecifications.equalsStatus(APPROVAL));

        pageNumber = Objects.isNull(pageNumber) || pageNumber == INVALID_VALUE ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize == INVALID_VALUE ? DEFAULT_PAGE_SIZE : pageSize;

        Page<Campaign> page = this.campaignRepository
                .findAll(condition, PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize, Sort.by(Campaign_.CREATE_DATE).descending()));

        List<CampaignForCollaboratorResDto> result = page.getContent().stream()
                .sorted(Comparator.comparing(Campaign::getStartDate).reversed())
                .map(CampaignMapper.INSTANCE::campaignToCampaignForCollaboratorResDto)
                .collect(Collectors.toList());
        return new PageImplResDto<>(result, page.getNumber() + SHIFT_TO_ACTUAL_PAGE,
                result.size(), page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4, #p5, #p6, #p7, #p8}", value = "findCampaign")
    public PageImplResDto<CampaignResDto> findCampaign(
            UUID enterpriseId, String name, LocalDateTime startDate,
            LocalDateTime endDate, Long minKpi, Long maxKpi, CampaignStatus status,
            Integer pageNumber, Integer pageSize) {

        Account enterprise = this.accountRepository.findById(enterpriseId)
                .filter(acc -> acc.getRole().getName().equals("Enterprise"))
                .orElseThrow(entityNotFoundExceptionSupplier.apply(enterpriseId));

        Specification<Campaign> condition = Specification
                .where(CampaignSpecifications.equalsEnterpriseId(enterprise))
                .and(StringUtils.isEmpty(name) ? null : CampaignSpecifications.containsName(name))
                .and(startDate == null ? null : CampaignSpecifications.afterStartDate(startDate))
                .and(maxKpi == null || maxKpi == 0 ? null : CampaignSpecifications.smallerKpi(maxKpi))
                .and(status == null ? null : CampaignSpecifications.equalsStatus(status))
                .and(CampaignSpecifications.excludeStatus(CampaignStatus.DISABLED));

        return getCampaignResDtoPageImplResDto(pageNumber, pageSize, condition);
    }

    @Override
    @Cacheable(key = "#p0", value = "campaignFindById")
    public CampaignDetailDto findById(UUID id) throws EntityNotFoundException {
        return campaignRepository
                .findById(id)
                .map(CampaignMapper.INSTANCE::toCampaignDetailDto)
                .orElseThrow(campaignNotFoundException);
    }


    @Transactional
    @Override
    public UUID createCampaign(CampaignCreatorReqDto dto, List<MultipartFile> images) throws CampaignInvalidException {

        Account enterprise = this.accountRepository
                .findById(dto.getEnterprise().getEnterpriseId())
                .filter(acc -> acc.getRole().getName().equals("Enterprise"))
                .filter(Account::getIsActive)
                .orElseThrow(entityNotFoundExceptionSupplier.apply(dto.getEnterprise().getEnterpriseId()));

        List<Product> productList = this.productRepository
                .findAllById(dto.getProducts().stream()
                        .map(CampaignCreatorReqDto.ProductInnerCampaignCreatorDto::getProductId)
                        .collect(Collectors.toList()));

        List<Prize> prize = this.prizeRepository.findAllById(dto.getPrizes()
                .stream()
                .map(CampaignCreatorReqDto.PrizeInnerCampaignCreatorDto::getPrizeId)
                .collect(Collectors.toList()));

        Campaign campaign = new Campaign()
                .setAccount(enterprise)
                .setName(dto.getName())
                .setCampaignShortDescription(dto.getCampaignShortDescription())
                .setCampaignDescription(dto.getCampaignDescription())
                .setStartDate(dto.getStartDate().atStartOfDay())
                .setEndDate(dto.getEndDate().atStartOfDay())
                .setKpiSaleProduct(dto.getKpi())
                .setCampaignStatus(CampaignStatus.CREATED);

        if (!productList.isEmpty()) campaign.addProducts(productList.toArray(new Product[0]));

        if (!productList.isEmpty()) campaign.addPrize(prize.toArray(new Prize[0]));

        Campaign saved = this.campaignRepository.save(campaign);

        this.prizeRepository.saveAll(prize);
        this.productRepository.saveAll(productList);
        clearCache();
        //todo save image
        return this.campaignRepository.save(handlerImage(images, saved)).getId();
    }

    @Transactional
    @Override
    public UUID updateCampaign(UUID campaignId, CampaignUpdaterReqDto dto, List<MultipartFile> images)
            throws EntityNotFoundException {

        Campaign entity = this.campaignRepository.findById(campaignId)
                .filter(camp -> camp.getCampaignStatus().equals(CampaignStatus.CREATED))
                .orElseThrow(campaignNotFoundException);

//        if (!entity.getCampaignStatus().equals(CampaignStatus.SENT)) {
//            throw new RuntimeException("The campaign is not pending status so cant be updated!!!");
//        }
        Set<CampaignUpdaterReqDto.PrizeInnerCampaignDto> prizes = dto.getPrizes();
        Set<CampaignUpdaterReqDto.ProductInnerCampaignDto> products = dto.getProducts();

//        handlerAddPrize(entity, prizes);
//        handlerAddProduct(entity, products);

        prizes
                .stream()
                .map(CampaignUpdaterReqDto.PrizeInnerCampaignDto::getId)
                .map(this.prizeRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(entity::addPrize);

        products
                .stream()
                .map(CampaignUpdaterReqDto.ProductInnerCampaignDto::getId)
                .map(this.productRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(entity::addProducts);

        entity
                .setName(dto.getName())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setCampaignShortDescription(dto.getCampaignShortDescription())
                .setCampaignDescription(dto.getCampaignDescription())
                .setKpiSaleProduct(dto.getKpiSaleProduct());

        //todo image handler:
        Campaign campaign = handlerImage(images, entity);
        this.campaignRepository.save(campaign);
        clearCache();
        return entity.getId();
    }

    //
//    private final Function<UUID, Supplier<RuntimeException>> notFoundPrizeRuntimeException =
//            (id) -> () -> new RuntimeException("The prizes with id: " + id + " was not found!!!");
//
//    private final Function<UUID, Supplier<RuntimeException>> notFoundProductRuntimeException =
//            (id) -> () -> new RuntimeException("The prizes with id: " + id + " was not found!!!");
//    @Async("threadPoolTaskExecutor")

    //    public void handlerAddPrize(Campaign entity, Set<CampaignUpdaterReqDto.PrizeInnerCampaignDto> prizes) {
//        prizes
//                .stream()
//                .map(CampaignUpdaterReqDto.PrizeInnerCampaignDto::getId)
//                .map(this.prizeRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .map(entity::addPrize)
//                .forEach(this.campaignRepository::save);
//    }
//
//    @Async("threadPoolTaskExecutor")
//    public void handlerAddProduct(Campaign entity, Set<CampaignUpdaterReqDto.ProductInnerCampaignDto> products) {
//
//    }
    @Transactional
    @Override
    public void deleteCampaign(UUID id) throws EntityNotFoundException {
        this.campaignRepository.findById(id)
                .map(x -> x.setCampaignStatus(CampaignStatus.DISABLED))
                .map(this.campaignRepository::save)
                .orElseThrow(campaignNotFoundException);
        clearCache();
    }

    @Transactional
    @Override
    public void scheduleCloseCampaign() {
        this.campaignRepository
                .findAll().stream()
                .filter(campaign -> campaign.getEndDate().isBefore(LocalDateTime.now()))
                .filter(campaign -> campaign.getCampaignStatus().equals(APPROVAL))
                .forEach(completeSchedule(this::closingCampaign));
        clearCache();
    }

    /**
     * todo complete campaign t o mapping collaborator into prize
     * todo send notification with account: <BR>
     *     enterprise: list collaborator:
     *     collaborator: prize + campaign
     *
     * @param id
     */
    @Transactional
    @Override
    public void completeCampaign(UUID id) {
        //find campaign and status not finish
        Campaign campaign = this.campaignRepository.loadFetchOnProducts(id)
                .filter(camp -> Objects.nonNull(camp.getPrizes()))
                .filter(camp -> Objects.nonNull(camp.getProducts()))
                .filter(camp -> camp.getCampaignStatus().equals(APPROVAL))
                .orElseThrow(handlerCampaignNotFoundException);


        closingCampaign(campaign);
    }

    private void closingCampaign(Campaign campaign) {
        //get sort collaborator and Long by OrderRepository
        Map<UUID, Long> collaboratorSelling = new HashMap<>();
        Account enterprise = campaign.getAccount();
        //get product in campaign
        List<UUID> productIds = campaign.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        //get all [prize] with desc price:
        List<Prize> prizes = campaign.getPrizes().stream()
                .sorted(Comparator.comparing(Prize::getPrice).reversed())
                .collect(Collectors.toList());

        for (UUID productId : productIds) {
            Map<UUID, Long> _tmp = this.orderRepository
                    .getCollaboratorAndTotalQuantitySold(productId).stream()
                    .collect(Collectors.toMap(
                            tuple -> tuple.get(OrderRepository.COLLABORATOR_IDS, UUID.class),
                            tuple -> tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class)));

            _tmp.forEach((key, value) -> collaboratorSelling
                    .compute(key, (k, v) -> Objects.isNull(v) ? value : v + value));
        }

        //filter collaborators have enough standard: ASC
        List<Account> accounts = collaboratorSelling.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .filter(_entry -> _entry.getValue() >= campaign.getKpiSaleProduct())
                //todo grt list collaborator by prizes size
                .limit(prizes.size())
                //find account by the key in collaborator map
                .flatMap(entry -> this.accountRepository
                        .findById(entry.getKey())
                        .map(Stream::of)
                        .orElseGet(Stream::empty))
                .collect(Collectors.toList());

        //todo send notification:
        long quantity = collaboratorSelling.values().stream().mapToLong(Long::longValue).sum();

        if (accounts.isEmpty()) {
            this.campaignRepository.save(campaign.setCampaignStatus(CampaignStatus.FINISHED));
            //todo send message no have enough kpi
            sendNotificationEnterprise(campaign, enterprise, quantity);
            return;
        }
//        mapping prize by using campaign prize with greater than KPI on campaign KPI
        int count = 0;
        for (Account account : accounts) {
            LOGGER.info("count prize: {}", count);
            if (count < prizes.size()) {
                Prize prize = prizes.get(count++);
                Account accountMapping = account.awardPrize(prize);
                this.accountRepository.save(accountMapping);
                sendNotificationFinishCampaign(campaign, account, prize,
                        collaboratorSelling.get(account.getId()));
            }
        }

        this.campaignRepository.save(campaign.setCampaignStatus(CampaignStatus.FINISHED));
        clearCache();
        sendNotificationEnterprise(campaign, enterprise, quantity);
    }

    @Transactional
    @Override
    public void rejectCampaignInDate() {

        Consumer<Campaign> sendNotification = camp -> this.accountTokenRepository
                .getAccountTokenByAccountOptional(camp.getAccount().getId())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .ifPresent(
                        fcmException(token -> sendNotificationSentCampaign(
                                camp, CampaignStatus.REJECTED,
                                token.getRegistrationToken())));

        this.campaignRepository
                //query sent filter
                .getAllCampaignInDate().stream()
                .map(camp -> camp.setCampaignStatus(CampaignStatus.REJECTED))
                .peek(sendNotification)
                .forEach(this.campaignRepository::save);
        clearCache();
    }

    /**
     * Todo create notification firebase
     *
     * @param campaignId
     * @param status
     */
    @Transactional
    @Override
    public void updateStatusCampaignForModerator(UUID campaignId, CampaignStatus status) throws ExecutionException, JsonProcessingException, InterruptedException {
        Campaign campaign = this.campaignRepository
                .findById(campaignId)
                .map(camp -> camp.setCampaignStatus(status))
                .map(this.campaignRepository::save)
                .orElseThrow(campaignNotFoundException);

        AccountToken token = this.accountTokenRepository
                .getAccountTokenByAccountOptional(campaign.getAccount().getId())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .orElseThrow(noTokenException);
        System.out.println(token);
        clearCache();
        sendNotificationSentCampaign(campaign, status, token.getRegistrationToken());

    }

    @Transactional
    @Override
    public void sentCampaign(UUID campaignId) {
        this.campaignRepository
                .findById(campaignId)
                .map(camp -> camp.setCampaignStatus(CampaignStatus.SENT))
                .map(this.campaignRepository::save)
                .orElseThrow(notFoundException);
        clearCache();
    }

    private PageImplResDto<CampaignResDto> getCampaignResDtoPageImplResDto(
            Integer pageNumber, Integer pageSize, Specification<Campaign> condition) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber == INVALID_VALUE ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize == INVALID_VALUE ? DEFAULT_PAGE_SIZE : pageSize;

        Page<Campaign> page = this.campaignRepository.findAll(condition,
                PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize, Sort.by(Campaign_.CREATE_DATE).descending()));

        List<CampaignResDto> content = page.getContent()
                .stream()
                .map(CampaignMapper.INSTANCE::toCampaignResDto)
                .collect(Collectors.toList());
        return new PageImplResDto<>(
                content, page.getNumber() + SHIFT_TO_ACTUAL_PAGE, content.size(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }


    private Campaign handlerImage(List<MultipartFile> images, Campaign campaign) {
        if (Objects.nonNull(images)) {
            return images
                    .stream()
                    .flatMap(image -> this.saveImageOnAzure(image, campaign.getId())
                            .map(Stream::of)
                            .orElseGet(Stream::empty))
                    .filter(Objects::nonNull)
                    .map(campaign::addImage)
                    .findFirst().orElseThrow(weirdError);
        }
        return campaign;
    }

    private Optional<CampaignImage> saveImageOnAzure(MultipartFile multipartFile, UUID campaignId) {
        String nameImageOnAzure = campaignId + "/";
        Map<String, MultipartFile> collect = Stream.of(multipartFile)
                .collect(Collectors.toMap(
                        image -> nameImageOnAzure + image.getOriginalFilename(),
                        image -> image));

        collect.forEach(blobUploadImages::azureCampaignStorageHandler);

        return collect.keySet()
                .stream()
                .map(imageName -> new CampaignImage(endpoint + campaignContainer + "/" + imageName))
                .peek(image -> LOGGER.info("the image path {}", image.getPath()))
                .peek(this.campaignImageRepository::save)
                .findFirst();
    }

    private void sendNotificationSentCampaign(Campaign campaign, CampaignStatus status, String token)
            throws ExecutionException, JsonProcessingException, InterruptedException {
        PushNotificationRequest notification = new PushNotificationRequest(
                "Campaign Approval Result",
                "The campaign was " + (status.equals(CampaignStatus.REJECTED) ? "reject" : "approval"),
                "The Campaign",
                token,
                campaign.getImage().get(0).getPath());

        Map<String, String> data = new HashMap<>();

        data.put(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString());

        this.firebaseMessageService.sendMessage(data, notification);
    }

    private void sendNotificationFinishCampaign(Campaign campaign, Account account, Prize prize, Long kpi) {
        this.accountTokenRepository.getAccountTokenByAccountOptional(account.getId())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .map(token -> new PushNotificationRequest(
                        "The Finished Campaign",
                        "You receive the prize: " + prize.getName() + ",price: " + prize.getPrice(),
                        "The award prize",
                        token.getRegistrationToken(),
                        campaign.getImage().get(0).getPath()))
                .ifPresent(fcmException(notification -> this.firebaseMessageService.sendMessage(
                        Collections.singletonMap(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString()),
                        notification)));
    }

    private void sendNotificationEnterprise(Campaign campaign, Account enterprise, long quantity) {
        this.accountTokenRepository
                .getAccountTokenByAccountOptional(enterprise.getId())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .map(token -> new PushNotificationRequest(
                        "The Finished Campaign",
                        "The Campaign name: " + campaign.getName() + ",with quantity sold: " + quantity,
                        "The Finished Campaign",
                        token.getRegistrationToken(),
                        campaign.getImage().get(0).getPath()))
                .ifPresent(fcmException(notification -> firebaseMessageService.sendMessage(
                        Collections.singletonMap(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString()),
                        notification
                )));

    }
}
