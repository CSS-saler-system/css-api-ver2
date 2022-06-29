package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CampaignCreatorReqDto {
    private String name;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime endDate;
    private String campaignShortDescription;
    private String campaignDescription;

    @Min(1)
    private Long kpi;

    private final List<PrizeInnerCampaignCreatorDto> prizeInnerCampaignCreatorDto;
    private final AccountInnerCampaignCreatorDto enterprise;
    private final Set<ProductInnerCampaignCreatorDto> products;

    @Data
    public static class AccountInnerCampaignCreatorDto implements Serializable {
        private final UUID enterpriseId;

        public AccountInnerCampaignCreatorDto(UUID enterpriseId) {
            this.enterpriseId = enterpriseId;
        }
    }

    @Data
    public static class PrizeInnerCampaignCreatorDto implements Serializable {
        private final UUID prizeId;

        public PrizeInnerCampaignCreatorDto(UUID prizeId) {
            this.prizeId = prizeId;
        }
    }


    @Data
    public static class ProductInnerCampaignCreatorDto implements Serializable {
        private final UUID productId;

        public ProductInnerCampaignCreatorDto(UUID productId) {
            this.productId = productId;
        }
    }

    @JsonCreator(mode = PROPERTIES)
    public CampaignCreatorReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("startDate") LocalDateTime startDate,
            @JsonProperty("endDate") LocalDateTime endDate,
            @JsonProperty("campaignShortDescription") String campaignShortDescription,
            @JsonProperty("campaignDescription") String campaignDescription,
            @JsonProperty("kpi") Long kpi,
            @JsonProperty("prizeDto") List<PrizeInnerCampaignCreatorDto> prizeInnerCampaignCreatorDto,
            @JsonProperty("enterprise") AccountInnerCampaignCreatorDto enterprise,
            @JsonProperty("products") Set<ProductInnerCampaignCreatorDto> products) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignShortDescription = campaignShortDescription;
        this.campaignDescription = campaignDescription;
        this.kpi = kpi;
        this.prizeInnerCampaignCreatorDto = prizeInnerCampaignCreatorDto;
        this.enterprise = enterprise;
        this.products = products;
    }
}
