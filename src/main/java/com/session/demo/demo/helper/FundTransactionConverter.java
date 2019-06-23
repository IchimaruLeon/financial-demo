package com.session.demo.demo.helper;

import com.session.demo.demo.helper.enums.FundTransactionDirectEnum;

import java.beans.PropertyEditorSupport;

public class FundTransactionConverter extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(FundTransactionDirectEnum.fromValue(text));
    }
}
