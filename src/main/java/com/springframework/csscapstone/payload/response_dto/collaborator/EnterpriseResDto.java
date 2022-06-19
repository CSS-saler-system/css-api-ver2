package com.springframework.csscapstone.payload.response_dto.collaborator;

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
import java.util.UUID;

@Data
public class EnterpriseResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate dob;

    private final AccountImageDto avatar;
    private final AccountImageDto license;
    private final AccountImageDto idCard;

    public EnterpriseResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("avatars") AccountImageDto avatar,
            @JsonProperty("licenses") AccountImageDto license,
            @JsonProperty("idCard") AccountImageDto idCard) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.dob = dob;
        this.avatar = avatar;
        this.license = license;
        this.idCard = idCard;
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
