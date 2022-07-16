package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.PrizeStatus;
import com.springframework.csscapstone.payload.basic.CampaignImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class CampaignForCollaboratorResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final List<CampaignImageBasicDto> image;
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
    private final Set<PrizeDto> prizes;
    private final Set<ProductDto> products;

    public CampaignForCollaboratorResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("image") List<CampaignImageBasicDto> image,
            @JsonProperty("startDate") LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("campaignShortDescription") String campaignShortDescription,
            @JsonProperty("campaignDescription") String campaignDescription,
            @JsonProperty("kpiSaleProduct") Long kpiSaleProduct,
            @JsonProperty("prizes") Set<PrizeDto> prizes,
            @JsonProperty("products") Set<ProductDto> products) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpiSaleProduct = kpiSaleProduct;
        this.prizes = prizes;
        this.products = products;
    }

    @Data
    public static class PrizeDto implements Serializable {
        private final UUID id;
        private final String name;
        private final Double price;
        private final String description;
        private final PrizeStatus prizeStatus;

        public PrizeDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("price") Double price,
                @JsonProperty("description") String description,
                @JsonProperty("prizeStatus") PrizeStatus prizeStatus) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.prizeStatus = prizeStatus;
        }
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final String shortDescription;
        private final Double price;
        private final Double pointSale;

        public ProductDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("brand") String brand,
                @JsonProperty("shortDescription") String shortDescription,
                @JsonProperty("price") Double price,
                @JsonProperty("pointSale") Double pointSale) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.shortDescription = shortDescription;
            this.price = price;
            this.pointSale = pointSale;
        }
    }
}
