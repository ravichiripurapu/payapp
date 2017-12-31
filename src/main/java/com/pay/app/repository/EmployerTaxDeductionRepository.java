package com.pay.app.repository;

import com.pay.app.domain.EmployerTaxDeduction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeTaxDeduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployerTaxDeductionRepository extends JpaRepository<EmployerTaxDeduction, Long> {

}
