package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeCreatorVer2ReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import com.springframework.csscapstone.services.PrizeService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Prize.*;
import static java.util.Objects.isNull;
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

    @PostMapping(value = V2_PRIZE_CREATE)
    public ResponseEntity<?> createPrize(
            @RequestBody PrizeCreatorVer2ReqDto prizeDto
    ){
        if (isNull(prizeDto.getPrice()))
            throw new RuntimeException("The price of prize must be not null");
        UUID prize = this.prizeService.createPrize(prizeDto);
        return ok(prize);
    }

    @PutMapping(
            value = V2_PRIZE_UPDATE + "/{prizeId}")
    public ResponseEntity<?> updatePrize(
            @PathVariable("prizeId") UUID prizeId,
            @RequestBody PrizeUpdaterReqDto dto
    ) {
        UUID prize = this.prizeService.updatePrize(prizeId, dto);
        return ok(prize);
    }

    @GetMapping(V2_PRIZE_RETRIEVE + "/{prizeId}")
    public ResponseEntity<?> retrievePrizeById(@PathVariable("prizeId") UUID id) {
        return ok(this.prizeService.getPrizeByPrize(id)
                .orElseThrow(handlerEntityNotFoundException(id)));
    }

    @DeleteMapping(V2_PRIZE_DELETE + "/{prizeId}")
    public ResponseEntity<?> deletePrizeById(@PathVariable("prizeId") UUID prizeId) {
        this.prizeService.deletePrizeById(prizeId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

    private Supplier<EntityNotFoundException> handlerEntityNotFoundException(UUID id) {
        return () -> new EntityNotFoundException("The prize with id: " + id + " not found");
    }
}
