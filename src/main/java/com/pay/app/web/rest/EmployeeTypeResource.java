package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeType;

import com.pay.app.repository.EmployeeTypeRepository;
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
 * REST controller for managing EmployeeType.
 */
@RestController
@RequestMapping("/api")
public class EmployeeTypeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeResource.class);

    private static final String ENTITY_NAME = "employeeType";

    private final EmployeeTypeRepository employeeTypeRepository;

    public EmployeeTypeResource(EmployeeTypeRepository employeeTypeRepository) {
        this.employeeTypeRepository = employeeTypeRepository;
    }

    /**
     * POST  /employee-types : Create a new employeeType.
     *
     * @param employeeType the employeeType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeType, or with status 400 (Bad Request) if the employeeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-types")
    @Timed
    public ResponseEntity<EmployeeType> createEmployeeType(@RequestBody EmployeeType employeeType) throws URISyntaxException {
        log.debug("REST request to save EmployeeType : {}", employeeType);
        if (employeeType.getId() != null) {
            throw new BadRequestAlertException("A new employeeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeType result = employeeTypeRepository.save(employeeType);
        return ResponseEntity.created(new URI("/api/employee-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-types : Updates an existing employeeType.
     *
     * @param employeeType the employeeType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeType,
     * or with status 400 (Bad Request) if the employeeType is not valid,
     * or with status 500 (Internal Server Error) if the employeeType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-types")
    @Timed
    public ResponseEntity<EmployeeType> updateEmployeeType(@RequestBody EmployeeType employeeType) throws URISyntaxException {
        log.debug("REST request to update EmployeeType : {}", employeeType);
        if (employeeType.getId() == null) {
            return createEmployeeType(employeeType);
        }
        EmployeeType result = employeeTypeRepository.save(employeeType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-types : get all the employeeTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeTypes in body
     */
    @GetMapping("/employee-types")
    @Timed
    public List<EmployeeType> getAllEmployeeTypes() {
        log.debug("REST request to get all EmployeeTypes");
        return employeeTypeRepository.findAll();
        }

    /**
     * GET  /employee-types/:id : get the "id" employeeType.
     *
     * @param id the id of the employeeType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeType, or with status 404 (Not Found)
     */
    @GetMapping("/employee-types/{id}")
    @Timed
    public ResponseEntity<EmployeeType> getEmployeeType(@PathVariable Long id) {
        log.debug("REST request to get EmployeeType : {}", id);
        EmployeeType employeeType = employeeTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeType));
    }

    /**
     * DELETE  /employee-types/:id : delete the "id" employeeType.
     *
     * @param id the id of the employeeType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeType(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeType : {}", id);
        employeeTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
