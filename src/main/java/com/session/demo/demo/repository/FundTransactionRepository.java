package com.session.demo.demo.repository;

import com.session.demo.demo.dto.internalDTO.FundTransactionData;
import com.session.demo.demo.entity.FundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FundTransactionRepository extends JpaRepository<FundTransaction, String> {

    @Query(value = "SELECT a.id AS accountId, COALESCE(SUM(ft.amount), 0) AS totalBalance, COUNT(COALESCE(ft.id,0)) AS totalTrx, current_timestamp() AS trxDate FROM fund_transaction ft " +
            "INNER JOIN account a ON ft.account_id=a.id AND a.id =:accountId", nativeQuery = true)
    FundTransactionData getActiveBalance(@Param("accountId") String accountId);

}
