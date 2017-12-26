package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.DeductionSubType;

import com.pay.app.repository.DeductionSubTypeRepository;
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
 * REST controller for managing DeductionSubType.
 */
@RestController
@RequestMapping("/api")
public class DeductionSubTypeResource {

    private final Logger log = LoggerFactory.getLogger(DeductionSubTypeResource.class);

    private static final String ENTITY_NAME = "deductionSubType";

    private final DeductionSubTypeRepository deductionSubTypeRepository;

    public DeductionSubTypeResource(DeductionSubTypeRepository deductionSubTypeRepository) {
        this.deductionSubTypeRepository = deductionSubTypeRepository;
    }

    /**
     * POST  /deduction-sub-types : Create a new deductionSubType.
     *
     * @param deductionSubType the deductionSubType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deductionSubType, or with status 400 (Bad Request) if the deductionSubType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deduction-sub-types")
    @Timed
    public ResponseEntity<DeductionSubType> createDeductionSubType(@RequestBody DeductionSubType deductionSubType) throws URISyntaxException {
        log.debug("REST request to save DeductionSubType : {}", deductionSubType);
        if (deductionSubType.getId() != null) {
            throw new BadRequestAlertException("A new deductionSubType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeductionSubType result = deductionSubTypeRepository.save(deductionSubType);
        return ResponseEntity.created(new URI("/api/deduction-sub-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deduction-sub-types : Updates an existing deductionSubType.
     *
     * @param deductionSubType the deductionSubType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deductionSubType,
     * or with status 400 (Bad Request) if the deductionSubType is not valid,
     * or with status 500 (Internal Server Error) if the deductionSubType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deduction-sub-types")
    @Timed
    public ResponseEntity<DeductionSubType> updateDeductionSubType(@RequestBody DeductionSubType deductionSubType) throws URISyntaxException {
        log.debug("REST request to update DeductionSubType : {}", deductionSubType);
        if (deductionSubType.getId() == null) {
            return createDeductionSubType(deductionSubType);
        }
        DeductionSubType result = deductionSubTypeRepository.save(deductionSubType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deductionSubType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deduction-sub-types : get all the deductionSubTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deductionSubTypes in body
     */
    @GetMapping("/deduction-sub-types")
    @Timed
    public List<DeductionSubType> getAllDeductionSubTypes() {
        log.debug("REST request to get all DeductionSubTypes");
        return deductionSubTypeRepository.findAll();
        }

    /**
     * GET  /deduction-sub-types/:id : get the "id" deductionSubType.
     *
     * @param id the id of the deductionSubType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deductionSubType, or with status 404 (Not Found)
     */
    @GetMapping("/deduction-sub-types/{id}")
    @Timed
    public ResponseEntity<DeductionSubType> getDeductionSubType(@PathVariable Long id) {
        log.debug("REST request to get DeductionSubType : {}", id);
        DeductionSubType deductionSubType = deductionSubTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deductionSubType));
    }

    /**
     * DELETE  /deduction-sub-types/:id : delete the "id" deductionSubType.
     *
     * @param id the id of the deductionSubType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deduction-sub-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeductionSubType(@PathVariable Long id) {
        log.debug("REST request to delete DeductionSubType : {}", id);
        deductionSubTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
