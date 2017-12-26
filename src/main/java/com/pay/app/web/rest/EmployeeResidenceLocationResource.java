package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeResidenceLocation;

import com.pay.app.repository.EmployeeResidenceLocationRepository;
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
 * REST controller for managing EmployeeResidenceLocation.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResidenceLocationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResidenceLocationResource.class);

    private static final String ENTITY_NAME = "employeeResidenceLocation";

    private final EmployeeResidenceLocationRepository employeeResidenceLocationRepository;

    public EmployeeResidenceLocationResource(EmployeeResidenceLocationRepository employeeResidenceLocationRepository) {
        this.employeeResidenceLocationRepository = employeeResidenceLocationRepository;
    }

    /**
     * POST  /employee-residence-locations : Create a new employeeResidenceLocation.
     *
     * @param employeeResidenceLocation the employeeResidenceLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeResidenceLocation, or with status 400 (Bad Request) if the employeeResidenceLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-residence-locations")
    @Timed
    public ResponseEntity<EmployeeResidenceLocation> createEmployeeResidenceLocation(@RequestBody EmployeeResidenceLocation employeeResidenceLocation) throws URISyntaxException {
        log.debug("REST request to save EmployeeResidenceLocation : {}", employeeResidenceLocation);
        if (employeeResidenceLocation.getId() != null) {
            throw new BadRequestAlertException("A new employeeResidenceLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeResidenceLocation result = employeeResidenceLocationRepository.save(employeeResidenceLocation);
        return ResponseEntity.created(new URI("/api/employee-residence-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-residence-locations : Updates an existing employeeResidenceLocation.
     *
     * @param employeeResidenceLocation the employeeResidenceLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeResidenceLocation,
     * or with status 400 (Bad Request) if the employeeResidenceLocation is not valid,
     * or with status 500 (Internal Server Error) if the employeeResidenceLocation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-residence-locations")
    @Timed
    public ResponseEntity<EmployeeResidenceLocation> updateEmployeeResidenceLocation(@RequestBody EmployeeResidenceLocation employeeResidenceLocation) throws URISyntaxException {
        log.debug("REST request to update EmployeeResidenceLocation : {}", employeeResidenceLocation);
        if (employeeResidenceLocation.getId() == null) {
            return createEmployeeResidenceLocation(employeeResidenceLocation);
        }
        EmployeeResidenceLocation result = employeeResidenceLocationRepository.save(employeeResidenceLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeResidenceLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-residence-locations : get all the employeeResidenceLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeResidenceLocations in body
     */
    @GetMapping("/employee-residence-locations")
    @Timed
    public List<EmployeeResidenceLocation> getAllEmployeeResidenceLocations() {
        log.debug("REST request to get all EmployeeResidenceLocations");
        return employeeResidenceLocationRepository.findAll();
        }

    /**
     * GET  /employee-residence-locations/:id : get the "id" employeeResidenceLocation.
     *
     * @param id the id of the employeeResidenceLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeResidenceLocation, or with status 404 (Not Found)
     */
    @GetMapping("/employee-residence-locations/{id}")
    @Timed
    public ResponseEntity<EmployeeResidenceLocation> getEmployeeResidenceLocation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeResidenceLocation : {}", id);
        EmployeeResidenceLocation employeeResidenceLocation = employeeResidenceLocationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeResidenceLocation));
    }

    /**
     * DELETE  /employee-residence-locations/:id : delete the "id" employeeResidenceLocation.
     *
     * @param id the id of the employeeResidenceLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-residence-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeResidenceLocation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeResidenceLocation : {}", id);
        employeeResidenceLocationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
