package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.EmployeeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing EmployeeType.
 */
@RestController
@RequestMapping("/api")
public class EmployeeTypeResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/employee-types")
    @Timed
    public List<EmployeeType> getAllEmployeeTypes() {
        return Arrays.asList(EmployeeType.values());
    }


}
