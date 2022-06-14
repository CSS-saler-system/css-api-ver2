package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
import com.springframework.csscapstone.services.TransactionServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Transaction.V4_TRANSACTION_LIST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Transaction (Moderator)")
public class ModeratorTransactionController {
    private final TransactionServices transactionServices;

    @GetMapping(V4_TRANSACTION_LIST)
    public ResponseEntity<?> listTransaction(
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
            @RequestParam(value = "page_number", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "page_size", required = false, defaultValue = "1") Integer pageSize
    ) {
        PageImplResDto<TransactionsResDto> page = transactionServices.getAllTransaction(createDate, modifiedDate, pageNumber, pageSize);
        return ok(page);
    }

}
