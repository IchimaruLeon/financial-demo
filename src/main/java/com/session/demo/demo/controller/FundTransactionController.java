package com.session.demo.demo.controller;

import com.session.demo.demo.dto.internalDTO.FundTransactionCreatedDTO;
import com.session.demo.demo.handler.ResponseModel;
import com.session.demo.demo.helper.enums.FundTransactionDirectEnum;
import com.session.demo.demo.helper.enums.FundTransactionTransferEnum;
import com.session.demo.demo.service.FundTransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/fund-transaction")
public class FundTransactionController {


    @Autowired
    private FundTransactionService fundTransactionService;

    @ApiOperation(value = "Create Transaction to do TOP_UP & CASH_OUT", notes = "TOP_UP & CASH_OUT")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid parameters supplied"),
                    @ApiResponse(code = 500, message = "server error")
            })
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

    @ApiOperation(value = "Create Transaction to do TRANSFER", notes = "TRANSFER")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid parameters supplied"),
                    @ApiResponse(code = 500, message = "server error")
            })
    @PostMapping("/{transactionType}/{accountIdFrom}/{accountIdTo}/{amount}")
    public ResponseEntity<ResponseModel<FundTransactionCreatedDTO>> postTransfer(@Validated
                                                                                    @PathVariable @NotBlank FundTransactionTransferEnum transactionType,
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

    @ApiOperation(value = "Get Fund Transaction By Account Id & ReferenceNo", notes = "SINGLE FUND TRANSACTION DATA")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid parameters supplied"),
                    @ApiResponse(code = 500, message = "server error")
            })
    @GetMapping("/history/{accountId}/{referenceNo}")
    public ResponseEntity<ResponseModel<FundTransactionCreatedDTO>> getFundTransaction(@Validated
                                                                                           @PathVariable @NotBlank String accountId,
                                                                                           @PathVariable @NotBlank String referenceNo) {
        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.getByAccountIdAndReferenceNo(accountId, referenceNo);
        return ResponseModel.success(fundTransactionCreatedDTO);
    }

    @ApiOperation(value = "Get List Fund Transaction By Account Id", notes = "LIST OF FUND TRANSACTION")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid parameters supplied"),
                    @ApiResponse(code = 500, message = "server error")
            })
    @GetMapping("/history/{accountId}")
    public ResponseEntity<ResponseModel<Page<FundTransactionCreatedDTO>>> getFundTransactions(@Validated
                                                                                       @PathVariable @NotBlank String accountId,
                                                                                       @RequestParam(name = "max", required = false, defaultValue = "10") Integer max,
                                                                                       @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset) {
        Page<FundTransactionCreatedDTO> fundTransactionCreatedDTOPage = fundTransactionService.getByAccountId(accountId, new PageRequest(offset, max, Sort.Direction.DESC, "createdDate"));
        log.info("will response with data {}", fundTransactionCreatedDTOPage.getContent());
        return ResponseModel.success(fundTransactionCreatedDTOPage);
    }
}
