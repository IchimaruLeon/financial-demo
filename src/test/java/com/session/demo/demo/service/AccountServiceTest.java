package com.session.demo.demo.service;

import com.session.demo.demo.dto.internalDTO.AccountDTO;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.AccountType;
import com.session.demo.demo.entity.UserData;
import com.session.demo.demo.handler.AppException;
import com.session.demo.demo.handler.ResponseCodeEnum;
import com.session.demo.demo.helper.StringUtils;
import com.session.demo.demo.repository.AccountRepository;
import com.sun.org.apache.xpath.internal.Arg;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountTypeService accountTypeService;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testCallMethodCreateShouldReturnAccountDataWhenValidUserDataGivenAsExpected() {
        UserData userData = getDummyUserData();
        Mockito.when(accountTypeService.getRandom()).thenReturn(getDummyAccountType("SAMPLE"));

        accountService.create(userData);

        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        Mockito.verify(accountRepository, Mockito.times(1)).save(accountArgumentCaptor.capture());
        assertNotNull(accountArgumentCaptor.getValue());
        assertEquals(userData, accountArgumentCaptor.getValue().getUserData());
        assertEquals(BigDecimal.ZERO, accountArgumentCaptor.getValue().getActiveBalance());
        assertEquals(Integer.valueOf(0), accountArgumentCaptor.getValue().getTotalActivity());
        assertEquals("SAMPLE", accountArgumentCaptor.getValue().getAccountType().getCode());
        assertEquals("DUMMY ACCOUNT TYPE SAMPLE", accountArgumentCaptor.getValue().getAccountType().getName());
        assertNotNull(accountArgumentCaptor.getValue().getCreatedDate());
    }

    @Test
    void testCallMethodFindByIdShouldReturnExpectedAccountData() {
        Mockito.when(accountRepository.findByIdAndDeleted(Mockito.eq("12123"), Mockito.eq(Boolean.FALSE))).thenReturn(Optional.of(getDummyAccount()));

        Optional<Account> accountOptional = accountService.findById("12123");
        assertTrue(accountOptional.isPresent());
        assertEquals("DUMMY", accountOptional.get().getAccountType().getCode());
        assertEquals("DUMMY ACCOUNT TYPE DUMMY", accountOptional.get().getAccountType().getName());
        assertEquals(BigDecimal.ZERO, accountOptional.get().getActiveBalance());
        assertEquals(Integer.valueOf(0), accountOptional.get().getTotalActivity());
    }

    @Test
    void testCallMethodFindByIdShouldThrowAppExceptionWhenAccountIsNotPresent() {
        Mockito.when(accountRepository.findByIdAndDeleted(Mockito.eq("12123"), Mockito.eq(Boolean.FALSE))).thenReturn(Optional.empty());

        AppException ex;

        ex = assertThrows(AppException.class, () -> {
            accountService.findById("12123");
        });
        assertEquals(ResponseCodeEnum.NOT_FOUND, ex.getCode());
        assertEquals("Account with id 12123 not found", ex.getMessageKey());
    }

    @Test
    void findCallMethodByIdForDTOShouldReturnAccountDTOModelAsExpected() {
        Mockito.when(accountRepository.findByIdAndDeleteFull(Mockito.eq("12123"), Mockito.eq(Boolean.FALSE))).thenReturn(Optional.of(getDummyAccount()));

        AccountDTO accountDTO = accountService.findByIdForDTO("12123");
        assertNotNull(accountDTO);
        assertEquals("ACC001",accountDTO.getId());
        assertEquals(BigDecimal.ZERO,accountDTO.getActiveBalance());
        assertEquals("SAMPLE FIRST NAME",accountDTO.getFirstName());
        assertEquals("SAMPLE LAST NAME",accountDTO.getLastName());
        assertEquals("userexample-01@sample-mail.com",accountDTO.getEmail());
        assertEquals("294014001001",accountDTO.getPhoneNo());
        assertEquals("FEMALE",accountDTO.getGender());
        assertEquals(Integer.valueOf(0), accountDTO.getTotalActivity());
        assertEquals("DUMMY ACCOUNT TYPE DUMMY", accountDTO.getAccountTypeName());
        assertNotNull(accountDTO.getCreatedDate());
        assertNotNull(accountDTO.getUpdatedDate());
        assertEquals(accountDTO.getCreatedDate(), accountDTO.getUpdatedDate());
    }

    @Test
    void findCallMethodByIdForDTOShouldThrowAppExceptionWhenAccountIsNotPresent() {
        Mockito.when(accountRepository.findByIdAndDeleteFull(Mockito.eq("12123"), Mockito.eq(Boolean.FALSE))).thenReturn(Optional.empty());

        AppException ex;

        ex = assertThrows(AppException.class, () -> {
            accountService.findByIdForDTO("12123");
        });

        assertEquals(ResponseCodeEnum.NOT_FOUND, ex.getCode());
        assertEquals("Account with id 12123 not found", ex.getMessageKey());
    }

    @Test
    void testCallMethodUpdateAmountShouldUpdateFieldActiveBalanceAndTotalActivityAndCallSaveRepositoryAsExpected() {
        Account account = getDummyAccount();
        accountService.updateAmount(account, new BigDecimal("1294991.23"), 12);
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        Mockito.verify(accountRepository, Mockito.times(1)).save(accountArgumentCaptor.capture());
        assertNotNull(accountArgumentCaptor.getValue());

        assertEquals(new BigDecimal("1294991.23"), accountArgumentCaptor.getValue().getActiveBalance());
        assertEquals(Integer.valueOf(12), accountArgumentCaptor.getValue().getTotalActivity());
    }

    @Test
    void testCallMethodSaveShouldCallSaveOnRepositoryAsExpected() {
        Account account = getDummyAccount();
        accountService.save(account);
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.eq(account));
    }

    private UserData getDummyUserData() {
        UserData userData = new UserData();
        userData.setId("USD001");
        userData.setFirstName("SAMPLE FIRST NAME");
        userData.setLastName("SAMPLE LAST NAME");
        userData.setEmail("userexample-01@sample-mail.com");
        userData.setPhoneNo("294014001001");
        userData.setGender("FEMALE");
        return userData;
    }

    private Account getDummyAccount() {
        Instant currentTime = Instant.now();
        Account account = new Account();
        account.setId("ACC001");
        account.setActiveBalance(BigDecimal.ZERO);
        account.setTotalActivity(0);
        account.setAccountType(getDummyAccountType("DUMMY"));
        account.setUserData(getDummyUserData());
        account.setCreatedDate(currentTime);
        account.setUpdatedDate(currentTime);
        return account;
    }

    private AccountType getDummyAccountType(String code) {
        AccountType accountType = new AccountType();
        accountType.setId("ACT001");
        accountType.setCode(code);
        accountType.setName("DUMMY ACCOUNT TYPE ".concat(code));
        accountType.setCreatedDate(Instant.now());
        return accountType;
    }
}