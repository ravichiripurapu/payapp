package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyStateTax;

import com.pay.app.repository.CompanyStateTaxRepository;
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
 * REST controller for managing CompanyStateTax.
 */
@RestController
@RequestMapping("/api")
public class CompanyStateTaxResource {

    private final Logger log = LoggerFactory.getLogger(CompanyStateTaxResource.class);

    private static final String ENTITY_NAME = "companyStateTax";

    private final CompanyStateTaxRepository companyStateTaxRepository;

    public CompanyStateTaxResource(CompanyStateTaxRepository companyStateTaxRepository) {
        this.companyStateTaxRepository = companyStateTaxRepository;
    }

    /**
     * POST  /company-state-taxes : Create a new companyStateTax.
     *
     * @param companyStateTax the companyStateTax to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyStateTax, or with status 400 (Bad Request) if the companyStateTax has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-state-taxes")
    @Timed
    public ResponseEntity<CompanyStateTax> createCompanyStateTax(@RequestBody CompanyStateTax companyStateTax) throws URISyntaxException {
        log.debug("REST request to save CompanyStateTax : {}", companyStateTax);
        if (companyStateTax.getId() != null) {
            throw new BadRequestAlertException("A new companyStateTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyStateTax result = companyStateTaxRepository.save(companyStateTax);
        return ResponseEntity.created(new URI("/api/company-state-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-state-taxes : Updates an existing companyStateTax.
     *
     * @param companyStateTax the companyStateTax to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyStateTax,
     * or with status 400 (Bad Request) if the companyStateTax is not valid,
     * or with status 500 (Internal Server Error) if the companyStateTax couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-state-taxes")
    @Timed
    public ResponseEntity<CompanyStateTax> updateCompanyStateTax(@RequestBody CompanyStateTax companyStateTax) throws URISyntaxException {
        log.debug("REST request to update CompanyStateTax : {}", companyStateTax);
        if (companyStateTax.getId() == null) {
            return createCompanyStateTax(companyStateTax);
        }
        CompanyStateTax result = companyStateTaxRepository.save(companyStateTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyStateTax.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-state-taxes : get all the companyStateTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyStateTaxes in body
     */
    @GetMapping("/company-state-taxes")
    @Timed
    public List<CompanyStateTax> getAllCompanyStateTaxes() {
        log.debug("REST request to get all CompanyStateTaxes");
        return companyStateTaxRepository.findAll();
        }

    /**
     * GET  /company-state-taxes/:id : get the "id" companyStateTax.
     *
     * @param id the id of the companyStateTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyStateTax, or with status 404 (Not Found)
     */
    @GetMapping("/company-state-taxes/{id}")
    @Timed
    public ResponseEntity<CompanyStateTax> getCompanyStateTax(@PathVariable Long id) {
        log.debug("REST request to get CompanyStateTax : {}", id);
        CompanyStateTax companyStateTax = companyStateTaxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyStateTax));
    }

    /**
     * DELETE  /company-state-taxes/:id : delete the "id" companyStateTax.
     *
     * @param id the id of the companyStateTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-state-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyStateTax(@PathVariable Long id) {
        log.debug("REST request to delete CompanyStateTax : {}", id);
        companyStateTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
