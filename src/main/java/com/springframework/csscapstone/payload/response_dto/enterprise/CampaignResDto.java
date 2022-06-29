package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.CampaignImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CampaignResDto implements Serializable {
    private final UUID campaignId;
    private final String name;
    private final List<CampaignImageBasicDto> image;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime createDate;
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
    private final List<CampaignPrizeInnerCampaignResDto> campaignPrizes;
    private final Set<ProductInnerCampaignResDto> products;

    @JsonCreator(mode = PROPERTIES)
    public CampaignResDto(
            @JsonProperty("campaignId") UUID campaignId,
            @JsonProperty("name") String name,
            @JsonProperty("image") List<CampaignImageBasicDto> image,
            @JsonProperty("createDate") LocalDateTime createDate,
            @JsonProperty("lastModifiedDate") LocalDateTime lastModifiedDate,
            @JsonProperty("startDate") LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("campaignShortDescription") String campaignShortDescription,
            @JsonProperty("campaignDescription") String campaignDescription,
            @JsonProperty("kpiSaleProduct") Long kpiSaleProduct,
            @JsonProperty("campaignStatus") CampaignStatus campaignStatus,
            @JsonProperty("campaignPrizes") List<CampaignPrizeInnerCampaignResDto> campaignPrizes,
            @JsonProperty("products") Set<ProductInnerCampaignResDto> products) {
        this.campaignId = campaignId;
        this.name = name;
        this.image = image;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.campaignStatus = campaignStatus;
        this.campaignPrizes = campaignPrizes;
        this.products = products;
    }

    @Data
    public static class CampaignPrizeInnerCampaignResDto implements Serializable {
        private final UUID id;
    }

    @Data
    public static class ProductInnerCampaignResDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final String shortDescription;
        private final String description;
        private final Long quantityInStock;
        private final Double price;
        private final Double pointSale;
        private final ProductStatus productStatus;

        public ProductInnerCampaignResDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("brand") String brand,
                @JsonProperty("shortDescription") String shortDescription,
                @JsonProperty("description") String description,
                @JsonProperty("quantityInStock") Long quantityInStock,
                @JsonProperty("price") Double price,
                @JsonProperty("pointSale") Double pointSale,
                @JsonProperty("productStatus") ProductStatus productStatus) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.shortDescription = shortDescription;
            this.description = description;
            this.quantityInStock = quantityInStock;
            this.price = price;
            this.pointSale = pointSale;
            this.productStatus = productStatus;
        }
    }
}
