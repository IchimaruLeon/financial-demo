package com.session.demo.demo.controller;

import com.session.demo.demo.dto.internalDTO.FundTransactionCreatedDTO;
import com.session.demo.demo.handler.ResponseModel;
import com.session.demo.demo.helper.FundTransactionConverter;
import com.session.demo.demo.helper.enums.FundTransactionDirectEnum;
import com.session.demo.demo.helper.enums.FundTransactionUnDirectEnum;
import com.session.demo.demo.service.FundTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<ResponseModel<FundTransactionCreatedDTO>> postTransaction(@Validated
                                          @PathVariable @NotBlank FundTransactionDirectEnum transactionType,
                                                         @PathVariable @NotBlank String accountId,
                                                         @PathVariable @NotBlank
                                          @Range(min = 0L, message = "Please select positive numbers Only")
                                          @DecimalMin(value = "0.01", message = "Amount must be positive and greater than 0")
                                          @DecimalMax(value = "9999999999999.99", message = "Amount must be less than 9999999999999.99, unless you are very rich lets meet up") BigDecimal amount) {
        log.info("incoming transaction for {} from account no : {}, with amount : {}", transactionType, accountId, amount);

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.createTransaction(transactionType, accountId, amount);
        return ResponseModel.success(fundTransactionCreatedDTO);
    }

    @PostMapping("/{transactionType}/{accountIdFrom}/{accountIdTo}/{amount}")
    public ResponseEntity<ResponseModel<FundTransactionCreatedDTO>> postTransfer(@Validated
                                                                                    @PathVariable @NotBlank FundTransactionUnDirectEnum transactionType,
                                                                                    @PathVariable @NotBlank String accountIdFrom,
                                                                                    @PathVariable @NotBlank String accountIdTo,
                                                                                    @PathVariable @NotBlank
                                                                                    @Range(min = 0L, message = "Please select positive numbers Only")
                                                                                    @DecimalMin(value = "0.01", message = "Amount must be positive and greater than 0")
                                                                                    @DecimalMax(value = "9999999999999.99", message = "Amount must be less than 9999999999999.99, unless you are very rich lets meet up") BigDecimal amount) {
        log.info("post transfer from {} to {} for {}  with amount : {}", accountIdFrom, accountIdTo, transactionType, amount);

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.createTransfer(transactionType, accountIdFrom, accountIdTo, amount);
        return ResponseModel.success(fundTransactionCreatedDTO);
    }

    @GetMapping("/history/{accountId}/{referenceNo}")
    public ResponseEntity<ResponseModel<FundTransactionCreatedDTO>> getFundTransaction(@Validated
                                                                                           @PathVariable @NotBlank String accountId,
                                                                                           @PathVariable @NotBlank String referenceNo) {
        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.getByAccountIdAndReferenceNo(accountId, referenceNo);
        return ResponseModel.success(fundTransactionCreatedDTO);
    }

    @GetMapping("/history/{accountId}")
    public ResponseEntity<ResponseModel<Page<FundTransactionCreatedDTO>>> getFundTransactions(@Validated
                                                                                       @PathVariable @NotBlank String accountId,
                                                                                       @RequestParam(name = "max", required = false, defaultValue = "10") Integer max,
                                                                                       @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset) {
        Page<FundTransactionCreatedDTO> fundTransactionCreatedDTOPage = fundTransactionService.getByAccountId(accountId, new PageRequest(offset, max, Sort.Direction.DESC, "createdDate"));
        log.info("will response with data {}", fundTransactionCreatedDTOPage.getContent());
        return ResponseModel.success(fundTransactionCreatedDTOPage);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(FundTransactionDirectEnum.class, new FundTransactionConverter());
    }
}
