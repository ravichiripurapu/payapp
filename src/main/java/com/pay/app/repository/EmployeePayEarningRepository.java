package com.pay.app.repository;

import com.pay.app.domain.EmployeePayEarning;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeePayEarning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePayEarningRepository extends JpaRepository<EmployeePayEarning, Long> {

}
