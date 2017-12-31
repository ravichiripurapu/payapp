package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeLocalTax;

import com.pay.app.repository.EmployeeLocalTaxRepository;
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
 * REST controller for managing EmployeeLocalTax.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLocalTaxResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLocalTaxResource.class);

    private static final String ENTITY_NAME = "employeeLocalTax";

    private final EmployeeLocalTaxRepository employeeLocalTaxRepository;

    public EmployeeLocalTaxResource(EmployeeLocalTaxRepository employeeLocalTaxRepository) {
        this.employeeLocalTaxRepository = employeeLocalTaxRepository;
    }

    /**
     * POST  /employee-local-taxes : Create a new employeeLocalTax.
     *
     * @param employeeLocalTax the employeeLocalTax to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeLocalTax, or with status 400 (Bad Request) if the employeeLocalTax has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-local-taxes")
    @Timed
    public ResponseEntity<EmployeeLocalTax> createEmployeeLocalTax(@RequestBody EmployeeLocalTax employeeLocalTax) throws URISyntaxException {
        log.debug("REST request to save EmployeeLocalTax : {}", employeeLocalTax);
        if (employeeLocalTax.getId() != null) {
            throw new BadRequestAlertException("A new employeeLocalTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeLocalTax result = employeeLocalTaxRepository.save(employeeLocalTax);
        return ResponseEntity.created(new URI("/api/employee-local-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-local-taxes : Updates an existing employeeLocalTax.
     *
     * @param employeeLocalTax the employeeLocalTax to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeLocalTax,
     * or with status 400 (Bad Request) if the employeeLocalTax is not valid,
     * or with status 500 (Internal Server Error) if the employeeLocalTax couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-local-taxes")
    @Timed
    public ResponseEntity<EmployeeLocalTax> updateEmployeeLocalTax(@RequestBody EmployeeLocalTax employeeLocalTax) throws URISyntaxException {
        log.debug("REST request to update EmployeeLocalTax : {}", employeeLocalTax);
        if (employeeLocalTax.getId() == null) {
            return createEmployeeLocalTax(employeeLocalTax);
        }
        EmployeeLocalTax result = employeeLocalTaxRepository.save(employeeLocalTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeLocalTax.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-local-taxes : get all the employeeLocalTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeLocalTaxes in body
     */
    @GetMapping("/employee-local-taxes")
    @Timed
    public List<EmployeeLocalTax> getAllEmployeeLocalTaxes() {
        log.debug("REST request to get all EmployeeLocalTaxes");
        return employeeLocalTaxRepository.findAll();
    }

    /**
     * GET  /employee-local-taxes/:id : get the "id" employeeLocalTax.
     *
     * @param id the id of the employeeLocalTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeLocalTax, or with status 404 (Not Found)
     */
    @GetMapping("/employee-local-taxes/{id}")
    @Timed
    public ResponseEntity<EmployeeLocalTax> getEmployeeLocalTax(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLocalTax : {}", id);
        EmployeeLocalTax employeeLocalTax = employeeLocalTaxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeLocalTax));
    }

    /**
     * DELETE  /employee-local-taxes/:id : delete the "id" employeeLocalTax.
     *
     * @param id the id of the employeeLocalTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-local-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeLocalTax(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLocalTax : {}", id);
        employeeLocalTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
