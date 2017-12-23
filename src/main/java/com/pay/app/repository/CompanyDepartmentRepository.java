package com.pay.app.repository;

import com.pay.app.domain.CompanyDepartment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDepartmentRepository extends JpaRepository<CompanyDepartment, Long> {

}
