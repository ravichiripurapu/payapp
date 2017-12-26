package com.pay.app.repository;

import com.pay.app.domain.CompensationType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CompensationType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompensationTypeRepository extends JpaRepository<CompensationType, Long> {

}
