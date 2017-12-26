package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.FilingStatus;

import com.pay.app.repository.FilingStatusRepository;
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
 * REST controller for managing FilingStatus.
 */
@RestController
@RequestMapping("/api")
public class FilingStatusResource {

    private final Logger log = LoggerFactory.getLogger(FilingStatusResource.class);

    private static final String ENTITY_NAME = "filingStatus";

    private final FilingStatusRepository filingStatusRepository;

    public FilingStatusResource(FilingStatusRepository filingStatusRepository) {
        this.filingStatusRepository = filingStatusRepository;
    }

    /**
     * POST  /filing-statuses : Create a new filingStatus.
     *
     * @param filingStatus the filingStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filingStatus, or with status 400 (Bad Request) if the filingStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filing-statuses")
    @Timed
    public ResponseEntity<FilingStatus> createFilingStatus(@RequestBody FilingStatus filingStatus) throws URISyntaxException {
        log.debug("REST request to save FilingStatus : {}", filingStatus);
        if (filingStatus.getId() != null) {
            throw new BadRequestAlertException("A new filingStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FilingStatus result = filingStatusRepository.save(filingStatus);
        return ResponseEntity.created(new URI("/api/filing-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filing-statuses : Updates an existing filingStatus.
     *
     * @param filingStatus the filingStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filingStatus,
     * or with status 400 (Bad Request) if the filingStatus is not valid,
     * or with status 500 (Internal Server Error) if the filingStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filing-statuses")
    @Timed
    public ResponseEntity<FilingStatus> updateFilingStatus(@RequestBody FilingStatus filingStatus) throws URISyntaxException {
        log.debug("REST request to update FilingStatus : {}", filingStatus);
        if (filingStatus.getId() == null) {
            return createFilingStatus(filingStatus);
        }
        FilingStatus result = filingStatusRepository.save(filingStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filingStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filing-statuses : get all the filingStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filingStatuses in body
     */
    @GetMapping("/filing-statuses")
    @Timed
    public List<FilingStatus> getAllFilingStatuses() {
        log.debug("REST request to get all FilingStatuses");
        return filingStatusRepository.findAll();
        }

    /**
     * GET  /filing-statuses/:id : get the "id" filingStatus.
     *
     * @param id the id of the filingStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filingStatus, or with status 404 (Not Found)
     */
    @GetMapping("/filing-statuses/{id}")
    @Timed
    public ResponseEntity<FilingStatus> getFilingStatus(@PathVariable Long id) {
        log.debug("REST request to get FilingStatus : {}", id);
        FilingStatus filingStatus = filingStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filingStatus));
    }

    /**
     * DELETE  /filing-statuses/:id : delete the "id" filingStatus.
     *
     * @param id the id of the filingStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filing-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilingStatus(@PathVariable Long id) {
        log.debug("REST request to delete FilingStatus : {}", id);
        filingStatusRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
