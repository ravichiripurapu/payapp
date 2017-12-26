package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyEarning;

import com.pay.app.repository.CompanyEarningRepository;
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
 * REST controller for managing CompanyEarning.
 */
@RestController
@RequestMapping("/api")
public class CompanyEarningResource {

    private final Logger log = LoggerFactory.getLogger(CompanyEarningResource.class);

    private static final String ENTITY_NAME = "companyEarning";

    private final CompanyEarningRepository companyEarningRepository;

    public CompanyEarningResource(CompanyEarningRepository companyEarningRepository) {
        this.companyEarningRepository = companyEarningRepository;
    }

    /**
     * POST  /company-earnings : Create a new companyEarning.
     *
     * @param companyEarning the companyEarning to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyEarning, or with status 400 (Bad Request) if the companyEarning has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-earnings")
    @Timed
    public ResponseEntity<CompanyEarning> createCompanyEarning(@RequestBody CompanyEarning companyEarning) throws URISyntaxException {
        log.debug("REST request to save CompanyEarning : {}", companyEarning);
        if (companyEarning.getId() != null) {
            throw new BadRequestAlertException("A new companyEarning cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyEarning result = companyEarningRepository.save(companyEarning);
        return ResponseEntity.created(new URI("/api/company-earnings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-earnings : Updates an existing companyEarning.
     *
     * @param companyEarning the companyEarning to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyEarning,
     * or with status 400 (Bad Request) if the companyEarning is not valid,
     * or with status 500 (Internal Server Error) if the companyEarning couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-earnings")
    @Timed
    public ResponseEntity<CompanyEarning> updateCompanyEarning(@RequestBody CompanyEarning companyEarning) throws URISyntaxException {
        log.debug("REST request to update CompanyEarning : {}", companyEarning);
        if (companyEarning.getId() == null) {
            return createCompanyEarning(companyEarning);
        }
        CompanyEarning result = companyEarningRepository.save(companyEarning);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyEarning.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-earnings : get all the companyEarnings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyEarnings in body
     */
    @GetMapping("/company-earnings")
    @Timed
    public List<CompanyEarning> getAllCompanyEarnings() {
        log.debug("REST request to get all CompanyEarnings");
        return companyEarningRepository.findAll();
        }

    /**
     * GET  /company-earnings/:id : get the "id" companyEarning.
     *
     * @param id the id of the companyEarning to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyEarning, or with status 404 (Not Found)
     */
    @GetMapping("/company-earnings/{id}")
    @Timed
    public ResponseEntity<CompanyEarning> getCompanyEarning(@PathVariable Long id) {
        log.debug("REST request to get CompanyEarning : {}", id);
        CompanyEarning companyEarning = companyEarningRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyEarning));
    }

    /**
     * DELETE  /company-earnings/:id : delete the "id" companyEarning.
     *
     * @param id the id of the companyEarning to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-earnings/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyEarning(@PathVariable Long id) {
        log.debug("REST request to delete CompanyEarning : {}", id);
        companyEarningRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
