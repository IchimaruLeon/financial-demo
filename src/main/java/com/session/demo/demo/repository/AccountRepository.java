package com.session.demo.demo.repository;

import com.session.demo.demo.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByIdAndDeleted(String id, Boolean value);

    @EntityGraph(attributePaths = {"userData","accountType"})
    @Query("select a from Account a where a.id=:id and a.deleted=:isDelete")
    Optional<Account> findByIdAndDeleteFull(@Param("id") String id, @Param("isDelete") Boolean isDelete);
}

