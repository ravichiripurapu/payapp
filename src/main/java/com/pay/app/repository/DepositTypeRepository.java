package com.pay.app.repository;

import com.pay.app.domain.DepositType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DepositType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositTypeRepository extends JpaRepository<DepositType, Long> {

}
