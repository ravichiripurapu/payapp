package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.enumerations.FilingStatus;
import com.pay.app.enumerations.FutaExemptReasonCode;

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
 * REST controller for managing FutaExemptReasonCode.
 */
@RestController
@RequestMapping("/api")
public class FutaExemptReasonCodeResource {

    /**
     * GET  /states : get all the states.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of states in body
     */
    @GetMapping("/futa-exempt-reason-codes")
    @Timed
    public List<FutaExemptReasonCode> getAllFutaExemptReasonCodes() {
        return Arrays.asList(FutaExemptReasonCode.values());
    }

}
