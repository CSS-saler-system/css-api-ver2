package com.springframework.csscapstone.payload.response_dto.fpt_ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AccountFromPassportResDto {

    private final String errorCode;
    private final String errorMessage;
    private final List<DataInnerClass> data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountFromPassportResDto(
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") List<DataInnerClass> data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    @Data
    public static class DataInnerClass {
        private final String passport_number;
        private final String passport_number_prob;
        private final String name;
        private final String name_prob;
        private final String pob;
        private final String pob_prob;
        private final String sex;
        private final String sex_prob;
        private final String id_number;
        private final String id_number_prob;
        private final String doi;
        private final String doi_prob;
        private final String doe;
        private final String doe_prob;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DataInnerClass(
                @JsonProperty("passport_number") String passport_number,
                @JsonProperty("passport_number_prob") String passport_number_prob,
                @JsonProperty("name") String name,
                @JsonProperty("name_prob") String name_prob,
                @JsonProperty("pob") String pob,
                @JsonProperty("pob_prob") String pob_prob,
                @JsonProperty("sex") String sex,
                @JsonProperty("sex_prob") String sex_prob,
                @JsonProperty("id_number") String id_number,
                @JsonProperty("id_number_prob") String id_number_prob,
                @JsonProperty("doi") String doi,
                @JsonProperty("doi_prob") String doi_prob,
                @JsonProperty("doe") String doe,
                @JsonProperty("doe_prob") String doe_prob) {
            this.passport_number = passport_number;
            this.passport_number_prob = passport_number_prob;
            this.name = name;
            this.name_prob = name_prob;
            this.pob = pob;
            this.pob_prob = pob_prob;
            this.sex = sex;
            this.sex_prob = sex_prob;
            this.id_number = id_number;
            this.id_number_prob = id_number_prob;
            this.doi = doi;
            this.doi_prob = doi_prob;
            this.doe = doe;
            this.doe_prob = doe_prob;
        }
    }
}
