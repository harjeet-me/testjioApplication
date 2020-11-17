package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AuditInfo;
import com.mycompany.myapp.repository.AuditInfoRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AuditInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AuditInfoResource {

    private final Logger log = LoggerFactory.getLogger(AuditInfoResource.class);

    private static final String ENTITY_NAME = "auditInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditInfoRepository auditInfoRepository;

    public AuditInfoResource(AuditInfoRepository auditInfoRepository) {
        this.auditInfoRepository = auditInfoRepository;
    }

    /**
     * {@code POST  /audit-infos} : Create a new auditInfo.
     *
     * @param auditInfo the auditInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditInfo, or with status {@code 400 (Bad Request)} if the auditInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-infos")
    public ResponseEntity<AuditInfo> createAuditInfo(@RequestBody AuditInfo auditInfo) throws URISyntaxException {
        log.debug("REST request to save AuditInfo : {}", auditInfo);
        if (auditInfo.getId() != null) {
            throw new BadRequestAlertException("A new auditInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditInfo result = auditInfoRepository.save(auditInfo);
        return ResponseEntity.created(new URI("/api/audit-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-infos} : Updates an existing auditInfo.
     *
     * @param auditInfo the auditInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditInfo,
     * or with status {@code 400 (Bad Request)} if the auditInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-infos")
    public ResponseEntity<AuditInfo> updateAuditInfo(@RequestBody AuditInfo auditInfo) throws URISyntaxException {
        log.debug("REST request to update AuditInfo : {}", auditInfo);
        if (auditInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AuditInfo result = auditInfoRepository.save(auditInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /audit-infos} : get all the auditInfos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditInfos in body.
     */
    @GetMapping("/audit-infos")
    public List<AuditInfo> getAllAuditInfos(@RequestParam(required = false) String filter) {
        if ("filesystem-is-null".equals(filter)) {
            log.debug("REST request to get all AuditInfos where fileSystem is null");
            return StreamSupport
                .stream(auditInfoRepository.findAll().spliterator(), false)
                .filter(auditInfo -> auditInfo.getFileSystem() == null)
                .collect(Collectors.toList());
        }
        if ("envelope-is-null".equals(filter)) {
            log.debug("REST request to get all AuditInfos where envelope is null");
            return StreamSupport
                .stream(auditInfoRepository.findAll().spliterator(), false)
                .filter(auditInfo -> auditInfo.getEnvelope() == null)
                .collect(Collectors.toList());
        }
        if ("conversation-is-null".equals(filter)) {
            log.debug("REST request to get all AuditInfos where conversation is null");
            return StreamSupport
                .stream(auditInfoRepository.findAll().spliterator(), false)
                .filter(auditInfo -> auditInfo.getConversation() == null)
                .collect(Collectors.toList());
        }
        if ("dynamicdataenvelope-is-null".equals(filter)) {
            log.debug("REST request to get all AuditInfos where dynamicDataEnvelope is null");
            return StreamSupport
                .stream(auditInfoRepository.findAll().spliterator(), false)
                .filter(auditInfo -> auditInfo.getDynamicDataEnvelope() == null)
                .collect(Collectors.toList());
        }
        if ("dynamicdata-is-null".equals(filter)) {
            log.debug("REST request to get all AuditInfos where dynamicData is null");
            return StreamSupport
                .stream(auditInfoRepository.findAll().spliterator(), false)
                .filter(auditInfo -> auditInfo.getDynamicData() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all AuditInfos");
        return auditInfoRepository.findAll();
    }

    /**
     * {@code GET  /audit-infos/:id} : get the "id" auditInfo.
     *
     * @param id the id of the auditInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-infos/{id}")
    public ResponseEntity<AuditInfo> getAuditInfo(@PathVariable Long id) {
        log.debug("REST request to get AuditInfo : {}", id);
        Optional<AuditInfo> auditInfo = auditInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(auditInfo);
    }

    /**
     * {@code DELETE  /audit-infos/:id} : delete the "id" auditInfo.
     *
     * @param id the id of the auditInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-infos/{id}")
    public ResponseEntity<Void> deleteAuditInfo(@PathVariable Long id) {
        log.debug("REST request to delete AuditInfo : {}", id);
        auditInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
