package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.FilingStatus;
import com.pay.app.repository.FilingStatusRepository;
import com.pay.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pay.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FilingStatusResource REST controller.
 *
 * @see FilingStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class FilingStatusResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FilingStatusRepository filingStatusRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilingStatusMockMvc;

    private FilingStatus filingStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilingStatusResource filingStatusResource = new FilingStatusResource(filingStatusRepository);
        this.restFilingStatusMockMvc = MockMvcBuilders.standaloneSetup(filingStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FilingStatus createEntity(EntityManager em) {
        FilingStatus filingStatus = new FilingStatus()
            .name(DEFAULT_NAME);
        return filingStatus;
    }

    @Before
    public void initTest() {
        filingStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilingStatus() throws Exception {
        int databaseSizeBeforeCreate = filingStatusRepository.findAll().size();

        // Create the FilingStatus
        restFilingStatusMockMvc.perform(post("/api/filing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filingStatus)))
            .andExpect(status().isCreated());

        // Validate the FilingStatus in the database
        List<FilingStatus> filingStatusList = filingStatusRepository.findAll();
        assertThat(filingStatusList).hasSize(databaseSizeBeforeCreate + 1);
        FilingStatus testFilingStatus = filingStatusList.get(filingStatusList.size() - 1);
        assertThat(testFilingStatus.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFilingStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filingStatusRepository.findAll().size();

        // Create the FilingStatus with an existing ID
        filingStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilingStatusMockMvc.perform(post("/api/filing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filingStatus)))
            .andExpect(status().isBadRequest());

        // Validate the FilingStatus in the database
        List<FilingStatus> filingStatusList = filingStatusRepository.findAll();
        assertThat(filingStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFilingStatuses() throws Exception {
        // Initialize the database
        filingStatusRepository.saveAndFlush(filingStatus);

        // Get all the filingStatusList
        restFilingStatusMockMvc.perform(get("/api/filing-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filingStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFilingStatus() throws Exception {
        // Initialize the database
        filingStatusRepository.saveAndFlush(filingStatus);

        // Get the filingStatus
        restFilingStatusMockMvc.perform(get("/api/filing-statuses/{id}", filingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filingStatus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFilingStatus() throws Exception {
        // Get the filingStatus
        restFilingStatusMockMvc.perform(get("/api/filing-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilingStatus() throws Exception {
        // Initialize the database
        filingStatusRepository.saveAndFlush(filingStatus);
        int databaseSizeBeforeUpdate = filingStatusRepository.findAll().size();

        // Update the filingStatus
        FilingStatus updatedFilingStatus = filingStatusRepository.findOne(filingStatus.getId());
        // Disconnect from session so that the updates on updatedFilingStatus are not directly saved in db
        em.detach(updatedFilingStatus);
        updatedFilingStatus
            .name(UPDATED_NAME);

        restFilingStatusMockMvc.perform(put("/api/filing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilingStatus)))
            .andExpect(status().isOk());

        // Validate the FilingStatus in the database
        List<FilingStatus> filingStatusList = filingStatusRepository.findAll();
        assertThat(filingStatusList).hasSize(databaseSizeBeforeUpdate);
        FilingStatus testFilingStatus = filingStatusList.get(filingStatusList.size() - 1);
        assertThat(testFilingStatus.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFilingStatus() throws Exception {
        int databaseSizeBeforeUpdate = filingStatusRepository.findAll().size();

        // Create the FilingStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilingStatusMockMvc.perform(put("/api/filing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filingStatus)))
            .andExpect(status().isCreated());

        // Validate the FilingStatus in the database
        List<FilingStatus> filingStatusList = filingStatusRepository.findAll();
        assertThat(filingStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFilingStatus() throws Exception {
        // Initialize the database
        filingStatusRepository.saveAndFlush(filingStatus);
        int databaseSizeBeforeDelete = filingStatusRepository.findAll().size();

        // Get the filingStatus
        restFilingStatusMockMvc.perform(delete("/api/filing-statuses/{id}", filingStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FilingStatus> filingStatusList = filingStatusRepository.findAll();
        assertThat(filingStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilingStatus.class);
        FilingStatus filingStatus1 = new FilingStatus();
        filingStatus1.setId(1L);
        FilingStatus filingStatus2 = new FilingStatus();
        filingStatus2.setId(filingStatus1.getId());
        assertThat(filingStatus1).isEqualTo(filingStatus2);
        filingStatus2.setId(2L);
        assertThat(filingStatus1).isNotEqualTo(filingStatus2);
        filingStatus1.setId(null);
        assertThat(filingStatus1).isNotEqualTo(filingStatus2);
    }
}
