package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TestjioApplicationApp;
import com.mycompany.myapp.domain.Conversation;
import com.mycompany.myapp.repository.ConversationRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.CONVERSATIONTYPE;
/**
 * Integration tests for the {@link ConversationResource} REST controller.
 */
@SpringBootTest(classes = TestjioApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConversationResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final CONVERSATIONTYPE DEFAULT_TYPE = CONVERSATIONTYPE.EMAIL;
    private static final CONVERSATIONTYPE UPDATED_TYPE = CONVERSATIONTYPE.SMS;

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ATTACHMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SENT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SENT_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConversationMockMvc;

    private Conversation conversation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversation createEntity(EntityManager em) {
        Conversation conversation = new Conversation()
            .subject(DEFAULT_SUBJECT)
            .type(DEFAULT_TYPE)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .attachmentName(DEFAULT_ATTACHMENT_NAME)
            .status(DEFAULT_STATUS)
            .sentDateTime(DEFAULT_SENT_DATE_TIME);
        return conversation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversation createUpdatedEntity(EntityManager em) {
        Conversation conversation = new Conversation()
            .subject(UPDATED_SUBJECT)
            .type(UPDATED_TYPE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .attachmentName(UPDATED_ATTACHMENT_NAME)
            .status(UPDATED_STATUS)
            .sentDateTime(UPDATED_SENT_DATE_TIME);
        return conversation;
    }

    @BeforeEach
    public void initTest() {
        conversation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversation() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();
        // Create the Conversation
        restConversationMockMvc.perform(post("/api/conversations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isCreated());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate + 1);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testConversation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConversation.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testConversation.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testConversation.getAttachmentName()).isEqualTo(DEFAULT_ATTACHMENT_NAME);
        assertThat(testConversation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConversation.getSentDateTime()).isEqualTo(DEFAULT_SENT_DATE_TIME);
    }

    @Test
    @Transactional
    public void createConversationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // Create the Conversation with an existing ID
        conversation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversationMockMvc.perform(post("/api/conversations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllConversations() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].attachmentName").value(hasItem(DEFAULT_ATTACHMENT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sentDateTime").value(hasItem(DEFAULT_SENT_DATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", conversation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conversation.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.attachmentName").value(DEFAULT_ATTACHMENT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.sentDateTime").value(DEFAULT_SENT_DATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingConversation() throws Exception {
        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation
        Conversation updatedConversation = conversationRepository.findById(conversation.getId()).get();
        // Disconnect from session so that the updates on updatedConversation are not directly saved in db
        em.detach(updatedConversation);
        updatedConversation
            .subject(UPDATED_SUBJECT)
            .type(UPDATED_TYPE)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .attachmentName(UPDATED_ATTACHMENT_NAME)
            .status(UPDATED_STATUS)
            .sentDateTime(UPDATED_SENT_DATE_TIME);

        restConversationMockMvc.perform(put("/api/conversations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConversation)))
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testConversation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConversation.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testConversation.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testConversation.getAttachmentName()).isEqualTo(UPDATED_ATTACHMENT_NAME);
        assertThat(testConversation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConversation.getSentDateTime()).isEqualTo(UPDATED_SENT_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversationMockMvc.perform(put("/api/conversations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeDelete = conversationRepository.findAll().size();

        // Delete the conversation
        restConversationMockMvc.perform(delete("/api/conversations/{id}", conversation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
