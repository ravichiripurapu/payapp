package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.CompanyContactType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing CompanyContactType.
 */
@RestController
@RequestMapping("/api")
public class CompanyContactTypeResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/company-contact-types")
    @Timed
    public List<CompanyContactType> getAllCompanyContactTypes() {
        return Arrays.asList(CompanyContactType.values());
    }

}
