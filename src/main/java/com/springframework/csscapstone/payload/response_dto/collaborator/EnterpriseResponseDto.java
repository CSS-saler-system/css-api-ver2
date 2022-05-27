package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class EnterpriseResponseDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;

    private final List<AccountImageDto> avatar;

    public EnterpriseResponseDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("avatars") List<AccountImageDto> avatar) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.dob = dob;
        this.avatar = avatar;
    }

    @Data
    public static class AccountImageDto implements Serializable {
        private final UUID id;
        private final AccountImageType type;
        private final String path;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AccountImageDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("account_type") AccountImageType type,
                @JsonProperty("path") String path) {
            this.id = id;
            this.type = type;
            this.path = path;
        }
    }

    @Override
    public String toString() {
        return "EnterpriseResponseDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", dob=" + dob +
                ", avatar=" + avatar +
                '}';
    }
}