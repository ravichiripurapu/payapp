package com.pay.app.repository;

import com.pay.app.domain.EmployeeBank;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeBankRepository extends JpaRepository<EmployeeBank, Long> {

}
