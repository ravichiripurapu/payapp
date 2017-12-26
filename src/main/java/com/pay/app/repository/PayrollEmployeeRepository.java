package com.pay.app.repository;

import com.pay.app.domain.PayrollEmployee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollEmployee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollEmployeeRepository extends JpaRepository<PayrollEmployee, Long> {

}
