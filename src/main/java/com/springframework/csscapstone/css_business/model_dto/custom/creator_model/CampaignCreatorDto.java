package com.springframework.csscapstone.css_business.model_dto.custom.creator_model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CampaignCreatorDto {
    private String name;
    private String image;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String campaignShortDescription;
    private String campaignDescription;
    private Long kpi;

}
