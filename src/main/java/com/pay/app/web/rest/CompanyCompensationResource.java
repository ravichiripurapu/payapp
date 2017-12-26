package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyCompensation;

import com.pay.app.repository.CompanyCompensationRepository;
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
 * REST controller for managing CompanyCompensation.
 */
@RestController
@RequestMapping("/api")
public class CompanyCompensationResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCompensationResource.class);

    private static final String ENTITY_NAME = "companyCompensation";

    private final CompanyCompensationRepository companyCompensationRepository;

    public CompanyCompensationResource(CompanyCompensationRepository companyCompensationRepository) {
        this.companyCompensationRepository = companyCompensationRepository;
    }

    /**
     * POST  /company-compensations : Create a new companyCompensation.
     *
     * @param companyCompensation the companyCompensation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyCompensation, or with status 400 (Bad Request) if the companyCompensation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-compensations")
    @Timed
    public ResponseEntity<CompanyCompensation> createCompanyCompensation(@RequestBody CompanyCompensation companyCompensation) throws URISyntaxException {
        log.debug("REST request to save CompanyCompensation : {}", companyCompensation);
        if (companyCompensation.getId() != null) {
            throw new BadRequestAlertException("A new companyCompensation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyCompensation result = companyCompensationRepository.save(companyCompensation);
        return ResponseEntity.created(new URI("/api/company-compensations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-compensations : Updates an existing companyCompensation.
     *
     * @param companyCompensation the companyCompensation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyCompensation,
     * or with status 400 (Bad Request) if the companyCompensation is not valid,
     * or with status 500 (Internal Server Error) if the companyCompensation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-compensations")
    @Timed
    public ResponseEntity<CompanyCompensation> updateCompanyCompensation(@RequestBody CompanyCompensation companyCompensation) throws URISyntaxException {
        log.debug("REST request to update CompanyCompensation : {}", companyCompensation);
        if (companyCompensation.getId() == null) {
            return createCompanyCompensation(companyCompensation);
        }
        CompanyCompensation result = companyCompensationRepository.save(companyCompensation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyCompensation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-compensations : get all the companyCompensations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyCompensations in body
     */
    @GetMapping("/company-compensations")
    @Timed
    public List<CompanyCompensation> getAllCompanyCompensations() {
        log.debug("REST request to get all CompanyCompensations");
        return companyCompensationRepository.findAll();
        }

    /**
     * GET  /company-compensations/:id : get the "id" companyCompensation.
     *
     * @param id the id of the companyCompensation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyCompensation, or with status 404 (Not Found)
     */
    @GetMapping("/company-compensations/{id}")
    @Timed
    public ResponseEntity<CompanyCompensation> getCompanyCompensation(@PathVariable Long id) {
        log.debug("REST request to get CompanyCompensation : {}", id);
        CompanyCompensation companyCompensation = companyCompensationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyCompensation));
    }

    /**
     * DELETE  /company-compensations/:id : delete the "id" companyCompensation.
     *
     * @param id the id of the companyCompensation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-compensations/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyCompensation(@PathVariable Long id) {
        log.debug("REST request to delete CompanyCompensation : {}", id);
        companyCompensationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
