package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.pay.app.domain.CompanyTaxInfo;
import com.pay.app.repository.CompanyTaxInfoRepository;
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
 * REST controller for managing CompanyTaxInfo.
 */
@RestController
@RequestMapping("/api")
public class CompanyTaxInfoResource {

    private final Logger log = LoggerFactory.getLogger(CompanyTaxInfoResource.class);

    private static final String ENTITY_NAME = "CompanyTaxInfo";

    private final CompanyTaxInfoRepository companyTaxInfoRepository;

    public CompanyTaxInfoResource(CompanyTaxInfoRepository companyTaxInfoRepository) {
        this.companyTaxInfoRepository = companyTaxInfoRepository;
    }

    /**
     * POST  /company-tax-info : Create a new CompanyTaxInfo.
     *
     * @param CompanyTaxInfo the CompanyTaxInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new CompanyTaxInfo, or with status 400 (Bad Request) if the CompanyTaxInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-tax-info")
    @Timed
    public ResponseEntity<CompanyTaxInfo> createCompanyTaxInfo(@RequestBody CompanyTaxInfo CompanyTaxInfo) throws URISyntaxException {
        log.debug("REST request to save CompanyTaxInfo : {}", CompanyTaxInfo);
        if (CompanyTaxInfo.getId() != null) {
            throw new BadRequestAlertException("A new CompanyTaxInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyTaxInfo result = companyTaxInfoRepository.save(CompanyTaxInfo);
        return ResponseEntity.created(new URI("/api/company-tax-info/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-tax-info : Updates an existing CompanyTaxInfo.
     *
     * @param CompanyTaxInfo the CompanyTaxInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated CompanyTaxInfo,
     * or with status 400 (Bad Request) if the CompanyTaxInfo is not valid,
     * or with status 500 (Internal Server Error) if the CompanyTaxInfo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-tax-info")
    @Timed
    public ResponseEntity<CompanyTaxInfo> updateCompanyTaxInfo(@RequestBody CompanyTaxInfo CompanyTaxInfo) throws URISyntaxException {
        log.debug("REST request to update CompanyTaxInfo : {}", CompanyTaxInfo);
        if (CompanyTaxInfo.getId() == null) {
            return createCompanyTaxInfo(CompanyTaxInfo);
        }
        CompanyTaxInfo result = companyTaxInfoRepository.save(CompanyTaxInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, CompanyTaxInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-tax-info : get all the CompanyTaxInfoes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of CompanyTaxInfoes in body
     */
    @GetMapping("/company-tax-info")
    @Timed
    public List<CompanyTaxInfo> getAllCompanyTaxInfoes() {
        log.debug("REST request to get all CompanyTaxInfoes");
        return companyTaxInfoRepository.findAll();
        }

    /**
     * GET  /company-tax-info/:id : get the "id" CompanyTaxInfo.
     *
     * @param id the id of the CompanyTaxInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the CompanyTaxInfo, or with status 404 (Not Found)
     */
    @GetMapping("/company-tax-info/{id}")
    @Timed
    public ResponseEntity<CompanyTaxInfo> getCompanyTaxInfo(@PathVariable Long id) {
        log.debug("REST request to get CompanyTaxInfo : {}", id);
        CompanyTaxInfo CompanyTaxInfo = companyTaxInfoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(CompanyTaxInfo));
    }

    /**
     * DELETE  /company-tax-info/:id : delete the "id" CompanyTaxInfo.
     *
     * @param id the id of the CompanyTaxInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-tax-info/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyTaxInfo(@PathVariable Long id) {
        log.debug("REST request to delete CompanyTaxInfo : {}", id);
        companyTaxInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
