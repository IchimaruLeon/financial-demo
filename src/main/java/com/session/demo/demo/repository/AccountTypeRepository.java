package com.session.demo.demo.repository;

import com.session.demo.demo.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, String> {
}
