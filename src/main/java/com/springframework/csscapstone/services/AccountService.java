package com.springframework.csscapstone.services;


import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.EnterpriseLofiginTestResDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.AccountCollaboratorUpdaterDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.EnterpriseSignUpDto;
import com.springframework.csscapstone.payload.request_dto.moderator.AccountModeratorUpdateReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorWithQuantitySoldByCategoryDto;
import com.springframework.csscapstone.payload.response_dto.moderator.AccountModeratorPageResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Optional<CollaboratorWithQuantitySoldByCategoryDto> getCollaboratorWithPerformance(UUID uuid);
    PageImplResDto<AccountResDto> getAccountDto(
            String name, String phone,
            String email, String address,
            Integer pageSize, Integer pageNumber);

    AccountResDto getById(UUID id);

    //todo for admin and moderator role create enterprise
    UUID createEnterpriseAccount(
            AccountCreatorReqDto dto,
            MultipartFile avatar,
            MultipartFile license,
            MultipartFile idCards) throws AccountExistException, AccountNotFoundException, FirebaseAuthException;

    UUID updateAccount(UUID accountId, AccountUpdaterJsonDto accountUpdaterJsonDto,
                       MultipartFile avatars,
                       MultipartFile licenses,
                       MultipartFile idCards) throws AccountInvalidException;

    boolean disableEnterprise(UUID id);
    void disableAccount(UUID id);

    PageImplResDto<AccountResDto> getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize);

    PageImplResDto<AccountResDto> getAllCollaboratorsOfEnterprise(
            UUID idEnterprise, Integer pageNumber, Integer pageSize);

    //    UUID createAccount(AccountDto dto);
    PageImplResDto<CollaboratorResDto> collaboratorsByEnterpriseIncludeNumberOfQuantitySold(
            UUID idEnterprise, Integer pageNumber, Integer pageSize);

    List<CollaboratorResDto> awardCollaboratorByCampaign(UUID campaign);

    Optional<UUID> singUpEnterprise(EnterpriseSignUpDto enterprise, MultipartFile avatar, MultipartFile licences);

    Optional<AccountResDto> getProfile(UUID accountId);

    UUID updateCollaboratorProfiles(UUID collaboratorId, AccountCollaboratorUpdaterDto accountUpdaterJsonDto, MultipartFile avatar);

    EnterpriseLofiginTestResDto getByIdSignup(UUID id);

    PageImplResDto<AccountModeratorPageResDto> pageEnterprise(Boolean isActive, Integer pageNumber, Integer pageSize);

    boolean activeEnterprise(UUID enterpriseId);

    Optional<Account> updateAccountForModerator(UUID moderatorId, AccountModeratorUpdateReqDto moderator, MultipartFile avatar);
}
