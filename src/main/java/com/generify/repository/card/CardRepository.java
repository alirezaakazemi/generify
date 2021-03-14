package com.generify.repository.card;

import com.generify.repository.entity.CardTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface CardRepository extends JpaRepository<CardTransfer, Long> {
}
