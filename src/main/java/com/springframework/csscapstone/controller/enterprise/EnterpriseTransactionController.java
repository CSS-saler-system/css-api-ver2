package com.springframework.csscapstone.controller.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
import com.springframework.csscapstone.services.TransactionServices;
import com.springframework.csscapstone.utils.exception_utils.transaction_exceptions.TransactionNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Transaction.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Transaction (Enterprise)")
public class EnterpriseTransactionController {
    private final TransactionServices transactionServices;
    private final ObjectMapper objectMapper;

    @PostMapping(V2_TRANSACTION_CREATE)
    public ResponseEntity<?> createTransaction(
            @RequestPart("transaction") String transactionCreatorDto,
            @RequestPart("image") List<MultipartFile> images
    ) throws JsonProcessingException {
        TransactionsCreatorReqDto dto = objectMapper.readValue(transactionCreatorDto, TransactionsCreatorReqDto.class);
        UUID transaction = this.transactionServices.createTransaction(dto, images);
        return ok(transaction);
    }

    @GetMapping(V2_TRANSACTION_LIST)
    public ResponseEntity<?> getAllTransactionExcludeDisableStatus(
            @RequestParam(value = "start_date", required = false, defaultValue = "06/08/1999 00:00:00")
            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = "MM-dd-yyyy hh:mm:ss",
                    timezone = "America/New_York")
            LocalDateTime createDate,
            @RequestParam(value = "modified_date", required = false, defaultValue = "06/08/1999 00:00:00")
            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = "MM-dd-yyyy hh:mm:ss",
                    timezone = "America/New_York")
            LocalDateTime modifiedDate,
            @RequestParam(value = "page_number", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", required = false) Integer pageSize) {
        return ok(transactionServices.getAllTransaction(createDate,
                modifiedDate, pageNumber, pageSize));
    }

    @GetMapping(V2_TRANSACTION_GET + "/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable UUID id) {
        Optional<TransactionsResDto> transactionById = this.transactionServices.getTransactionById(id);
        TransactionsResDto transactionsResDto = transactionById.orElseThrow(
                () -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));
        return ok(transactionsResDto);
    }

    @PutMapping(value = V2_TRANSACTION_UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateTransaction(
            @RequestPart("bill_images") List<MultipartFile> images,
            @RequestPart("update_transaction") String dto
            ) throws JsonProcessingException {
        TransactionsUpdateReqDto transactionsUpdateReqDto = objectMapper
                .readValue(dto, TransactionsUpdateReqDto.class);
        if (Objects.isNull(transactionsUpdateReqDto.getId()))
            throw new RuntimeException("The Json's id field is empty");
        UUID uuid = this.transactionServices.updateTransaction(transactionsUpdateReqDto, images);
        return ok(uuid);
    }

    @PutMapping(V2_TRANSACTION_REJECT + "/{id}")
    public ResponseEntity<?> rejectTransaction(@PathVariable("id") UUID id) {
        UUID uuid = this.transactionServices.rejectTransaction(id);
        return ok(uuid);
    }

    @DeleteMapping(V2_TRANSACTION_DELETE + "/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") UUID id) {
        this.transactionServices.deleteTransaction(id);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

}
