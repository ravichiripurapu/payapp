package com.pay.app.repository;

import com.pay.app.domain.FutaExemptReasonCode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FutaExemptReasonCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FutaExemptReasonCodeRepository extends JpaRepository<FutaExemptReasonCode, Long> {

}
