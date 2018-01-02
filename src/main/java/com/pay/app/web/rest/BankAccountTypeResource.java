package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.BankAccountType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing BankAccountType.
 */
@RestController
@RequestMapping("/api")
public class BankAccountTypeResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/bank-account-types")
    @Timed
    public List<BankAccountType> getAllBankAccountTypes() {
        return Arrays.asList(BankAccountType.values());
    }

}
