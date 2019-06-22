package com.session.demo.demo.controller;

import com.session.demo.demo.dto.internalDTO.FundTransactionCreatedDTO;
import com.session.demo.demo.helper.FundTransactionConverter;
import com.session.demo.demo.helper.enums.FundTransactionTypeEnum;
import com.session.demo.demo.service.FundTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/fund-transaction")
public class FundTransactionController {


    @Autowired
    private FundTransactionService fundTransactionService;

    @PostMapping("/{transactionType}/{accountId}/{amount}")
    public ResponseEntity<FundTransactionCreatedDTO> postTransaction(@Validated
                                          @PathVariable @NotBlank FundTransactionTypeEnum transactionType,
                                                                     @PathVariable @NotBlank String accountId,
                                                                     @PathVariable @NotBlank
                                          @Range(min = 0L, message = "Please select positive numbers Only")
                                          @DecimalMin(value = "0.01", message = "Amount must be positive and greater than 0")
                                          @DecimalMax(value = "9999999999999.99", message = "Amount must be less than 9999999999999.99, unless you are very rich lets meet up") BigDecimal amount) {
        log.info("incoming transaction for {} from account no : {}, with amount : {}", transactionType, accountId, amount);

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.createTransaction(transactionType, accountId, amount);
        return ResponseEntity.ok(fundTransactionCreatedDTO);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(FundTransactionTypeEnum.class, new FundTransactionConverter());
    }
}
