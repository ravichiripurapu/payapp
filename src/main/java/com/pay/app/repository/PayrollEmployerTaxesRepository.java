package com.pay.app.repository;

import com.pay.app.domain.PayrollEmployerTaxes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollEmployerTaxes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollEmployerTaxesRepository extends JpaRepository<PayrollEmployerTaxes, Long> {

}
