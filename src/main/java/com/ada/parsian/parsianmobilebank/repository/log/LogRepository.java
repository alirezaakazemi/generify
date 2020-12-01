package com.ada.parsian.parsianmobilebank.repository.log;

import com.ada.parsian.parsianmobilebank.repository.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface LogRepository extends JpaRepository<TransactionLog, Long> {
}
