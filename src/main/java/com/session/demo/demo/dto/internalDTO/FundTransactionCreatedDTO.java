package com.session.demo.demo.dto.internalDTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class FundTransactionCreatedDTO {

    private String referenceNo;

    private String accountId;

    private BigDecimal amount;

    private String transactionTime;

    private String transactionType;

    private BigDecimal activeBalance;
}
