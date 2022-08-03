package com.springframework.csscapstone.controller.collaborator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.V3_PROFILE_ACCOUNT;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.V3_PROFILE_UPDATE_ACCOUNT;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Profile (Collaborator)")
@RestController
@RequiredArgsConstructor
public class CollaboratorProfileController {

    private final AccountService accountService;
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
        AccountUpdaterJsonDto accountUpdaterJsonDto = new ObjectMapper()
                .readValue(collaborator, AccountUpdaterJsonDto.class);
        UUID uuid = accountService.updateAccount(collaboratorId, accountUpdaterJsonDto, avatar, null, null);
        return ok(uuid);
    }



}
