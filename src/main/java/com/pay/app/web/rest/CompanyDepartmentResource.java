package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.CompanyDepartment;

import com.pay.app.repository.CompanyDepartmentRepository;
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
 * REST controller for managing CompanyDepartment.
 */
@RestController
@RequestMapping("/api")
public class CompanyDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(CompanyDepartmentResource.class);

    private static final String ENTITY_NAME = "companyDepartment";

    private final CompanyDepartmentRepository companyDepartmentRepository;

    public CompanyDepartmentResource(CompanyDepartmentRepository companyDepartmentRepository) {
        this.companyDepartmentRepository = companyDepartmentRepository;
    }

    /**
     * POST  /company-departments : Create a new companyDepartment.
     *
     * @param companyDepartment the companyDepartment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDepartment, or with status 400 (Bad Request) if the companyDepartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-departments")
    @Timed
    public ResponseEntity<CompanyDepartment> createCompanyDepartment(@RequestBody CompanyDepartment companyDepartment) throws URISyntaxException {
        log.debug("REST request to save CompanyDepartment : {}", companyDepartment);
        if (companyDepartment.getId() != null) {
            throw new BadRequestAlertException("A new companyDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyDepartment result = companyDepartmentRepository.save(companyDepartment);
        return ResponseEntity.created(new URI("/api/company-departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-departments : Updates an existing companyDepartment.
     *
     * @param companyDepartment the companyDepartment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDepartment,
     * or with status 400 (Bad Request) if the companyDepartment is not valid,
     * or with status 500 (Internal Server Error) if the companyDepartment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-departments")
    @Timed
    public ResponseEntity<CompanyDepartment> updateCompanyDepartment(@RequestBody CompanyDepartment companyDepartment) throws URISyntaxException {
        log.debug("REST request to update CompanyDepartment : {}", companyDepartment);
        if (companyDepartment.getId() == null) {
            return createCompanyDepartment(companyDepartment);
        }
        CompanyDepartment result = companyDepartmentRepository.save(companyDepartment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDepartment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-departments : get all the companyDepartments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyDepartments in body
     */
    @GetMapping("/company-departments")
    @Timed
    public List<CompanyDepartment> getAllCompanyDepartments() {
        log.debug("REST request to get all CompanyDepartments");
        return companyDepartmentRepository.findAll();
        }

    /**
     * GET  /company-departments/:id : get the "id" companyDepartment.
     *
     * @param id the id of the companyDepartment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDepartment, or with status 404 (Not Found)
     */
    @GetMapping("/company-departments/{id}")
    @Timed
    public ResponseEntity<CompanyDepartment> getCompanyDepartment(@PathVariable Long id) {
        log.debug("REST request to get CompanyDepartment : {}", id);
        CompanyDepartment companyDepartment = companyDepartmentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyDepartment));
    }

    /**
     * DELETE  /company-departments/:id : delete the "id" companyDepartment.
     *
     * @param id the id of the companyDepartment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-departments/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyDepartment(@PathVariable Long id) {
        log.debug("REST request to delete CompanyDepartment : {}", id);
        companyDepartmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
