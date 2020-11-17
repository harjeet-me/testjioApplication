package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.DynamicData;
import com.mycompany.myapp.repository.DynamicDataRepository;

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

import com.mycompany.myapp.domain.enumeration.DataType;
/**
 * Integration tests for the {@link DynamicDataResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DynamicDataResourceIT {

    private static final String DEFAULT_DATA_KEY = "AAAAAAAAAA";
    private static final String UPDATED_DATA_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_VALUE = "BBBBBBBBBB";

    private static final DataType DEFAULT_VALUE_DATA_TYPE = DataType.DString;
    private static final DataType UPDATED_VALUE_DATA_TYPE = DataType.DByte;

    @Autowired
    private DynamicDataRepository dynamicDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDynamicDataMockMvc;

    private DynamicData dynamicData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicData createEntity(EntityManager em) {
        DynamicData dynamicData = new DynamicData()
            .dataKey(DEFAULT_DATA_KEY)
            .dataValue(DEFAULT_DATA_VALUE)
            .valueDataType(DEFAULT_VALUE_DATA_TYPE);
        return dynamicData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DynamicData createUpdatedEntity(EntityManager em) {
        DynamicData dynamicData = new DynamicData()
            .dataKey(UPDATED_DATA_KEY)
            .dataValue(UPDATED_DATA_VALUE)
            .valueDataType(UPDATED_VALUE_DATA_TYPE);
        return dynamicData;
    }

    @BeforeEach
    public void initTest() {
        dynamicData = createEntity(em);
    }

    @Test
    @Transactional
    public void createDynamicData() throws Exception {
        int databaseSizeBeforeCreate = dynamicDataRepository.findAll().size();
        // Create the DynamicData
        restDynamicDataMockMvc.perform(post("/api/dynamic-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicData)))
            .andExpect(status().isCreated());

        // Validate the DynamicData in the database
        List<DynamicData> dynamicDataList = dynamicDataRepository.findAll();
        assertThat(dynamicDataList).hasSize(databaseSizeBeforeCreate + 1);
        DynamicData testDynamicData = dynamicDataList.get(dynamicDataList.size() - 1);
        assertThat(testDynamicData.getDataKey()).isEqualTo(DEFAULT_DATA_KEY);
        assertThat(testDynamicData.getDataValue()).isEqualTo(DEFAULT_DATA_VALUE);
        assertThat(testDynamicData.getValueDataType()).isEqualTo(DEFAULT_VALUE_DATA_TYPE);
    }

    @Test
    @Transactional
    public void createDynamicDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dynamicDataRepository.findAll().size();

        // Create the DynamicData with an existing ID
        dynamicData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDynamicDataMockMvc.perform(post("/api/dynamic-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicData)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicData in the database
        List<DynamicData> dynamicDataList = dynamicDataRepository.findAll();
        assertThat(dynamicDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDynamicData() throws Exception {
        // Initialize the database
        dynamicDataRepository.saveAndFlush(dynamicData);

        // Get all the dynamicDataList
        restDynamicDataMockMvc.perform(get("/api/dynamic-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dynamicData.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataKey").value(hasItem(DEFAULT_DATA_KEY)))
            .andExpect(jsonPath("$.[*].dataValue").value(hasItem(DEFAULT_DATA_VALUE)))
            .andExpect(jsonPath("$.[*].valueDataType").value(hasItem(DEFAULT_VALUE_DATA_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getDynamicData() throws Exception {
        // Initialize the database
        dynamicDataRepository.saveAndFlush(dynamicData);

        // Get the dynamicData
        restDynamicDataMockMvc.perform(get("/api/dynamic-data/{id}", dynamicData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dynamicData.getId().intValue()))
            .andExpect(jsonPath("$.dataKey").value(DEFAULT_DATA_KEY))
            .andExpect(jsonPath("$.dataValue").value(DEFAULT_DATA_VALUE))
            .andExpect(jsonPath("$.valueDataType").value(DEFAULT_VALUE_DATA_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDynamicData() throws Exception {
        // Get the dynamicData
        restDynamicDataMockMvc.perform(get("/api/dynamic-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDynamicData() throws Exception {
        // Initialize the database
        dynamicDataRepository.saveAndFlush(dynamicData);

        int databaseSizeBeforeUpdate = dynamicDataRepository.findAll().size();

        // Update the dynamicData
        DynamicData updatedDynamicData = dynamicDataRepository.findById(dynamicData.getId()).get();
        // Disconnect from session so that the updates on updatedDynamicData are not directly saved in db
        em.detach(updatedDynamicData);
        updatedDynamicData
            .dataKey(UPDATED_DATA_KEY)
            .dataValue(UPDATED_DATA_VALUE)
            .valueDataType(UPDATED_VALUE_DATA_TYPE);

        restDynamicDataMockMvc.perform(put("/api/dynamic-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDynamicData)))
            .andExpect(status().isOk());

        // Validate the DynamicData in the database
        List<DynamicData> dynamicDataList = dynamicDataRepository.findAll();
        assertThat(dynamicDataList).hasSize(databaseSizeBeforeUpdate);
        DynamicData testDynamicData = dynamicDataList.get(dynamicDataList.size() - 1);
        assertThat(testDynamicData.getDataKey()).isEqualTo(UPDATED_DATA_KEY);
        assertThat(testDynamicData.getDataValue()).isEqualTo(UPDATED_DATA_VALUE);
        assertThat(testDynamicData.getValueDataType()).isEqualTo(UPDATED_VALUE_DATA_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDynamicData() throws Exception {
        int databaseSizeBeforeUpdate = dynamicDataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDynamicDataMockMvc.perform(put("/api/dynamic-data").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dynamicData)))
            .andExpect(status().isBadRequest());

        // Validate the DynamicData in the database
        List<DynamicData> dynamicDataList = dynamicDataRepository.findAll();
        assertThat(dynamicDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDynamicData() throws Exception {
        // Initialize the database
        dynamicDataRepository.saveAndFlush(dynamicData);

        int databaseSizeBeforeDelete = dynamicDataRepository.findAll().size();

        // Delete the dynamicData
        restDynamicDataMockMvc.perform(delete("/api/dynamic-data/{id}", dynamicData.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DynamicData> dynamicDataList = dynamicDataRepository.findAll();
        assertThat(dynamicDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
