package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeStateTax;

import com.pay.app.repository.EmployeeStateTaxRepository;
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
 * REST controller for managing EmployeeStateTax.
 */
@RestController
@RequestMapping("/api")
public class EmployeeStateTaxResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeStateTaxResource.class);

    private static final String ENTITY_NAME = "employeeStateTax";

    private final EmployeeStateTaxRepository employeeStateTaxRepository;

    public EmployeeStateTaxResource(EmployeeStateTaxRepository employeeStateTaxRepository) {
        this.employeeStateTaxRepository = employeeStateTaxRepository;
    }

    /**
     * POST  /employee-state-taxes : Create a new employeeStateTax.
     *
     * @param employeeStateTax the employeeStateTax to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeStateTax, or with status 400 (Bad Request) if the employeeStateTax has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-state-taxes")
    @Timed
    public ResponseEntity<EmployeeStateTax> createEmployeeStateTax(@RequestBody EmployeeStateTax employeeStateTax) throws URISyntaxException {
        log.debug("REST request to save EmployeeStateTax : {}", employeeStateTax);
        if (employeeStateTax.getId() != null) {
            throw new BadRequestAlertException("A new employeeStateTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeStateTax result = employeeStateTaxRepository.save(employeeStateTax);
        return ResponseEntity.created(new URI("/api/employee-state-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-state-taxes : Updates an existing employeeStateTax.
     *
     * @param employeeStateTax the employeeStateTax to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeStateTax,
     * or with status 400 (Bad Request) if the employeeStateTax is not valid,
     * or with status 500 (Internal Server Error) if the employeeStateTax couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-state-taxes")
    @Timed
    public ResponseEntity<EmployeeStateTax> updateEmployeeStateTax(@RequestBody EmployeeStateTax employeeStateTax) throws URISyntaxException {
        log.debug("REST request to update EmployeeStateTax : {}", employeeStateTax);
        if (employeeStateTax.getId() == null) {
            return createEmployeeStateTax(employeeStateTax);
        }
        EmployeeStateTax result = employeeStateTaxRepository.save(employeeStateTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeStateTax.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-state-taxes : get all the employeeStateTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeStateTaxes in body
     */
    @GetMapping("/employee-state-taxes")
    @Timed
    public List<EmployeeStateTax> getAllEmployeeStateTaxes() {
        log.debug("REST request to get all EmployeeStateTaxes");
        return employeeStateTaxRepository.findAll();
    }

    /**
     * GET  /employee-state-taxes/:id : get the "id" employeeStateTax.
     *
     * @param id the id of the employeeStateTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeStateTax, or with status 404 (Not Found)
     */
    @GetMapping("/employee-state-taxes/{id}")
    @Timed
    public ResponseEntity<EmployeeStateTax> getEmployeeStateTax(@PathVariable Long id) {
        log.debug("REST request to get EmployeeStateTax : {}", id);
        EmployeeStateTax employeeStateTax = employeeStateTaxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeStateTax));
    }

    /**
     * DELETE  /employee-state-taxes/:id : delete the "id" employeeStateTax.
     *
     * @param id the id of the employeeStateTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-state-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeStateTax(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeStateTax : {}", id);
        employeeStateTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
