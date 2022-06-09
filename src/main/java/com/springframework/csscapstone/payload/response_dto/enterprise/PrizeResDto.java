package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.springframework.csscapstone.data.status.PrizeStatus;
import com.springframework.csscapstone.payload.basic.PrizeImageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class PrizeResDto {
    private final UUID id;
    private final String name;
    private final Double price;
    private final Long quantity;
    private final String description;
    private final PrizeStatus prizeStatus;
    private final List<PrizeImageDto> prizeImages;
    private final AccountDto creator;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
    }
}
