package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.DeductionCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing State.
 */
@RestController
@RequestMapping("/api")
public class DeductionCodeResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/deduction-codes")
    @Timed
    public List<DeductionCode> getAllDeductionCodes() {
        return Arrays.asList(DeductionCode.values());
    }


}
