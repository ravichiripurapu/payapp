package com.pay.app.repository;

import com.pay.app.domain.LocalTaxes;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LocalTaxes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalTaxesRepository extends JpaRepository<LocalTaxes, Long> {

}
