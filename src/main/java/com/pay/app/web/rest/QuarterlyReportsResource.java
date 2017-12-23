package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.QuarterlyReports;

import com.pay.app.repository.QuarterlyReportsRepository;
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
 * REST controller for managing QuarterlyReports.
 */
@RestController
@RequestMapping("/api")
public class QuarterlyReportsResource {

    private final Logger log = LoggerFactory.getLogger(QuarterlyReportsResource.class);

    private static final String ENTITY_NAME = "quarterlyReports";

    private final QuarterlyReportsRepository quarterlyReportsRepository;

    public QuarterlyReportsResource(QuarterlyReportsRepository quarterlyReportsRepository) {
        this.quarterlyReportsRepository = quarterlyReportsRepository;
    }

    /**
     * POST  /quarterly-reports : Create a new quarterlyReports.
     *
     * @param quarterlyReports the quarterlyReports to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quarterlyReports, or with status 400 (Bad Request) if the quarterlyReports has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quarterly-reports")
    @Timed
    public ResponseEntity<QuarterlyReports> createQuarterlyReports(@RequestBody QuarterlyReports quarterlyReports) throws URISyntaxException {
        log.debug("REST request to save QuarterlyReports : {}", quarterlyReports);
        if (quarterlyReports.getId() != null) {
            throw new BadRequestAlertException("A new quarterlyReports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuarterlyReports result = quarterlyReportsRepository.save(quarterlyReports);
        return ResponseEntity.created(new URI("/api/quarterly-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quarterly-reports : Updates an existing quarterlyReports.
     *
     * @param quarterlyReports the quarterlyReports to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quarterlyReports,
     * or with status 400 (Bad Request) if the quarterlyReports is not valid,
     * or with status 500 (Internal Server Error) if the quarterlyReports couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quarterly-reports")
    @Timed
    public ResponseEntity<QuarterlyReports> updateQuarterlyReports(@RequestBody QuarterlyReports quarterlyReports) throws URISyntaxException {
        log.debug("REST request to update QuarterlyReports : {}", quarterlyReports);
        if (quarterlyReports.getId() == null) {
            return createQuarterlyReports(quarterlyReports);
        }
        QuarterlyReports result = quarterlyReportsRepository.save(quarterlyReports);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quarterlyReports.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quarterly-reports : get all the quarterlyReports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quarterlyReports in body
     */
    @GetMapping("/quarterly-reports")
    @Timed
    public List<QuarterlyReports> getAllQuarterlyReports() {
        log.debug("REST request to get all QuarterlyReports");
        return quarterlyReportsRepository.findAll();
        }

    /**
     * GET  /quarterly-reports/:id : get the "id" quarterlyReports.
     *
     * @param id the id of the quarterlyReports to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quarterlyReports, or with status 404 (Not Found)
     */
    @GetMapping("/quarterly-reports/{id}")
    @Timed
    public ResponseEntity<QuarterlyReports> getQuarterlyReports(@PathVariable Long id) {
        log.debug("REST request to get QuarterlyReports : {}", id);
        QuarterlyReports quarterlyReports = quarterlyReportsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(quarterlyReports));
    }

    /**
     * DELETE  /quarterly-reports/:id : delete the "id" quarterlyReports.
     *
     * @param id the id of the quarterlyReports to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quarterly-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuarterlyReports(@PathVariable Long id) {
        log.debug("REST request to delete QuarterlyReports : {}", id);
        quarterlyReportsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
