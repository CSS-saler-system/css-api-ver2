package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CollaboratorWithQuantitySoldByCategoryDto implements Serializable {
    private final UUID id;
    private final String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate dob;
    private final String phone;
    private final String address;
    private final String description;
    private final Boolean gender;
    private final Double point;
    private final AccountImageBasicDto avatar;
    private final AccountImageBasicDto licenses;
    private final AccountImageBasicDto idCard;

//    private final Map<CategoryInnerResDto, Long> percentSoldByCategory;
    private final Map<Category, Long> percentSoldByCategory;

    @Data
    public static class CategoryInnerResDto {
        private final String name;

        public CategoryInnerResDto(@JsonProperty("categoryName") String name) {
            this.name = name;
        }
    }

    @JsonCreator(mode = PROPERTIES)
    public CollaboratorWithQuantitySoldByCategoryDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("gender") Boolean gender,
            @JsonProperty("point") Double point,
            @JsonProperty("avatar") AccountImageBasicDto avatar,
            @JsonProperty("license") AccountImageBasicDto license,
            @JsonProperty("idCard") AccountImageBasicDto idCard,
            @JsonProperty("percentSoldByCategory") Map<Category, Long> percentSoldByCategory) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.gender = gender;
        this.point = point;
        this.avatar = avatar;
        this.licenses = license;
        this.idCard = idCard;
        this.percentSoldByCategory = percentSoldByCategory;
    }
}
