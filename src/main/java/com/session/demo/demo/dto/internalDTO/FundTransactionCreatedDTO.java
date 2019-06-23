package com.session.demo.demo.dto.internalDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class FundTransactionCreatedDTO {

    private String referenceNo;

    private String accountFrom;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountTo;

    private BigDecimal amount;

    private String transactionTime;

    private String transactionType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal activeBalance;
}
