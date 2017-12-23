package com.pay.app.repository;

import com.pay.app.domain.PayrollSummary;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollSummaryRepository extends JpaRepository<PayrollSummary, Long> {

}
