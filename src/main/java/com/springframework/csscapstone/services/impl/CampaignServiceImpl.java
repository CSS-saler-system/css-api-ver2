package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.CampaignRepository;
import com.springframework.csscapstone.data.repositories.OrderRepository;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.payload.basic.CampaignBasicDto;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.NotEnoughKpiException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private final EntityManager em;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    //TODO Need Modified
    @Cacheable(cacheNames = "campaigns")
    @Override
    public List<CampaignBasicDto> findCampaign(String name, LocalDateTime createdDate,
                                               LocalDateTime lastModifiedDate, LocalDateTime startDate,
                                               LocalDateTime endDate, String description,
                                               Long kpi, CampaignStatus status) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Campaign> query = builder.createQuery(Campaign.class);
        Root<Campaign> root = query.from(Campaign.class);

        List<Predicate> predicates = Arrays.asList(
                builder.like(root.get(Campaign_.NAME), name),
                builder.greaterThan(root.get(Campaign_.LAST_MODIFIED_DATE), createdDate),
                builder.lessThan(root.get(Campaign_.LAST_MODIFIED_DATE), lastModifiedDate),
                builder.greaterThan(root.get(Campaign_.START_DATE), startDate),
                builder.lessThan(root.get(Campaign_.END_DATE), endDate),
                builder.greaterThan(root.get(Campaign_.KPI_SALE_PRODUCT), kpi),
                builder.like(root.get(Campaign_.CAMPAIGN_DESCRIPTION), description),
                builder.equal(root.get(Campaign_.CAMPAIGN_STATUS), status)
        );

        CriteriaQuery<Campaign> processQuery = query.where(builder.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(processQuery).getResultList()
                .stream().map(MapperDTO.INSTANCE::toCampaignDto)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignBasicDto findById(UUID id) throws EntityNotFoundException {
        return campaignRepository
                .findById(id).map(MapperDTO.INSTANCE::toCampaignDto)
                .orElseThrow(campaignNotFoundException());
    }


    @Transactional
    @Override
    public UUID createCampaign(CampaignCreatorReqDto dto) throws CampaignInvalidException {
        Campaign campaign = Optional.of(new Campaign())
                .map(x -> toCampaignEntity(dto, x))
                .map(this.campaignRepository::save)
                .orElseThrow(() -> new CampaignInvalidException(
                        MessagesUtils.getMessage(MessageConstant.Campaign.INVALID)
                ));
        return campaign.getId();
    }

    private Campaign toCampaignEntity(CampaignCreatorReqDto dto, Campaign x) {
        x.setName(dto.getName())
                .setImage(dto.getImage())
                .setCampaignShortDescription(dto.getCampaignShortDescription())
                .setCampaignDescription(dto.getCampaignDescription())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setKpiSaleProduct(dto.getKpi());
        return x;
    }

    @Transactional
    @Override
    public UUID updateCampaign(CampaignBasicDto dto) throws EntityNotFoundException {

        Campaign entity = this.campaignRepository.findById(dto.getId())
                .orElseThrow(campaignNotFoundException());

        entity.setName(dto.getName())
                .setImage(dto.getImage())
                .setStartDate(dto.getStartDate())
                .setEndDate(dto.getEndDate())
                .setCampaignShortDescription(dto.getCampaignShortDescription())
                .setCampaignDescription(dto.getCampaignDescription())
                .setKpiSaleProduct(dto.getKpiSaleProduct())
                .setCampaignStatus(dto.getCampaignStatus());
        this.campaignRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    @Override
    public void deleteCampaign(UUID id) throws EntityNotFoundException {
        this.campaignRepository.findById(id)
                .map(this::updateCampaign)
                .map(MapperDTO.INSTANCE::toCampaignDto)
                .orElseThrow(campaignNotFoundException());
    }

    public void scheduleCloseCampaign() {
        List<UUID> result = this.campaignRepository
                .findAll().stream()
                .filter(campaign -> campaign.getStartDate().isBefore(LocalDateTime.now()))
                .filter(campaign -> campaign.getEndDate().isAfter(LocalDateTime.now()))
                .filter(campaign -> campaign.getCampaignStatus().equals(CampaignStatus.ACCEPTED))
                .map(Campaign::getId)
                .peek(this::completeCampaign)
                .collect(Collectors.toList());
    }

    /**
     * todo complete campaign to mapping collaborator into prize
     *
     * @param id
     */
    @Transactional
    @Override
    public void completeCampaign(UUID id) {

        //find campaign and status not finish
        Campaign campaign = this.campaignRepository.findById(id)
                .filter(_campaign -> !_campaign.getCampaignStatus().equals(CampaignStatus.FINISHED))
                .orElseThrow(handlerCampaignNotFoundException());

        //get sort collaborator by OrderRepository
        Map<UUID, Long> collaborator = new HashMap<>();

        List<UUID> idProduct = campaign.getProducts().stream().map(Product::getId).collect(Collectors.toList());

        idProduct.forEach(System.out::println);

        for (UUID productId : idProduct) {

            Map<UUID, Long> _tmp = this.orderRepository
                    .getCollaboratorAndTotalQuantitySold(productId).stream()
                    .collect(Collectors.toMap(
                            tuple -> tuple.get(OrderRepository.COLL_ID, UUID.class),
                            tuple -> tuple.get(OrderRepository.TOTAL_QUANTITY, Long.class)));
            _tmp.forEach((key, value) -> collaborator.compute(key, (k, v) -> Objects.isNull(v) ? value : v + value));
        }

        //get all [prize] -> sort campaign prize:
        List<CampaignPrize> campaignPrizes = campaign.getCampaignPrizes().stream()
                //todo sort by comparing price of prize
                .sorted(Comparator.comparing((CampaignPrize cp) -> cp.getPrize().getPrice()).reversed())
                .collect(Collectors.toList());

        campaignPrizes.stream()
                .map(CampaignPrize::getPrize)
                .forEach(System.out::println);

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

        //mapping prize by using campaign prize with greater than KPI on campaign KPI
        int count = 0;
        for (Account account : accounts) {
            LOGGER.info("IM HERE");
            account.addCampaignPrizes(campaignPrizes.get(count++));
            this.accountRepository.save(account);
        }
    }

    private Supplier<NotEnoughKpiException> handlerNotEnoughKPIException() {
        return () -> new NotEnoughKpiException(MessagesUtils.getMessage(MessageConstant.KPI_NOT_ENOUGH));
    }

    private Supplier<CampaignNotFoundException> handlerCampaignNotFoundException() {
        return () -> new CampaignNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));
    }

    private Campaign updateCampaign(Campaign x) {
        x.setCampaignStatus(CampaignStatus.DISABLE);
        this.campaignRepository.save(x);
        return x;
    }

    private Supplier<EntityNotFoundException> campaignNotFoundException() {
        return () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));
    }
}
