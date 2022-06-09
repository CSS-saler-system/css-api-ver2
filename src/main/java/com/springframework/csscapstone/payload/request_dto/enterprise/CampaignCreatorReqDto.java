package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class CampaignCreatorReqDto implements Serializable {
    private final String name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String campaignShortDescription;
    private final String campaignDescription;
    private final Long kpiSaleProduct;
    private final AccountDto account;
    private final Set<ProductDto> products;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
    }

    public CampaignCreatorReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("start_date") LocalDateTime startDate,
            @JsonProperty("end_date") LocalDateTime endDate,
            @JsonProperty("short_description") String campaignShortDescription,
            @JsonProperty("description") String campaignDescription,
            @JsonProperty("kpi") Long kpiSaleProduct,
            @JsonProperty("account") AccountDto account,
            @JsonProperty("list_product") Set<ProductDto> products) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.account = account;
        this.products = products;
    }
}
