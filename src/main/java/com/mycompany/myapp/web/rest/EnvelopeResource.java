package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Envelope;
import com.mycompany.myapp.repository.EnvelopeRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Envelope}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EnvelopeResource {

    private final Logger log = LoggerFactory.getLogger(EnvelopeResource.class);

    private static final String ENTITY_NAME = "envelope";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvelopeRepository envelopeRepository;

    public EnvelopeResource(EnvelopeRepository envelopeRepository) {
        this.envelopeRepository = envelopeRepository;
    }

    /**
     * {@code POST  /envelopes} : Create a new envelope.
     *
     * @param envelope the envelope to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new envelope, or with status {@code 400 (Bad Request)} if the envelope has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/envelopes")
    public ResponseEntity<Envelope> createEnvelope(@RequestBody Envelope envelope) throws URISyntaxException {
        log.debug("REST request to save Envelope : {}", envelope);
        if (envelope.getId() != null) {
            throw new BadRequestAlertException("A new envelope cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Envelope result = envelopeRepository.save(envelope);
        return ResponseEntity.created(new URI("/api/envelopes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /envelopes} : Updates an existing envelope.
     *
     * @param envelope the envelope to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated envelope,
     * or with status {@code 400 (Bad Request)} if the envelope is not valid,
     * or with status {@code 500 (Internal Server Error)} if the envelope couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/envelopes")
    public ResponseEntity<Envelope> updateEnvelope(@RequestBody Envelope envelope) throws URISyntaxException {
        log.debug("REST request to update Envelope : {}", envelope);
        if (envelope.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Envelope result = envelopeRepository.save(envelope);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, envelope.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /envelopes} : get all the envelopes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of envelopes in body.
     */
    @GetMapping("/envelopes")
    public List<Envelope> getAllEnvelopes() {
        log.debug("REST request to get all Envelopes");
        return envelopeRepository.findAll();
    }

    /**
     * {@code GET  /envelopes/:id} : get the "id" envelope.
     *
     * @param id the id of the envelope to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the envelope, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/envelopes/{id}")
    public ResponseEntity<Envelope> getEnvelope(@PathVariable Long id) {
        log.debug("REST request to get Envelope : {}", id);
        Optional<Envelope> envelope = envelopeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(envelope);
    }

    /**
     * {@code DELETE  /envelopes/:id} : delete the "id" envelope.
     *
     * @param id the id of the envelope to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/envelopes/{id}")
    public ResponseEntity<Void> deleteEnvelope(@PathVariable Long id) {
        log.debug("REST request to delete Envelope : {}", id);
        envelopeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
