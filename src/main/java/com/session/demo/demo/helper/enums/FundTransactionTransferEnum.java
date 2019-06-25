package com.session.demo.demo.helper.enums;

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

    private TypeFlagEnum typeFlagEnum;
}
