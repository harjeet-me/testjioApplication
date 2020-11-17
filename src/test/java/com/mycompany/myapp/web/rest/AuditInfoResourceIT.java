package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.AuditInfo;
import com.mycompany.myapp.repository.AuditInfoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AuditInfoResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuditInfoResourceIT {

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private AuditInfoRepository auditInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditInfoMockMvc;

    private AuditInfo auditInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditInfo createEntity(EntityManager em) {
        AuditInfo auditInfo = new AuditInfo()
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return auditInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditInfo createUpdatedEntity(EntityManager em) {
        AuditInfo auditInfo = new AuditInfo()
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return auditInfo;
    }

    @BeforeEach
    public void initTest() {
        auditInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditInfo() throws Exception {
        int databaseSizeBeforeCreate = auditInfoRepository.findAll().size();
        // Create the AuditInfo
        restAuditInfoMockMvc.perform(post("/api/audit-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditInfo)))
            .andExpect(status().isCreated());

        // Validate the AuditInfo in the database
        List<AuditInfo> auditInfoList = auditInfoRepository.findAll();
        assertThat(auditInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AuditInfo testAuditInfo = auditInfoList.get(auditInfoList.size() - 1);
        assertThat(testAuditInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAuditInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAuditInfo.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testAuditInfo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createAuditInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditInfoRepository.findAll().size();

        // Create the AuditInfo with an existing ID
        auditInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditInfoMockMvc.perform(post("/api/audit-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditInfo)))
            .andExpect(status().isBadRequest());

        // Validate the AuditInfo in the database
        List<AuditInfo> auditInfoList = auditInfoRepository.findAll();
        assertThat(auditInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAuditInfos() throws Exception {
        // Initialize the database
        auditInfoRepository.saveAndFlush(auditInfo);

        // Get all the auditInfoList
        restAuditInfoMockMvc.perform(get("/api/audit-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getAuditInfo() throws Exception {
        // Initialize the database
        auditInfoRepository.saveAndFlush(auditInfo);

        // Get the auditInfo
        restAuditInfoMockMvc.perform(get("/api/audit-infos/{id}", auditInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditInfo.getId().intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingAuditInfo() throws Exception {
        // Get the auditInfo
        restAuditInfoMockMvc.perform(get("/api/audit-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditInfo() throws Exception {
        // Initialize the database
        auditInfoRepository.saveAndFlush(auditInfo);

        int databaseSizeBeforeUpdate = auditInfoRepository.findAll().size();

        // Update the auditInfo
        AuditInfo updatedAuditInfo = auditInfoRepository.findById(auditInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAuditInfo are not directly saved in db
        em.detach(updatedAuditInfo);
        updatedAuditInfo
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAuditInfoMockMvc.perform(put("/api/audit-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuditInfo)))
            .andExpect(status().isOk());

        // Validate the AuditInfo in the database
        List<AuditInfo> auditInfoList = auditInfoRepository.findAll();
        assertThat(auditInfoList).hasSize(databaseSizeBeforeUpdate);
        AuditInfo testAuditInfo = auditInfoList.get(auditInfoList.size() - 1);
        assertThat(testAuditInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuditInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuditInfo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testAuditInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditInfo() throws Exception {
        int databaseSizeBeforeUpdate = auditInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditInfoMockMvc.perform(put("/api/audit-infos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(auditInfo)))
            .andExpect(status().isBadRequest());

        // Validate the AuditInfo in the database
        List<AuditInfo> auditInfoList = auditInfoRepository.findAll();
        assertThat(auditInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuditInfo() throws Exception {
        // Initialize the database
        auditInfoRepository.saveAndFlush(auditInfo);

        int databaseSizeBeforeDelete = auditInfoRepository.findAll().size();

        // Delete the auditInfo
        restAuditInfoMockMvc.perform(delete("/api/audit-infos/{id}", auditInfo.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditInfo> auditInfoList = auditInfoRepository.findAll();
        assertThat(auditInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
