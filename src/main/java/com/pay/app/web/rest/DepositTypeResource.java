package com.pay.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pay.app.domain.DepositType;

import com.pay.app.repository.DepositTypeRepository;
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
 * REST controller for managing DepositType.
 */
@RestController
@RequestMapping("/api")
public class DepositTypeResource {

    private final Logger log = LoggerFactory.getLogger(DepositTypeResource.class);

    private static final String ENTITY_NAME = "depositType";

    private final DepositTypeRepository depositTypeRepository;

    public DepositTypeResource(DepositTypeRepository depositTypeRepository) {
        this.depositTypeRepository = depositTypeRepository;
    }

    /**
     * POST  /deposit-types : Create a new depositType.
     *
     * @param depositType the depositType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new depositType, or with status 400 (Bad Request) if the depositType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deposit-types")
    @Timed
    public ResponseEntity<DepositType> createDepositType(@RequestBody DepositType depositType) throws URISyntaxException {
        log.debug("REST request to save DepositType : {}", depositType);
        if (depositType.getId() != null) {
            throw new BadRequestAlertException("A new depositType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepositType result = depositTypeRepository.save(depositType);
        return ResponseEntity.created(new URI("/api/deposit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deposit-types : Updates an existing depositType.
     *
     * @param depositType the depositType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated depositType,
     * or with status 400 (Bad Request) if the depositType is not valid,
     * or with status 500 (Internal Server Error) if the depositType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deposit-types")
    @Timed
    public ResponseEntity<DepositType> updateDepositType(@RequestBody DepositType depositType) throws URISyntaxException {
        log.debug("REST request to update DepositType : {}", depositType);
        if (depositType.getId() == null) {
            return createDepositType(depositType);
        }
        DepositType result = depositTypeRepository.save(depositType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, depositType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deposit-types : get all the depositTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of depositTypes in body
     */
    @GetMapping("/deposit-types")
    @Timed
    public List<DepositType> getAllDepositTypes() {
        log.debug("REST request to get all DepositTypes");
        return depositTypeRepository.findAll();
        }

    /**
     * GET  /deposit-types/:id : get the "id" depositType.
     *
     * @param id the id of the depositType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the depositType, or with status 404 (Not Found)
     */
    @GetMapping("/deposit-types/{id}")
    @Timed
    public ResponseEntity<DepositType> getDepositType(@PathVariable Long id) {
        log.debug("REST request to get DepositType : {}", id);
        DepositType depositType = depositTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(depositType));
    }

    /**
     * DELETE  /deposit-types/:id : delete the "id" depositType.
     *
     * @param id the id of the depositType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deposit-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepositType(@PathVariable Long id) {
        log.debug("REST request to delete DepositType : {}", id);
        depositTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
