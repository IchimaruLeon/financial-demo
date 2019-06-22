package com.session.demo.demo.service;

import com.session.demo.demo.entity.AccountType;
import com.session.demo.demo.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    public AccountType getRandom() {
        return accountTypeRepository.findAll()
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        collected ->{
                            Collections.shuffle(collected);
                            return collected.stream();
                        })).limit(1).collect(Collectors.toList()).get(0);
    }

}
