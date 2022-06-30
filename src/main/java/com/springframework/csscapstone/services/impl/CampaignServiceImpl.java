package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.CampaignSpecifications;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.NotEnoughKpiException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    @Value("${endpoint}")
    private String endpoint;

    @Value("${campaign_image_container}")
    private String campaignContainer;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public PageImplResDto<CampaignResDto> findCampaignWithoutEnterpriseId(
            String name, LocalDateTime date, Long kpi,
            CampaignStatus status, Integer pageNumber, Integer pageSize) {
        Specification<Campaign> condition = Specification
                .where(StringUtils.isEmpty(name) ? null : CampaignSpecifications.containsName(name))
                .and(date == null ? null : CampaignSpecifications.beforeEndDate(date))
                .and(kpi == null || kpi == 0 ? null : CampaignSpecifications.smallerKpi(kpi))
                .and(status == null ? null : CampaignSpecifications.equalsStatus(status));

        return getCampaignResDtoPageImplResDto(pageNumber, pageSize, condition);
    }

    @Override
    public PageImplResDto<CampaignResDto> findCampaign(
            UUID enterpriseId, String name, LocalDateTime startDate,
            LocalDateTime endDate, Long minKpi, Long maxKpi, CampaignStatus status,
            Integer pageNumber, Integer pageSize) {

        Account enterprise = this.accountRepository.findById(enterpriseId)
                .filter(acc -> acc.getRole().getName().equals("Enterprise"))
                .orElseThrow(() -> new EntityNotFoundException("The Enterprise with id: " + enterpriseId + " was not found"));

        Specification<Campaign> condition = Specification
                .where(CampaignSpecifications.equalsEnterpriseId(enterprise))
                .and(StringUtils.isEmpty(name) ? null : CampaignSpecifications.containsName(name))
                .and(startDate == null ? null : CampaignSpecifications.afterStartDate(startDate))
                .and(endDate == null ? null : CampaignSpecifications.beforeEndDate(endDate))
                .and(minKpi == null || minKpi == 0 ? null : CampaignSpecifications.greaterKpi(minKpi))
                .and(maxKpi == null || maxKpi == 0 ? null : CampaignSpecifications.smallerKpi(maxKpi));

        return getCampaignResDtoPageImplResDto(pageNumber, pageSize, condition);
    }

    @Override
    public CampaignResDto findById(UUID id) throws EntityNotFoundException {
        return campaignRepository
                .findById(id).map(MapperDTO.INSTANCE::toCampaignResDto)
                .orElseThrow(campaignNotFoundException());
    }


    @Transactional
    @Override
    public UUID createCampaign(CampaignCreatorReqDto dto, List<MultipartFile> images)
            throws CampaignInvalidException {

        Account enterprise = this.accountRepository
                .findById(dto.getEnterprise().getEnterpriseId())
                .filter(acc -> acc.getRole().getName().equals("Enterprise"))
                .filter(Account::getIsActive)
                .orElseThrow(() -> new EntityNotFoundException("The enterprise with id: " +
                        dto.getEnterprise().getEnterpriseId() + " was not found!!!"));

        List<Product> productList = this.productRepository
                .findAllById(dto.getProducts().stream()
                        .map(CampaignCreatorReqDto.ProductInnerCampaignCreatorDto::getProductId)
                        .collect(Collectors.toList()));

        List<Prize> prize = this.prizeRepository.findAllById(dto.getPrizeInnerCampaignCreatorDto()
                .stream()
                .map(CampaignCreatorReqDto.PrizeInnerCampaignCreatorDto::getPrizeId)
                .collect(Collectors.toList()));

        Campaign campaign = new Campaign()
                .setAccount(enterprise)
                .setName(dto.getName())
                .setCampaignShortDescription(dto.getCampaignShortDescription())
                .setCampaignDescription(dto.getCampaignDescription())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setKpiSaleProduct(dto.getKpi());

        if (!productList.isEmpty()) campaign.addProducts(productList.toArray(new Product[0]));

        if (!productList.isEmpty()) campaign.addPrize(prize.toArray(new Prize[0]));

        Campaign saved = this.campaignRepository.save(campaign);

        //todo save image
        return this.campaignRepository.save(handlerImage(images, saved)).getId();
    }

    @Transactional
    @Override
    public UUID updateCampaign(CampaignUpdaterReqDto dto, List<MultipartFile> images) throws EntityNotFoundException {

        Campaign entity = this.campaignRepository.findById(dto.getId())
                .orElseThrow(campaignNotFoundException());

        if (!entity.getCampaignStatus().equals(CampaignStatus.PENDING)) {
            throw new RuntimeException("The campaign is not pending status so cant be updated!!!");
        }

        entity.setName(dto.getName())
//                .setImage(dto.getImage())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setCampaignShortDescription(dto.getCampaignShortDescription())
                .setCampaignDescription(dto.getCampaignDescription())
                .setKpiSaleProduct(dto.getKpiSaleProduct());
//                .setCampaignStatus(dto.getCampaignStatus());
        //todo image handler:
        Campaign campaign = handlerImage(images, entity);
        this.campaignRepository.save(campaign);
        return entity.getId();
    }

    @Transactional
    @Override
    public void deleteCampaign(UUID id) throws EntityNotFoundException {
        this.campaignRepository.findById(id)
                .map(this::deleteCampaign)
                .map(MapperDTO.INSTANCE::toCampaignResDto)
                .orElseThrow(campaignNotFoundException());
    }

    public void scheduleCloseCampaign() {
        this.campaignRepository
                .findAll().stream()
//                .filter(campaign -> campaign.getStartDate().isBefore(LocalDateTime.now()))
                .filter(campaign -> campaign.getEndDate().isBefore(LocalDateTime.now()))
                .filter(campaign -> campaign.getCampaignStatus().equals(CampaignStatus.PENDING))
                .map(Campaign::getId)
                .peek(this::completeCampaign)
                .close();
    }

    /**
     * todo complete campaign t o mapping collaborator into prize
     *
     * @param id
     */
    @Transactional
    @Override
    public void completeCampaign(UUID id) {

        //find campaign and status not finish
        Campaign campaign = this.campaignRepository.findById(id)
                .filter(_campaign -> !_campaign.getCampaignStatus()
                        .equals(CampaignStatus.FINISHED))
                .orElseThrow(handlerCampaignNotFoundException());

        //get sort collaborator and Long by OrderRepository
        Map<UUID, Long> collaborator = new HashMap<>();

        //get product in campaign
        List<UUID> idProduct = campaign.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

//        idProduct.forEach(System.out::println);
        for (UUID productId : idProduct) {

            Map<UUID, Long> _tmp = this.orderRepository
                    .getCollaboratorAndTotalQuantitySold(productId).stream()
                    .collect(Collectors.toMap(
                            tuple -> tuple.get(OrderRepository.COLL_ID, UUID.class),
                            tuple -> tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class)));
            _tmp.forEach((key, value) -> collaborator.compute(key, (k, v) -> Objects.isNull(v) ? value : v + value));
        }

        //get all [prize] -> sort campaign prize:
        List<Prize> campaignPrizes = campaign.getPrizes().stream()
                //todo sort by comparing price of prize
                .sorted(Comparator.comparing(Prize::getPrice).reversed())
                .collect(Collectors.toList());

        //filter collaborators have enough standard: ASC
        List<Account> accounts = collaborator.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))

                //todo test so in active unlock this code
                .filter(_entry -> _entry.getValue() >= campaign.getKpiSaleProduct())

                //todo get number of element by campaign Prize size
                .limit(campaignPrizes.size())

                .flatMap(entry -> this.accountRepository
                        .findById(entry.getKey())
                        .map(Stream::of).orElseGet(Stream::empty))

                .collect(Collectors.toList());

        if (accounts.isEmpty()) throw handlerNotEnoughKPIException().get();
