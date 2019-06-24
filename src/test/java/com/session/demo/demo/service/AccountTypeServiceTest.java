package com.session.demo.demo.service;

import com.session.demo.demo.entity.AccountType;
import com.session.demo.demo.repository.AccountTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountTypeServiceTest {

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @InjectMocks
    private AccountTypeService accountTypeService;

    @Test
    public void testCallMethodGetRandomShouldReturnOneRandomAccountTypeFromDummyGiven() {
        List<AccountType> accountTypeList = Arrays.asList(
                createDummyAccountType("CODE-A", "ACT_CODE_A"),
                createDummyAccountType("CODE-B", "ACT_CODE_B"),
                createDummyAccountType("CODE-C", "ACT_CODE_C"),
                createDummyAccountType("CODE-D", "ACT_CODE_D"),
                createDummyAccountType("CODE-E", "ACT_CODE_E")
        );

        Mockito.when(accountTypeRepository.findAll()).thenReturn(accountTypeList);
        List<AccountType> accountTypeListResult = new ArrayList<>();

        for(int a = 0; a < 10; a++) {
            AccountType accountType = accountTypeService.getRandom();
            accountTypeListResult.add(accountType);
        }
        assertNotSame("CODE-A,CODE-B,CODE-C,CODE-D,CODE-E,CODE-A,CODE-B,CODE-C,CODE-D,CODE-E", accountTypeListResult.stream().map(AccountType::getCode).collect(Collectors.joining(",")));
        assertEquals(10, accountTypeListResult.size());
        Mockito.verify(accountTypeRepository, Mockito.times(10)).findAll();
        
    }

    private AccountType createDummyAccountType(String code, String name) {
        AccountType accountType = new AccountType();
        accountType.setCode(code);
        accountType.setName(name);
        accountType.setCreatedDate(Instant.now());
        return accountType;
    }
}