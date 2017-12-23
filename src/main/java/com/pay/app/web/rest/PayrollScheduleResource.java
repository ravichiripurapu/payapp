package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollSchedule;

import com.pay.app.repository.PayrollScheduleRepository;
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
 * REST controller for managing PayrollSchedule.
 */
@RestController
@RequestMapping("/api")
public class PayrollScheduleResource {

    private final Logger log = LoggerFactory.getLogger(PayrollScheduleResource.class);

    private static final String ENTITY_NAME = "payrollSchedule";

    private final PayrollScheduleRepository payrollScheduleRepository;

    public PayrollScheduleResource(PayrollScheduleRepository payrollScheduleRepository) {
        this.payrollScheduleRepository = payrollScheduleRepository;
    }

    /**
     * POST  /payroll-schedules : Create a new payrollSchedule.
     *
     * @param payrollSchedule the payrollSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollSchedule, or with status 400 (Bad Request) if the payrollSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-schedules")
    @Timed
    public ResponseEntity<PayrollSchedule> createPayrollSchedule(@RequestBody PayrollSchedule payrollSchedule) throws URISyntaxException {
        log.debug("REST request to save PayrollSchedule : {}", payrollSchedule);
        if (payrollSchedule.getId() != null) {
            throw new BadRequestAlertException("A new payrollSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollSchedule result = payrollScheduleRepository.save(payrollSchedule);
        return ResponseEntity.created(new URI("/api/payroll-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-schedules : Updates an existing payrollSchedule.
     *
     * @param payrollSchedule the payrollSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollSchedule,
     * or with status 400 (Bad Request) if the payrollSchedule is not valid,
     * or with status 500 (Internal Server Error) if the payrollSchedule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-schedules")
    @Timed
    public ResponseEntity<PayrollSchedule> updatePayrollSchedule(@RequestBody PayrollSchedule payrollSchedule) throws URISyntaxException {
        log.debug("REST request to update PayrollSchedule : {}", payrollSchedule);
        if (payrollSchedule.getId() == null) {
            return createPayrollSchedule(payrollSchedule);
        }
        PayrollSchedule result = payrollScheduleRepository.save(payrollSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-schedules : get all the payrollSchedules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollSchedules in body
     */
    @GetMapping("/payroll-schedules")
    @Timed
    public List<PayrollSchedule> getAllPayrollSchedules() {
        log.debug("REST request to get all PayrollSchedules");
        return payrollScheduleRepository.findAll();
        }

    /**
     * GET  /payroll-schedules/:id : get the "id" payrollSchedule.
     *
     * @param id the id of the payrollSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollSchedule, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-schedules/{id}")
    @Timed
    public ResponseEntity<PayrollSchedule> getPayrollSchedule(@PathVariable Long id) {
        log.debug("REST request to get PayrollSchedule : {}", id);
        PayrollSchedule payrollSchedule = payrollScheduleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollSchedule));
    }

    /**
     * DELETE  /payroll-schedules/:id : delete the "id" payrollSchedule.
     *
     * @param id the id of the payrollSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollSchedule(@PathVariable Long id) {
        log.debug("REST request to delete PayrollSchedule : {}", id);
        payrollScheduleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
