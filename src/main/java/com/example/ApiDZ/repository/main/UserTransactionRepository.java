package com.example.ApiDZ.repository.main;

import com.example.ApiDZ.domain.main.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional("mainTransactionManager")
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

    @Query("SELECT COUNT(t) FROM UserTransaction t WHERE t.userId = :userId AND t.productType = :productType")
    long countByUserIdAndProductType(@Param("userId") String userId, @Param("productType") String productType);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM UserTransaction t WHERE t.userId = :userId AND t.productType = :productType AND t.transactionType = :txType")
    long sumAmountByUserProductTxType(@Param("userId") String userId, @Param("productType") String productType, @Param("txType") String txType);
}
