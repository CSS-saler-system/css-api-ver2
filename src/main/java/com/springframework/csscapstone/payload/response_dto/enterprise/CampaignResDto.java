package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    private final UUID id;
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
    private final List<CampaignPrizeDto> campaignPrizes;
    private final Set<ProductDto> products;

    @JsonCreator(mode = PROPERTIES)
    public CampaignResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("image") List<CampaignImageBasicDto> image,
            @JsonProperty("create_date") LocalDateTime createDate,
            @JsonProperty("last_modified") LocalDateTime lastModifiedDate,
            @JsonProperty("start_date") LocalDateTime startDate,
            @JsonProperty("end_date") LocalDateTime endDate,
            @JsonProperty("short_description") String campaignShortDescription,
            @JsonProperty("description") String campaignDescription,
            @JsonProperty("kpi") Long kpiSaleProduct,
            @JsonProperty("status") CampaignStatus campaignStatus,
            @JsonProperty("prizes") List<CampaignPrizeDto> campaignPrizes,
            @JsonProperty("products") Set<ProductDto> products) {
        this.id = id;
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
    public static class CampaignPrizeDto implements Serializable {
        private final UUID id;
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final String shortDescription;
        private final String description;
        private final Long quantityInStock;
        private final Double price;
        private final Double pointSale;
        private final ProductStatus productStatus;

        public ProductDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("brand") String brand,
                @JsonProperty("short_description") String shortDescription,
                @JsonProperty("description") String description,
                @JsonProperty("quantity") Long quantityInStock,
                @JsonProperty("price") Double price,
                @JsonProperty("point_sale") Double pointSale,
                @JsonProperty("status") ProductStatus productStatus) {
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
