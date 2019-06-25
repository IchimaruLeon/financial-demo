package com.session.demo.demo.helper.enums;

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

    private TypeFlagEnum typeFlagEnum;
}
