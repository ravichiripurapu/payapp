package com.pay.app.repository;

import com.pay.app.domain.TaxPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the PayrollSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxPaymentRepository extends JpaRepository<TaxPayment, Long> {

}
