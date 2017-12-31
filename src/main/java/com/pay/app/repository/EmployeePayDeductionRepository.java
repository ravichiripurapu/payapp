package com.pay.app.repository;

import com.pay.app.domain.EmployeePayDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the EmployeePayEarning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePayDeductionRepository extends JpaRepository<EmployeePayDeduction, Long> {

}
