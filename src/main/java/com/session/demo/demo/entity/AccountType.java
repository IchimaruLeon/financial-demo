package com.session.demo.demo.entity;

import com.session.demo.demo.entity.base.Parameterized;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "account_type")
public class AccountType extends Parameterized {

    public static final String PREFIX = "ACT";

    @Override
    public String getPrefix() {
        return PREFIX;
    }

}
