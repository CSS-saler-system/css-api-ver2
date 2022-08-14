package com.springframework.csscapstone.controller.collaborator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsDto;
import com.springframework.csscapstone.services.TransactionServices;
import com.springframework.csscapstone.utils.exception_utils.transaction_exceptions.TransactionNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Transaction.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Transaction (Collaborator)")
@RestController
@RequiredArgsConstructor
public class CollaboratorTransactionController {

    private final TransactionServices transactionServices;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping(
            value = V3_TRANSACTION_CREATE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createTransaction(
            @RequestPart("transaction") String transactionCreatorDto,
            @RequestPart(value = "image", required = false) List<MultipartFile> images) throws JsonProcessingException {
        TransactionsCreatorReqDto dto = objectMapper.readValue(transactionCreatorDto, TransactionsCreatorReqDto.class);
        UUID transaction = this.transactionServices.createTransaction(dto, images);
        return ok(transaction);
    }

    @GetMapping(V3_TRANSACTION_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getAllTransactionExcludeDisableStatus(
            @PathVariable("enterpriseId") UUID idEnterprise,
            @RequestParam(value = "createDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createDate,
            @RequestParam(value = "modifiedDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime modifiedDate,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ok(transactionServices.getAllTransaction(idEnterprise, createDate, modifiedDate, pageNumber, pageSize));
    }

    @GetMapping(V3_TRANSACTION_GET + "/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID transactionId) {
        Optional<TransactionsDto> transactionById = this.transactionServices.getTransactionById(transactionId);
        TransactionsDto transactionsResDto = transactionById.orElseThrow(
                () -> new TransactionNotFoundException("The transaction with transactionId: " + transactionId + " not found"));
        return ok(transactionsResDto);
    }

    @PutMapping(
            value = V3_TRANSACTION_UPDATE + "/{transactionId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateTransaction(
            @PathVariable("transactionId") UUID transactionId,
            @RequestPart(value = "billImages", required = false) List<MultipartFile> images,
            @RequestPart(value = "updateTransaction", required = false) String dto
    ) throws JsonProcessingException {
        TransactionsUpdateReqDto transactionsUpdateReqDto = objectMapper
                .readValue(dto, TransactionsUpdateReqDto.class);
        UUID uuid = this.transactionServices.updateTransaction(
                transactionId, transactionsUpdateReqDto, images);
        return ok(uuid);
    }

    @DeleteMapping(V3_TRANSACTION_DELETE + "/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") UUID transactionId) {
        this.transactionServices.deleteTransaction(transactionId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }


}
