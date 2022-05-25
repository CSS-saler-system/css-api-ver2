package com.springframework.csscapstone.payload.response_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class AccountResponseDto implements Serializable {
    private final UUID id;
    private final String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;
    private final Boolean gender;
    private final Double point;
    private final List<AccountImageDto> avatar;
    private final RoleDto role;

    public AccountResponseDto(@JsonProperty("id") UUID id,
                              @JsonProperty("name") String name,
                              @JsonProperty("dob") LocalDate dob,
                              @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email,
                              @JsonProperty("address") String address,
                              @JsonProperty("description") String description,
                              @JsonProperty("gender") Boolean gender,
                              @JsonProperty("point") Double point,
                              @JsonProperty("avatar") List<AccountImageDto> avatar,
                              @JsonProperty("role") RoleDto role) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.gender = gender;
        this.point = point;
        this.avatar = avatar;
        this.role = role;
    }

    @Data
    public static class AccountImageDto implements Serializable {
        private final UUID id;
        private final AccountImageType type;
        private final String path;

        @JsonCreator(mode = PROPERTIES)
        public AccountImageDto(@JsonProperty("id") UUID id,
                               @JsonProperty("image_type") AccountImageType type,
                               @JsonProperty("path") String path) {
            this.id = id;
            this.type = type;
            this.path = path;
        }
    }

    @Data
    public static class RoleDto implements Serializable {
        private final String id;
        private final String name;
        @JsonCreator(mode = PROPERTIES)
        public RoleDto(@JsonProperty("id") String id, @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }
    }
}
