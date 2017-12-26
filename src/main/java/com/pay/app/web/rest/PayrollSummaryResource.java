package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollSummary;

import com.pay.app.repository.PayrollSummaryRepository;
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
 * REST controller for managing PayrollSummary.
 */
@RestController
@RequestMapping("/api")
public class PayrollSummaryResource {

    private final Logger log = LoggerFactory.getLogger(PayrollSummaryResource.class);

    private static final String ENTITY_NAME = "payrollSummary";

    private final PayrollSummaryRepository payrollSummaryRepository;

    public PayrollSummaryResource(PayrollSummaryRepository payrollSummaryRepository) {
        this.payrollSummaryRepository = payrollSummaryRepository;
    }

    /**
     * POST  /payroll-summaries : Create a new payrollSummary.
     *
     * @param payrollSummary the payrollSummary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollSummary, or with status 400 (Bad Request) if the payrollSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-summaries")
    @Timed
    public ResponseEntity<PayrollSummary> createPayrollSummary(@RequestBody PayrollSummary payrollSummary) throws URISyntaxException {
        log.debug("REST request to save PayrollSummary : {}", payrollSummary);
        if (payrollSummary.getId() != null) {
            throw new BadRequestAlertException("A new payrollSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollSummary result = payrollSummaryRepository.save(payrollSummary);
        return ResponseEntity.created(new URI("/api/payroll-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-summaries : Updates an existing payrollSummary.
     *
     * @param payrollSummary the payrollSummary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollSummary,
     * or with status 400 (Bad Request) if the payrollSummary is not valid,
     * or with status 500 (Internal Server Error) if the payrollSummary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-summaries")
    @Timed
    public ResponseEntity<PayrollSummary> updatePayrollSummary(@RequestBody PayrollSummary payrollSummary) throws URISyntaxException {
        log.debug("REST request to update PayrollSummary : {}", payrollSummary);
        if (payrollSummary.getId() == null) {
            return createPayrollSummary(payrollSummary);
        }
        PayrollSummary result = payrollSummaryRepository.save(payrollSummary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollSummary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-summaries : get all the payrollSummaries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollSummaries in body
     */
    @GetMapping("/payroll-summaries")
    @Timed
    public List<PayrollSummary> getAllPayrollSummaries() {
        log.debug("REST request to get all PayrollSummaries");
        return payrollSummaryRepository.findAll();
        }

    /**
     * GET  /payroll-summaries/:id : get the "id" payrollSummary.
     *
     * @param id the id of the payrollSummary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollSummary, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-summaries/{id}")
    @Timed
    public ResponseEntity<PayrollSummary> getPayrollSummary(@PathVariable Long id) {
        log.debug("REST request to get PayrollSummary : {}", id);
        PayrollSummary payrollSummary = payrollSummaryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollSummary));
    }

    /**
     * DELETE  /payroll-summaries/:id : delete the "id" payrollSummary.
     *
     * @param id the id of the payrollSummary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-summaries/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollSummary(@PathVariable Long id) {
        log.debug("REST request to delete PayrollSummary : {}", id);
        payrollSummaryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
