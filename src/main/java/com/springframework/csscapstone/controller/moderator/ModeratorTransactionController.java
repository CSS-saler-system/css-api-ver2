package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springframework.csscapstone.payload.request_dto.moderator.TransactionHandler;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsDto;
import com.springframework.csscapstone.services.TransactionServices;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Transaction.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Transaction (Moderator)")
public class ModeratorTransactionController {
    private final TransactionServices transactionServices;

    @GetMapping(V4_TRANSACTION_PENDING)
    public ResponseEntity<?> listTransactionPendingList(
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<TransactionsDto> result = this.transactionServices
                .getAllPendingStatusList(pageNumber, pageSize);
        return ok(result);
    }

    @GetMapping(V4_TRANSACTION_GET + "/{transactionId}")
    public ResponseEntity<TransactionsDto> getTransactionById(@PathVariable("transactionId") UUID transactionId){
        Optional<TransactionsDto> transactionById = this.transactionServices.getTransactionById(transactionId);
        TransactionsDto result = transactionById.orElseThrow(() -> new EntityNotFoundException("The transaction with id: " + transactionById + " was not found"));
        return ok(result);
    }

    @GetMapping(V4_TRANSACTION_LIST + "/{idEnterprise}")
    public ResponseEntity<?> listTransaction(
            @PathVariable("idEnterprise") UUID idEnterprise,
            @RequestParam(value = "createDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createDate,
            @RequestParam(value = "modifiedDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime modifiedDate,
            @RequestParam(value = "page_number", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", required = false) Integer pageSize
    ) {
        PageImplResDto<TransactionsDto> page = transactionServices
                .getAllTransaction(idEnterprise, createDate, modifiedDate, pageNumber, pageSize);
        return ok(page);
    }

    @PutMapping(V4_TRANSACTION_HANDLER + "/{idTransaction}")
    public ResponseEntity<?> handledTransaction(
//            @PathVariable("idTransaction") UUID idTransaction
            @RequestBody @Valid TransactionHandler transactionHanlder
            ) {
        UUID uuid = this.transactionServices.acceptedTransaction(transactionHanlder);
        return ok(uuid);
    }

}
