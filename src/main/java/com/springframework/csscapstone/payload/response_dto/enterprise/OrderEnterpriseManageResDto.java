package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderEnterpriseManageResDto implements Serializable {
    private final UUID orderId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime createDate;
    private final Double totalPrice;

    private final OrderStatus status;
    private final AccountInnerOrderDto account;
    private final CustomerInnerOrderDto customer;

    public OrderEnterpriseManageResDto(
            @JsonProperty("orderId") UUID orderId,
            @JsonProperty("createDate") LocalDateTime createDate,
            @JsonProperty("totalPrice") Double totalPrice,
            @JsonProperty("status") OrderStatus status,
            @JsonProperty("account") AccountInnerOrderDto account,
            @JsonProperty("customer") CustomerInnerOrderDto customer) {
        this.orderId = orderId;
        this.createDate = createDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.account = account;
        this.customer = customer;
    }

    @Data
    public static class AccountInnerOrderDto implements Serializable {
        private final UUID accountId;
        private final String name;

        public AccountInnerOrderDto(@JsonProperty("accountId") UUID accountId, @JsonProperty("name") String name) {
            this.accountId = accountId;
            this.name = name;
        }
    }

    @Data
    public static class CustomerInnerOrderDto implements Serializable {
        private final UUID customerId;
        private final String name;
        private final String phone;

        public CustomerInnerOrderDto(
                @JsonProperty("customerId") UUID customerId,
                @JsonProperty("name") String name, @JsonProperty("phone") String phone) {
            this.customerId = customerId;
            this.name = name;
            this.phone = phone;
        }
    }
}
