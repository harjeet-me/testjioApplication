package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.Discrim;
import com.mycompany.myapp.repository.DiscrimRepository;

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
 * Integration tests for the {@link DiscrimResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiscrimResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DiscrimRepository discrimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscrimMockMvc;

    private Discrim discrim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discrim createEntity(EntityManager em) {
        Discrim discrim = new Discrim()
            .name(DEFAULT_NAME);
        return discrim;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discrim createUpdatedEntity(EntityManager em) {
        Discrim discrim = new Discrim()
            .name(UPDATED_NAME);
        return discrim;
    }

    @BeforeEach
    public void initTest() {
        discrim = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscrim() throws Exception {
        int databaseSizeBeforeCreate = discrimRepository.findAll().size();
        // Create the Discrim
        restDiscrimMockMvc.perform(post("/api/discrims").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discrim)))
            .andExpect(status().isCreated());

        // Validate the Discrim in the database
        List<Discrim> discrimList = discrimRepository.findAll();
        assertThat(discrimList).hasSize(databaseSizeBeforeCreate + 1);
        Discrim testDiscrim = discrimList.get(discrimList.size() - 1);
        assertThat(testDiscrim.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDiscrimWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discrimRepository.findAll().size();

        // Create the Discrim with an existing ID
        discrim.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscrimMockMvc.perform(post("/api/discrims").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discrim)))
            .andExpect(status().isBadRequest());

        // Validate the Discrim in the database
        List<Discrim> discrimList = discrimRepository.findAll();
        assertThat(discrimList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDiscrims() throws Exception {
        // Initialize the database
        discrimRepository.saveAndFlush(discrim);

        // Get all the discrimList
        restDiscrimMockMvc.perform(get("/api/discrims?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discrim.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getDiscrim() throws Exception {
        // Initialize the database
        discrimRepository.saveAndFlush(discrim);

        // Get the discrim
        restDiscrimMockMvc.perform(get("/api/discrims/{id}", discrim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discrim.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingDiscrim() throws Exception {
        // Get the discrim
        restDiscrimMockMvc.perform(get("/api/discrims/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscrim() throws Exception {
        // Initialize the database
        discrimRepository.saveAndFlush(discrim);

        int databaseSizeBeforeUpdate = discrimRepository.findAll().size();

        // Update the discrim
        Discrim updatedDiscrim = discrimRepository.findById(discrim.getId()).get();
        // Disconnect from session so that the updates on updatedDiscrim are not directly saved in db
        em.detach(updatedDiscrim);
        updatedDiscrim
            .name(UPDATED_NAME);

        restDiscrimMockMvc.perform(put("/api/discrims").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscrim)))
            .andExpect(status().isOk());

        // Validate the Discrim in the database
        List<Discrim> discrimList = discrimRepository.findAll();
        assertThat(discrimList).hasSize(databaseSizeBeforeUpdate);
        Discrim testDiscrim = discrimList.get(discrimList.size() - 1);
        assertThat(testDiscrim.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscrim() throws Exception {
        int databaseSizeBeforeUpdate = discrimRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscrimMockMvc.perform(put("/api/discrims").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discrim)))
            .andExpect(status().isBadRequest());

        // Validate the Discrim in the database
        List<Discrim> discrimList = discrimRepository.findAll();
        assertThat(discrimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscrim() throws Exception {
        // Initialize the database
        discrimRepository.saveAndFlush(discrim);

        int databaseSizeBeforeDelete = discrimRepository.findAll().size();

        // Delete the discrim
        restDiscrimMockMvc.perform(delete("/api/discrims/{id}", discrim.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discrim> discrimList = discrimRepository.findAll();
        assertThat(discrimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
