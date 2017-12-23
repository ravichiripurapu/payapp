package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyContact;

import com.pay.app.repository.CompanyContactRepository;
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
 * REST controller for managing CompanyContact.
 */
@RestController
@RequestMapping("/api")
public class CompanyContactResource {

    private final Logger log = LoggerFactory.getLogger(CompanyContactResource.class);

    private static final String ENTITY_NAME = "companyContact";

    private final CompanyContactRepository companyContactRepository;

    public CompanyContactResource(CompanyContactRepository companyContactRepository) {
        this.companyContactRepository = companyContactRepository;
    }

    /**
     * POST  /company-contacts : Create a new companyContact.
     *
     * @param companyContact the companyContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyContact, or with status 400 (Bad Request) if the companyContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-contacts")
    @Timed
    public ResponseEntity<CompanyContact> createCompanyContact(@RequestBody CompanyContact companyContact) throws URISyntaxException {
        log.debug("REST request to save CompanyContact : {}", companyContact);
        if (companyContact.getId() != null) {
            throw new BadRequestAlertException("A new companyContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyContact result = companyContactRepository.save(companyContact);
        return ResponseEntity.created(new URI("/api/company-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-contacts : Updates an existing companyContact.
     *
     * @param companyContact the companyContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyContact,
     * or with status 400 (Bad Request) if the companyContact is not valid,
     * or with status 500 (Internal Server Error) if the companyContact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-contacts")
    @Timed
    public ResponseEntity<CompanyContact> updateCompanyContact(@RequestBody CompanyContact companyContact) throws URISyntaxException {
        log.debug("REST request to update CompanyContact : {}", companyContact);
        if (companyContact.getId() == null) {
            return createCompanyContact(companyContact);
        }
        CompanyContact result = companyContactRepository.save(companyContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-contacts : get all the companyContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyContacts in body
     */
    @GetMapping("/company-contacts")
    @Timed
    public List<CompanyContact> getAllCompanyContacts() {
        log.debug("REST request to get all CompanyContacts");
        return companyContactRepository.findAll();
        }

    /**
     * GET  /company-contacts/:id : get the "id" companyContact.
     *
     * @param id the id of the companyContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyContact, or with status 404 (Not Found)
     */
    @GetMapping("/company-contacts/{id}")
    @Timed
    public ResponseEntity<CompanyContact> getCompanyContact(@PathVariable Long id) {
        log.debug("REST request to get CompanyContact : {}", id);
        CompanyContact companyContact = companyContactRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyContact));
    }

    /**
     * DELETE  /company-contacts/:id : delete the "id" companyContact.
     *
     * @param id the id of the companyContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyContact(@PathVariable Long id) {
        log.debug("REST request to delete CompanyContact : {}", id);
        companyContactRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
