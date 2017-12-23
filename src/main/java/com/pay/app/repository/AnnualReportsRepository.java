package com.pay.app.repository;

import com.pay.app.domain.AnnualReports;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AnnualReports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnualReportsRepository extends JpaRepository<AnnualReports, Long> {

}
