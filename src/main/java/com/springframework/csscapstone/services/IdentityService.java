package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.response_dto.AccountFromIdentityResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IdentityService {


    AccountFromIdentityResDto extractInfoIdentityCard(MultipartFile identityCard) throws IOException;
}
