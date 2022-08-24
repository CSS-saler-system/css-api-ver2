package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromDriverLicencesResDto;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromIdentityResDto;
import com.springframework.csscapstone.payload.response_dto.fpt_ai.AccountFromPassportResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface IdentityService {


    CompletableFuture<AccountFromIdentityResDto> extractInfoIdentityCard(MultipartFile identityCard) throws IOException;
    CompletableFuture<AccountFromDriverLicencesResDto> extractInfoDriveLicenseCard(MultipartFile identityCard) throws IOException;

    CompletableFuture<AccountFromPassportResDto> extractInfoPassport(MultipartFile identityCard) throws IOException;
}
