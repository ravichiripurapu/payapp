package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollFrequency;

import com.pay.app.repository.PayrollFrequencyRepository;
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
 * REST controller for managing PayrollFrequency.
 */
@RestController
@RequestMapping("/api")
public class PayrollFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(PayrollFrequencyResource.class);

    private static final String ENTITY_NAME = "payrollFrequency";

    private final PayrollFrequencyRepository payrollFrequencyRepository;

    public PayrollFrequencyResource(PayrollFrequencyRepository payrollFrequencyRepository) {
        this.payrollFrequencyRepository = payrollFrequencyRepository;
    }

    /**
     * POST  /payroll-frequencies : Create a new payrollFrequency.
     *
     * @param payrollFrequency the payrollFrequency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollFrequency, or with status 400 (Bad Request) if the payrollFrequency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-frequencies")
    @Timed
    public ResponseEntity<PayrollFrequency> createPayrollFrequency(@RequestBody PayrollFrequency payrollFrequency) throws URISyntaxException {
        log.debug("REST request to save PayrollFrequency : {}", payrollFrequency);
        if (payrollFrequency.getId() != null) {
            throw new BadRequestAlertException("A new payrollFrequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollFrequency result = payrollFrequencyRepository.save(payrollFrequency);
        return ResponseEntity.created(new URI("/api/payroll-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-frequencies : Updates an existing payrollFrequency.
     *
     * @param payrollFrequency the payrollFrequency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollFrequency,
     * or with status 400 (Bad Request) if the payrollFrequency is not valid,
     * or with status 500 (Internal Server Error) if the payrollFrequency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-frequencies")
    @Timed
    public ResponseEntity<PayrollFrequency> updatePayrollFrequency(@RequestBody PayrollFrequency payrollFrequency) throws URISyntaxException {
        log.debug("REST request to update PayrollFrequency : {}", payrollFrequency);
        if (payrollFrequency.getId() == null) {
            return createPayrollFrequency(payrollFrequency);
        }
        PayrollFrequency result = payrollFrequencyRepository.save(payrollFrequency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollFrequency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-frequencies : get all the payrollFrequencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollFrequencies in body
     */
    @GetMapping("/payroll-frequencies")
    @Timed
    public List<PayrollFrequency> getAllPayrollFrequencies() {
        log.debug("REST request to get all PayrollFrequencies");
        return payrollFrequencyRepository.findAll();
        }

    /**
     * GET  /payroll-frequencies/:id : get the "id" payrollFrequency.
     *
     * @param id the id of the payrollFrequency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollFrequency, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-frequencies/{id}")
    @Timed
    public ResponseEntity<PayrollFrequency> getPayrollFrequency(@PathVariable Long id) {
        log.debug("REST request to get PayrollFrequency : {}", id);
        PayrollFrequency payrollFrequency = payrollFrequencyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollFrequency));
    }

    /**
     * DELETE  /payroll-frequencies/:id : delete the "id" payrollFrequency.
     *
     * @param id the id of the payrollFrequency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-frequencies/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollFrequency(@PathVariable Long id) {
        log.debug("REST request to delete PayrollFrequency : {}", id);
        payrollFrequencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
