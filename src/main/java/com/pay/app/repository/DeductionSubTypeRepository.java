package com.pay.app.repository;

import com.pay.app.domain.DeductionSubType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeductionSubType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeductionSubTypeRepository extends JpaRepository<DeductionSubType, Long> {

}
