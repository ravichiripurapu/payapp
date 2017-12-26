package com.pay.app.repository;

import com.pay.app.domain.EmployeeResidenceLocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeResidenceLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeResidenceLocationRepository extends JpaRepository<EmployeeResidenceLocation, Long> {

}
