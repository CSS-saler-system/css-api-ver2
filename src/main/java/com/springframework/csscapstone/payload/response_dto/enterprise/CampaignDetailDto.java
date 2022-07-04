package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.basic.CampaignImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CampaignDetailDto implements Serializable {
    private final UUID id;
    private final String name;
    private final List<CampaignImageBasicDto> image;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime lastModifiedDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime startDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime endDate;
    private final String campaignShortDescription;
    private final String campaignDescription;
    private final Long kpiSaleProduct;
    private final CampaignStatus campaignStatus;
    private final Set<PrizeInnerCampaignDto> prizes;
    private final Set<ProductInnerCamapaignDto> products;

    public CampaignDetailDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("image") List<CampaignImageBasicDto> image,
            @JsonProperty("lastModifiedDate") LocalDateTime lastModifiedDate,
            @JsonProperty("startDate") LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("campaignShortDescription") String campaignShortDescription,
            @JsonProperty("campaignDescription") String campaignDescription,
            @JsonProperty("kpiSaleProduct") Long kpiSaleProduct,
            @JsonProperty("campaignStatus") CampaignStatus campaignStatus,
            @JsonProperty("prizes") Set<PrizeInnerCampaignDto> prizes,
            @JsonProperty("products") Set<ProductInnerCamapaignDto> products) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.lastModifiedDate = lastModifiedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.campaignStatus = campaignStatus;
        this.prizes = prizes;
        this.products = products;
    }

    @Data
    public static class PrizeInnerCampaignDto implements Serializable {
        private final UUID id;
        private final String name;
        private final Double price;

        public PrizeInnerCampaignDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("price") Double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
    }

    @Data
    public static class ProductInnerCamapaignDto implements Serializable {
        private final UUID id;
        private final String name;
        private final Double price;
        private final Double pointSale;

        @JsonCreator(mode = PROPERTIES)
        public ProductInnerCamapaignDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("price") Double price,
                @JsonProperty("pointSale") Double pointSale) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.pointSale = pointSale;
        }
    }
}
