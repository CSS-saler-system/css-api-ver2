package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
import com.springframework.csscapstone.services.TransactionServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Transaction.V4_TRANSACTION_HANDLER;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Transaction.V4_TRANSACTION_LIST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Transaction (Moderator)")
public class ModeratorTransactionController {
    private final TransactionServices transactionServices;

    @GetMapping(V4_TRANSACTION_LIST + "/{idEnterprise}")
    public ResponseEntity<?> listTransaction(
            @PathVariable("idEnterprise") UUID idEnterprise,
            @RequestParam(value = "start_date", required = false, defaultValue = "06/08/1999 00:00:00")
            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = "MM-dd-yyyy hh:mm:ss",
                    timezone = "America/New_York")
            LocalDateTime createDate,
            @RequestParam(value = "modified_date", required = false, defaultValue = "06/08/2030 00:00:00")
            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = "MM-dd-yyyy hh:mm:ss",
                    timezone = "America/New_York")
            LocalDateTime modifiedDate,
            @RequestParam(value = "page_number", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "1") Integer pageSize
    ) {
        PageImplResDto<TransactionsResDto> page = transactionServices
                .getAllTransaction(idEnterprise, createDate, modifiedDate, pageNumber, pageSize);
        return ok(page);
    }

    @PutMapping(V4_TRANSACTION_HANDLER + "/{idTransaction}")
    public ResponseEntity<?> handledTransaction(
            @PathVariable("idTransaction") UUID idTransaction
    ) {
        UUID uuid = this.transactionServices.acceptedTransaction(idTransaction);
        return ok(uuid);
    }

}
