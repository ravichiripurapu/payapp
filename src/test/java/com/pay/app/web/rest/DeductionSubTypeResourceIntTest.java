package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.DeductionSubType;
import com.pay.app.repository.DeductionSubTypeRepository;
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
 * Test class for the DeductionSubTypeResource REST controller.
 *
 * @see DeductionSubTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class DeductionSubTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DeductionSubTypeRepository deductionSubTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeductionSubTypeMockMvc;

    private DeductionSubType deductionSubType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeductionSubTypeResource deductionSubTypeResource = new DeductionSubTypeResource(deductionSubTypeRepository);
        this.restDeductionSubTypeMockMvc = MockMvcBuilders.standaloneSetup(deductionSubTypeResource)
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
    public static DeductionSubType createEntity(EntityManager em) {
        DeductionSubType deductionSubType = new DeductionSubType()
            .name(DEFAULT_NAME);
        return deductionSubType;
    }

    @Before
    public void initTest() {
        deductionSubType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeductionSubType() throws Exception {
        int databaseSizeBeforeCreate = deductionSubTypeRepository.findAll().size();

        // Create the DeductionSubType
        restDeductionSubTypeMockMvc.perform(post("/api/deduction-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionSubType)))
            .andExpect(status().isCreated());

        // Validate the DeductionSubType in the database
        List<DeductionSubType> deductionSubTypeList = deductionSubTypeRepository.findAll();
        assertThat(deductionSubTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DeductionSubType testDeductionSubType = deductionSubTypeList.get(deductionSubTypeList.size() - 1);
        assertThat(testDeductionSubType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDeductionSubTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deductionSubTypeRepository.findAll().size();

        // Create the DeductionSubType with an existing ID
        deductionSubType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeductionSubTypeMockMvc.perform(post("/api/deduction-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionSubType)))
            .andExpect(status().isBadRequest());

        // Validate the DeductionSubType in the database
        List<DeductionSubType> deductionSubTypeList = deductionSubTypeRepository.findAll();
        assertThat(deductionSubTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeductionSubTypes() throws Exception {
        // Initialize the database
        deductionSubTypeRepository.saveAndFlush(deductionSubType);

        // Get all the deductionSubTypeList
        restDeductionSubTypeMockMvc.perform(get("/api/deduction-sub-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deductionSubType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDeductionSubType() throws Exception {
        // Initialize the database
        deductionSubTypeRepository.saveAndFlush(deductionSubType);

        // Get the deductionSubType
        restDeductionSubTypeMockMvc.perform(get("/api/deduction-sub-types/{id}", deductionSubType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deductionSubType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeductionSubType() throws Exception {
        // Get the deductionSubType
        restDeductionSubTypeMockMvc.perform(get("/api/deduction-sub-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeductionSubType() throws Exception {
        // Initialize the database
        deductionSubTypeRepository.saveAndFlush(deductionSubType);
        int databaseSizeBeforeUpdate = deductionSubTypeRepository.findAll().size();

        // Update the deductionSubType
        DeductionSubType updatedDeductionSubType = deductionSubTypeRepository.findOne(deductionSubType.getId());
        // Disconnect from session so that the updates on updatedDeductionSubType are not directly saved in db
        em.detach(updatedDeductionSubType);
        updatedDeductionSubType
            .name(UPDATED_NAME);

        restDeductionSubTypeMockMvc.perform(put("/api/deduction-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeductionSubType)))
            .andExpect(status().isOk());

        // Validate the DeductionSubType in the database
        List<DeductionSubType> deductionSubTypeList = deductionSubTypeRepository.findAll();
        assertThat(deductionSubTypeList).hasSize(databaseSizeBeforeUpdate);
        DeductionSubType testDeductionSubType = deductionSubTypeList.get(deductionSubTypeList.size() - 1);
        assertThat(testDeductionSubType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeductionSubType() throws Exception {
        int databaseSizeBeforeUpdate = deductionSubTypeRepository.findAll().size();

        // Create the DeductionSubType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeductionSubTypeMockMvc.perform(put("/api/deduction-sub-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deductionSubType)))
            .andExpect(status().isCreated());

        // Validate the DeductionSubType in the database
        List<DeductionSubType> deductionSubTypeList = deductionSubTypeRepository.findAll();
        assertThat(deductionSubTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDeductionSubType() throws Exception {
        // Initialize the database
        deductionSubTypeRepository.saveAndFlush(deductionSubType);
        int databaseSizeBeforeDelete = deductionSubTypeRepository.findAll().size();

        // Get the deductionSubType
        restDeductionSubTypeMockMvc.perform(delete("/api/deduction-sub-types/{id}", deductionSubType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeductionSubType> deductionSubTypeList = deductionSubTypeRepository.findAll();
        assertThat(deductionSubTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeductionSubType.class);
        DeductionSubType deductionSubType1 = new DeductionSubType();
        deductionSubType1.setId(1L);
        DeductionSubType deductionSubType2 = new DeductionSubType();
        deductionSubType2.setId(deductionSubType1.getId());
        assertThat(deductionSubType1).isEqualTo(deductionSubType2);
        deductionSubType2.setId(2L);
        assertThat(deductionSubType1).isNotEqualTo(deductionSubType2);
        deductionSubType1.setId(null);
        assertThat(deductionSubType1).isNotEqualTo(deductionSubType2);
    }
}
