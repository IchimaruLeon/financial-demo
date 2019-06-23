package com.session.demo.demo.entity;

import com.session.demo.demo.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "fund_transaction")
public class FundTransaction extends BaseEntity {

    private static final String PREFIX = "FTR";

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account accountTo;

    private BigDecimal amount;

    private String fundTransactionType;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
