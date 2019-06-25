package com.session.demo.demo.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.session.demo.demo.dto.internalDTO.FundTransactionCreatedDTO;
import com.session.demo.demo.dto.internalDTO.FundTransactionData;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.FundTransaction;
import com.session.demo.demo.handler.AppException;
import com.session.demo.demo.handler.ResponseCodeEnum;
import com.session.demo.demo.helper.enums.FundTransactionBackgroundEnum;
import com.session.demo.demo.helper.enums.FundTransactionDirectEnum;
import com.session.demo.demo.helper.enums.FundTransactionTransferEnum;
import com.session.demo.demo.helper.enums.FundTransactionTypeEnum;
import com.session.demo.demo.repository.FundTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FundTransactionServiceTest {

    @Mock
    private FundTransactionRepository fundTransactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private FundTransactionService fundTransactionService;

    @Test
    public void testCallMethodCreateTransactionShouldReturnFundTransactionCreatedDTOAsExpectedWhenValidAccountIdAndFundTransactionTypeEnumTopUpGiven() {
        String sampleAccountId = "SAMPLE_ACCOUNT";
        Account account = getDummyAccount(sampleAccountId, new BigDecimal("1000"), 1);
        BigDecimal trxAmount = new BigDecimal("12000");
        Mockito.when(accountService.findById(Mockito.eq("SAMPLE_ACCOUNT"))).thenReturn(Optional.of(account));
        Mockito.when(fundTransactionRepository.getActiveBalance(Mockito.eq(sampleAccountId))).thenReturn(generateDummyFundTransactionData(sampleAccountId, new BigDecimal("13000"), 2, Instant.now()));
        
        Mockito.when(fundTransactionRepository.save(Mockito.any(FundTransaction.class))).thenReturn(getDummyFundTransaction(account, null, trxAmount, FundTransactionDirectEnum.TOP_UP));

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.createTransaction(FundTransactionDirectEnum.TOP_UP, sampleAccountId, trxAmount);
        assertNotNull(fundTransactionCreatedDTO);

        ArgumentCaptor<FundTransaction> fundTransactionArgumentCaptor = ArgumentCaptor.forClass(FundTransaction.class);

        Mockito.verify(fundTransactionRepository, Mockito.times(1)).save(fundTransactionArgumentCaptor.capture());
        Mockito.verify(accountService, Mockito.times(1)).updateAmount(Mockito.eq(account), Mockito.eq(new BigDecimal("13000")), Mockito.eq(2));
        assertNotNull(fundTransactionArgumentCaptor.getValue());
    }

    @Test
    public void testCallMethodCreateTransactionShouldReturnFundTransactionCreatedDTOAsExpectedWhenValidAccountIdAndFundTransactionTypeEnumCashOutGiven() {
        String sampleAccountId = "SAMPLE_ACCOUNT";
        Account account = getDummyAccount(sampleAccountId, new BigDecimal("50000"), 1);
        BigDecimal trxAmount = new BigDecimal("12000");
        Mockito.when(accountService.findById(Mockito.eq("SAMPLE_ACCOUNT"))).thenReturn(Optional.of(account));
        Mockito.when(fundTransactionRepository.getActiveBalance(Mockito.eq(sampleAccountId))).thenReturn(generateDummyFundTransactionData(sampleAccountId, new BigDecimal("13000"), 2, Instant.now()));
        Mockito.when(fundTransactionRepository.save(Mockito.any(FundTransaction.class))).thenReturn(getDummyFundTransaction(account, null, trxAmount, FundTransactionDirectEnum.TOP_UP));

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.createTransaction(FundTransactionDirectEnum.CASH_OUT, sampleAccountId, trxAmount);
        assertNotNull(fundTransactionCreatedDTO);

        ArgumentCaptor<FundTransaction> fundTransactionArgumentCaptor = ArgumentCaptor.forClass(FundTransaction.class);

        Mockito.verify(fundTransactionRepository, Mockito.times(1)).save(fundTransactionArgumentCaptor.capture());
        Mockito.verify(accountService, Mockito.times(1)).updateAmount(Mockito.eq(account), Mockito.eq(new BigDecimal("13000")), Mockito.eq(2));
        assertNotNull(fundTransactionArgumentCaptor.getValue());
    }

    @Test
    public void testCallMethodCreateTransactionShouldThrowAppExceptionWhenValidAccountIdAndFundTransactionTypeEnumCashOutButAvailableBalanceIsNotEnoughGiven() {
        AppException ex;
        String sampleAccountId = "SAMPLE_ACCOUNT";
        ex = assertThrows(AppException.class, () -> {
            Account account = getDummyAccount(sampleAccountId, new BigDecimal("11000"), 1);
            BigDecimal trxAmount = new BigDecimal("12000");
            Mockito.when(accountService.findById(Mockito.eq("SAMPLE_ACCOUNT"))).thenReturn(Optional.of(account));
            fundTransactionService.createTransaction(FundTransactionDirectEnum.CASH_OUT, sampleAccountId, trxAmount);
        });
        assertEquals(ResponseCodeEnum.INSUFFICIENT_BALANCE, ex.getCode());
        assertEquals("Insufficient Balance, please do top up first", ex.getMessageKey());
    }

    @Test
    public void testCallMethodCreateTransactionShouldThrowAppExceptionWhenInvalidAccountIdGiven() {
        AppException ex;
        String accountId = "UNKNOWN_ACCOUNT_ID";
        ex = assertThrows(AppException.class, () -> {
            Mockito.when(accountService.findById(Mockito.eq(accountId))).thenReturn(Optional.empty());
            fundTransactionService.createTransaction(FundTransactionDirectEnum.TOP_UP, accountId, new BigDecimal("10000"));
        });
        assertEquals(ResponseCodeEnum.NOT_FOUND, ex.getCode());
        assertEquals("Account not found with id UNKNOWN_ACCOUNT_ID", ex.getMessageKey());
    }

    @Test
    public void testCallMethodCreateTransferShouldThrowAppExceptionWhenInvalidAccountIdFromGiven() {
        AppException ex;
        String accountIdFrom = "UNKNOWN_ACCOUNT_ID";
        String accountIdTo = "KNOWN_ACCOUNT_ID";
        ex = assertThrows(AppException.class, () -> {
            Mockito.when(accountService.findById(Mockito.eq(accountIdFrom))).thenReturn(Optional.empty());
            fundTransactionService.createTransfer(FundTransactionTransferEnum.TRANSFER_OUT, accountIdFrom, accountIdTo, new BigDecimal("10000"));
        });
        assertEquals(ResponseCodeEnum.NOT_FOUND, ex.getCode());
        assertEquals("Account not found with id UNKNOWN_ACCOUNT_ID", ex.getMessageKey());
    }

    @Test
    public void testCallMethodCreateTransferShouldThrowAppExceptionWhenValidAccountIdFromButInvalidAccountIdToGiven() {
        AppException ex;
        String accountIdFrom = "KNOWN_ACCOUNT_ID";
        String accountIdTo = "UNKNOWN_ACCOUNT_ID";
        Account account = getDummyAccount(accountIdFrom, BigDecimal.ZERO, 0);
        ex = assertThrows(AppException.class, () -> {
            Mockito.when(accountService.findById(Mockito.eq(accountIdFrom))).thenReturn(Optional.of(account));
            Mockito.when(accountService.findById(Mockito.eq(accountIdTo))).thenReturn(Optional.empty());
            fundTransactionService.createTransfer(FundTransactionTransferEnum.TRANSFER_OUT, accountIdFrom, accountIdTo, new BigDecimal("10000"));
        });
        assertEquals(ResponseCodeEnum.NOT_FOUND, ex.getCode());
        assertEquals("Account not found with id UNKNOWN_ACCOUNT_ID", ex.getMessageKey());
    }

    @Test
    public void testCallMethodCreateTransferShouldReturnFundTransactionCreatedDTOAsExpectedWhenValidParameterGiven() {
        String accountIdFrom = "SAMPLE_ACCOUNT_FROM";
        String accountIdTo = "SAMPLE_ACCOUNT_TO";
        Account accountFrom = getDummyAccount(accountIdFrom, new BigDecimal("13000"), 1);
        Account accountTo = getDummyAccount(accountIdTo, new BigDecimal("1000"), 1);
        BigDecimal trxAmount = new BigDecimal("12000");
        Mockito.when(accountService.findById(Mockito.eq(accountIdFrom))).thenReturn(Optional.of(accountFrom));
        Mockito.when(accountService.findById(Mockito.eq(accountIdTo))).thenReturn(Optional.of(accountTo));
        Mockito.when(fundTransactionRepository.getActiveBalance(Mockito.eq(accountIdFrom))).thenReturn(generateDummyFundTransactionData(accountIdFrom, new BigDecimal("1000"), 2, Instant.now()));
        Mockito.when(fundTransactionRepository.getActiveBalance(Mockito.eq(accountIdTo))).thenReturn(generateDummyFundTransactionData(accountIdTo, new BigDecimal("13000"), 2, Instant.now()));

        Mockito.when(fundTransactionRepository.save(Mockito.any(FundTransaction.class))).thenReturn(getDummyFundTransaction(accountFrom, accountTo, trxAmount, FundTransactionDirectEnum.TOP_UP));

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.createTransfer(FundTransactionTransferEnum.TRANSFER_OUT, accountIdFrom, accountIdTo, trxAmount);
        assertNotNull(fundTransactionCreatedDTO);

        ArgumentCaptor<FundTransaction> fundTransactionArgumentCaptor = ArgumentCaptor.forClass(FundTransaction.class);

        Mockito.verify(fundTransactionRepository, Mockito.times(2)).save(fundTransactionArgumentCaptor.capture());
        Mockito.verify(accountService, Mockito.times(2)).updateAmount(Mockito.any(Account.class), Mockito.any(BigDecimal.class), Mockito.anyInt());
        assertNotNull(fundTransactionArgumentCaptor.getAllValues());
        assertEquals(2, fundTransactionArgumentCaptor.getAllValues().size());
    }

    @Test
    public void testCallMethodGetTotalBalanceWillReturnCustomDTOFundTransactionDataAsExpected() {
        String sampleAccountId = "ACCOUNT_ID";
        Mockito.when(fundTransactionRepository.getActiveBalance(Mockito.eq(sampleAccountId))).thenReturn(generateDummyFundTransactionData(sampleAccountId, new BigDecimal("250000"), 20, Instant.parse("2014-12-03T10:15:30Z")));
        FundTransactionData fundTransactionData = fundTransactionService.getTotalBalance(sampleAccountId);
        assertEquals(new BigDecimal("250000"), fundTransactionData.getTotalBalance());
        assertEquals(sampleAccountId, fundTransactionData.getAccountId());
        assertEquals(Integer.valueOf(20), fundTransactionData.getTotalTrx());
        assertEquals("2014-12-03T10:15:30Z", fundTransactionData.getTrxDate().toString());
    }

    @Test
    public void testCallMethodGetByAccountIdAndReferenceNoShouldReturnFundTransactionCreatedDTOWithoutActiveBalanceWhenValidAccountIdAndReferenceNoAsIdGiven() {
        Account account = getDummyAccount("DUMMY_ACCOUNT_01", new BigDecimal("1000"), 12);
        Account accountTo = getDummyAccount("DUMMY_ACCOUNT_02", new BigDecimal("120000"), 5);
        FundTransaction fundTransaction = getDummyFundTransaction(account, accountTo, new BigDecimal("-1000"), FundTransactionTransferEnum.TRANSFER_OUT);
        fundTransaction.setId("ID-TRX-01");

        Mockito.when(fundTransactionRepository.findByIdAndAccountId(Mockito.eq(fundTransaction.getId()), Mockito.eq(account.getId()))).thenReturn(Optional.of(fundTransaction));
        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.getByAccountIdAndReferenceNo(account.getId(), fundTransaction.getId());
        assertNotNull(fundTransactionCreatedDTO);
        assertEquals("ID-TRX-01", fundTransactionCreatedDTO.getReferenceNo());
        assertEquals(account.getId(), fundTransactionCreatedDTO.getAccountFrom());
        assertEquals(accountTo.getId(), fundTransactionCreatedDTO.getAccountTo());
        assertEquals(new BigDecimal("-1000"), fundTransactionCreatedDTO.getAmount());
        assertEquals("TRANSFER_OUT", fundTransactionCreatedDTO.getTransactionType());
        assertNotNull(fundTransactionCreatedDTO.getTransactionTime());
        assertNull(fundTransactionCreatedDTO.getActiveBalance());
    }

    @Test
    public void testCallMethodGetByAccountIdAndReferenceNoShouldThrowAppExceptionWhenInvalidAccountIdAndReferenceNoAsIdGiven() {
        AppException ex;

        ex = assertThrows(AppException.class, () ->{
            Mockito.when(fundTransactionRepository.findByIdAndAccountId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
            fundTransactionService.getByAccountIdAndReferenceNo("123123", "123123123123");
        });
        assertEquals(ResponseCodeEnum.NOT_FOUND, ex.getCode());
        assertEquals("Fund transaction with reference 123123123123 not found", ex.getMessageKey());
    }

    @Test
    public void testCallMethodConstructResponseShouldReturnFundTransactionCreatedDTOWithFullReturnFieldWhenValidFundTransactionAndActiveBalanceGiven() {
        Account accountFrom = getDummyAccount("ID-01", new BigDecimal("120000"), 10);
        Account accountTo = getDummyAccount("ID-02", new BigDecimal("120000"), 5);
        BigDecimal trxAmount = new BigDecimal("25000");
        FundTransactionTypeEnum fundTrxEnum = FundTransactionTransferEnum.TRANSFER_OUT;

        FundTransaction fundTransaction = getDummyFundTransaction(accountFrom, accountTo, trxAmount, fundTrxEnum);
        fundTransaction.setId("ID-TRX-01");

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.constructResponse(fundTransaction, new BigDecimal("95000"));
        assertNotNull(fundTransactionCreatedDTO);
        assertEquals("ID-TRX-01", fundTransactionCreatedDTO.getReferenceNo());
        assertEquals(accountFrom.getId(), fundTransactionCreatedDTO.getAccountFrom());
        assertEquals(accountTo.getId(), fundTransactionCreatedDTO.getAccountTo());
        assertEquals(trxAmount, fundTransactionCreatedDTO.getAmount());
        assertEquals("TRANSFER_OUT", fundTransactionCreatedDTO.getTransactionType());
        assertEquals(new BigDecimal("95000"), fundTransactionCreatedDTO.getActiveBalance());
        assertNotNull(fundTransactionCreatedDTO.getTransactionTime());
    }

    @Test
    public void testCallMethodConstructResponseShouldReturnFundTransactionCreatedDTOWithReturnFieldWithoutAccountToWhenValidFundTransactionIsNotContainingAccountToAndActiveBalanceGiven() {
        Account accountFrom = getDummyAccount("ID-01", new BigDecimal("120000"), 10);
        BigDecimal trxAmount = new BigDecimal("25000");
        FundTransactionTypeEnum fundTrxEnum = FundTransactionDirectEnum.TOP_UP;

        FundTransaction fundTransaction = getDummyFundTransaction(accountFrom, null, trxAmount, fundTrxEnum);
        fundTransaction.setId("ID-TRX-02");

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.constructResponse(fundTransaction, new BigDecimal("145000"));
        assertNotNull(fundTransactionCreatedDTO);
        assertEquals("ID-TRX-02", fundTransactionCreatedDTO.getReferenceNo());
        assertEquals(accountFrom.getId(), fundTransactionCreatedDTO.getAccountFrom());
        assertEquals(trxAmount, fundTransactionCreatedDTO.getAmount());
        assertEquals("TOP_UP", fundTransactionCreatedDTO.getTransactionType());
        assertEquals(new BigDecimal("145000"), fundTransactionCreatedDTO.getActiveBalance());
        assertNotNull(fundTransactionCreatedDTO.getTransactionTime());
        assertNull(fundTransactionCreatedDTO.getAccountTo());
    }

    @Test
    public void testCallMethodSaveShouldCallSaveInRepositoryAsExpected() {
        FundTransaction fundTransaction = new FundTransaction();
        fundTransactionService.save(fundTransaction);
        Mockito.verify(fundTransactionRepository, Mockito.times(1)).save(Mockito.eq(fundTransaction));
    }

    @Test
    public void testCallMethodConstructResponseShouldReturnFundTransactionCreatedDTOWithFullReturnFieldWhenValidFundTransactionIsNotContainingAccountToWithActiveBalanceZeroGiven() {
        Account accountFrom = getDummyAccount("ID-01", new BigDecimal("120000"), 10);
        BigDecimal trxAmount = new BigDecimal("25000");
        FundTransactionTypeEnum fundTrxEnum = FundTransactionDirectEnum.TOP_UP;

        FundTransaction fundTransaction = getDummyFundTransaction(accountFrom, null, trxAmount, fundTrxEnum);
        fundTransaction.setId("ID-TRX-03");

        FundTransactionCreatedDTO fundTransactionCreatedDTO = fundTransactionService.constructResponse(fundTransaction, BigDecimal.ZERO);
        assertNotNull(fundTransactionCreatedDTO);
        assertEquals("ID-TRX-03", fundTransactionCreatedDTO.getReferenceNo());
        assertEquals(accountFrom.getId(), fundTransactionCreatedDTO.getAccountFrom());
        assertEquals(trxAmount, fundTransactionCreatedDTO.getAmount());
        assertEquals("TOP_UP", fundTransactionCreatedDTO.getTransactionType());
        assertNotNull(fundTransactionCreatedDTO.getTransactionTime());
        assertNull(fundTransactionCreatedDTO.getAccountTo());
        assertNull(fundTransactionCreatedDTO.getActiveBalance());
    }

    @Test
    public void testCallMethodValidateAmountAndActiveBalanceShouldThrowAppExceptionTrxAmountCantBeZeroWhenAmountZeroGiven() {
        AppException ex;

        ex = assertThrows(AppException.class, () -> {
            BigDecimal amount = BigDecimal.ZERO;
            Account account = new Account(){{setActiveBalance(new BigDecimal("1000"));}};
            fundTransactionService.validateAmountAndActiveBalance(FundTransactionTransferEnum.TRANSFER_OUT, amount, account);
        });
        assertEquals(ResponseCodeEnum.TRANSACTION_AMOUNT_CANT_BE_ZERO, ex.getCode());
        assertEquals("cant make transaction with amount : 0", ex.getMessageKey());
    }

    @Test
    public void testCallMethodValidateAmountAndActiveBalanceShouldThrowAppExceptionInsufficientBalanceWhenActiveBalanceLessThanGivenTrxAmount() {
        AppException ex;

        ex = assertThrows(AppException.class, () -> {
            BigDecimal amount = new BigDecimal("1500");
            Account account = new Account(){{setActiveBalance(new BigDecimal("1000"));}};
            fundTransactionService.validateAmountAndActiveBalance(FundTransactionTransferEnum.TRANSFER_OUT, amount, account);
        });
        assertEquals(ResponseCodeEnum.INSUFFICIENT_BALANCE, ex.getCode());
        assertEquals("Insufficient Balance, please do top up first", ex.getMessageKey());
    }

    @Test
    public void testCallMethodValidateAmountAndActiveBalanceShouldNotThrowAppExceptionWhenValidActiveBalanceEqualsToGivenTrxAmount() {
        assertDoesNotThrow(() -> {
            BigDecimal amount = new BigDecimal("1500");
            Account account = new Account(){{setActiveBalance(new BigDecimal("1500"));}};
            fundTransactionService.validateAmountAndActiveBalance(FundTransactionDirectEnum.CASH_OUT, amount, account);
        });
    }

    @Test
    public void testCallMethodValidateAmountAndActiveBalanceShouldNotThrowAppExceptionWhenValidActiveBalanceGreaterThanGivenTrxAmount() {
        assertDoesNotThrow(() -> {
            BigDecimal amount = new BigDecimal("1500");
            Account account = new Account(){{setActiveBalance(new BigDecimal("3500"));}};
            fundTransactionService.validateAmountAndActiveBalance(FundTransactionDirectEnum.CASH_OUT, amount, account);
        });
    }

    @Test
    public void testCallMethodGetAmountByTypeFundTransactionDirectEnumTopUpShouldReturnPositiveValue() {
        BigDecimal amount = fundTransactionService.getAmountByType(FundTransactionDirectEnum.TOP_UP, new BigDecimal("1200"));
        assertEquals(new BigDecimal("1200"), amount);
    }

    @Test
    public void testCallMethodGetAmountByTypeFundTransactionDirectEnumCashOutShouldReturnNegativeValue() {
        BigDecimal amount = fundTransactionService.getAmountByType(FundTransactionDirectEnum.CASH_OUT, new BigDecimal("1200"));
        assertEquals(new BigDecimal("-1200"), amount);
    }

    @Test
    public void testCallMethodGetAmountByTypeFundTransactionBackgroundEnumTransferInShouldReturnPositiveValue() {
        BigDecimal amount = fundTransactionService.getAmountByType(FundTransactionBackgroundEnum.TRANSFER_IN, new BigDecimal("1200"));
        assertEquals(new BigDecimal("1200"), amount);
    }

    @Test
    public void testCallMethodGetAmountByTypeFundTransactionBackgroundEnumTaxShouldReturnPositiveValue() {
        BigDecimal amount = fundTransactionService.getAmountByType(FundTransactionBackgroundEnum.TAX, new BigDecimal("1200"));
        assertEquals(new BigDecimal("-1200"), amount);
    }

    @Test
    public void testCallMethodGetAmountByTypeFundTransactionTransferEnumTaxShouldReturnPositiveValue() {
        BigDecimal amount = fundTransactionService.getAmountByType(FundTransactionTransferEnum.TRANSFER_OUT, new BigDecimal("1200"));
        assertEquals(new BigDecimal("-1200"), amount);
    }

    private Account getDummyAccount(String id, BigDecimal activeBalance, Integer totalActivity) {
        Account account = new Account();
        account.setId(id);
        account.setActiveBalance(activeBalance);
        account.setTotalActivity(totalActivity);
        account.setCreatedDate(Instant.parse("2019-03-03T15:15:30.00Z"));
        account.setUpdatedDate(Instant.now());
        return account;
    }

    private FundTransaction getDummyFundTransaction(Account accountFrom, Account accountTo, BigDecimal trxAmount, FundTransactionTypeEnum fundTrxEnum) {
        FundTransaction fundTransaction = new FundTransaction();
        fundTransaction.setAccount(accountFrom);
        fundTransaction.setAccountTo(accountTo);
        fundTransaction.setAmount(trxAmount);
        fundTransaction.setFundTransactionType(fundTrxEnum.getName());
        fundTransaction.setCreatedDate(Instant.now());
        return fundTransaction;
    }

    private FundTransactionData generateDummyFundTransactionData(String accountId, BigDecimal totalBalance, Integer totalTrx, Instant trxDate) {
        FundTransactionData fundTransactionData = new FundTransactionData() {
            @Override
            public String getAccountId() {
                return accountId;
            }

            @Override
            public BigDecimal getTotalBalance() {
                return totalBalance;
            }

            @Override
            public Integer getTotalTrx() {
                return totalTrx;
            }

            @Override
            public Instant getTrxDate() {
                return trxDate;
            }
        };

        return fundTransactionData;
    }
}