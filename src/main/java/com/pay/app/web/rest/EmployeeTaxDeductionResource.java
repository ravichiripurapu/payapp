package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeTaxDeduction;

import com.pay.app.repository.EmployeeTaxDeductionRepository;
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
public class EmployeeTaxDeductionResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTaxDeductionResource.class);

    private static final String ENTITY_NAME = "employeeFederalTax";

    private final EmployeeTaxDeductionRepository employeeTaxDeductionRepository;

    public EmployeeTaxDeductionResource(EmployeeTaxDeductionRepository employeeTaxDeductionRepository) {
        this.employeeTaxDeductionRepository = employeeTaxDeductionRepository;
    }

    /**
     * POST  /employee-tax-deduction : Create a new employeeTaxDeduction.
     *
     * @param employeeTaxDeduction the employeeTaxDeduction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeTaxDeduction, or with status 400 (Bad Request) if the employeeTaxDeduction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-tax-deduction")
    @Timed
    public ResponseEntity<EmployeeTaxDeduction> createEmployeeFederalTax(@RequestBody EmployeeTaxDeduction employeeTaxDeduction) throws URISyntaxException {
        log.debug("REST request to save EmployeeTaxDeduction : {}", employeeTaxDeduction);
        if (employeeTaxDeduction.getId() != null) {
            throw new BadRequestAlertException("A new employeeTaxDeduction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeTaxDeduction result = employeeTaxDeductionRepository.save(employeeTaxDeduction);
        return ResponseEntity.created(new URI("/api/employee-tax-deduction/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-tax-deduction : Updates an existing employeeTaxDeduction.
     *
     * @param employeeTaxDeduction the employeeTaxDeduction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeTaxDeduction,
     * or with status 400 (Bad Request) if the employeeTaxDeduction is not valid,
     * or with status 500 (Internal Server Error) if the employeeTaxDeduction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-tax-deduction")
    @Timed
    public ResponseEntity<EmployeeTaxDeduction> updateEmployeeFederalTax(@RequestBody EmployeeTaxDeduction employeeTaxDeduction) throws URISyntaxException {
        log.debug("REST request to update EmployeeTaxDeduction : {}", employeeTaxDeduction);
        if (employeeTaxDeduction.getId() == null) {
            return createEmployeeFederalTax(employeeTaxDeduction);
        }
        EmployeeTaxDeduction result = employeeTaxDeductionRepository.save(employeeTaxDeduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeTaxDeduction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-tax-deduction : get all the employeeFederalTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeFederalTaxes in body
     */
    @GetMapping("/employee-tax-deduction")
    @Timed
    public List<EmployeeTaxDeduction> getAllEmployeeFederalTaxes() {
        log.debug("REST request to get all EmployeeFederalTaxes");
        return employeeTaxDeductionRepository.findAll();
        }

    /**
     * GET  /employee-tax-deduction/:id : get the "id" employeeFederalTax.
     *
     * @param id the id of the employeeFederalTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeFederalTax, or with status 404 (Not Found)
     */
    @GetMapping("/employee-tax-deduction/{id}")
    @Timed
    public ResponseEntity<EmployeeTaxDeduction> getEmployeeFederalTax(@PathVariable Long id) {
        log.debug("REST request to get EmployeeTaxDeduction : {}", id);
        EmployeeTaxDeduction employeeTaxDeduction = employeeTaxDeductionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeTaxDeduction));
    }

    /**
     * DELETE  /employee-tax-deduction/:id : delete the "id" employeeFederalTax.
     *
     * @param id the id of the employeeFederalTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-tax-deduction/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeFederalTax(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeTaxDeduction : {}", id);
        employeeTaxDeductionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
