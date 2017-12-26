package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyBank;

import com.pay.app.repository.CompanyBankRepository;
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
 * REST controller for managing CompanyBank.
 */
@RestController
@RequestMapping("/api")
public class CompanyBankResource {

    private final Logger log = LoggerFactory.getLogger(CompanyBankResource.class);

    private static final String ENTITY_NAME = "companyBank";

    private final CompanyBankRepository companyBankRepository;

    public CompanyBankResource(CompanyBankRepository companyBankRepository) {
        this.companyBankRepository = companyBankRepository;
    }

    /**
     * POST  /company-banks : Create a new companyBank.
     *
     * @param companyBank the companyBank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyBank, or with status 400 (Bad Request) if the companyBank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-banks")
    @Timed
    public ResponseEntity<CompanyBank> createCompanyBank(@RequestBody CompanyBank companyBank) throws URISyntaxException {
        log.debug("REST request to save CompanyBank : {}", companyBank);
        if (companyBank.getId() != null) {
            throw new BadRequestAlertException("A new companyBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyBank result = companyBankRepository.save(companyBank);
        return ResponseEntity.created(new URI("/api/company-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-banks : Updates an existing companyBank.
     *
     * @param companyBank the companyBank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyBank,
     * or with status 400 (Bad Request) if the companyBank is not valid,
     * or with status 500 (Internal Server Error) if the companyBank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-banks")
    @Timed
    public ResponseEntity<CompanyBank> updateCompanyBank(@RequestBody CompanyBank companyBank) throws URISyntaxException {
        log.debug("REST request to update CompanyBank : {}", companyBank);
        if (companyBank.getId() == null) {
            return createCompanyBank(companyBank);
        }
        CompanyBank result = companyBankRepository.save(companyBank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyBank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-banks : get all the companyBanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyBanks in body
     */
    @GetMapping("/company-banks")
    @Timed
    public List<CompanyBank> getAllCompanyBanks() {
        log.debug("REST request to get all CompanyBanks");
        return companyBankRepository.findAll();
        }

    /**
     * GET  /company-banks/:id : get the "id" companyBank.
     *
     * @param id the id of the companyBank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyBank, or with status 404 (Not Found)
     */
    @GetMapping("/company-banks/{id}")
    @Timed
    public ResponseEntity<CompanyBank> getCompanyBank(@PathVariable Long id) {
        log.debug("REST request to get CompanyBank : {}", id);
        CompanyBank companyBank = companyBankRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyBank));
    }

    /**
     * DELETE  /company-banks/:id : delete the "id" companyBank.
     *
     * @param id the id of the companyBank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-banks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyBank(@PathVariable Long id) {
        log.debug("REST request to delete CompanyBank : {}", id);
        companyBankRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
