package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.domain.CampaignDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CampaignUpdaterReqDto {
    private final String name;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endDate;
    private final String campaignShortDescription;
    private final String campaignDescription;
    private final Long kpiSaleProduct;
    private final Set<PrizeInnerCampaignDto> prizes;
    private final AccountInnerCampaignDto account;
    private final Set<ProductInnerCampaignDto> products;

    @Data
    public static class PrizeInnerCampaignDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public PrizeInnerCampaignDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }

    @Data
    public static class AccountInnerCampaignDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public AccountInnerCampaignDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }

    @Data
    public static class ProductInnerCampaignDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public ProductInnerCampaignDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }

    public CampaignUpdaterReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("startDate") LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("campaignShortDescription") String campaignShortDescription,
            @JsonProperty("campaignDescription") String campaignDescription,
            @JsonProperty("kpiSaleProduct") Long kpiSaleProduct,
            @JsonProperty("prizes") Set<PrizeInnerCampaignDto> prizes,
            @JsonProperty("account") AccountInnerCampaignDto account,
            @JsonProperty("products") Set<ProductInnerCampaignDto> products) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.prizes = prizes;
        this.account = account;
        this.products = products;
    }
}