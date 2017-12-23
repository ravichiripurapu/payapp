package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.DepositFrequency;

import com.pay.app.repository.DepositFrequencyRepository;
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
 * REST controller for managing DepositFrequency.
 */
@RestController
@RequestMapping("/api")
public class DepositFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(DepositFrequencyResource.class);

    private static final String ENTITY_NAME = "depositFrequency";

    private final DepositFrequencyRepository depositFrequencyRepository;

    public DepositFrequencyResource(DepositFrequencyRepository depositFrequencyRepository) {
        this.depositFrequencyRepository = depositFrequencyRepository;
    }

    /**
     * POST  /deposit-frequencies : Create a new depositFrequency.
     *
     * @param depositFrequency the depositFrequency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depositFrequency, or with status 400 (Bad Request) if the depositFrequency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deposit-frequencies")
    @Timed
    public ResponseEntity<DepositFrequency> createDepositFrequency(@RequestBody DepositFrequency depositFrequency) throws URISyntaxException {
        log.debug("REST request to save DepositFrequency : {}", depositFrequency);
        if (depositFrequency.getId() != null) {
            throw new BadRequestAlertException("A new depositFrequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositFrequency result = depositFrequencyRepository.save(depositFrequency);
        return ResponseEntity.created(new URI("/api/deposit-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deposit-frequencies : Updates an existing depositFrequency.
     *
     * @param depositFrequency the depositFrequency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depositFrequency,
     * or with status 400 (Bad Request) if the depositFrequency is not valid,
     * or with status 500 (Internal Server Error) if the depositFrequency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deposit-frequencies")
    @Timed
    public ResponseEntity<DepositFrequency> updateDepositFrequency(@RequestBody DepositFrequency depositFrequency) throws URISyntaxException {
        log.debug("REST request to update DepositFrequency : {}", depositFrequency);
        if (depositFrequency.getId() == null) {
            return createDepositFrequency(depositFrequency);
        }
        DepositFrequency result = depositFrequencyRepository.save(depositFrequency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depositFrequency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deposit-frequencies : get all the depositFrequencies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of depositFrequencies in body
     */
    @GetMapping("/deposit-frequencies")
    @Timed
    public List<DepositFrequency> getAllDepositFrequencies() {
        log.debug("REST request to get all DepositFrequencies");
        return depositFrequencyRepository.findAll();
        }

    /**
     * GET  /deposit-frequencies/:id : get the "id" depositFrequency.
     *
     * @param id the id of the depositFrequency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depositFrequency, or with status 404 (Not Found)
     */
    @GetMapping("/deposit-frequencies/{id}")
    @Timed
    public ResponseEntity<DepositFrequency> getDepositFrequency(@PathVariable Long id) {
        log.debug("REST request to get DepositFrequency : {}", id);
        DepositFrequency depositFrequency = depositFrequencyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(depositFrequency));
    }

    /**
     * DELETE  /deposit-frequencies/:id : delete the "id" depositFrequency.
     *
     * @param id the id of the depositFrequency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deposit-frequencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepositFrequency(@PathVariable Long id) {
        log.debug("REST request to delete DepositFrequency : {}", id);
        depositFrequencyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
