package com.session.demo.demo.service;

import com.session.demo.demo.dto.internalDTO.FundTransactionCreatedDTO;
import com.session.demo.demo.dto.internalDTO.FundTransactionData;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.FundTransaction;
import com.session.demo.demo.helper.enums.FundTransactionTypeEnum;
import com.session.demo.demo.helper.enums.TypeFlagEnum;
import com.session.demo.demo.repository.FundTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class FundTransactionService {

    @Autowired
    private FundTransactionRepository fundTransactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public FundTransactionCreatedDTO createTransaction(FundTransactionTypeEnum transactionTypeEnum, String accountId, BigDecimal amount) {
        Optional<Account> accountOptional = accountService.findById(accountId);
        if(!accountOptional.isPresent()) {
            throw new RuntimeException(String.format("Account not found with id %s",accountId));
        }
        FundTransaction fundTransaction = new FundTransaction();
        fundTransaction.setFundTransactionType(transactionTypeEnum);
        fundTransaction.setAccount(accountOptional.get());
        fundTransaction.setAmount(getAmountByType(transactionTypeEnum,amount));
        save(fundTransaction);
        FundTransactionData data = getTotalBalance(accountId);

        log.info("current balance for {} is {} with total data {}, at {}", data.getAccountId(), data.getTotalBalance(), data.getTotalTrx(), data.getTrxDate());

        accountService.updateAmount(accountOptional.get(), data.getTotalBalance(), data.getTotalTrx());
        return constructResponse(fundTransaction, data.getTotalBalance());
    }

    public FundTransactionCreatedDTO constructResponse(FundTransaction fundTransaction, BigDecimal activeBalance) {
        FundTransactionCreatedDTO fTrxCreated = new FundTransactionCreatedDTO();
        fTrxCreated.setReferenceNo(fundTransaction.getId());
        fTrxCreated.setAccountId(fundTransaction.getAccount().getId());
        fTrxCreated.setAmount(fundTransaction.getAmount());
        fTrxCreated.setTransactionTime(fundTransaction.getCreatedDate().toString());
        fTrxCreated.setTransactionType(fundTransaction.getFundTransactionType().name());
        fTrxCreated.setActiveBalance(activeBalance);
        return fTrxCreated;
    }

    public FundTransactionData getTotalBalance(String accountId) {
        return fundTransactionRepository.getActiveBalance(accountId);
    }

    public FundTransaction save(FundTransaction fundTransaction) {
        return fundTransactionRepository.save(fundTransaction);
    }

    public BigDecimal getAmountByType(FundTransactionTypeEnum transactionTypeEnum, BigDecimal amount) {

        if (TypeFlagEnum.CREDIT.equals(transactionTypeEnum.getTypeFlagEnum())) {
            return amount.abs();
        } else {
            return amount.compareTo(BigDecimal.ZERO) > 0 ? amount.negate() : amount;
        }
    }

}
