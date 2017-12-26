package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollEmployeeTaxes;

import com.pay.app.repository.PayrollEmployeeTaxesRepository;
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
 * REST controller for managing PayrollEmployeeTaxes.
 */
@RestController
@RequestMapping("/api")
public class PayrollEmployeeTaxesResource {

    private final Logger log = LoggerFactory.getLogger(PayrollEmployeeTaxesResource.class);

    private static final String ENTITY_NAME = "payrollEmployeeTaxes";

    private final PayrollEmployeeTaxesRepository payrollEmployeeTaxesRepository;

    public PayrollEmployeeTaxesResource(PayrollEmployeeTaxesRepository payrollEmployeeTaxesRepository) {
        this.payrollEmployeeTaxesRepository = payrollEmployeeTaxesRepository;
    }

    /**
     * POST  /payroll-employee-taxes : Create a new payrollEmployeeTaxes.
     *
     * @param payrollEmployeeTaxes the payrollEmployeeTaxes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollEmployeeTaxes, or with status 400 (Bad Request) if the payrollEmployeeTaxes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-employee-taxes")
    @Timed
    public ResponseEntity<PayrollEmployeeTaxes> createPayrollEmployeeTaxes(@RequestBody PayrollEmployeeTaxes payrollEmployeeTaxes) throws URISyntaxException {
        log.debug("REST request to save PayrollEmployeeTaxes : {}", payrollEmployeeTaxes);
        if (payrollEmployeeTaxes.getId() != null) {
            throw new BadRequestAlertException("A new payrollEmployeeTaxes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollEmployeeTaxes result = payrollEmployeeTaxesRepository.save(payrollEmployeeTaxes);
        return ResponseEntity.created(new URI("/api/payroll-employee-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-employee-taxes : Updates an existing payrollEmployeeTaxes.
     *
     * @param payrollEmployeeTaxes the payrollEmployeeTaxes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollEmployeeTaxes,
     * or with status 400 (Bad Request) if the payrollEmployeeTaxes is not valid,
     * or with status 500 (Internal Server Error) if the payrollEmployeeTaxes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-employee-taxes")
    @Timed
    public ResponseEntity<PayrollEmployeeTaxes> updatePayrollEmployeeTaxes(@RequestBody PayrollEmployeeTaxes payrollEmployeeTaxes) throws URISyntaxException {
        log.debug("REST request to update PayrollEmployeeTaxes : {}", payrollEmployeeTaxes);
        if (payrollEmployeeTaxes.getId() == null) {
            return createPayrollEmployeeTaxes(payrollEmployeeTaxes);
        }
        PayrollEmployeeTaxes result = payrollEmployeeTaxesRepository.save(payrollEmployeeTaxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollEmployeeTaxes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-employee-taxes : get all the payrollEmployeeTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollEmployeeTaxes in body
     */
    @GetMapping("/payroll-employee-taxes")
    @Timed
    public List<PayrollEmployeeTaxes> getAllPayrollEmployeeTaxes() {
        log.debug("REST request to get all PayrollEmployeeTaxes");
        return payrollEmployeeTaxesRepository.findAll();
        }

    /**
     * GET  /payroll-employee-taxes/:id : get the "id" payrollEmployeeTaxes.
     *
     * @param id the id of the payrollEmployeeTaxes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollEmployeeTaxes, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-employee-taxes/{id}")
    @Timed
    public ResponseEntity<PayrollEmployeeTaxes> getPayrollEmployeeTaxes(@PathVariable Long id) {
        log.debug("REST request to get PayrollEmployeeTaxes : {}", id);
        PayrollEmployeeTaxes payrollEmployeeTaxes = payrollEmployeeTaxesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollEmployeeTaxes));
    }

    /**
     * DELETE  /payroll-employee-taxes/:id : delete the "id" payrollEmployeeTaxes.
     *
     * @param id the id of the payrollEmployeeTaxes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-employee-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollEmployeeTaxes(@PathVariable Long id) {
        log.debug("REST request to delete PayrollEmployeeTaxes : {}", id);
        payrollEmployeeTaxesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
