package com.pay.app.repository;

import com.pay.app.domain.DepositFrequency;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DepositFrequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositFrequencyRepository extends JpaRepository<DepositFrequency, Long> {

}
