package com.springframework.csscapstone.data.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestSellingProductEnterpriseManagerDto implements Serializable {
    private final UUID requestId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")

    private final LocalDateTime dateTimeRequest;
    private final RequestStatus requestStatus;
    private final ProductInnerRequestDto product;
    private final AccountInnerRequestDto account;

    public RequestSellingProductEnterpriseManagerDto(
            @JsonProperty("requestId") UUID requestId,
            @JsonProperty("dateTimeRequest") LocalDateTime dateTimeRequest,
            @JsonProperty("requestStatus") RequestStatus requestStatus,
            @JsonProperty("product") ProductInnerRequestDto product,
            @JsonProperty("account") AccountInnerRequestDto account) {
        this.requestId = requestId;
        this.dateTimeRequest = dateTimeRequest;
        this.requestStatus = requestStatus;
        this.product = product;
        this.account = account;
    }

    @Data
    public static class ProductInnerRequestDto implements Serializable {
        private final UUID productID;
        private final String productName;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ProductInnerRequestDto(
                @JsonProperty("productID") UUID productID,
                @JsonProperty("productName") String productName) {
            this.productID = productID;
            this.productName = productName;
        }
    }

    @Data
    public static class AccountInnerRequestDto implements Serializable {
        private final UUID collaboratorId;
        private final String collaboratorName;
        private final String collaboratorPhone;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AccountInnerRequestDto(
                @JsonProperty("collaboratorId") UUID collaboratorId,
                @JsonProperty("collaboratorName") String collaboratorName,
                @JsonProperty("collaboratorPhone") String collaboratorPhone) {
            this.collaboratorId = collaboratorId;
            this.collaboratorName = collaboratorName;
            this.collaboratorPhone = collaboratorPhone;
        }
    }
}
