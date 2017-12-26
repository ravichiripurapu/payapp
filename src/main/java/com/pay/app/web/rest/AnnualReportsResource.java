package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.AnnualReports;

import com.pay.app.repository.AnnualReportsRepository;
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
 * REST controller for managing AnnualReports.
 */
@RestController
@RequestMapping("/api")
public class AnnualReportsResource {

    private final Logger log = LoggerFactory.getLogger(AnnualReportsResource.class);

    private static final String ENTITY_NAME = "annualReports";

    private final AnnualReportsRepository annualReportsRepository;

    public AnnualReportsResource(AnnualReportsRepository annualReportsRepository) {
        this.annualReportsRepository = annualReportsRepository;
    }

    /**
     * POST  /annual-reports : Create a new annualReports.
     *
     * @param annualReports the annualReports to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annualReports, or with status 400 (Bad Request) if the annualReports has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annual-reports")
    @Timed
    public ResponseEntity<AnnualReports> createAnnualReports(@RequestBody AnnualReports annualReports) throws URISyntaxException {
        log.debug("REST request to save AnnualReports : {}", annualReports);
        if (annualReports.getId() != null) {
            throw new BadRequestAlertException("A new annualReports cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnualReports result = annualReportsRepository.save(annualReports);
        return ResponseEntity.created(new URI("/api/annual-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annual-reports : Updates an existing annualReports.
     *
     * @param annualReports the annualReports to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annualReports,
     * or with status 400 (Bad Request) if the annualReports is not valid,
     * or with status 500 (Internal Server Error) if the annualReports couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annual-reports")
    @Timed
    public ResponseEntity<AnnualReports> updateAnnualReports(@RequestBody AnnualReports annualReports) throws URISyntaxException {
        log.debug("REST request to update AnnualReports : {}", annualReports);
        if (annualReports.getId() == null) {
            return createAnnualReports(annualReports);
        }
        AnnualReports result = annualReportsRepository.save(annualReports);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annualReports.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annual-reports : get all the annualReports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of annualReports in body
     */
    @GetMapping("/annual-reports")
    @Timed
    public List<AnnualReports> getAllAnnualReports() {
        log.debug("REST request to get all AnnualReports");
        return annualReportsRepository.findAll();
        }

    /**
     * GET  /annual-reports/:id : get the "id" annualReports.
     *
     * @param id the id of the annualReports to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annualReports, or with status 404 (Not Found)
     */
    @GetMapping("/annual-reports/{id}")
    @Timed
    public ResponseEntity<AnnualReports> getAnnualReports(@PathVariable Long id) {
        log.debug("REST request to get AnnualReports : {}", id);
        AnnualReports annualReports = annualReportsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(annualReports));
    }

    /**
     * DELETE  /annual-reports/:id : delete the "id" annualReports.
     *
     * @param id the id of the annualReports to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annual-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnnualReports(@PathVariable Long id) {
        log.debug("REST request to delete AnnualReports : {}", id);
        annualReportsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
