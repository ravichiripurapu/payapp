package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.DeductionType;
import com.pay.app.repository.DeductionTypeRepository;
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
 * Test class for the DeductionTypeResource REST controller.
 *
 * @see DeductionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class DeductionTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeductionTypeRepository deductionTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeductionTypeMockMvc;

    private DeductionType deductionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeductionTypeResource deductionTypeResource = new DeductionTypeResource(deductionTypeRepository);
        this.restDeductionTypeMockMvc = MockMvcBuilders.standaloneSetup(deductionTypeResource)
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
    public static DeductionType createEntity(EntityManager em) {
        DeductionType deductionType = new DeductionType()
            .name(DEFAULT_NAME);
        return deductionType;
    }

    @Before
    public void initTest() {
        deductionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeductionType() throws Exception {
        int databaseSizeBeforeCreate = deductionTypeRepository.findAll().size();

        // Create the DeductionType
        restDeductionTypeMockMvc.perform(post("/api/deduction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionType)))
            .andExpect(status().isCreated());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeductionType testDeductionType = deductionTypeList.get(deductionTypeList.size() - 1);
        assertThat(testDeductionType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeductionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deductionTypeRepository.findAll().size();

        // Create the DeductionType with an existing ID
        deductionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeductionTypeMockMvc.perform(post("/api/deduction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionType)))
            .andExpect(status().isBadRequest());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeductionTypes() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);

        // Get all the deductionTypeList
        restDeductionTypeMockMvc.perform(get("/api/deduction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deductionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDeductionType() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);

        // Get the deductionType
        restDeductionTypeMockMvc.perform(get("/api/deduction-types/{id}", deductionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deductionType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeductionType() throws Exception {
        // Get the deductionType
        restDeductionTypeMockMvc.perform(get("/api/deduction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeductionType() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);
        int databaseSizeBeforeUpdate = deductionTypeRepository.findAll().size();

        // Update the deductionType
        DeductionType updatedDeductionType = deductionTypeRepository.findOne(deductionType.getId());
        // Disconnect from session so that the updates on updatedDeductionType are not directly saved in db
        em.detach(updatedDeductionType);
        updatedDeductionType
            .name(UPDATED_NAME);

        restDeductionTypeMockMvc.perform(put("/api/deduction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeductionType)))
            .andExpect(status().isOk());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeUpdate);
        DeductionType testDeductionType = deductionTypeList.get(deductionTypeList.size() - 1);
        assertThat(testDeductionType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeductionType() throws Exception {
        int databaseSizeBeforeUpdate = deductionTypeRepository.findAll().size();

        // Create the DeductionType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeductionTypeMockMvc.perform(put("/api/deduction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionType)))
            .andExpect(status().isCreated());

        // Validate the DeductionType in the database
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeductionType() throws Exception {
        // Initialize the database
        deductionTypeRepository.saveAndFlush(deductionType);
        int databaseSizeBeforeDelete = deductionTypeRepository.findAll().size();

        // Get the deductionType
        restDeductionTypeMockMvc.perform(delete("/api/deduction-types/{id}", deductionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeductionType> deductionTypeList = deductionTypeRepository.findAll();
        assertThat(deductionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeductionType.class);
        DeductionType deductionType1 = new DeductionType();
        deductionType1.setId(1L);
        DeductionType deductionType2 = new DeductionType();
        deductionType2.setId(deductionType1.getId());
        assertThat(deductionType1).isEqualTo(deductionType2);
        deductionType2.setId(2L);
        assertThat(deductionType1).isNotEqualTo(deductionType2);
        deductionType1.setId(null);
        assertThat(deductionType1).isNotEqualTo(deductionType2);
    }
}
