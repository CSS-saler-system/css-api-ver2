package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResDto implements Serializable {
    private final UUID id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private final LocalDateTime createDate;

    private final Double totalPrice;
    private final Double totalPointSale;
    private final String customerName;
    private final String deliveryPhone;
    private final String deliveryAddress;

    private final List<OrderDetailDto> orderDetails;
    private final AccountDto account;
    private final CustomerDto customer;

    @Data
    public static class OrderDetailDto implements Serializable {
        private final UUID id;
        private final String nameProduct;
        private final Double productPrice;
        private final Double productPoint;
        private final Long quantity;
        private final Double totalPointProduct;
        private final Double totalPriceProduct;
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String phone;
    }

    @Data
    public static class CustomerDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String phone;
        private final String address;

        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd")
        private final LocalDate dob;
        private final String description;
    }
}