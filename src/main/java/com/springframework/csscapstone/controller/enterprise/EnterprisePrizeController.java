package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import com.springframework.csscapstone.services.PrizeService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Prize.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Prize (Enterprise)")
public class EnterprisePrizeController {
    private final PrizeService prizeService;

    //
    @GetMapping(V2_PRIZE_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getAllPrize(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "namePrize", required = false) String namePrize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ok(this.prizeService.getAll(enterpriseId, namePrize, pageNumber, pageSize));
    }

    @PostMapping(value = V2_PRIZE_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPrize(
            @RequestParam("name") @Valid @NotNull String prizeName,
            @RequestParam("price") @Valid @NotNull Double price,
            @RequestParam("price") @Valid @NotNull UUID accountId) {

        if (price == null || price < 1.0)
            throw new RuntimeException("The price of prize is not null");

        UUID prize = this.prizeService.createPrize(
                new PrizeCreatorReqDto(prizeName, price,
                        new PrizeCreatorReqDto.AccountDto(accountId)));

        return ok(prize);
    }

    @PutMapping(
            value = V2_PRIZE_UPDATE + "/{prizeId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePrize(
            @PathVariable("prizeId") UUID prizeId,
            @RequestParam("prizeName") String prizeName,
            @RequestParam("price") Double price
    ) {

        if (price == null || price < 1.0)
            throw new RuntimeException("The price of prize is not null");

        UUID prize = this.prizeService.updatePrize(new PrizeUpdaterReqDto(prizeId, prizeName, price));

        return ok(prize);
    }

    @GetMapping(V2_PRIZE_RETRIEVE + "/{prizeId}")
    public ResponseEntity<?> retrievePrizeById(@PathVariable("prizeId") UUID id) {
        return ok(this.prizeService.getPrizeByPrize(id)
                .orElseThrow(handlerEntityNotFoundException(id)));
    }

    private Supplier<EntityNotFoundException> handlerEntityNotFoundException(UUID id) {
        return () -> new EntityNotFoundException("The prize with id: " + id + " not found");
    }
}
