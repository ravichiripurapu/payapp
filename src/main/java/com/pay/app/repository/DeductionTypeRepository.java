package com.pay.app.repository;

import com.pay.app.domain.DeductionType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeductionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeductionTypeRepository extends JpaRepository<DeductionType, Long> {

}
