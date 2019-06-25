package com.session.demo.demo.helper.enums;

public enum FundTransactionBackgroundEnum implements FundTransactionTypeEnum {
    TRANSFER_IN(TypeFlagEnum.CREDIT),
    TAX(TypeFlagEnum.DEBIT);

    FundTransactionBackgroundEnum(TypeFlagEnum typeFlagEnum) {
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
