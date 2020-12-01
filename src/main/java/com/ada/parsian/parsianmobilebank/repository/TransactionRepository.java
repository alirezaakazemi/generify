package com.ada.parsian.parsianmobilebank.repository;

import com.ada.parsian.parsianmobilebank.repository.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //    @Override
    //    <S extends Transaction> S save(S s){
    //        return save(s);
    //    }
}
