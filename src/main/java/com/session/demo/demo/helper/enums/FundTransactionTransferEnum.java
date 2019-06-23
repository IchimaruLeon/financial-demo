package com.session.demo.demo.helper.enums;

import java.util.Arrays;

    public enum FundTransactionTransferEnum implements FundTransactionTypeEnum {
    TRANSFER_OUT(TypeFlagEnum.DEBIT);

    FundTransactionTransferEnum(TypeFlagEnum typeFlagEnum) {
        this.typeFlagEnum = typeFlagEnum;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public TypeFlagEnum getTypeFlagEnum() {
            return this.typeFlagEnum;
        }

    public FundTransactionTransferEnum fromValue(String value) {
        for (FundTransactionTransferEnum fundTransactionType : values()) {
            if (fundTransactionType.name().equalsIgnoreCase(value)) {
                return fundTransactionType;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
    private TypeFlagEnum typeFlagEnum;
}
