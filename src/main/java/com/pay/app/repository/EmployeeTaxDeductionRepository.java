package com.pay.app.repository;

import com.pay.app.domain.EmployeeTaxDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the EmployeeTaxDeduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeTaxDeductionRepository extends JpaRepository<EmployeeTaxDeduction, Long> {

}
