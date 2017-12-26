package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollEmployerTaxes;

import com.pay.app.repository.PayrollEmployerTaxesRepository;
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
 * REST controller for managing PayrollEmployerTaxes.
 */
@RestController
@RequestMapping("/api")
public class PayrollEmployerTaxesResource {

    private final Logger log = LoggerFactory.getLogger(PayrollEmployerTaxesResource.class);

    private static final String ENTITY_NAME = "payrollEmployerTaxes";

    private final PayrollEmployerTaxesRepository payrollEmployerTaxesRepository;

    public PayrollEmployerTaxesResource(PayrollEmployerTaxesRepository payrollEmployerTaxesRepository) {
        this.payrollEmployerTaxesRepository = payrollEmployerTaxesRepository;
    }

    /**
     * POST  /payroll-employer-taxes : Create a new payrollEmployerTaxes.
     *
     * @param payrollEmployerTaxes the payrollEmployerTaxes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollEmployerTaxes, or with status 400 (Bad Request) if the payrollEmployerTaxes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-employer-taxes")
    @Timed
    public ResponseEntity<PayrollEmployerTaxes> createPayrollEmployerTaxes(@RequestBody PayrollEmployerTaxes payrollEmployerTaxes) throws URISyntaxException {
        log.debug("REST request to save PayrollEmployerTaxes : {}", payrollEmployerTaxes);
        if (payrollEmployerTaxes.getId() != null) {
            throw new BadRequestAlertException("A new payrollEmployerTaxes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollEmployerTaxes result = payrollEmployerTaxesRepository.save(payrollEmployerTaxes);
        return ResponseEntity.created(new URI("/api/payroll-employer-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-employer-taxes : Updates an existing payrollEmployerTaxes.
     *
     * @param payrollEmployerTaxes the payrollEmployerTaxes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollEmployerTaxes,
     * or with status 400 (Bad Request) if the payrollEmployerTaxes is not valid,
     * or with status 500 (Internal Server Error) if the payrollEmployerTaxes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-employer-taxes")
    @Timed
    public ResponseEntity<PayrollEmployerTaxes> updatePayrollEmployerTaxes(@RequestBody PayrollEmployerTaxes payrollEmployerTaxes) throws URISyntaxException {
        log.debug("REST request to update PayrollEmployerTaxes : {}", payrollEmployerTaxes);
        if (payrollEmployerTaxes.getId() == null) {
            return createPayrollEmployerTaxes(payrollEmployerTaxes);
        }
        PayrollEmployerTaxes result = payrollEmployerTaxesRepository.save(payrollEmployerTaxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollEmployerTaxes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-employer-taxes : get all the payrollEmployerTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollEmployerTaxes in body
     */
    @GetMapping("/payroll-employer-taxes")
    @Timed
    public List<PayrollEmployerTaxes> getAllPayrollEmployerTaxes() {
        log.debug("REST request to get all PayrollEmployerTaxes");
        return payrollEmployerTaxesRepository.findAll();
        }

    /**
     * GET  /payroll-employer-taxes/:id : get the "id" payrollEmployerTaxes.
     *
     * @param id the id of the payrollEmployerTaxes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollEmployerTaxes, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-employer-taxes/{id}")
    @Timed
    public ResponseEntity<PayrollEmployerTaxes> getPayrollEmployerTaxes(@PathVariable Long id) {
        log.debug("REST request to get PayrollEmployerTaxes : {}", id);
        PayrollEmployerTaxes payrollEmployerTaxes = payrollEmployerTaxesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollEmployerTaxes));
    }

    /**
     * DELETE  /payroll-employer-taxes/:id : delete the "id" payrollEmployerTaxes.
     *
     * @param id the id of the payrollEmployerTaxes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-employer-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollEmployerTaxes(@PathVariable Long id) {
        log.debug("REST request to delete PayrollEmployerTaxes : {}", id);
        payrollEmployerTaxesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
