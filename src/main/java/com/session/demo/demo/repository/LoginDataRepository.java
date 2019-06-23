package com.session.demo.demo.repository;

import com.session.demo.demo.entity.LoginData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDataRepository extends JpaRepository<LoginData, String> {
}
