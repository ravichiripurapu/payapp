package com.pay.app.repository;

import com.pay.app.domain.CompanyContactType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyContactType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyContactTypeRepository extends JpaRepository<CompanyContactType, Long> {

}
