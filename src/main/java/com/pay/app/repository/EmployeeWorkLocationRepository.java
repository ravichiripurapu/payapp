package com.pay.app.repository;

import com.pay.app.domain.EmployeeWorkLocation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeeWorkLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeWorkLocationRepository extends JpaRepository<EmployeeWorkLocation, Long> {

}
