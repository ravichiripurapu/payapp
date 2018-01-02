package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.State;


import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;

/**
 * REST controller for managing State.
 */
@RestController
@RequestMapping("/api")
public class StateResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/states")
    @Timed
    public List<State> getAllStates() {
        return Arrays.asList(State.values());
    }


}
