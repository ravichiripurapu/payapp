package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyDeduction;

import com.pay.app.repository.CompanyDeductionRepository;
import com.pay.app.web.rest.errors.BadRequestAlertException;
import com.pay.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyDeduction.
 */
@RestController
@RequestMapping("/api")
public class CompanyDeductionResource {

    private final Logger log = LoggerFactory.getLogger(CompanyDeductionResource.class);

    private static final String ENTITY_NAME = "companyDeduction";

    private final CompanyDeductionRepository companyDeductionRepository;

    public CompanyDeductionResource(CompanyDeductionRepository companyDeductionRepository) {
        this.companyDeductionRepository = companyDeductionRepository;
    }

    /**
     * POST  /company-deductions : Create a new companyDeduction.
     *
     * @param companyDeduction the companyDeduction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDeduction, or with status 400 (Bad Request) if the companyDeduction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-deductions")
    @Timed
    public ResponseEntity<CompanyDeduction> createCompanyDeduction(@RequestBody CompanyDeduction companyDeduction) throws URISyntaxException {
        log.debug("REST request to save CompanyDeduction : {}", companyDeduction);
        if (companyDeduction.getId() != null) {
            throw new BadRequestAlertException("A new companyDeduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyDeduction result = companyDeductionRepository.save(companyDeduction);
        return ResponseEntity.created(new URI("/api/company-deductions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-deductions : Updates an existing companyDeduction.
     *
     * @param companyDeduction the companyDeduction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDeduction,
     * or with status 400 (Bad Request) if the companyDeduction is not valid,
     * or with status 500 (Internal Server Error) if the companyDeduction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-deductions")
    @Timed
    public ResponseEntity<CompanyDeduction> updateCompanyDeduction(@RequestBody CompanyDeduction companyDeduction) throws URISyntaxException {
        log.debug("REST request to update CompanyDeduction : {}", companyDeduction);
        if (companyDeduction.getId() == null) {
            return createCompanyDeduction(companyDeduction);
        }
        CompanyDeduction result = companyDeductionRepository.save(companyDeduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDeduction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-deductions : get all the companyDeductions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyDeductions in body
     */
    @GetMapping("/company-deductions")
    @Timed
    public List<CompanyDeduction> getAllCompanyDeductions() {
        log.debug("REST request to get all CompanyDeductions");
        return companyDeductionRepository.findAll();
        }

    /**
     * GET  /company-deductions/:id : get the "id" companyDeduction.
     *
     * @param id the id of the companyDeduction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDeduction, or with status 404 (Not Found)
     */
    @GetMapping("/company-deductions/{id}")
    @Timed
    public ResponseEntity<CompanyDeduction> getCompanyDeduction(@PathVariable Long id) {
        log.debug("REST request to get CompanyDeduction : {}", id);
        CompanyDeduction companyDeduction = companyDeductionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyDeduction));
    }

    /**
     * DELETE  /company-deductions/:id : delete the "id" companyDeduction.
     *
     * @param id the id of the companyDeduction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-deductions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyDeduction(@PathVariable Long id) {
        log.debug("REST request to delete CompanyDeduction : {}", id);
        companyDeductionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
