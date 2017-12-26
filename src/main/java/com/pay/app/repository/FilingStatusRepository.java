package com.pay.app.repository;

import com.pay.app.domain.FilingStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FilingStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilingStatusRepository extends JpaRepository<FilingStatus, Long> {

}