//
//        mapping prize by using campaign prize with greater than KPI on campaign KPI
        int count = 0;
        for (Account account : accounts) {
            if (count < campaignPrizes.size()) {
                account.awardPrize(campaignPrizes.get(count++));
                this.accountRepository.save(account);
            }
        }
    }

    private Supplier<NotEnoughKpiException> handlerNotEnoughKPIException() {
        return () -> new NotEnoughKpiException(MessagesUtils.getMessage(MessageConstant.KPI_NOT_ENOUGH));
    }

    private Supplier<CampaignNotFoundException> handlerCampaignNotFoundException() {
        return () -> new CampaignNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));
    }

    private Campaign deleteCampaign(Campaign x) {
        x.setCampaignStatus(CampaignStatus.CANCELLED);
        this.campaignRepository.save(x);
        return x;
    }

    private Supplier<EntityNotFoundException> campaignNotFoundException() {
        return () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));
    }


    private PageImplResDto<CampaignResDto> getCampaignResDtoPageImplResDto(Integer pageNumber, Integer pageSize, Specification<Campaign> condition) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber == 0 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize == 0 ? 10 : pageSize;

        Page<Campaign> page = this.campaignRepository.findAll(condition,
                PageRequest.of(pageNumber - 1, pageSize));

        List<CampaignResDto> content = page.getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toCampaignResDto)
                .collect(Collectors.toList());
        return new PageImplResDto<>(
                content, page.getNumber() + 1, content.size(), page.getTotalElements(),
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
                    .findFirst().orElseThrow(() -> new RuntimeException("Some thing went wrong in handler image"));
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


}
