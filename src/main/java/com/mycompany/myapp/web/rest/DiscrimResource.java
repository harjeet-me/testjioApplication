package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Discrim;
import com.mycompany.myapp.repository.DiscrimRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Discrim}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiscrimResource {

    private final Logger log = LoggerFactory.getLogger(DiscrimResource.class);

    private static final String ENTITY_NAME = "discrim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscrimRepository discrimRepository;

    public DiscrimResource(DiscrimRepository discrimRepository) {
        this.discrimRepository = discrimRepository;
    }

    /**
     * {@code POST  /discrims} : Create a new discrim.
     *
     * @param discrim the discrim to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discrim, or with status {@code 400 (Bad Request)} if the discrim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discrims")
    public ResponseEntity<Discrim> createDiscrim(@RequestBody Discrim discrim) throws URISyntaxException {
        log.debug("REST request to save Discrim : {}", discrim);
        if (discrim.getId() != null) {
            throw new BadRequestAlertException("A new discrim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Discrim result = discrimRepository.save(discrim);
        return ResponseEntity.created(new URI("/api/discrims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discrims} : Updates an existing discrim.
     *
     * @param discrim the discrim to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discrim,
     * or with status {@code 400 (Bad Request)} if the discrim is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discrim couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discrims")
    public ResponseEntity<Discrim> updateDiscrim(@RequestBody Discrim discrim) throws URISyntaxException {
        log.debug("REST request to update Discrim : {}", discrim);
        if (discrim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Discrim result = discrimRepository.save(discrim);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discrim.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discrims} : get all the discrims.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discrims in body.
     */
    @GetMapping("/discrims")
    public List<Discrim> getAllDiscrims() {
        log.debug("REST request to get all Discrims");
        return discrimRepository.findAll();
    }

    /**
     * {@code GET  /discrims/:id} : get the "id" discrim.
     *
     * @param id the id of the discrim to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discrim, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discrims/{id}")
    public ResponseEntity<Discrim> getDiscrim(@PathVariable Long id) {
        log.debug("REST request to get Discrim : {}", id);
        Optional<Discrim> discrim = discrimRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(discrim);
    }

    /**
     * {@code DELETE  /discrims/:id} : delete the "id" discrim.
     *
     * @param id the id of the discrim to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discrims/{id}")
    public ResponseEntity<Void> deleteDiscrim(@PathVariable Long id) {
        log.debug("REST request to delete Discrim : {}", id);
        discrimRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
