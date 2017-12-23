package com.pay.app.repository;

import com.pay.app.domain.W2Reports;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the W2Reports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface W2ReportsRepository extends JpaRepository<W2Reports, Long> {

}
