package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.data.domain.Campaign_;
import com.springframework.csscapstone.data.repositories.CampaignRepository;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.payload.basic.CampaignDto;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;
    private final EntityManager em;

    @Cacheable(cacheNames = "campaigns")
    @Override
    public List<CampaignDto> findCampaign(String name, LocalDateTime createdDate,
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
    public CampaignDto findById(UUID id) throws EntityNotFoundException {
        return campaignRepository
                .findById(id).map(MapperDTO.INSTANCE::toCampaignDto)
                .orElseThrow(campaignNotFoundException());
    }


    @Transactional
    @Override
    public UUID createCampaign(CampaignCreatorDto dto) throws CampaignInvalidException {
        Campaign campaign = Optional.of(new Campaign())
                .map(x -> toCampaignEntity(dto, x))
                .map(this.campaignRepository::save)
                .orElseThrow(() -> new CampaignInvalidException(
                        MessagesUtils.getMessage(MessageConstant.Campaign.INVALID)
                ));
        return campaign.getId();
    }

    private Campaign toCampaignEntity(CampaignCreatorDto dto, Campaign x) {
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
    public UUID updateCampaign(CampaignDto dto) throws EntityNotFoundException {

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

    private Campaign updateCampaign(Campaign x) {
        x.setCampaignStatus(CampaignStatus.DISABLE);
        this.campaignRepository.save(x);
        return x;
    }

    private Supplier<EntityNotFoundException> campaignNotFoundException() {
        return () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Campaign.NOT_FOUND));
    }
}
