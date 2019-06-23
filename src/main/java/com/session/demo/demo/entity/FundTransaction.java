package com.session.demo.demo.entity;

import com.session.demo.demo.entity.base.BaseEntity;
import com.session.demo.demo.helper.enums.FundTransactionDirectEnum;
import com.session.demo.demo.helper.enums.FundTransactionTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@ToString
@Table(name = "fund_transaction")
public class FundTransaction extends BaseEntity {

    private static final String PREFIX = "FTR";

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    private BigDecimal amount;

    private String fundTransactionType;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
