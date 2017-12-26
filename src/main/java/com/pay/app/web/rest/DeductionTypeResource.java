package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.DeductionType;

import com.pay.app.repository.DeductionTypeRepository;
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
 * REST controller for managing DeductionType.
 */
@RestController
@RequestMapping("/api")
public class DeductionTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeductionTypeResource.class);

    private static final String ENTITY_NAME = "deductionType";

    private final DeductionTypeRepository deductionTypeRepository;

    public DeductionTypeResource(DeductionTypeRepository deductionTypeRepository) {
        this.deductionTypeRepository = deductionTypeRepository;
    }

    /**
     * POST  /deduction-types : Create a new deductionType.
     *
     * @param deductionType the deductionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deductionType, or with status 400 (Bad Request) if the deductionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deduction-types")
    @Timed
    public ResponseEntity<DeductionType> createDeductionType(@RequestBody DeductionType deductionType) throws URISyntaxException {
        log.debug("REST request to save DeductionType : {}", deductionType);
        if (deductionType.getId() != null) {
            throw new BadRequestAlertException("A new deductionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeductionType result = deductionTypeRepository.save(deductionType);
        return ResponseEntity.created(new URI("/api/deduction-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deduction-types : Updates an existing deductionType.
     *
     * @param deductionType the deductionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deductionType,
     * or with status 400 (Bad Request) if the deductionType is not valid,
     * or with status 500 (Internal Server Error) if the deductionType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deduction-types")
    @Timed
    public ResponseEntity<DeductionType> updateDeductionType(@RequestBody DeductionType deductionType) throws URISyntaxException {
        log.debug("REST request to update DeductionType : {}", deductionType);
        if (deductionType.getId() == null) {
            return createDeductionType(deductionType);
        }
        DeductionType result = deductionTypeRepository.save(deductionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deductionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deduction-types : get all the deductionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deductionTypes in body
     */
    @GetMapping("/deduction-types")
    @Timed
    public List<DeductionType> getAllDeductionTypes() {
        log.debug("REST request to get all DeductionTypes");
        return deductionTypeRepository.findAll();
        }

    /**
     * GET  /deduction-types/:id : get the "id" deductionType.
     *
     * @param id the id of the deductionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deductionType, or with status 404 (Not Found)
     */
    @GetMapping("/deduction-types/{id}")
    @Timed
    public ResponseEntity<DeductionType> getDeductionType(@PathVariable Long id) {
        log.debug("REST request to get DeductionType : {}", id);
        DeductionType deductionType = deductionTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deductionType));
    }

    /**
     * DELETE  /deduction-types/:id : delete the "id" deductionType.
     *
     * @param id the id of the deductionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deduction-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeductionType(@PathVariable Long id) {
        log.debug("REST request to delete DeductionType : {}", id);
        deductionTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
