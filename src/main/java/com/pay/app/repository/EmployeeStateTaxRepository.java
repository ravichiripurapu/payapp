package com.pay.app.repository;

import com.pay.app.domain.EmployeeStateTax;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeStateTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeStateTaxRepository extends JpaRepository<EmployeeStateTax, Long> {

}
