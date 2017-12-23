package com.pay.app.repository;

import com.pay.app.domain.CompanyBank;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyBankRepository extends JpaRepository<CompanyBank, Long> {

}
