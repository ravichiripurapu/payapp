package com.pay.app.repository;

import com.pay.app.domain.CompanyEarning;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyEarning entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyEarningRepository extends JpaRepository<CompanyEarning, Long> {

}
