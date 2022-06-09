package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.CampaignStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class CampaignUpdaterReqDto {
    private final UUID id;
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String campaignShortDescription;
    private final String campaignDescription;
    private final Long kpiSaleProduct;
    private final CampaignCreatorReqDto.AccountDto account;
    private final Set<CampaignCreatorReqDto.ProductDto> products;

    private final CampaignStatus status;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
    }

    public CampaignUpdaterReqDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("start_date") LocalDateTime startDate,
            @JsonProperty("end_date") LocalDateTime endDate,
            @JsonProperty("short_description") String campaignShortDescription,
            @JsonProperty("description") String campaignDescription,
            @JsonProperty("kpi") Long kpiSaleProduct,
            @JsonProperty("account") CampaignCreatorReqDto.AccountDto account,
            @JsonProperty("list_product") Set<CampaignCreatorReqDto.ProductDto> products,
            @JsonProperty("status") CampaignStatus status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.account = account;
        this.products = products;
        this.status = status;
    }
}