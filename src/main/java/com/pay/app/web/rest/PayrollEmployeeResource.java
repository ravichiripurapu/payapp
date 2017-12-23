package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.PayrollEmployee;

import com.pay.app.repository.PayrollEmployeeRepository;
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
 * REST controller for managing PayrollEmployee.
 */
@RestController
@RequestMapping("/api")
public class PayrollEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(PayrollEmployeeResource.class);

    private static final String ENTITY_NAME = "payrollEmployee";

    private final PayrollEmployeeRepository payrollEmployeeRepository;

    public PayrollEmployeeResource(PayrollEmployeeRepository payrollEmployeeRepository) {
        this.payrollEmployeeRepository = payrollEmployeeRepository;
    }

    /**
     * POST  /payroll-employees : Create a new payrollEmployee.
     *
     * @param payrollEmployee the payrollEmployee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payrollEmployee, or with status 400 (Bad Request) if the payrollEmployee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payroll-employees")
    @Timed
    public ResponseEntity<PayrollEmployee> createPayrollEmployee(@RequestBody PayrollEmployee payrollEmployee) throws URISyntaxException {
        log.debug("REST request to save PayrollEmployee : {}", payrollEmployee);
        if (payrollEmployee.getId() != null) {
            throw new BadRequestAlertException("A new payrollEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollEmployee result = payrollEmployeeRepository.save(payrollEmployee);
        return ResponseEntity.created(new URI("/api/payroll-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payroll-employees : Updates an existing payrollEmployee.
     *
     * @param payrollEmployee the payrollEmployee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payrollEmployee,
     * or with status 400 (Bad Request) if the payrollEmployee is not valid,
     * or with status 500 (Internal Server Error) if the payrollEmployee couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payroll-employees")
    @Timed
    public ResponseEntity<PayrollEmployee> updatePayrollEmployee(@RequestBody PayrollEmployee payrollEmployee) throws URISyntaxException {
        log.debug("REST request to update PayrollEmployee : {}", payrollEmployee);
        if (payrollEmployee.getId() == null) {
            return createPayrollEmployee(payrollEmployee);
        }
        PayrollEmployee result = payrollEmployeeRepository.save(payrollEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, payrollEmployee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payroll-employees : get all the payrollEmployees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of payrollEmployees in body
     */
    @GetMapping("/payroll-employees")
    @Timed
    public List<PayrollEmployee> getAllPayrollEmployees() {
        log.debug("REST request to get all PayrollEmployees");
        return payrollEmployeeRepository.findAll();
        }

    /**
     * GET  /payroll-employees/:id : get the "id" payrollEmployee.
     *
     * @param id the id of the payrollEmployee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payrollEmployee, or with status 404 (Not Found)
     */
    @GetMapping("/payroll-employees/{id}")
    @Timed
    public ResponseEntity<PayrollEmployee> getPayrollEmployee(@PathVariable Long id) {
        log.debug("REST request to get PayrollEmployee : {}", id);
        PayrollEmployee payrollEmployee = payrollEmployeeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(payrollEmployee));
    }

    /**
     * DELETE  /payroll-employees/:id : delete the "id" payrollEmployee.
     *
     * @param id the id of the payrollEmployee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payroll-employees/{id}")
    @Timed
    public ResponseEntity<Void> deletePayrollEmployee(@PathVariable Long id) {
        log.debug("REST request to delete PayrollEmployee : {}", id);
        payrollEmployeeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
