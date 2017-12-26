package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompensationType;
import com.pay.app.repository.CompensationTypeRepository;
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
 * Test class for the CompensationTypeResource REST controller.
 *
 * @see CompensationTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompensationTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompensationTypeRepository compensationTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompensationTypeMockMvc;

    private CompensationType compensationType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompensationTypeResource compensationTypeResource = new CompensationTypeResource(compensationTypeRepository);
        this.restCompensationTypeMockMvc = MockMvcBuilders.standaloneSetup(compensationTypeResource)
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
    public static CompensationType createEntity(EntityManager em) {
        CompensationType compensationType = new CompensationType()
            .name(DEFAULT_NAME);
        return compensationType;
    }

    @Before
    public void initTest() {
        compensationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompensationType() throws Exception {
        int databaseSizeBeforeCreate = compensationTypeRepository.findAll().size();

        // Create the CompensationType
        restCompensationTypeMockMvc.perform(post("/api/compensation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compensationType)))
            .andExpect(status().isCreated());

        // Validate the CompensationType in the database
        List<CompensationType> compensationTypeList = compensationTypeRepository.findAll();
        assertThat(compensationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CompensationType testCompensationType = compensationTypeList.get(compensationTypeList.size() - 1);
        assertThat(testCompensationType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCompensationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compensationTypeRepository.findAll().size();

        // Create the CompensationType with an existing ID
        compensationType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompensationTypeMockMvc.perform(post("/api/compensation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compensationType)))
            .andExpect(status().isBadRequest());

        // Validate the CompensationType in the database
        List<CompensationType> compensationTypeList = compensationTypeRepository.findAll();
        assertThat(compensationTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompensationTypes() throws Exception {
        // Initialize the database
        compensationTypeRepository.saveAndFlush(compensationType);

        // Get all the compensationTypeList
        restCompensationTypeMockMvc.perform(get("/api/compensation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compensationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCompensationType() throws Exception {
        // Initialize the database
        compensationTypeRepository.saveAndFlush(compensationType);

        // Get the compensationType
        restCompensationTypeMockMvc.perform(get("/api/compensation-types/{id}", compensationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compensationType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompensationType() throws Exception {
        // Get the compensationType
        restCompensationTypeMockMvc.perform(get("/api/compensation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompensationType() throws Exception {
        // Initialize the database
        compensationTypeRepository.saveAndFlush(compensationType);
        int databaseSizeBeforeUpdate = compensationTypeRepository.findAll().size();

        // Update the compensationType
        CompensationType updatedCompensationType = compensationTypeRepository.findOne(compensationType.getId());
        // Disconnect from session so that the updates on updatedCompensationType are not directly saved in db
        em.detach(updatedCompensationType);
        updatedCompensationType
            .name(UPDATED_NAME);

        restCompensationTypeMockMvc.perform(put("/api/compensation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompensationType)))
            .andExpect(status().isOk());

        // Validate the CompensationType in the database
        List<CompensationType> compensationTypeList = compensationTypeRepository.findAll();
        assertThat(compensationTypeList).hasSize(databaseSizeBeforeUpdate);
        CompensationType testCompensationType = compensationTypeList.get(compensationTypeList.size() - 1);
        assertThat(testCompensationType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompensationType() throws Exception {
        int databaseSizeBeforeUpdate = compensationTypeRepository.findAll().size();

        // Create the CompensationType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompensationTypeMockMvc.perform(put("/api/compensation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compensationType)))
            .andExpect(status().isCreated());

        // Validate the CompensationType in the database
        List<CompensationType> compensationTypeList = compensationTypeRepository.findAll();
        assertThat(compensationTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompensationType() throws Exception {
        // Initialize the database
        compensationTypeRepository.saveAndFlush(compensationType);
        int databaseSizeBeforeDelete = compensationTypeRepository.findAll().size();

        // Get the compensationType
        restCompensationTypeMockMvc.perform(delete("/api/compensation-types/{id}", compensationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompensationType> compensationTypeList = compensationTypeRepository.findAll();
        assertThat(compensationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompensationType.class);
        CompensationType compensationType1 = new CompensationType();
        compensationType1.setId(1L);
        CompensationType compensationType2 = new CompensationType();
        compensationType2.setId(compensationType1.getId());
        assertThat(compensationType1).isEqualTo(compensationType2);
        compensationType2.setId(2L);
        assertThat(compensationType1).isNotEqualTo(compensationType2);
        compensationType1.setId(null);
        assertThat(compensationType1).isNotEqualTo(compensationType2);
    }
}
