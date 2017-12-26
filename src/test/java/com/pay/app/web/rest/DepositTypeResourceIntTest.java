package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.DepositType;
import com.pay.app.repository.DepositTypeRepository;
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
 * Test class for the DepositTypeResource REST controller.
 *
 * @see DepositTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class DepositTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DepositTypeRepository depositTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDepositTypeMockMvc;

    private DepositType depositType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepositTypeResource depositTypeResource = new DepositTypeResource(depositTypeRepository);
        this.restDepositTypeMockMvc = MockMvcBuilders.standaloneSetup(depositTypeResource)
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
    public static DepositType createEntity(EntityManager em) {
        DepositType depositType = new DepositType()
            .name(DEFAULT_NAME);
        return depositType;
    }

    @Before
    public void initTest() {
        depositType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepositType() throws Exception {
        int databaseSizeBeforeCreate = depositTypeRepository.findAll().size();

        // Create the DepositType
        restDepositTypeMockMvc.perform(post("/api/deposit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositType)))
            .andExpect(status().isCreated());

        // Validate the DepositType in the database
        List<DepositType> depositTypeList = depositTypeRepository.findAll();
        assertThat(depositTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DepositType testDepositType = depositTypeList.get(depositTypeList.size() - 1);
        assertThat(testDepositType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDepositTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depositTypeRepository.findAll().size();

        // Create the DepositType with an existing ID
        depositType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositTypeMockMvc.perform(post("/api/deposit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositType)))
            .andExpect(status().isBadRequest());

        // Validate the DepositType in the database
        List<DepositType> depositTypeList = depositTypeRepository.findAll();
        assertThat(depositTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDepositTypes() throws Exception {
        // Initialize the database
        depositTypeRepository.saveAndFlush(depositType);

        // Get all the depositTypeList
        restDepositTypeMockMvc.perform(get("/api/deposit-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depositType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDepositType() throws Exception {
        // Initialize the database
        depositTypeRepository.saveAndFlush(depositType);

        // Get the depositType
        restDepositTypeMockMvc.perform(get("/api/deposit-types/{id}", depositType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depositType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepositType() throws Exception {
        // Get the depositType
        restDepositTypeMockMvc.perform(get("/api/deposit-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepositType() throws Exception {
        // Initialize the database
        depositTypeRepository.saveAndFlush(depositType);
        int databaseSizeBeforeUpdate = depositTypeRepository.findAll().size();

        // Update the depositType
        DepositType updatedDepositType = depositTypeRepository.findOne(depositType.getId());
        // Disconnect from session so that the updates on updatedDepositType are not directly saved in db
        em.detach(updatedDepositType);
        updatedDepositType
            .name(UPDATED_NAME);

        restDepositTypeMockMvc.perform(put("/api/deposit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepositType)))
            .andExpect(status().isOk());

        // Validate the DepositType in the database
        List<DepositType> depositTypeList = depositTypeRepository.findAll();
        assertThat(depositTypeList).hasSize(databaseSizeBeforeUpdate);
        DepositType testDepositType = depositTypeList.get(depositTypeList.size() - 1);
        assertThat(testDepositType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDepositType() throws Exception {
        int databaseSizeBeforeUpdate = depositTypeRepository.findAll().size();

        // Create the DepositType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDepositTypeMockMvc.perform(put("/api/deposit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depositType)))
            .andExpect(status().isCreated());

        // Validate the DepositType in the database
        List<DepositType> depositTypeList = depositTypeRepository.findAll();
        assertThat(depositTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDepositType() throws Exception {
        // Initialize the database
        depositTypeRepository.saveAndFlush(depositType);
        int databaseSizeBeforeDelete = depositTypeRepository.findAll().size();

        // Get the depositType
        restDepositTypeMockMvc.perform(delete("/api/deposit-types/{id}", depositType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DepositType> depositTypeList = depositTypeRepository.findAll();
        assertThat(depositTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepositType.class);
        DepositType depositType1 = new DepositType();
        depositType1.setId(1L);
        DepositType depositType2 = new DepositType();
        depositType2.setId(depositType1.getId());
        assertThat(depositType1).isEqualTo(depositType2);
        depositType2.setId(2L);
        assertThat(depositType1).isNotEqualTo(depositType2);
        depositType1.setId(null);
        assertThat(depositType1).isNotEqualTo(depositType2);
    }
}
