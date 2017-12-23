package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyLocalTax;

import com.pay.app.repository.CompanyLocalTaxRepository;
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
 * REST controller for managing CompanyLocalTax.
 */
@RestController
@RequestMapping("/api")
public class CompanyLocalTaxResource {

    private final Logger log = LoggerFactory.getLogger(CompanyLocalTaxResource.class);

    private static final String ENTITY_NAME = "companyLocalTax";

    private final CompanyLocalTaxRepository companyLocalTaxRepository;

    public CompanyLocalTaxResource(CompanyLocalTaxRepository companyLocalTaxRepository) {
        this.companyLocalTaxRepository = companyLocalTaxRepository;
    }

    /**
     * POST  /company-local-taxes : Create a new companyLocalTax.
     *
     * @param companyLocalTax the companyLocalTax to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyLocalTax, or with status 400 (Bad Request) if the companyLocalTax has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-local-taxes")
    @Timed
    public ResponseEntity<CompanyLocalTax> createCompanyLocalTax(@RequestBody CompanyLocalTax companyLocalTax) throws URISyntaxException {
        log.debug("REST request to save CompanyLocalTax : {}", companyLocalTax);
        if (companyLocalTax.getId() != null) {
            throw new BadRequestAlertException("A new companyLocalTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyLocalTax result = companyLocalTaxRepository.save(companyLocalTax);
        return ResponseEntity.created(new URI("/api/company-local-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-local-taxes : Updates an existing companyLocalTax.
     *
     * @param companyLocalTax the companyLocalTax to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyLocalTax,
     * or with status 400 (Bad Request) if the companyLocalTax is not valid,
     * or with status 500 (Internal Server Error) if the companyLocalTax couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-local-taxes")
    @Timed
    public ResponseEntity<CompanyLocalTax> updateCompanyLocalTax(@RequestBody CompanyLocalTax companyLocalTax) throws URISyntaxException {
        log.debug("REST request to update CompanyLocalTax : {}", companyLocalTax);
        if (companyLocalTax.getId() == null) {
            return createCompanyLocalTax(companyLocalTax);
        }
        CompanyLocalTax result = companyLocalTaxRepository.save(companyLocalTax);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyLocalTax.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-local-taxes : get all the companyLocalTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyLocalTaxes in body
     */
    @GetMapping("/company-local-taxes")
    @Timed
    public List<CompanyLocalTax> getAllCompanyLocalTaxes() {
        log.debug("REST request to get all CompanyLocalTaxes");
        return companyLocalTaxRepository.findAll();
        }

    /**
     * GET  /company-local-taxes/:id : get the "id" companyLocalTax.
     *
     * @param id the id of the companyLocalTax to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyLocalTax, or with status 404 (Not Found)
     */
    @GetMapping("/company-local-taxes/{id}")
    @Timed
    public ResponseEntity<CompanyLocalTax> getCompanyLocalTax(@PathVariable Long id) {
        log.debug("REST request to get CompanyLocalTax : {}", id);
        CompanyLocalTax companyLocalTax = companyLocalTaxRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyLocalTax));
    }

    /**
     * DELETE  /company-local-taxes/:id : delete the "id" companyLocalTax.
     *
     * @param id the id of the companyLocalTax to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-local-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyLocalTax(@PathVariable Long id) {
        log.debug("REST request to delete CompanyLocalTax : {}", id);
        companyLocalTaxRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
