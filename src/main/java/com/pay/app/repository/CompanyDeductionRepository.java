package com.pay.app.repository;

import com.pay.app.domain.CompanyDeduction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyDeduction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyDeductionRepository extends JpaRepository<CompanyDeduction, Long> {

}
