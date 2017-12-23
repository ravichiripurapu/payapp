package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.W2Reports;

import com.pay.app.repository.W2ReportsRepository;
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
 * REST controller for managing W2Reports.
 */
@RestController
@RequestMapping("/api")
public class W2ReportsResource {

    private final Logger log = LoggerFactory.getLogger(W2ReportsResource.class);

    private static final String ENTITY_NAME = "w2Reports";

    private final W2ReportsRepository w2ReportsRepository;

    public W2ReportsResource(W2ReportsRepository w2ReportsRepository) {
        this.w2ReportsRepository = w2ReportsRepository;
    }

    /**
     * POST  /w-2-reports : Create a new w2Reports.
     *
     * @param w2Reports the w2Reports to create
     * @return the ResponseEntity with status 201 (Created) and with body the new w2Reports, or with status 400 (Bad Request) if the w2Reports has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/w-2-reports")
    @Timed
    public ResponseEntity<W2Reports> createW2Reports(@RequestBody W2Reports w2Reports) throws URISyntaxException {
        log.debug("REST request to save W2Reports : {}", w2Reports);
        if (w2Reports.getId() != null) {
            throw new BadRequestAlertException("A new w2Reports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        W2Reports result = w2ReportsRepository.save(w2Reports);
        return ResponseEntity.created(new URI("/api/w-2-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /w-2-reports : Updates an existing w2Reports.
     *
     * @param w2Reports the w2Reports to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated w2Reports,
     * or with status 400 (Bad Request) if the w2Reports is not valid,
     * or with status 500 (Internal Server Error) if the w2Reports couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/w-2-reports")
    @Timed
    public ResponseEntity<W2Reports> updateW2Reports(@RequestBody W2Reports w2Reports) throws URISyntaxException {
        log.debug("REST request to update W2Reports : {}", w2Reports);
        if (w2Reports.getId() == null) {
            return createW2Reports(w2Reports);
        }
        W2Reports result = w2ReportsRepository.save(w2Reports);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, w2Reports.getId().toString()))
            .body(result);
    }

    /**
     * GET  /w-2-reports : get all the w2Reports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of w2Reports in body
     */
    @GetMapping("/w-2-reports")
    @Timed
    public List<W2Reports> getAllW2Reports() {
        log.debug("REST request to get all W2Reports");
        return w2ReportsRepository.findAll();
        }

    /**
     * GET  /w-2-reports/:id : get the "id" w2Reports.
     *
     * @param id the id of the w2Reports to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the w2Reports, or with status 404 (Not Found)
     */
    @GetMapping("/w-2-reports/{id}")
    @Timed
    public ResponseEntity<W2Reports> getW2Reports(@PathVariable Long id) {
        log.debug("REST request to get W2Reports : {}", id);
        W2Reports w2Reports = w2ReportsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(w2Reports));
    }

    /**
     * DELETE  /w-2-reports/:id : delete the "id" w2Reports.
     *
     * @param id the id of the w2Reports to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/w-2-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteW2Reports(@PathVariable Long id) {
        log.debug("REST request to delete W2Reports : {}", id);
        w2ReportsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
