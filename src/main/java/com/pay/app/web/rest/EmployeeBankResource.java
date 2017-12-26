package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeBank;

import com.pay.app.repository.EmployeeBankRepository;
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
 * REST controller for managing EmployeeBank.
 */
@RestController
@RequestMapping("/api")
public class EmployeeBankResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeBankResource.class);

    private static final String ENTITY_NAME = "employeeBank";

    private final EmployeeBankRepository employeeBankRepository;

    public EmployeeBankResource(EmployeeBankRepository employeeBankRepository) {
        this.employeeBankRepository = employeeBankRepository;
    }

    /**
     * POST  /employee-banks : Create a new employeeBank.
     *
     * @param employeeBank the employeeBank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeBank, or with status 400 (Bad Request) if the employeeBank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-banks")
    @Timed
    public ResponseEntity<EmployeeBank> createEmployeeBank(@RequestBody EmployeeBank employeeBank) throws URISyntaxException {
        log.debug("REST request to save EmployeeBank : {}", employeeBank);
        if (employeeBank.getId() != null) {
            throw new BadRequestAlertException("A new employeeBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeBank result = employeeBankRepository.save(employeeBank);
        return ResponseEntity.created(new URI("/api/employee-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-banks : Updates an existing employeeBank.
     *
     * @param employeeBank the employeeBank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeBank,
     * or with status 400 (Bad Request) if the employeeBank is not valid,
     * or with status 500 (Internal Server Error) if the employeeBank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-banks")
    @Timed
    public ResponseEntity<EmployeeBank> updateEmployeeBank(@RequestBody EmployeeBank employeeBank) throws URISyntaxException {
        log.debug("REST request to update EmployeeBank : {}", employeeBank);
        if (employeeBank.getId() == null) {
            return createEmployeeBank(employeeBank);
        }
        EmployeeBank result = employeeBankRepository.save(employeeBank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeBank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-banks : get all the employeeBanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeBanks in body
     */
    @GetMapping("/employee-banks")
    @Timed
    public List<EmployeeBank> getAllEmployeeBanks() {
        log.debug("REST request to get all EmployeeBanks");
        return employeeBankRepository.findAll();
        }

    /**
     * GET  /employee-banks/:id : get the "id" employeeBank.
     *
     * @param id the id of the employeeBank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeBank, or with status 404 (Not Found)
     */
    @GetMapping("/employee-banks/{id}")
    @Timed
    public ResponseEntity<EmployeeBank> getEmployeeBank(@PathVariable Long id) {
        log.debug("REST request to get EmployeeBank : {}", id);
        EmployeeBank employeeBank = employeeBankRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeBank));
    }

    /**
     * DELETE  /employee-banks/:id : delete the "id" employeeBank.
     *
     * @param id the id of the employeeBank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-banks/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeBank(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeBank : {}", id);
        employeeBankRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
