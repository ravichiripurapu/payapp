package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollEarnings;

import com.pay.app.repository.PayrollEarningsRepository;
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
 * REST controller for managing PayrollEarnings.
 */
@RestController
@RequestMapping("/api")
public class PayrollEarningsResource {

    private final Logger log = LoggerFactory.getLogger(PayrollEarningsResource.class);

    private static final String ENTITY_NAME = "payrollEarnings";

    private final PayrollEarningsRepository payrollEarningsRepository;

    public PayrollEarningsResource(PayrollEarningsRepository payrollEarningsRepository) {
        this.payrollEarningsRepository = payrollEarningsRepository;
    }

    /**
     * POST  /payroll-earnings : Create a new payrollEarnings.
     *
     * @param payrollEarnings the payrollEarnings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollEarnings, or with status 400 (Bad Request) if the payrollEarnings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-earnings")
    @Timed
    public ResponseEntity<PayrollEarnings> createPayrollEarnings(@RequestBody PayrollEarnings payrollEarnings) throws URISyntaxException {
        log.debug("REST request to save PayrollEarnings : {}", payrollEarnings);
        if (payrollEarnings.getId() != null) {
            throw new BadRequestAlertException("A new payrollEarnings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollEarnings result = payrollEarningsRepository.save(payrollEarnings);
        return ResponseEntity.created(new URI("/api/payroll-earnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-earnings : Updates an existing payrollEarnings.
     *
     * @param payrollEarnings the payrollEarnings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollEarnings,
     * or with status 400 (Bad Request) if the payrollEarnings is not valid,
     * or with status 500 (Internal Server Error) if the payrollEarnings couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-earnings")
    @Timed
    public ResponseEntity<PayrollEarnings> updatePayrollEarnings(@RequestBody PayrollEarnings payrollEarnings) throws URISyntaxException {
        log.debug("REST request to update PayrollEarnings : {}", payrollEarnings);
        if (payrollEarnings.getId() == null) {
            return createPayrollEarnings(payrollEarnings);
        }
        PayrollEarnings result = payrollEarningsRepository.save(payrollEarnings);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollEarnings.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-earnings : get all the payrollEarnings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollEarnings in body
     */
    @GetMapping("/payroll-earnings")
    @Timed
    public List<PayrollEarnings> getAllPayrollEarnings() {
        log.debug("REST request to get all PayrollEarnings");
        return payrollEarningsRepository.findAll();
        }

    /**
     * GET  /payroll-earnings/:id : get the "id" payrollEarnings.
     *
     * @param id the id of the payrollEarnings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollEarnings, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-earnings/{id}")
    @Timed
    public ResponseEntity<PayrollEarnings> getPayrollEarnings(@PathVariable Long id) {
        log.debug("REST request to get PayrollEarnings : {}", id);
        PayrollEarnings payrollEarnings = payrollEarningsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollEarnings));
    }

    /**
     * DELETE  /payroll-earnings/:id : delete the "id" payrollEarnings.
     *
     * @param id the id of the payrollEarnings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-earnings/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollEarnings(@PathVariable Long id) {
        log.debug("REST request to delete PayrollEarnings : {}", id);
        payrollEarningsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
