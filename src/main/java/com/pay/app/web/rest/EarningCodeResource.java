package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.EarningCode;
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
public class EarningCodeResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/earning-codes")
    @Timed
    public List<EarningCode> getAllEarnings() {
        return Arrays.asList(EarningCode.values());
    }


}
