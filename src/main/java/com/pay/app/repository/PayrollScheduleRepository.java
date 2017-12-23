package com.pay.app.repository;

import com.pay.app.domain.PayrollSchedule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PayrollSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollScheduleRepository extends JpaRepository<PayrollSchedule, Long> {

}
