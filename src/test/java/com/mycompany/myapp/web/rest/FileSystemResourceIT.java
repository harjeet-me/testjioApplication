package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.FileSystem;
import com.mycompany.myapp.repository.FileSystemRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FileSystemResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FileSystemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_EXT = false;
    private static final Boolean UPDATED_EXT = true;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private FileSystemRepository fileSystemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileSystemMockMvc;

    private FileSystem fileSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSystem createEntity(EntityManager em) {
        FileSystem fileSystem = new FileSystem()
            .name(DEFAULT_NAME)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .ext(DEFAULT_EXT)
            .url(DEFAULT_URL);
        return fileSystem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSystem createUpdatedEntity(EntityManager em) {
        FileSystem fileSystem = new FileSystem()
            .name(UPDATED_NAME)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .ext(UPDATED_EXT)
            .url(UPDATED_URL);
        return fileSystem;
    }

    @BeforeEach
    public void initTest() {
        fileSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileSystem() throws Exception {
        int databaseSizeBeforeCreate = fileSystemRepository.findAll().size();
        // Create the FileSystem
        restFileSystemMockMvc.perform(post("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileSystem)))
            .andExpect(status().isCreated());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeCreate + 1);
        FileSystem testFileSystem = fileSystemList.get(fileSystemList.size() - 1);
        assertThat(testFileSystem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileSystem.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testFileSystem.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
        assertThat(testFileSystem.isExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testFileSystem.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createFileSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileSystemRepository.findAll().size();

        // Create the FileSystem with an existing ID
        fileSystem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileSystemMockMvc.perform(post("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileSystem)))
            .andExpect(status().isBadRequest());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFileSystems() throws Exception {
        // Initialize the database
        fileSystemRepository.saveAndFlush(fileSystem);

        // Get all the fileSystemList
        restFileSystemMockMvc.perform(get("/api/file-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT.booleanValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }
    
    @Test
    @Transactional
    public void getFileSystem() throws Exception {
        // Initialize the database
        fileSystemRepository.saveAndFlush(fileSystem);

        // Get the fileSystem
        restFileSystemMockMvc.perform(get("/api/file-systems/{id}", fileSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileSystem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT.booleanValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }
    @Test
    @Transactional
    public void getNonExistingFileSystem() throws Exception {
        // Get the fileSystem
        restFileSystemMockMvc.perform(get("/api/file-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileSystem() throws Exception {
        // Initialize the database
        fileSystemRepository.saveAndFlush(fileSystem);

        int databaseSizeBeforeUpdate = fileSystemRepository.findAll().size();

        // Update the fileSystem
        FileSystem updatedFileSystem = fileSystemRepository.findById(fileSystem.getId()).get();
        // Disconnect from session so that the updates on updatedFileSystem are not directly saved in db
        em.detach(updatedFileSystem);
        updatedFileSystem
            .name(UPDATED_NAME)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .ext(UPDATED_EXT)
            .url(UPDATED_URL);

        restFileSystemMockMvc.perform(put("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileSystem)))
            .andExpect(status().isOk());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeUpdate);
        FileSystem testFileSystem = fileSystemList.get(fileSystemList.size() - 1);
        assertThat(testFileSystem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileSystem.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testFileSystem.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
        assertThat(testFileSystem.isExt()).isEqualTo(UPDATED_EXT);
        assertThat(testFileSystem.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingFileSystem() throws Exception {
        int databaseSizeBeforeUpdate = fileSystemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileSystemMockMvc.perform(put("/api/file-systems").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileSystem)))
            .andExpect(status().isBadRequest());

        // Validate the FileSystem in the database
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileSystem() throws Exception {
        // Initialize the database
        fileSystemRepository.saveAndFlush(fileSystem);

        int databaseSizeBeforeDelete = fileSystemRepository.findAll().size();

        // Delete the fileSystem
        restFileSystemMockMvc.perform(delete("/api/file-systems/{id}", fileSystem.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileSystem> fileSystemList = fileSystemRepository.findAll();
        assertThat(fileSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
