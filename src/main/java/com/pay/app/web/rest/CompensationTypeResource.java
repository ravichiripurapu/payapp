package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompensationType;

import com.pay.app.repository.CompensationTypeRepository;
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
 * REST controller for managing CompensationType.
 */
@RestController
@RequestMapping("/api")
public class CompensationTypeResource {

    private final Logger log = LoggerFactory.getLogger(CompensationTypeResource.class);

    private static final String ENTITY_NAME = "compensationType";

    private final CompensationTypeRepository compensationTypeRepository;

    public CompensationTypeResource(CompensationTypeRepository compensationTypeRepository) {
        this.compensationTypeRepository = compensationTypeRepository;
    }

    /**
     * POST  /compensation-types : Create a new compensationType.
     *
     * @param compensationType the compensationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compensationType, or with status 400 (Bad Request) if the compensationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compensation-types")
    @Timed
    public ResponseEntity<CompensationType> createCompensationType(@RequestBody CompensationType compensationType) throws URISyntaxException {
        log.debug("REST request to save CompensationType : {}", compensationType);
        if (compensationType.getId() != null) {
            throw new BadRequestAlertException("A new compensationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompensationType result = compensationTypeRepository.save(compensationType);
        return ResponseEntity.created(new URI("/api/compensation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compensation-types : Updates an existing compensationType.
     *
     * @param compensationType the compensationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compensationType,
     * or with status 400 (Bad Request) if the compensationType is not valid,
     * or with status 500 (Internal Server Error) if the compensationType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compensation-types")
    @Timed
    public ResponseEntity<CompensationType> updateCompensationType(@RequestBody CompensationType compensationType) throws URISyntaxException {
        log.debug("REST request to update CompensationType : {}", compensationType);
        if (compensationType.getId() == null) {
            return createCompensationType(compensationType);
        }
        CompensationType result = compensationTypeRepository.save(compensationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compensationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compensation-types : get all the compensationTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compensationTypes in body
     */
    @GetMapping("/compensation-types")
    @Timed
    public List<CompensationType> getAllCompensationTypes() {
        log.debug("REST request to get all CompensationTypes");
        return compensationTypeRepository.findAll();
        }

    /**
     * GET  /compensation-types/:id : get the "id" compensationType.
     *
     * @param id the id of the compensationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compensationType, or with status 404 (Not Found)
     */
    @GetMapping("/compensation-types/{id}")
    @Timed
    public ResponseEntity<CompensationType> getCompensationType(@PathVariable Long id) {
        log.debug("REST request to get CompensationType : {}", id);
        CompensationType compensationType = compensationTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compensationType));
    }

    /**
     * DELETE  /compensation-types/:id : delete the "id" compensationType.
     *
     * @param id the id of the compensationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compensation-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompensationType(@PathVariable Long id) {
        log.debug("REST request to delete CompensationType : {}", id);
        compensationTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
