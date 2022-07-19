package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
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
    private final String phone;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;
    private final AccountImageBasicDto avatar;
    private final Map<String, Long> percentSoldByCategory;
    private final String address;
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
            @JsonProperty("phone") String phone,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("address") String address,
            @JsonProperty("avatar") AccountImageBasicDto avatar,
            @JsonProperty("percentSoldByCategory") Map<String, Long> percentSoldByCategory
    ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dob = dob;
        this.avatar = avatar;
        this.percentSoldByCategory = percentSoldByCategory;
        this.address = address;
    }

}
