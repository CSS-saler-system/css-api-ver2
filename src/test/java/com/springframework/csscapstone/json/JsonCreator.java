package com.springframework.csscapstone.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class JsonCreator {

    public static String jsonAccountCreatorReqDto(PasswordEncoder passwordEncoder) throws JsonProcessingException {
        AccountCreatorReqDto accountCreatorReqDto = new AccountCreatorReqDto("enterprise_3",
                LocalDate.now(), "",
                "enterprise_test_save_on_firabase@gmail.com",
                passwordEncoder.encode("123"),
                "HCMC 9 dictrict",
                "This is address",
                true,
                "Enterprise"
        );
        String jsonObject = JsonUtils.toJson(accountCreatorReqDto);
        return jsonObject;
    }
}
