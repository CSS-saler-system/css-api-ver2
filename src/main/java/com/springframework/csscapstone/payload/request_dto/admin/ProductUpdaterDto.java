package com.springframework.csscapstone.payload.request_dto.admin;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ProductUpdaterDto {
    @NotNull(message = "The product id must be not null")
    private UUID id;

    private String name;
    private String branch;
    private String shortDescription;
    private String description;
    private Double price;
    private Double pointSale;
    private Long quantity;
}
