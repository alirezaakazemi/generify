package com.ada.parsian.parsianmobilebank.repository.card;

import com.ada.parsian.parsianmobilebank.repository.entity.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface ChargeRepository extends JpaRepository<Charge, Long> {
    Charge findByRequestId(String requestId);
}
