package com.pay.app.repository;

import com.pay.app.domain.EmployeeFederalTax;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeFederalTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeFederalTaxRepository extends JpaRepository<EmployeeFederalTax, Long> {

}
