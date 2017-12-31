package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployerTaxDeduction;
import com.pay.app.repository.EmployerTaxDeductionRepository;
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
 * REST controller for managing EmployeeTaxDeduction.
 */
@RestController
@RequestMapping("/api")
public class EmployerTaxDeductionResource {

    private final Logger log = LoggerFactory.getLogger(EmployerTaxDeductionResource.class);

    private static final String ENTITY_NAME = "employerFederalTax";

    private final EmployerTaxDeductionRepository employerTaxDeductionRepository;

    public EmployerTaxDeductionResource(EmployerTaxDeductionRepository employerTaxDeductionRepository) {
        this.employerTaxDeductionRepository = employerTaxDeductionRepository;
    }

    /**
     * POST  /employer-tax-deduction : Create a new employeeTaxDeduction.
     *
     * @param employerTaxDeduction the employeeTaxDeduction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeTaxDeduction, or with status 400 (Bad Request) if the employeeTaxDeduction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employer-tax-deduction")
    @Timed
    public ResponseEntity<EmployerTaxDeduction> createEmployerFederalTax(@RequestBody EmployerTaxDeduction employerTaxDeduction) throws URISyntaxException {
        log.debug("REST request to save EmployeeTaxDeduction : {}", employerTaxDeduction);
        if (employerTaxDeduction.getId() != null) {
            throw new BadRequestAlertException("A new employeeTaxDeduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployerTaxDeduction result = employerTaxDeductionRepository.save(employerTaxDeduction);
        return ResponseEntity.created(new URI("/api/employee-tax-deduction/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employer-tax-deduction : Updates an existing employeeTaxDeduction.
     *
     * @param employerTaxDeduction the employeeTaxDeduction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeTaxDeduction,
     * or with status 400 (Bad Request) if the employeeTaxDeduction is not valid,
     * or with status 500 (Internal Server Error) if the employeeTaxDeduction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employer-tax-deduction")
    @Timed
    public ResponseEntity<EmployerTaxDeduction> updateEmployeeFederalTax(@RequestBody EmployerTaxDeduction employerTaxDeduction) throws URISyntaxException {
        log.debug("REST request to update EmployeeTaxDeduction : {}", employerTaxDeduction);
        if (employerTaxDeduction.getId() == null) {
            return createEmployerFederalTax(employerTaxDeduction);
        }
        EmployerTaxDeduction result = employerTaxDeductionRepository.save(employerTaxDeduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employerTaxDeduction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employer-tax-deduction : get all the employeeFederalTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeFederalTaxes in body
     */
    @GetMapping("/employer-tax-deduction")
    @Timed
    public List<EmployerTaxDeduction> getAllEmployerFederalTaxes() {
        log.debug("REST request to get all EmployeeFederalTaxes");
        return employerTaxDeductionRepository.findAll();
        }

    /**
     * GET  /employer-tax-deduction/:id : get the "id" employeeFederalTax.
     *
     * @param id the id of the employeeFederalTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeFederalTax, or with status 404 (Not Found)
     */
    @GetMapping("/employer-tax-deduction/{id}")
    @Timed
    public ResponseEntity<EmployerTaxDeduction> getEmployeeFederalTax(@PathVariable Long id) {
        log.debug("REST request to get EmployeeTaxDeduction : {}", id);
        EmployerTaxDeduction employerTaxDeduction = employerTaxDeductionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employerTaxDeduction));
    }

    /**
     * DELETE  /employer-tax-deduction/:id : delete the "id" employeeFederalTax.
     *
     * @param id the id of the employeeFederalTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employer-tax-deduction/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeFederalTax(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeTaxDeduction : {}", id);
        employerTaxDeductionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
