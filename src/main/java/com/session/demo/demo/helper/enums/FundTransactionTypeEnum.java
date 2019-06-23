package com.session.demo.demo.helper.enums;

public interface FundTransactionTypeEnum<T> {

    String getName();

    TypeFlagEnum getTypeFlagEnum();

    T fromValue(String value);
}
