package com.pay.app.repository;

import com.pay.app.domain.PayrollFrequency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollFrequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollFrequencyRepository extends JpaRepository<PayrollFrequency, Long> {

}
