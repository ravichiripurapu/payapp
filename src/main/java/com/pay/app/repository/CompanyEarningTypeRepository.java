package com.pay.app.repository;

import com.pay.app.domain.CompanyEarningType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyEarningType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyEarningTypeRepository extends JpaRepository<CompanyEarningType, Long> {

}
