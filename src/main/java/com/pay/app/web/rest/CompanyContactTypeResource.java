package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyContactType;

import com.pay.app.repository.CompanyContactTypeRepository;
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
 * REST controller for managing CompanyContactType.
 */
@RestController
@RequestMapping("/api")
public class CompanyContactTypeResource {

    private final Logger log = LoggerFactory.getLogger(CompanyContactTypeResource.class);

    private static final String ENTITY_NAME = "companyContactType";

    private final CompanyContactTypeRepository companyContactTypeRepository;

    public CompanyContactTypeResource(CompanyContactTypeRepository companyContactTypeRepository) {
        this.companyContactTypeRepository = companyContactTypeRepository;
    }

    /**
     * POST  /company-contact-types : Create a new companyContactType.
     *
     * @param companyContactType the companyContactType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyContactType, or with status 400 (Bad Request) if the companyContactType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-contact-types")
    @Timed
    public ResponseEntity<CompanyContactType> createCompanyContactType(@RequestBody CompanyContactType companyContactType) throws URISyntaxException {
        log.debug("REST request to save CompanyContactType : {}", companyContactType);
        if (companyContactType.getId() != null) {
            throw new BadRequestAlertException("A new companyContactType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyContactType result = companyContactTypeRepository.save(companyContactType);
        return ResponseEntity.created(new URI("/api/company-contact-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-contact-types : Updates an existing companyContactType.
     *
     * @param companyContactType the companyContactType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyContactType,
     * or with status 400 (Bad Request) if the companyContactType is not valid,
     * or with status 500 (Internal Server Error) if the companyContactType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-contact-types")
    @Timed
    public ResponseEntity<CompanyContactType> updateCompanyContactType(@RequestBody CompanyContactType companyContactType) throws URISyntaxException {
        log.debug("REST request to update CompanyContactType : {}", companyContactType);
        if (companyContactType.getId() == null) {
            return createCompanyContactType(companyContactType);
        }
        CompanyContactType result = companyContactTypeRepository.save(companyContactType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyContactType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-contact-types : get all the companyContactTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyContactTypes in body
     */
    @GetMapping("/company-contact-types")
    @Timed
    public List<CompanyContactType> getAllCompanyContactTypes() {
        log.debug("REST request to get all CompanyContactTypes");
        return companyContactTypeRepository.findAll();
        }

    /**
     * GET  /company-contact-types/:id : get the "id" companyContactType.
     *
     * @param id the id of the companyContactType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyContactType, or with status 404 (Not Found)
     */
    @GetMapping("/company-contact-types/{id}")
    @Timed
    public ResponseEntity<CompanyContactType> getCompanyContactType(@PathVariable Long id) {
        log.debug("REST request to get CompanyContactType : {}", id);
        CompanyContactType companyContactType = companyContactTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyContactType));
    }

    /**
     * DELETE  /company-contact-types/:id : delete the "id" companyContactType.
     *
     * @param id the id of the companyContactType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-contact-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyContactType(@PathVariable Long id) {
        log.debug("REST request to delete CompanyContactType : {}", id);
        companyContactTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
