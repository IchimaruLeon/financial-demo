package com.session.demo.demo.dto.internalDTO;

import java.math.BigDecimal;
import java.time.Instant;

public interface FundTransactionData {

    String getAccountId();

    BigDecimal getTotalBalance();

    Integer getTotalTrx();

    Instant getTrxDate();
}
