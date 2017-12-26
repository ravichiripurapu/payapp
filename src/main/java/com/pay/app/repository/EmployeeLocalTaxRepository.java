package com.pay.app.repository;

import com.pay.app.domain.EmployeeLocalTax;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeLocalTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeLocalTaxRepository extends JpaRepository<EmployeeLocalTax, Long> {

}
