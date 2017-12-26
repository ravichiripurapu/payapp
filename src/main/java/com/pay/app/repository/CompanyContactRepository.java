package com.pay.app.repository;

import com.pay.app.domain.CompanyContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompanyContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyContactRepository extends JpaRepository<CompanyContact, Long> {

}
