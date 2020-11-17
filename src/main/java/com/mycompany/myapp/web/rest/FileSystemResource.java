package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FileSystem;
import com.mycompany.myapp.repository.FileSystemRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FileSystem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FileSystemResource {

    private final Logger log = LoggerFactory.getLogger(FileSystemResource.class);

    private static final String ENTITY_NAME = "fileSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileSystemRepository fileSystemRepository;

    public FileSystemResource(FileSystemRepository fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
    }

    /**
     * {@code POST  /file-systems} : Create a new fileSystem.
     *
     * @param fileSystem the fileSystem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileSystem, or with status {@code 400 (Bad Request)} if the fileSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-systems")
    public ResponseEntity<FileSystem> createFileSystem(@RequestBody FileSystem fileSystem) throws URISyntaxException {
        log.debug("REST request to save FileSystem : {}", fileSystem);
        if (fileSystem.getId() != null) {
            throw new BadRequestAlertException("A new fileSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileSystem result = fileSystemRepository.save(fileSystem);
        return ResponseEntity.created(new URI("/api/file-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-systems} : Updates an existing fileSystem.
     *
     * @param fileSystem the fileSystem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileSystem,
     * or with status {@code 400 (Bad Request)} if the fileSystem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileSystem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-systems")
    public ResponseEntity<FileSystem> updateFileSystem(@RequestBody FileSystem fileSystem) throws URISyntaxException {
        log.debug("REST request to update FileSystem : {}", fileSystem);
        if (fileSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileSystem result = fileSystemRepository.save(fileSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileSystem.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-systems} : get all the fileSystems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileSystems in body.
     */
    @GetMapping("/file-systems")
    public List<FileSystem> getAllFileSystems() {
        log.debug("REST request to get all FileSystems");
        return fileSystemRepository.findAll();
    }

    /**
     * {@code GET  /file-systems/:id} : get the "id" fileSystem.
     *
     * @param id the id of the fileSystem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileSystem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-systems/{id}")
    public ResponseEntity<FileSystem> getFileSystem(@PathVariable Long id) {
        log.debug("REST request to get FileSystem : {}", id);
        Optional<FileSystem> fileSystem = fileSystemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fileSystem);
    }

    /**
     * {@code DELETE  /file-systems/:id} : delete the "id" fileSystem.
     *
     * @param id the id of the fileSystem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-systems/{id}")
    public ResponseEntity<Void> deleteFileSystem(@PathVariable Long id) {
        log.debug("REST request to delete FileSystem : {}", id);
        fileSystemRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
