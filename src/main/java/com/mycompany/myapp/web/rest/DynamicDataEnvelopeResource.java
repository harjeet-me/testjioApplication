package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DynamicDataEnvelope;
import com.mycompany.myapp.repository.DynamicDataEnvelopeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.DynamicDataEnvelope}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DynamicDataEnvelopeResource {

    private final Logger log = LoggerFactory.getLogger(DynamicDataEnvelopeResource.class);

    private static final String ENTITY_NAME = "dynamicDataEnvelope";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DynamicDataEnvelopeRepository dynamicDataEnvelopeRepository;

    public DynamicDataEnvelopeResource(DynamicDataEnvelopeRepository dynamicDataEnvelopeRepository) {
        this.dynamicDataEnvelopeRepository = dynamicDataEnvelopeRepository;
    }

    /**
     * {@code POST  /dynamic-data-envelopes} : Create a new dynamicDataEnvelope.
     *
     * @param dynamicDataEnvelope the dynamicDataEnvelope to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dynamicDataEnvelope, or with status {@code 400 (Bad Request)} if the dynamicDataEnvelope has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dynamic-data-envelopes")
    public ResponseEntity<DynamicDataEnvelope> createDynamicDataEnvelope(@RequestBody DynamicDataEnvelope dynamicDataEnvelope) throws URISyntaxException {
        log.debug("REST request to save DynamicDataEnvelope : {}", dynamicDataEnvelope);
        if (dynamicDataEnvelope.getId() != null) {
            throw new BadRequestAlertException("A new dynamicDataEnvelope cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DynamicDataEnvelope result = dynamicDataEnvelopeRepository.save(dynamicDataEnvelope);
        return ResponseEntity.created(new URI("/api/dynamic-data-envelopes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dynamic-data-envelopes} : Updates an existing dynamicDataEnvelope.
     *
     * @param dynamicDataEnvelope the dynamicDataEnvelope to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dynamicDataEnvelope,
     * or with status {@code 400 (Bad Request)} if the dynamicDataEnvelope is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dynamicDataEnvelope couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dynamic-data-envelopes")
    public ResponseEntity<DynamicDataEnvelope> updateDynamicDataEnvelope(@RequestBody DynamicDataEnvelope dynamicDataEnvelope) throws URISyntaxException {
        log.debug("REST request to update DynamicDataEnvelope : {}", dynamicDataEnvelope);
        if (dynamicDataEnvelope.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DynamicDataEnvelope result = dynamicDataEnvelopeRepository.save(dynamicDataEnvelope);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dynamicDataEnvelope.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dynamic-data-envelopes} : get all the dynamicDataEnvelopes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dynamicDataEnvelopes in body.
     */
    @GetMapping("/dynamic-data-envelopes")
    public List<DynamicDataEnvelope> getAllDynamicDataEnvelopes() {
        log.debug("REST request to get all DynamicDataEnvelopes");
        return dynamicDataEnvelopeRepository.findAll();
    }

    /**
     * {@code GET  /dynamic-data-envelopes/:id} : get the "id" dynamicDataEnvelope.
     *
     * @param id the id of the dynamicDataEnvelope to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dynamicDataEnvelope, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dynamic-data-envelopes/{id}")
    public ResponseEntity<DynamicDataEnvelope> getDynamicDataEnvelope(@PathVariable Long id) {
        log.debug("REST request to get DynamicDataEnvelope : {}", id);
        Optional<DynamicDataEnvelope> dynamicDataEnvelope = dynamicDataEnvelopeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dynamicDataEnvelope);
    }

    /**
     * {@code DELETE  /dynamic-data-envelopes/:id} : delete the "id" dynamicDataEnvelope.
     *
     * @param id the id of the dynamicDataEnvelope to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dynamic-data-envelopes/{id}")
    public ResponseEntity<Void> deleteDynamicDataEnvelope(@PathVariable Long id) {
        log.debug("REST request to delete DynamicDataEnvelope : {}", id);
        dynamicDataEnvelopeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
