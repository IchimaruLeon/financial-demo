package com.session.demo.demo.repository;

import com.session.demo.demo.dto.internalDTO.FundTransactionData;
import com.session.demo.demo.entity.FundTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundTransactionRepository extends JpaRepository<FundTransaction, String> {

    @Query(value = "SELECT a.id AS accountFrom, COALESCE(SUM(ft.amount), 0) AS totalBalance, COUNT(COALESCE(ft.id,0)) AS totalTrx, current_timestamp() AS trxDate FROM fund_transaction ft " +
            "INNER JOIN account a ON ft.account_id=a.id AND a.id =:accountFrom", nativeQuery = true)
    FundTransactionData getActiveBalance(@Param("accountFrom") String accountId);

    Optional<FundTransaction> findByIdAndAccountId(String id, String accountId);

    Page<FundTransaction> findByAccountId(String accountId, Pageable pageable);
}
