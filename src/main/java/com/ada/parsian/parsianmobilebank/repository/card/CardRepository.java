package com.ada.parsian.parsianmobilebank.repository.card;

import com.ada.parsian.parsianmobilebank.repository.entity.CardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface CardRepository extends JpaRepository<CardTransfer, Long> {
}
