package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyEarningType;

import com.pay.app.repository.CompanyEarningTypeRepository;
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
 * REST controller for managing CompanyEarningType.
 */
@RestController
@RequestMapping("/api")
public class CompanyEarningTypeResource {

    private final Logger log = LoggerFactory.getLogger(CompanyEarningTypeResource.class);

    private static final String ENTITY_NAME = "companyEarningType";

    private final CompanyEarningTypeRepository companyEarningTypeRepository;

    public CompanyEarningTypeResource(CompanyEarningTypeRepository companyEarningTypeRepository) {
        this.companyEarningTypeRepository = companyEarningTypeRepository;
    }

    /**
     * POST  /company-earning-types : Create a new companyEarningType.
     *
     * @param companyEarningType the companyEarningType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyEarningType, or with status 400 (Bad Request) if the companyEarningType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-earning-types")
    @Timed
    public ResponseEntity<CompanyEarningType> createCompanyEarning(@RequestBody CompanyEarningType companyEarningType) throws URISyntaxException {
        log.debug("REST request to save CompanyEarningType : {}", companyEarningType);
        if (companyEarningType.getId() != null) {
            throw new BadRequestAlertException("A new companyEarningType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyEarningType result = companyEarningTypeRepository.save(companyEarningType);
        return ResponseEntity.created(new URI("/api/company-earning-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-earning-types : Updates an existing companyEarningType.
     *
     * @param companyEarningType the companyEarningType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyEarningType,
     * or with status 400 (Bad Request) if the companyEarningType is not valid,
     * or with status 500 (Internal Server Error) if the companyEarningType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-earning-types")
    @Timed
    public ResponseEntity<CompanyEarningType> updateCompanyEarning(@RequestBody CompanyEarningType companyEarningType) throws URISyntaxException {
        log.debug("REST request to update CompanyEarningType : {}", companyEarningType);
        if (companyEarningType.getId() == null) {
            return createCompanyEarning(companyEarningType);
        }
        CompanyEarningType result = companyEarningTypeRepository.save(companyEarningType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyEarningType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-earning-types : get all the companyEarnings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyEarnings in body
     */
    @GetMapping("/company-earning-types")
    @Timed
    public List<CompanyEarningType> getAllCompanyEarnings() {
        log.debug("REST request to get all CompanyEarnings");
        return companyEarningTypeRepository.findAll();
        }

    /**
     * GET  /company-earning-types/:id : get the "id" companyEarning.
     *
     * @param id the id of the companyEarning to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyEarning, or with status 404 (Not Found)
     */
    @GetMapping("/company-earning-types/{id}")
    @Timed
    public ResponseEntity<CompanyEarningType> getCompanyEarning(@PathVariable Long id) {
        log.debug("REST request to get CompanyEarningType : {}", id);
        CompanyEarningType companyEarningType = companyEarningTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyEarningType));
    }

    /**
     * DELETE  /company-earning-types/:id : delete the "id" companyEarning.
     *
     * @param id the id of the companyEarning to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-earning-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyEarning(@PathVariable Long id) {
        log.debug("REST request to delete CompanyEarningType : {}", id);
        companyEarningTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
