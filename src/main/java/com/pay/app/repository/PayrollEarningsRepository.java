package com.pay.app.repository;

import com.pay.app.domain.PayrollEarnings;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollEarnings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollEarningsRepository extends JpaRepository<PayrollEarnings, Long> {

}
