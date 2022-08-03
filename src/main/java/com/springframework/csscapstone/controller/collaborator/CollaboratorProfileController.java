package com.springframework.csscapstone.controller.collaborator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.collaborator.AccountCollaboratorUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.V3_PROFILE_ACCOUNT;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.V3_PROFILE_UPDATE_ACCOUNT;
import static java.util.Objects.isNull;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Profile (Collaborator)")
@RestController
@RequiredArgsConstructor
public class CollaboratorProfileController {

    private final AccountService accountService;

    private final Predicate<String> checkStringNull = StringUtils::isEmpty;
    Function<UUID, Supplier<RuntimeException>> COLLABORATOR_NOT_FOUND =
            (id) -> () -> new RuntimeException("The collaborator with id: " + id + " was not found");

    @GetMapping(V3_PROFILE_ACCOUNT + "/{collaboratorId}")
    public ResponseEntity<?> getProfile(@PathVariable("collaboratorId") UUID collaboratorId) {
        AccountResDto collaborator = accountService
                .getProfile(collaboratorId)
                .filter(acc -> acc.getRole().getName().equals("Collaborator"))
                .orElseThrow(COLLABORATOR_NOT_FOUND.apply(collaboratorId));
        return ok(collaborator);
    }

    @PutMapping(value = V3_PROFILE_UPDATE_ACCOUNT + "/{collaboratorId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @PathVariable("collaboratorId") UUID collaboratorId,
            @RequestPart("collaborator") String collaborator,
            @RequestPart("avatar") MultipartFile avatar) throws JsonProcessingException {
        AccountCollaboratorUpdaterDto accountUpdaterJsonDto = new ObjectMapper()
                .readValue(collaborator, AccountCollaboratorUpdaterDto.class);

        if (checkStringNull.test(accountUpdaterJsonDto.getAddress()))
            throw new RuntimeException("The address must not be null!!!");

        if (checkStringNull.test(accountUpdaterJsonDto.getEmail()))
            throw new RuntimeException("The email must not be null!!!");

        if (checkStringNull.test(accountUpdaterJsonDto.getName()))
            throw new RuntimeException("The name must not be null!!!");

        if (isNull(accountUpdaterJsonDto.getGender()))
            throw new RuntimeException("The gender must not be null!!!");

        if (isNull(accountUpdaterJsonDto.getDob()))
            throw new RuntimeException("The dob must not be null!!!");

        UUID uuid = accountService.updateCollaboratorProfiles(collaboratorId, accountUpdaterJsonDto, avatar);
        return ok(uuid);
    }




}
