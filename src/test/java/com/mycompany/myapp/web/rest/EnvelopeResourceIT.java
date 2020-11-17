package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.Envelope;
import com.mycompany.myapp.repository.EnvelopeRepository;

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
 * Integration tests for the {@link EnvelopeResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnvelopeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private EnvelopeRepository envelopeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvelopeMockMvc;

    private Envelope envelope;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Envelope createEntity(EntityManager em) {
        Envelope envelope = new Envelope()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC);
        return envelope;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Envelope createUpdatedEntity(EntityManager em) {
        Envelope envelope = new Envelope()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);
        return envelope;
    }

    @BeforeEach
    public void initTest() {
        envelope = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvelope() throws Exception {
        int databaseSizeBeforeCreate = envelopeRepository.findAll().size();
        // Create the Envelope
        restEnvelopeMockMvc.perform(post("/api/envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envelope)))
            .andExpect(status().isCreated());

        // Validate the Envelope in the database
        List<Envelope> envelopeList = envelopeRepository.findAll();
        assertThat(envelopeList).hasSize(databaseSizeBeforeCreate + 1);
        Envelope testEnvelope = envelopeList.get(envelopeList.size() - 1);
        assertThat(testEnvelope.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnvelope.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createEnvelopeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = envelopeRepository.findAll().size();

        // Create the Envelope with an existing ID
        envelope.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvelopeMockMvc.perform(post("/api/envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envelope)))
            .andExpect(status().isBadRequest());

        // Validate the Envelope in the database
        List<Envelope> envelopeList = envelopeRepository.findAll();
        assertThat(envelopeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnvelopes() throws Exception {
        // Initialize the database
        envelopeRepository.saveAndFlush(envelope);

        // Get all the envelopeList
        restEnvelopeMockMvc.perform(get("/api/envelopes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(envelope.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }
    
    @Test
    @Transactional
    public void getEnvelope() throws Exception {
        // Initialize the database
        envelopeRepository.saveAndFlush(envelope);

        // Get the envelope
        restEnvelopeMockMvc.perform(get("/api/envelopes/{id}", envelope.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(envelope.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC));
    }
    @Test
    @Transactional
    public void getNonExistingEnvelope() throws Exception {
        // Get the envelope
        restEnvelopeMockMvc.perform(get("/api/envelopes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvelope() throws Exception {
        // Initialize the database
        envelopeRepository.saveAndFlush(envelope);

        int databaseSizeBeforeUpdate = envelopeRepository.findAll().size();

        // Update the envelope
        Envelope updatedEnvelope = envelopeRepository.findById(envelope.getId()).get();
        // Disconnect from session so that the updates on updatedEnvelope are not directly saved in db
        em.detach(updatedEnvelope);
        updatedEnvelope
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);

        restEnvelopeMockMvc.perform(put("/api/envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvelope)))
            .andExpect(status().isOk());

        // Validate the Envelope in the database
        List<Envelope> envelopeList = envelopeRepository.findAll();
        assertThat(envelopeList).hasSize(databaseSizeBeforeUpdate);
        Envelope testEnvelope = envelopeList.get(envelopeList.size() - 1);
        assertThat(testEnvelope.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnvelope.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvelope() throws Exception {
        int databaseSizeBeforeUpdate = envelopeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvelopeMockMvc.perform(put("/api/envelopes").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(envelope)))
            .andExpect(status().isBadRequest());

        // Validate the Envelope in the database
        List<Envelope> envelopeList = envelopeRepository.findAll();
        assertThat(envelopeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvelope() throws Exception {
        // Initialize the database
        envelopeRepository.saveAndFlush(envelope);

        int databaseSizeBeforeDelete = envelopeRepository.findAll().size();

        // Delete the envelope
        restEnvelopeMockMvc.perform(delete("/api/envelopes/{id}", envelope.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Envelope> envelopeList = envelopeRepository.findAll();
        assertThat(envelopeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
