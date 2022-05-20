package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Data
    public static class AccountImageDto implements Serializable {
        private final UUID id;
        private final AccountImageType type;
        private final String path;
    }
}
