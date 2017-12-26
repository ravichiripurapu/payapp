package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.FutaExemptReasonCode;

import com.pay.app.repository.FutaExemptReasonCodeRepository;
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
 * REST controller for managing FutaExemptReasonCode.
 */
@RestController
@RequestMapping("/api")
public class FutaExemptReasonCodeResource {

    private final Logger log = LoggerFactory.getLogger(FutaExemptReasonCodeResource.class);

    private static final String ENTITY_NAME = "futaExemptReasonCode";

    private final FutaExemptReasonCodeRepository futaExemptReasonCodeRepository;

    public FutaExemptReasonCodeResource(FutaExemptReasonCodeRepository futaExemptReasonCodeRepository) {
        this.futaExemptReasonCodeRepository = futaExemptReasonCodeRepository;
    }

    /**
     * POST  /futa-exempt-reason-codes : Create a new futaExemptReasonCode.
     *
     * @param futaExemptReasonCode the futaExemptReasonCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new futaExemptReasonCode, or with status 400 (Bad Request) if the futaExemptReasonCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/futa-exempt-reason-codes")
    @Timed
    public ResponseEntity<FutaExemptReasonCode> createFutaExemptReasonCode(@RequestBody FutaExemptReasonCode futaExemptReasonCode) throws URISyntaxException {
        log.debug("REST request to save FutaExemptReasonCode : {}", futaExemptReasonCode);
        if (futaExemptReasonCode.getId() != null) {
            throw new BadRequestAlertException("A new futaExemptReasonCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FutaExemptReasonCode result = futaExemptReasonCodeRepository.save(futaExemptReasonCode);
        return ResponseEntity.created(new URI("/api/futa-exempt-reason-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /futa-exempt-reason-codes : Updates an existing futaExemptReasonCode.
     *
     * @param futaExemptReasonCode the futaExemptReasonCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated futaExemptReasonCode,
     * or with status 400 (Bad Request) if the futaExemptReasonCode is not valid,
     * or with status 500 (Internal Server Error) if the futaExemptReasonCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/futa-exempt-reason-codes")
    @Timed
    public ResponseEntity<FutaExemptReasonCode> updateFutaExemptReasonCode(@RequestBody FutaExemptReasonCode futaExemptReasonCode) throws URISyntaxException {
        log.debug("REST request to update FutaExemptReasonCode : {}", futaExemptReasonCode);
        if (futaExemptReasonCode.getId() == null) {
            return createFutaExemptReasonCode(futaExemptReasonCode);
        }
        FutaExemptReasonCode result = futaExemptReasonCodeRepository.save(futaExemptReasonCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, futaExemptReasonCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /futa-exempt-reason-codes : get all the futaExemptReasonCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of futaExemptReasonCodes in body
     */
    @GetMapping("/futa-exempt-reason-codes")
    @Timed
    public List<FutaExemptReasonCode> getAllFutaExemptReasonCodes() {
        log.debug("REST request to get all FutaExemptReasonCodes");
        return futaExemptReasonCodeRepository.findAll();
        }

    /**
     * GET  /futa-exempt-reason-codes/:id : get the "id" futaExemptReasonCode.
     *
     * @param id the id of the futaExemptReasonCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the futaExemptReasonCode, or with status 404 (Not Found)
     */
    @GetMapping("/futa-exempt-reason-codes/{id}")
    @Timed
    public ResponseEntity<FutaExemptReasonCode> getFutaExemptReasonCode(@PathVariable Long id) {
        log.debug("REST request to get FutaExemptReasonCode : {}", id);
        FutaExemptReasonCode futaExemptReasonCode = futaExemptReasonCodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(futaExemptReasonCode));
    }

    /**
     * DELETE  /futa-exempt-reason-codes/:id : delete the "id" futaExemptReasonCode.
     *
     * @param id the id of the futaExemptReasonCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/futa-exempt-reason-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteFutaExemptReasonCode(@PathVariable Long id) {
        log.debug("REST request to delete FutaExemptReasonCode : {}", id);
        futaExemptReasonCodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
