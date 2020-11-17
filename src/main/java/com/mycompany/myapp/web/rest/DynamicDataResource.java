package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.DynamicData;
import com.mycompany.myapp.repository.DynamicDataRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.DynamicData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DynamicDataResource {

    private final Logger log = LoggerFactory.getLogger(DynamicDataResource.class);

    private static final String ENTITY_NAME = "dynamicData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DynamicDataRepository dynamicDataRepository;

    public DynamicDataResource(DynamicDataRepository dynamicDataRepository) {
        this.dynamicDataRepository = dynamicDataRepository;
    }

    /**
     * {@code POST  /dynamic-data} : Create a new dynamicData.
     *
     * @param dynamicData the dynamicData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dynamicData, or with status {@code 400 (Bad Request)} if the dynamicData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dynamic-data")
    public ResponseEntity<DynamicData> createDynamicData(@RequestBody DynamicData dynamicData) throws URISyntaxException {
        log.debug("REST request to save DynamicData : {}", dynamicData);
        if (dynamicData.getId() != null) {
            throw new BadRequestAlertException("A new dynamicData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DynamicData result = dynamicDataRepository.save(dynamicData);
        return ResponseEntity.created(new URI("/api/dynamic-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dynamic-data} : Updates an existing dynamicData.
     *
     * @param dynamicData the dynamicData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dynamicData,
     * or with status {@code 400 (Bad Request)} if the dynamicData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dynamicData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dynamic-data")
    public ResponseEntity<DynamicData> updateDynamicData(@RequestBody DynamicData dynamicData) throws URISyntaxException {
        log.debug("REST request to update DynamicData : {}", dynamicData);
        if (dynamicData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DynamicData result = dynamicDataRepository.save(dynamicData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dynamicData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dynamic-data} : get all the dynamicData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dynamicData in body.
     */
    @GetMapping("/dynamic-data")
    public List<DynamicData> getAllDynamicData() {
        log.debug("REST request to get all DynamicData");
        return dynamicDataRepository.findAll();
    }

    /**
     * {@code GET  /dynamic-data/:id} : get the "id" dynamicData.
     *
     * @param id the id of the dynamicData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dynamicData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dynamic-data/{id}")
    public ResponseEntity<DynamicData> getDynamicData(@PathVariable Long id) {
        log.debug("REST request to get DynamicData : {}", id);
        Optional<DynamicData> dynamicData = dynamicDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dynamicData);
    }

    /**
     * {@code DELETE  /dynamic-data/:id} : delete the "id" dynamicData.
     *
     * @param id the id of the dynamicData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dynamic-data/{id}")
    public ResponseEntity<Void> deleteDynamicData(@PathVariable Long id) {
        log.debug("REST request to delete DynamicData : {}", id);
        dynamicDataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
