package com.pay.app.repository;

import com.pay.app.domain.CompanyLocalTax;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyLocalTax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyLocalTaxRepository extends JpaRepository<CompanyLocalTax, Long> {

}
