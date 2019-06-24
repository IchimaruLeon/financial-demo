package com.session.demo.demo.service;

import com.session.demo.demo.dto.internalDTO.AccountDTO;
import com.session.demo.demo.entity.Account;
import com.session.demo.demo.entity.UserData;
import com.session.demo.demo.handler.AppException;
import com.session.demo.demo.handler.ResponseCodeEnum;
import com.session.demo.demo.repository.AccountRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@Setter
public class AccountService {

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    private AccountRepository accountRepository;

    private ModelMapper modelMapper = new ModelMapper();

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
        Optional<Account> accountOptional = accountRepository.findByIdAndDeleted(id, Boolean.FALSE);
        if(accountOptional.isPresent()) {
            return accountOptional;
        } else {
            throw new AppException(ResponseCodeEnum.NOT_FOUND, String.format("Account with id %s not found", id));
        }
    }

    public AccountDTO findByIdForDTO(String id) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AccountDTO accountDTO = modelMapper.map(findAccountById(id), AccountDTO.class);
        return accountDTO;
    }

    public void updateAmount(Account account, BigDecimal activeBalance, Integer totalData) {
        account.setActiveBalance(activeBalance);
        account.setTotalActivity(totalData);
        save(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    private Account findAccountById(String id) {
        Optional<Account> accountOptional = accountRepository.findByIdAndDeleteFull(id, Boolean.FALSE);
        if(accountOptional.isPresent()){
            return accountOptional.get();
        }else {
            throw new AppException(ResponseCodeEnum.NOT_FOUND, String.format("Account with id %s not found", id));
        }
    }

}
