package com.session.demo.demo.entity;

import com.session.demo.demo.entity.base.Historical;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@ToString
@Table(name = "account")
public class Account extends Historical {

    public static final String PREFIX = "ACC";

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserData.class)
    private UserData userData;

    @Column(name = "active_balance")
    private BigDecimal activeBalance;

    @Column(name = "total_activity")
    private Integer totalActivity;

    @OneToOne(targetEntity = AccountType.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AccountType accountType;

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
