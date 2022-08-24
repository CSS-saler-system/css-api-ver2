package com.springframework.csscapstone.payload.response_dto.fpt_ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountFromIdentityResDto implements Serializable {

    private final String errorCode;
    private final String errorMessage;
    private final List<DataInner> data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountFromIdentityResDto(
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") List<DataInner> data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    @Data
    public static class DataInner implements Serializable {
        public final String id;
        public final String id_prob;
        public final String name;
        public final String name_prob;
        public final String dob;
        public final String dob_prob;
        public final String sex;
        public final String sex_prob;
        public final String nationality;
        public final String nationality_prob;
        public final String type_new;
        public final String doe;
        public final String doe_prob;
        public final String home;
        public final String home_prob;
        public final String address;
        public final String address_prob;
        public final AddressEntityInner address_entities;
        public final String overall_score;
        public final String type;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DataInner(
                @JsonProperty("id") String id,
                @JsonProperty("id_prob") String id_prob,
                @JsonProperty("name") String name,
                @JsonProperty("name_prob") String name_prob,
                @JsonProperty("dob") String dob,
                @JsonProperty("dob_prob") String dob_prob,
                @JsonProperty("sex") String sex,
                @JsonProperty("sex_prob") String sex_prob,
                @JsonProperty("nationality") String nationality,
                @JsonProperty("nationality_prob") String nationality_prob,
                @JsonProperty("type_new") String type_new,
                @JsonProperty("doe") String doe,
                @JsonProperty("doe_prob") String doe_prob,
                @JsonProperty("home") String home,
                @JsonProperty("home_prob") String home_prob,
                @JsonProperty("address") String address,
                @JsonProperty("address_prob") String address_prob,
                @JsonProperty("address_entities") AddressEntityInner address_entities,
                @JsonProperty("overall_score") String overall_score,
                @JsonProperty("type") String type) {
            this.id = id;
            this.id_prob = id_prob;
            this.name = name;
            this.name_prob = name_prob;
            this.dob = dob;
            this.dob_prob = dob_prob;
            this.sex = sex;
            this.sex_prob = sex_prob;
            this.nationality = nationality;
            this.nationality_prob = nationality_prob;
            this.type_new = type_new;
            this.doe = doe;
            this.doe_prob = doe_prob;
            this.home = home;
            this.home_prob = home_prob;
            this.address = address;
            this.address_prob = address_prob;
            this.address_entities = address_entities;
            this.overall_score = overall_score;
            this.type = type;
        }

        @Data
        public static class AddressEntityInner implements Serializable {
            public final String province;
            public final String district;
            public final String ward;
            public final String street;

            @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
            public AddressEntityInner(
                    @JsonProperty("province") String province,
                    @JsonProperty("district") String district,
                    @JsonProperty("ward") String ward,
                    @JsonProperty("street") String street) {
                this.province = province;
                this.district = district;
                this.ward = ward;
                this.street = street;
            }
        }

    }
}
