package com.springframework.csscapstone.services.model_dto.basic;

import com.springframework.csscapstone.data.status.CampaignStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CampaignDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String image;
    private final LocalDateTime createDate;
    private final LocalDateTime lastModifiedDate;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String campaignShortDescription;
    private final String campaignDescription;
    private final Long kpiSaleProduct;
    private final CampaignStatus campaignStatus;
}
