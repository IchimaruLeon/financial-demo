package com.session.demo.demo.service;

import com.session.demo.demo.dto.internalDTO.FundTransactionCreatedDTO;
import com.session.demo.demo.dto.internalDTO.FundTransactionData;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.FundTransaction;
import com.session.demo.demo.handler.AppException;
import com.session.demo.demo.handler.ResponseCodeEnum;
import com.session.demo.demo.helper.StringUtils;
import com.session.demo.demo.helper.enums.FundTransactionBackgroundEnum;
import com.session.demo.demo.helper.enums.FundTransactionDirectEnum;
import com.session.demo.demo.helper.enums.FundTransactionTransferEnum;
import com.session.demo.demo.helper.enums.FundTransactionTypeEnum;
import com.session.demo.demo.helper.enums.TypeFlagEnum;
import com.session.demo.demo.repository.FundTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FundTransactionService {

    @Autowired
    private FundTransactionRepository fundTransactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public FundTransactionCreatedDTO createTransaction(FundTransactionDirectEnum transactionTypeEnum, String accountId, BigDecimal amount) {
        BigDecimal trxAmount = getAmountByType(transactionTypeEnum,amount);

        Optional<Account> accountOptional = accountService.findById(accountId);

        Account account = validateAccountExistence(accountId, accountOptional);
        validateAmountAndActiveBalance(transactionTypeEnum, trxAmount, account);

        FundTransaction fundTransaction = createTransaction(trxAmount, account, transactionTypeEnum.name());
        FundTransactionData data = getTotalBalance(accountId);

        log.info("current balance for {} is {} with total data {}, at {}", data.getAccountId(), data.getTotalBalance(), data.getTotalTrx(), data.getTrxDate());

        accountService.updateAmount(account, data.getTotalBalance(), data.getTotalTrx());
        return constructResponse(fundTransaction, data.getTotalBalance());
    }

    @Transactional
    public FundTransactionCreatedDTO createTransfer(FundTransactionTransferEnum transactionTypeEnum, String accountIdFrom, String accountIdTo, BigDecimal amount) {
        BigDecimal trxAmount = getAmountByType(transactionTypeEnum,amount);
        Optional<Account> accountFromOptional = accountService.findById(accountIdFrom);
        Optional<Account> accountToOptional = accountService.findById(accountIdTo);

        Account accountFrom = validateAccountExistence(accountIdFrom, accountFromOptional);
        Account accountTo = validateAccountExistence(accountIdTo, accountToOptional);

        validateAmountAndActiveBalance(transactionTypeEnum, trxAmount, accountFrom);

        FundTransaction fundTransactionFrom = createTransaction(trxAmount, accountFrom, accountTo, FundTransactionTransferEnum.TRANSFER_OUT.name());
        createTransaction(trxAmount.abs(), accountTo, accountFrom, FundTransactionBackgroundEnum.TRANSFER_IN.name());

        FundTransactionData dataFrom = getTotalBalance(accountFrom.getId());
        FundTransactionData dataTo = getTotalBalance(accountTo.getId());

        log.info("current balance for {} is {} with total data {}, at {}", dataFrom.getAccountId(), dataFrom.getTotalBalance(), dataFrom.getTotalTrx(), dataFrom.getTrxDate());
        log.info("current balance for {} is {} with total data {}, at {}", dataTo.getAccountId(), dataTo.getTotalBalance(), dataTo.getTotalTrx(), dataTo.getTrxDate());

        accountService.updateAmount(accountFrom, dataFrom.getTotalBalance(), dataFrom.getTotalTrx());
        accountService.updateAmount(accountTo, dataTo.getTotalBalance(), dataTo.getTotalTrx());
        return constructResponse(fundTransactionFrom, dataFrom.getTotalBalance());
    }

    public FundTransactionCreatedDTO getByAccountIdAndReferenceNo(String accountId, String referenceNo) {
        Optional<FundTransaction> fundTransactionOptional = fundTransactionRepository.findByIdAndAccountId(referenceNo, accountId);
        if(!fundTransactionOptional.isPresent()){
            throw new AppException(ResponseCodeEnum.NOT_FOUND, String.format("Fund transaction with reference %s not found",referenceNo));
        }
        return constructResponse(fundTransactionOptional.get(), BigDecimal.ZERO);
    }

    public Page<FundTransactionCreatedDTO> getByAccountId(String accountId, Pageable pageRequest) {
        Page<FundTransaction> pageResults = fundTransactionRepository.findByAccountId(accountId, pageRequest);
        log.info("total return transaction data for {} is {}",accountId, pageResults.getContent().size());
        List<FundTransactionCreatedDTO> fundTransactionCreatedDTOList = pageResults.stream().map(it -> constructResponse(it, BigDecimal.ZERO)).collect(Collectors.toList());

        return new PageImpl<>(fundTransactionCreatedDTOList, new PageRequest(pageResults.getTotalPages(), pageResults.getSize()), fundTransactionCreatedDTOList.size());
    }

    public FundTransactionCreatedDTO constructResponse(FundTransaction fundTransaction, BigDecimal activeBalance) {
        FundTransactionCreatedDTO fTrxCreated = new FundTransactionCreatedDTO();
        fTrxCreated.setReferenceNo(fundTransaction.getId());
        fTrxCreated.setAccountFrom(fundTransaction.getAccount().getId());
        fTrxCreated.setAmount(fundTransaction.getAmount());
        fTrxCreated.setTransactionTime(fundTransaction.getCreatedDate().toString());
        fTrxCreated.setTransactionType(fundTransaction.getFundTransactionType());

        if(!(0 == BigDecimal.ZERO.compareTo(activeBalance))) {
            fTrxCreated.setActiveBalance(activeBalance);
        }
        if(null != fundTransaction.getAccountTo()) {
            fTrxCreated.setAccountTo(fundTransaction.getAccountTo().getId());
        }
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

    private FundTransaction createTransaction(BigDecimal trxAmount, Account accountFrom, Account accountTo, String name) {
        FundTransaction fundTransaction = new FundTransaction();
        fundTransaction.setFundTransactionType(name);
        fundTransaction.setAccount(accountFrom);
        fundTransaction.setAccountTo(accountTo);
        fundTransaction.setAmount(trxAmount);
        save(fundTransaction);
        return fundTransaction;
    }

    private FundTransaction createTransaction(BigDecimal trxAmount, Account account, String name) {
        FundTransaction fundTransaction = new FundTransaction();
        fundTransaction.setFundTransactionType(name);
        fundTransaction.setAccount(account);
        fundTransaction.setAmount(trxAmount);
        save(fundTransaction);
        return fundTransaction;
    }

    private Account validateAccountExistence(String accountId, Optional<Account> accountOptional) {
        if (!accountOptional.isPresent()) {
            throw new RuntimeException(String.format("Account not found with id %s", accountId));
        } else {
            return accountOptional.get();
        }
    }

    private boolean isBalanceNotEnough(Account account, BigDecimal transactionAmount) {
        return account.getActiveBalance().compareTo(transactionAmount.abs()) < 0;
    }

    private void validateAmountAndActiveBalance(FundTransactionTypeEnum transactionTypeEnum, BigDecimal trxAmount, Account account) {
        if(TypeFlagEnum.DEBIT == transactionTypeEnum.getTypeFlagEnum()){
            log.info("amount : {} , transaction type : {}, flagType : {}, currentBalance : {}", trxAmount, transactionTypeEnum.getName(), transactionTypeEnum.getTypeFlagEnum(), account.getActiveBalance());
            if (0 == trxAmount.compareTo(BigDecimal.ZERO)) {
                throw new AppException(ResponseCodeEnum.TRANSACTION_AMOUNT_CANT_BE_ZERO, String.format("cant make transaction with amount : %s", trxAmount));
            }
            if (TypeFlagEnum.DEBIT.equals(transactionTypeEnum.getTypeFlagEnum())) {
                log.info("validate amount : {}", account.getActiveBalance().compareTo(trxAmount.abs()));
                if (isBalanceNotEnough(account, trxAmount)) {
                    throw new AppException(ResponseCodeEnum.INSUFFICIENT_BALANCE, "Insufficient Balance, please do top up first");
                }
            }
        }
    }
}
