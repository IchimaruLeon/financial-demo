package com.session.demo.demo.helper.enums;

import java.util.Arrays;

public enum FundTransactionDirectEnum implements FundTransactionTypeEnum {
    TOP_UP(TypeFlagEnum.CREDIT),
    CASH_OUT(TypeFlagEnum.DEBIT);

    FundTransactionDirectEnum(TypeFlagEnum typeFlagEnum) {
        this.typeFlagEnum = typeFlagEnum;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public TypeFlagEnum getTypeFlagEnum() {
        return this.typeFlagEnum;
    }

    public static FundTransactionDirectEnum fromValue(String value) {
        for (FundTransactionDirectEnum fundTransactionType : values()) {
            if (fundTransactionType.name().equalsIgnoreCase(value)) {
                return fundTransactionType;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }

    private TypeFlagEnum typeFlagEnum;
}
