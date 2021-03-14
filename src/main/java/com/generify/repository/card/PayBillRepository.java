package com.generify.repository.card;

import com.generify.repository.entity.PayBill;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface PayBillRepository extends JpaRepository<PayBill, Long> {
}
