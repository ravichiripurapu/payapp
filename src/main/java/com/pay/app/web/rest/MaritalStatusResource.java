package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.Gender;
import com.pay.app.enumerations.MaritalStatus;

import com.pay.app.web.rest.errors.BadRequestAlertException;
import com.pay.app.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MaritalStatus.
 */
@RestController
@RequestMapping("/api")
public class MaritalStatusResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/marital-statuses")
    @Timed
    public List<MaritalStatus> getAllGenders() {
        return Arrays.asList(MaritalStatus.values());
    }
}
