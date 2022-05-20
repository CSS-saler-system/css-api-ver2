package com.springframework.csscapstone.data.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class EnterpriseDto implements Serializable {
    private final UUID id;
    private final String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate dob;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;
    private final List<ProductDto> products;

    public EnterpriseDto(@JsonProperty("account_id") UUID id,
                         @JsonProperty("name") String name,
                         @JsonProperty("dob") LocalDate dob,
                         @JsonProperty("phone") String phone,
                         @JsonProperty("email") String email,
                         @JsonProperty("address") String address,
                         @JsonProperty("description") String description,
                         @JsonProperty("products") List<ProductDto> products) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.products = products;
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final Double weight;
        private final String shortDescription;
        private final String description;
        private final Long quantityInStock;
        private final Double price;
        private final Double pointSale;
        private final ProductStatus productStatus;

        @JsonCreator(mode = PROPERTIES)
        public ProductDto(
                @JsonProperty("product_id") UUID id,
                @JsonProperty("product_name") String name,
                @JsonProperty("brand") String brand,
                @JsonProperty("weight") double weight,
                @JsonProperty("short_description") String shortDescription,
                @JsonProperty("description") String description,
                @JsonProperty("in_stock") long quantityInStock,
                @JsonProperty("price") double price,
                @JsonProperty("pointSale") double pointSale,
                @JsonProperty("product_status") ProductStatus productStatus
        ) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.weight = weight;
            this.shortDescription = shortDescription;
            this.description = description;
            this.quantityInStock = quantityInStock;
            this.price = price;
            this.pointSale = pointSale;
            this.productStatus = productStatus;
        }

    }
}
