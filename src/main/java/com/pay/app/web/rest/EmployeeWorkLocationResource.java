package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.EmployeeWorkLocation;

import com.pay.app.repository.EmployeeWorkLocationRepository;
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
 * REST controller for managing EmployeeWorkLocation.
 */
@RestController
@RequestMapping("/api")
public class EmployeeWorkLocationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeWorkLocationResource.class);

    private static final String ENTITY_NAME = "employeeWorkLocation";

    private final EmployeeWorkLocationRepository employeeWorkLocationRepository;

    public EmployeeWorkLocationResource(EmployeeWorkLocationRepository employeeWorkLocationRepository) {
        this.employeeWorkLocationRepository = employeeWorkLocationRepository;
    }

    /**
     * POST  /employee-work-locations : Create a new employeeWorkLocation.
     *
     * @param employeeWorkLocation the employeeWorkLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeWorkLocation, or with status 400 (Bad Request) if the employeeWorkLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-work-locations")
    @Timed
    public ResponseEntity<EmployeeWorkLocation> createEmployeeWorkLocation(@RequestBody EmployeeWorkLocation employeeWorkLocation) throws URISyntaxException {
        log.debug("REST request to save EmployeeWorkLocation : {}", employeeWorkLocation);
        if (employeeWorkLocation.getId() != null) {
            throw new BadRequestAlertException("A new employeeWorkLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeWorkLocation result = employeeWorkLocationRepository.save(employeeWorkLocation);
        return ResponseEntity.created(new URI("/api/employee-work-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-work-locations : Updates an existing employeeWorkLocation.
     *
     * @param employeeWorkLocation the employeeWorkLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeWorkLocation,
     * or with status 400 (Bad Request) if the employeeWorkLocation is not valid,
     * or with status 500 (Internal Server Error) if the employeeWorkLocation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-work-locations")
    @Timed
    public ResponseEntity<EmployeeWorkLocation> updateEmployeeWorkLocation(@RequestBody EmployeeWorkLocation employeeWorkLocation) throws URISyntaxException {
        log.debug("REST request to update EmployeeWorkLocation : {}", employeeWorkLocation);
        if (employeeWorkLocation.getId() == null) {
            return createEmployeeWorkLocation(employeeWorkLocation);
        }
        EmployeeWorkLocation result = employeeWorkLocationRepository.save(employeeWorkLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeWorkLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-work-locations : get all the employeeWorkLocations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeWorkLocations in body
     */
    @GetMapping("/employee-work-locations")
    @Timed
    public List<EmployeeWorkLocation> getAllEmployeeWorkLocations() {
        log.debug("REST request to get all EmployeeWorkLocations");
        return employeeWorkLocationRepository.findAll();
        }

    /**
     * GET  /employee-work-locations/:id : get the "id" employeeWorkLocation.
     *
     * @param id the id of the employeeWorkLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeWorkLocation, or with status 404 (Not Found)
     */
    @GetMapping("/employee-work-locations/{id}")
    @Timed
    public ResponseEntity<EmployeeWorkLocation> getEmployeeWorkLocation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeWorkLocation : {}", id);
        EmployeeWorkLocation employeeWorkLocation = employeeWorkLocationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeWorkLocation));
    }

    /**
     * DELETE  /employee-work-locations/:id : delete the "id" employeeWorkLocation.
     *
     * @param id the id of the employeeWorkLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-work-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeWorkLocation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeWorkLocation : {}", id);
        employeeWorkLocationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
