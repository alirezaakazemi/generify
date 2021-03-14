package com.generify.repository;

import com.generify.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
//@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByMobileNumber(String mobileNumber);
}
