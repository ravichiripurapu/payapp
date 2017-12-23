package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.DepositFrequency;
import com.pay.app.repository.DepositFrequencyRepository;
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
 * Test class for the DepositFrequencyResource REST controller.
 *
 * @see DepositFrequencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class DepositFrequencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DepositFrequencyRepository depositFrequencyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDepositFrequencyMockMvc;

    private DepositFrequency depositFrequency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepositFrequencyResource depositFrequencyResource = new DepositFrequencyResource(depositFrequencyRepository);
        this.restDepositFrequencyMockMvc = MockMvcBuilders.standaloneSetup(depositFrequencyResource)
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
    public static DepositFrequency createEntity(EntityManager em) {
        DepositFrequency depositFrequency = new DepositFrequency()
            .name(DEFAULT_NAME);
        return depositFrequency;
    }

    @Before
    public void initTest() {
        depositFrequency = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepositFrequency() throws Exception {
        int databaseSizeBeforeCreate = depositFrequencyRepository.findAll().size();

        // Create the DepositFrequency
        restDepositFrequencyMockMvc.perform(post("/api/deposit-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositFrequency)))
            .andExpect(status().isCreated());

        // Validate the DepositFrequency in the database
        List<DepositFrequency> depositFrequencyList = depositFrequencyRepository.findAll();
        assertThat(depositFrequencyList).hasSize(databaseSizeBeforeCreate + 1);
        DepositFrequency testDepositFrequency = depositFrequencyList.get(depositFrequencyList.size() - 1);
        assertThat(testDepositFrequency.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDepositFrequencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositFrequencyRepository.findAll().size();

        // Create the DepositFrequency with an existing ID
        depositFrequency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositFrequencyMockMvc.perform(post("/api/deposit-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositFrequency)))
            .andExpect(status().isBadRequest());

        // Validate the DepositFrequency in the database
        List<DepositFrequency> depositFrequencyList = depositFrequencyRepository.findAll();
        assertThat(depositFrequencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDepositFrequencies() throws Exception {
        // Initialize the database
        depositFrequencyRepository.saveAndFlush(depositFrequency);

        // Get all the depositFrequencyList
        restDepositFrequencyMockMvc.perform(get("/api/deposit-frequencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depositFrequency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDepositFrequency() throws Exception {
        // Initialize the database
        depositFrequencyRepository.saveAndFlush(depositFrequency);

        // Get the depositFrequency
        restDepositFrequencyMockMvc.perform(get("/api/deposit-frequencies/{id}", depositFrequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depositFrequency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepositFrequency() throws Exception {
        // Get the depositFrequency
        restDepositFrequencyMockMvc.perform(get("/api/deposit-frequencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepositFrequency() throws Exception {
        // Initialize the database
        depositFrequencyRepository.saveAndFlush(depositFrequency);
        int databaseSizeBeforeUpdate = depositFrequencyRepository.findAll().size();

        // Update the depositFrequency
        DepositFrequency updatedDepositFrequency = depositFrequencyRepository.findOne(depositFrequency.getId());
        // Disconnect from session so that the updates on updatedDepositFrequency are not directly saved in db
        em.detach(updatedDepositFrequency);
        updatedDepositFrequency
            .name(UPDATED_NAME);

        restDepositFrequencyMockMvc.perform(put("/api/deposit-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepositFrequency)))
            .andExpect(status().isOk());

        // Validate the DepositFrequency in the database
        List<DepositFrequency> depositFrequencyList = depositFrequencyRepository.findAll();
        assertThat(depositFrequencyList).hasSize(databaseSizeBeforeUpdate);
        DepositFrequency testDepositFrequency = depositFrequencyList.get(depositFrequencyList.size() - 1);
        assertThat(testDepositFrequency.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDepositFrequency() throws Exception {
        int databaseSizeBeforeUpdate = depositFrequencyRepository.findAll().size();

        // Create the DepositFrequency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDepositFrequencyMockMvc.perform(put("/api/deposit-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositFrequency)))
            .andExpect(status().isCreated());

        // Validate the DepositFrequency in the database
        List<DepositFrequency> depositFrequencyList = depositFrequencyRepository.findAll();
        assertThat(depositFrequencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDepositFrequency() throws Exception {
        // Initialize the database
        depositFrequencyRepository.saveAndFlush(depositFrequency);
        int databaseSizeBeforeDelete = depositFrequencyRepository.findAll().size();

        // Get the depositFrequency
        restDepositFrequencyMockMvc.perform(delete("/api/deposit-frequencies/{id}", depositFrequency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepositFrequency> depositFrequencyList = depositFrequencyRepository.findAll();
        assertThat(depositFrequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositFrequency.class);
        DepositFrequency depositFrequency1 = new DepositFrequency();
        depositFrequency1.setId(1L);
        DepositFrequency depositFrequency2 = new DepositFrequency();
        depositFrequency2.setId(depositFrequency1.getId());
        assertThat(depositFrequency1).isEqualTo(depositFrequency2);
        depositFrequency2.setId(2L);
        assertThat(depositFrequency1).isNotEqualTo(depositFrequency2);
        depositFrequency1.setId(null);
        assertThat(depositFrequency1).isNotEqualTo(depositFrequency2);
    }
}
