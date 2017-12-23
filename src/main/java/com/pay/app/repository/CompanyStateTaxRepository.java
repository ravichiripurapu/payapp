package com.pay.app.repository;

import com.pay.app.domain.CompanyStateTax;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyStateTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyStateTaxRepository extends JpaRepository<CompanyStateTax, Long> {

}
