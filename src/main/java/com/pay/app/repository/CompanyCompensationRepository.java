package com.pay.app.repository;

import com.pay.app.domain.CompanyCompensation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyCompensation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCompensationRepository extends JpaRepository<CompanyCompensation, Long> {

}
