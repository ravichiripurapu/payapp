package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.LocalTaxes;

import com.pay.app.repository.LocalTaxesRepository;
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
 * REST controller for managing LocalTaxes.
 */
@RestController
@RequestMapping("/api")
public class LocalTaxesResource {

    private final Logger log = LoggerFactory.getLogger(LocalTaxesResource.class);

    private static final String ENTITY_NAME = "localTaxes";

    private final LocalTaxesRepository localTaxesRepository;

    public LocalTaxesResource(LocalTaxesRepository localTaxesRepository) {
        this.localTaxesRepository = localTaxesRepository;
    }

    /**
     * POST  /local-taxes : Create a new localTaxes.
     *
     * @param localTaxes the localTaxes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new localTaxes, or with status 400 (Bad Request) if the localTaxes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/local-taxes")
    @Timed
    public ResponseEntity<LocalTaxes> createLocalTaxes(@RequestBody LocalTaxes localTaxes) throws URISyntaxException {
        log.debug("REST request to save LocalTaxes : {}", localTaxes);
        if (localTaxes.getId() != null) {
            throw new BadRequestAlertException("A new localTaxes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalTaxes result = localTaxesRepository.save(localTaxes);
        return ResponseEntity.created(new URI("/api/local-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /local-taxes : Updates an existing localTaxes.
     *
     * @param localTaxes the localTaxes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated localTaxes,
     * or with status 400 (Bad Request) if the localTaxes is not valid,
     * or with status 500 (Internal Server Error) if the localTaxes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/local-taxes")
    @Timed
    public ResponseEntity<LocalTaxes> updateLocalTaxes(@RequestBody LocalTaxes localTaxes) throws URISyntaxException {
        log.debug("REST request to update LocalTaxes : {}", localTaxes);
        if (localTaxes.getId() == null) {
            return createLocalTaxes(localTaxes);
        }
        LocalTaxes result = localTaxesRepository.save(localTaxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, localTaxes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /local-taxes : get all the localTaxes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of localTaxes in body
     */
    @GetMapping("/local-taxes")
    @Timed
    public List<LocalTaxes> getAllLocalTaxes() {
        log.debug("REST request to get all LocalTaxes");
        return localTaxesRepository.findAll();
        }

    /**
     * GET  /local-taxes/:id : get the "id" localTaxes.
     *
     * @param id the id of the localTaxes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the localTaxes, or with status 404 (Not Found)
     */
    @GetMapping("/local-taxes/{id}")
    @Timed
    public ResponseEntity<LocalTaxes> getLocalTaxes(@PathVariable Long id) {
        log.debug("REST request to get LocalTaxes : {}", id);
        LocalTaxes localTaxes = localTaxesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(localTaxes));
    }

    /**
     * DELETE  /local-taxes/:id : delete the "id" localTaxes.
     *
     * @param id the id of the localTaxes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/local-taxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocalTaxes(@PathVariable Long id) {
        log.debug("REST request to delete LocalTaxes : {}", id);
        localTaxesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
