package com.pay.app.repository;

import com.pay.app.domain.QuarterlyReports;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the QuarterlyReports entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuarterlyReportsRepository extends JpaRepository<QuarterlyReports, Long> {

}
