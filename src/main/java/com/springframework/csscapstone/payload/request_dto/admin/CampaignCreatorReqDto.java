package com.springframework.csscapstone.payload.request_dto.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CampaignCreatorReqDto {
    private String name;
    private String image;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String campaignShortDescription;
    private String campaignDescription;
    private Long kpi;

}
