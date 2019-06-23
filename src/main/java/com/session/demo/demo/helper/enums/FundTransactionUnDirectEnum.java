package com.session.demo.demo.helper.enums;

import java.util.Arrays;

    public enum FundTransactionUnDirectEnum implements FundTransactionTypeEnum {
    TRANSFER_IN(TypeFlagEnum.CREDIT),
    TAX(TypeFlagEnum.DEBIT),
    TRANSFER_OUT(TypeFlagEnum.DEBIT);

    FundTransactionUnDirectEnum(TypeFlagEnum typeFlagEnum) {
        this.typeFlagEnum = typeFlagEnum;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public TypeFlagEnum getTypeFlagEnum() {
            return this.typeFlagEnum;
        }

    public static FundTransactionUnDirectEnum fromValue(String value) {
        for (FundTransactionUnDirectEnum fundTransactionType : values()) {
            if (fundTransactionType.name().equalsIgnoreCase(value)) {
                return fundTransactionType;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
    private TypeFlagEnum typeFlagEnum;
}
