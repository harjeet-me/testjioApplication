package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.DynamicDataEnvelope;
import com.mycompany.myapp.repository.DynamicDataEnvelopeRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DynamicDataEnvelopeResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DynamicDataEnvelopeResourceIT {

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private DynamicDataEnvelopeRepository dynamicDataEnvelopeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDynamicDataEnvelopeMockMvc;

    private DynamicDataEnvelope dynamicDataEnvelope;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicDataEnvelope createEntity(EntityManager em) {
        DynamicDataEnvelope dynamicDataEnvelope = new DynamicDataEnvelope()
            .desc(DEFAULT_DESC);
        return dynamicDataEnvelope;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicDataEnvelope createUpdatedEntity(EntityManager em) {
        DynamicDataEnvelope dynamicDataEnvelope = new DynamicDataEnvelope()
            .desc(UPDATED_DESC);
        return dynamicDataEnvelope;
    }

    @BeforeEach
    public void initTest() {
        dynamicDataEnvelope = createEntity(em);
    }

    @Test
    @Transactional
    public void createDynamicDataEnvelope() throws Exception {
        int databaseSizeBeforeCreate = dynamicDataEnvelopeRepository.findAll().size();
        // Create the DynamicDataEnvelope
        restDynamicDataEnvelopeMockMvc.perform(post("/api/dynamic-data-envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicDataEnvelope)))
            .andExpect(status().isCreated());

        // Validate the DynamicDataEnvelope in the database
        List<DynamicDataEnvelope> dynamicDataEnvelopeList = dynamicDataEnvelopeRepository.findAll();
        assertThat(dynamicDataEnvelopeList).hasSize(databaseSizeBeforeCreate + 1);
        DynamicDataEnvelope testDynamicDataEnvelope = dynamicDataEnvelopeList.get(dynamicDataEnvelopeList.size() - 1);
        assertThat(testDynamicDataEnvelope.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createDynamicDataEnvelopeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dynamicDataEnvelopeRepository.findAll().size();

        // Create the DynamicDataEnvelope with an existing ID
        dynamicDataEnvelope.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDynamicDataEnvelopeMockMvc.perform(post("/api/dynamic-data-envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicDataEnvelope)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicDataEnvelope in the database
        List<DynamicDataEnvelope> dynamicDataEnvelopeList = dynamicDataEnvelopeRepository.findAll();
        assertThat(dynamicDataEnvelopeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDynamicDataEnvelopes() throws Exception {
        // Initialize the database
        dynamicDataEnvelopeRepository.saveAndFlush(dynamicDataEnvelope);

        // Get all the dynamicDataEnvelopeList
        restDynamicDataEnvelopeMockMvc.perform(get("/api/dynamic-data-envelopes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dynamicDataEnvelope.getId().intValue())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }
    
    @Test
    @Transactional
    public void getDynamicDataEnvelope() throws Exception {
        // Initialize the database
        dynamicDataEnvelopeRepository.saveAndFlush(dynamicDataEnvelope);

        // Get the dynamicDataEnvelope
        restDynamicDataEnvelopeMockMvc.perform(get("/api/dynamic-data-envelopes/{id}", dynamicDataEnvelope.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dynamicDataEnvelope.getId().intValue()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC));
    }
    @Test
    @Transactional
    public void getNonExistingDynamicDataEnvelope() throws Exception {
        // Get the dynamicDataEnvelope
        restDynamicDataEnvelopeMockMvc.perform(get("/api/dynamic-data-envelopes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDynamicDataEnvelope() throws Exception {
        // Initialize the database
        dynamicDataEnvelopeRepository.saveAndFlush(dynamicDataEnvelope);

        int databaseSizeBeforeUpdate = dynamicDataEnvelopeRepository.findAll().size();

        // Update the dynamicDataEnvelope
        DynamicDataEnvelope updatedDynamicDataEnvelope = dynamicDataEnvelopeRepository.findById(dynamicDataEnvelope.getId()).get();
        // Disconnect from session so that the updates on updatedDynamicDataEnvelope are not directly saved in db
        em.detach(updatedDynamicDataEnvelope);
        updatedDynamicDataEnvelope
            .desc(UPDATED_DESC);

        restDynamicDataEnvelopeMockMvc.perform(put("/api/dynamic-data-envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDynamicDataEnvelope)))
            .andExpect(status().isOk());

        // Validate the DynamicDataEnvelope in the database
        List<DynamicDataEnvelope> dynamicDataEnvelopeList = dynamicDataEnvelopeRepository.findAll();
        assertThat(dynamicDataEnvelopeList).hasSize(databaseSizeBeforeUpdate);
        DynamicDataEnvelope testDynamicDataEnvelope = dynamicDataEnvelopeList.get(dynamicDataEnvelopeList.size() - 1);
        assertThat(testDynamicDataEnvelope.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingDynamicDataEnvelope() throws Exception {
        int databaseSizeBeforeUpdate = dynamicDataEnvelopeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDynamicDataEnvelopeMockMvc.perform(put("/api/dynamic-data-envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicDataEnvelope)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicDataEnvelope in the database
        List<DynamicDataEnvelope> dynamicDataEnvelopeList = dynamicDataEnvelopeRepository.findAll();
        assertThat(dynamicDataEnvelopeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDynamicDataEnvelope() throws Exception {
        // Initialize the database
        dynamicDataEnvelopeRepository.saveAndFlush(dynamicDataEnvelope);

        int databaseSizeBeforeDelete = dynamicDataEnvelopeRepository.findAll().size();

        // Delete the dynamicDataEnvelope
        restDynamicDataEnvelopeMockMvc.perform(delete("/api/dynamic-data-envelopes/{id}", dynamicDataEnvelope.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DynamicDataEnvelope> dynamicDataEnvelopeList = dynamicDataEnvelopeRepository.findAll();
        assertThat(dynamicDataEnvelopeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
