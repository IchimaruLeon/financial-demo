package com.session.demo.demo.controller;

import com.session.demo.demo.dto.internalDTO.AccountDTO;
import com.session.demo.demo.handler.ResponseModel;
import com.session.demo.demo.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Get Account Information", notes = "Account Details")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 400, message = "Invalid parameters supplied"),
                    @ApiResponse(code = 500, message = "server error")
            })
    @GetMapping("/{accountId}")
    public ResponseEntity<ResponseModel<AccountDTO>> getAccount(@Validated @NotNull @PathVariable String accountId) {
        AccountDTO accountDTO = accountService.findByIdForDTO(accountId);
        return ResponseModel.success(accountDTO);
    }
}
