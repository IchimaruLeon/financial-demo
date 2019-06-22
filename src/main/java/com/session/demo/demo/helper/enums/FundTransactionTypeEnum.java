package com.session.demo.demo.helper.enums;

import java.util.Arrays;

public enum FundTransactionTypeEnum {
    TOP_UP(TypeFlagEnum.CREDIT),
    CASH_OUT(TypeFlagEnum.DEBIT),
    TRANSFER_IN(TypeFlagEnum.CREDIT),
    TAX(TypeFlagEnum.DEBIT),
    TRANSFER_OUT(TypeFlagEnum.DEBIT);

    FundTransactionTypeEnum(TypeFlagEnum typeFlagEnum) {
        this.typeFlagEnum = typeFlagEnum;
    }

    public TypeFlagEnum getTypeFlagEnum() {
        return this.typeFlagEnum;
    }

    public static FundTransactionTypeEnum fromValue(String value) {
        for (FundTransactionTypeEnum fundTransactionType : values()) {
            if (fundTransactionType.name().equalsIgnoreCase(value)) {
                return fundTransactionType;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }

    private TypeFlagEnum typeFlagEnum;
}
