package com.session.demo.demo.repository;

import com.session.demo.demo.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository <UserData, String> {
}
