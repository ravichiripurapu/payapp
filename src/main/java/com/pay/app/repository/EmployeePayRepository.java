package com.pay.app.repository;

import com.pay.app.domain.EmployeePay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the EmployeePayEarning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePayRepository extends JpaRepository<EmployeePay, Long> {

}
