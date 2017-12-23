package com.pay.app.repository;

import com.pay.app.domain.BankAccountType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BankAccountType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountTypeRepository extends JpaRepository<BankAccountType, Long> {

}
