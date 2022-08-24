package com.springframework.csscapstone.payload.response_dto.fpt_ai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AccountFromDriverLicencesResDto implements Serializable {
    private final String errorCode;
    private final String errorMessage;
    private final List<DataInnerClass> data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountFromDriverLicencesResDto(
            @JsonProperty("errorCode") String errorCode,
            @JsonProperty("errorMessage") String errorMessage,
            @JsonProperty("data") List<DataInnerClass> data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    @Data
    public static class DataInnerClass implements Serializable {
        private final String id;
        private final String id_prob;
        private final String name;
        private final String name_prob;
        private final String dob;
        private final String dob_prob;
        private final String nation;
        private final String nation_prob;
        private final String address;
        private final String address_prob;
        private final String place_issue;
        private final String place_issue_prob;
        private final String date;
        private final String date_prob;
        private final String classType;
        private final String class_prob;
        private final String doe;
        private final String doe_prob;
        private final String overall_score;
        private final String type;
        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DataInnerClass(
                @JsonProperty("id") String id,
                @JsonProperty("id_prob") String id_prob,
                @JsonProperty("name") String name,
                @JsonProperty("name_prob") String name_prob,
                @JsonProperty("dob") String dob,
                @JsonProperty("dob_prob") String dob_prob,
                @JsonProperty("nation") String nation,
                @JsonProperty("nation_prob") String nation_prob,
                @JsonProperty("address") String address,
                @JsonProperty("address_prob") String address_prob,
                @JsonProperty("place_issue") String place_issue,
                @JsonProperty("place_issue_prob") String place_issue_prob,
                @JsonProperty("date") String date,
                @JsonProperty("date_prob") String date_prob,
                @JsonProperty("class") String classType,
                @JsonProperty("class_prob") String class_prob,
                @JsonProperty("doe") String doe,
                @JsonProperty("doe_prob") String doe_prob,
                @JsonProperty("overall_score") String overall_score,
                @JsonProperty("type") String type) {
            this.id = id;
            this.id_prob = id_prob;
            this.name = name;
            this.name_prob = name_prob;
            this.dob = dob;
            this.dob_prob = dob_prob;
            this.nation = nation;
            this.nation_prob = nation_prob;
            this.address = address;
            this.address_prob = address_prob;
            this.place_issue = place_issue;
            this.place_issue_prob = place_issue_prob;
            this.date = date;
            this.date_prob = date_prob;
            this.classType = classType;
            this.class_prob = class_prob;
            this.doe = doe;
            this.doe_prob = doe_prob;
            this.overall_score = overall_score;
            this.type = type;
        }
    }
}
