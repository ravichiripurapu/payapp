package com.pay.app.repository;

import com.pay.app.domain.PayrollEmployeeTaxes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollEmployeeTaxes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollEmployeeTaxesRepository extends JpaRepository<PayrollEmployeeTaxes, Long> {

}
