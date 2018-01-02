package com.pay.app.repository;

import com.pay.app.domain.EmployeeStateTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the EmployeeTaxDeduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeStateTaxRepository extends JpaRepository<EmployeeStateTax, Long> {

}
