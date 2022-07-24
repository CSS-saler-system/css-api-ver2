package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class PrizeCreatorVer2ReqDto implements Serializable {
    @NotEmpty
    private final String name;
    @NotNull
    private final Double price;
    @NotNull
    private final AccountInnerPrizeCreatorReqDto creator;


    @JsonCreator(mode = PROPERTIES)
    public PrizeCreatorVer2ReqDto(String name, Double price, AccountInnerPrizeCreatorReqDto creator) {
        this.name = name;
        this.price = price;
        this.creator = creator;
    }

    @Data
    public static class AccountInnerPrizeCreatorReqDto implements Serializable {
        @NotNull
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public AccountInnerPrizeCreatorReqDto(UUID id) {
            this.id = id;
        }
    }
}
