package com.session.demo.demo.service;

import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.UserData;
import com.session.demo.demo.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private AccountRepository accountRepository;

    public Account create(UserData userData) {
        Account account = new Account();
        account.setCreatedDate(Instant.now());
        account.setUserData(userData);
        account.setActiveBalance(BigDecimal.ZERO);
        account.setTotalActivity(0);
        account.setAccountType(accountTypeService.getRandom());
        return save(account);
    }

    public Optional<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    public void updateAmount(Account account, BigDecimal activeBalance, Integer totalData) {
        account.setActiveBalance(activeBalance);
        account.setTotalActivity(totalData);
        save(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
