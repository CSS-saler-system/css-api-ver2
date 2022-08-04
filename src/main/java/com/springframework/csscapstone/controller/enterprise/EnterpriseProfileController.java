package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.V2_PROFILE_UPDATE_ACCOUNT;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Profile (Enterprise)")
@RequiredArgsConstructor
@RestController
public class EnterpriseProfileController {
    private final AccountService accountService;
    private final Function<UUID, Supplier<EntityNotFoundException>> entityNotFoundExceptionSupplier =
            (enterpriseId) -> () -> new EntityNotFoundException("The enterprise with id: " + enterpriseId +
                    " was not found!!!");

    @GetMapping(V2_PROFILE_UPDATE_ACCOUNT + "/{enterpriseId}")
    public ResponseEntity<?> enterpriseGetProfile(@PathVariable("enterpriseId") UUID enterpriseId) {
        AccountResDto resultDto = this.accountService.getById(enterpriseId);

        Optional.of(resultDto)
                .filter(acc -> acc.getRole().getName().equals("Enterprise"))
                .orElseThrow(entityNotFoundExceptionSupplier.apply(enterpriseId));
        return ok(resultDto);
    }

}
