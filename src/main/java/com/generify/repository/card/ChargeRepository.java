package com.generify.repository.card;

import com.generify.repository.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface ChargeRepository extends JpaRepository<Charge, Long> {
    Charge findByRequestId(String requestId);
}
