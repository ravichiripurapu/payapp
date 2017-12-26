package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeFederalTax;

import com.pay.app.repository.EmployeeFederalTaxRepository;
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
 * REST controller for managing EmployeeFederalTax.
 */
@RestController
@RequestMapping("/api")
public class EmployeeFederalTaxResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeFederalTaxResource.class);

    private static final String ENTITY_NAME = "employeeFederalTax";

    private final EmployeeFederalTaxRepository employeeFederalTaxRepository;

    public EmployeeFederalTaxResource(EmployeeFederalTaxRepository employeeFederalTaxRepository) {
        this.employeeFederalTaxRepository = employeeFederalTaxRepository;
    }

    /**
     * POST  /employee-federal-taxes : Create a new employeeFederalTax.
     *
     * @param employeeFederalTax the employeeFederalTax to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeFederalTax, or with status 400 (Bad Request) if the employeeFederalTax has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-federal-taxes")
    @Timed
    public ResponseEntity<EmployeeFederalTax> createEmployeeFederalTax(@RequestBody EmployeeFederalTax employeeFederalTax) throws URISyntaxException {
        log.debug("REST request to save EmployeeFederalTax : {}", employeeFederalTax);
        if (employeeFederalTax.getId() != null) {
            throw new BadRequestAlertException("A new employeeFederalTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeFederalTax result = employeeFederalTaxRepository.save(employeeFederalTax);
        return ResponseEntity.created(new URI("/api/employee-federal-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-federal-taxes : Updates an existing employeeFederalTax.
     *
     * @param employeeFederalTax the employeeFederalTax to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeFederalTax,
     * or with status 400 (Bad Request) if the employeeFederalTax is not valid,
     * or with status 500 (Internal Server Error) if the employeeFederalTax couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-federal-taxes")
    @Timed
    public ResponseEntity<EmployeeFederalTax> updateEmployeeFederalTax(@RequestBody EmployeeFederalTax employeeFederalTax) throws URISyntaxException {
        log.debug("REST request to update EmployeeFederalTax : {}", employeeFederalTax);
        if (employeeFederalTax.getId() == null) {
            return createEmployeeFederalTax(employeeFederalTax);
        }
        EmployeeFederalTax result = employeeFederalTaxRepository.save(employeeFederalTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeFederalTax.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-federal-taxes : get all the employeeFederalTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeFederalTaxes in body
     */
    @GetMapping("/employee-federal-taxes")
    @Timed
    public List<EmployeeFederalTax> getAllEmployeeFederalTaxes() {
        log.debug("REST request to get all EmployeeFederalTaxes");
        return employeeFederalTaxRepository.findAll();
        }

    /**
     * GET  /employee-federal-taxes/:id : get the "id" employeeFederalTax.
     *
     * @param id the id of the employeeFederalTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeFederalTax, or with status 404 (Not Found)
     */
    @GetMapping("/employee-federal-taxes/{id}")
    @Timed
    public ResponseEntity<EmployeeFederalTax> getEmployeeFederalTax(@PathVariable Long id) {
        log.debug("REST request to get EmployeeFederalTax : {}", id);
        EmployeeFederalTax employeeFederalTax = employeeFederalTaxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeFederalTax));
    }

    /**
     * DELETE  /employee-federal-taxes/:id : delete the "id" employeeFederalTax.
     *
     * @param id the id of the employeeFederalTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-federal-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeFederalTax(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeFederalTax : {}", id);
        employeeFederalTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
