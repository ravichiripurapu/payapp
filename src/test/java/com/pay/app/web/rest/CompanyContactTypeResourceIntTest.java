package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyContactType;
import com.pay.app.repository.CompanyContactTypeRepository;
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
 * Test class for the CompanyContactTypeResource REST controller.
 *
 * @see CompanyContactTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyContactTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyContactTypeRepository companyContactTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyContactTypeMockMvc;

    private CompanyContactType companyContactType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyContactTypeResource companyContactTypeResource = new CompanyContactTypeResource(companyContactTypeRepository);
        this.restCompanyContactTypeMockMvc = MockMvcBuilders.standaloneSetup(companyContactTypeResource)
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
    public static CompanyContactType createEntity(EntityManager em) {
        CompanyContactType companyContactType = new CompanyContactType()
            .name(DEFAULT_NAME);
        return companyContactType;
    }

    @Before
    public void initTest() {
        companyContactType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyContactType() throws Exception {
        int databaseSizeBeforeCreate = companyContactTypeRepository.findAll().size();

        // Create the CompanyContactType
        restCompanyContactTypeMockMvc.perform(post("/api/company-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyContactType)))
            .andExpect(status().isCreated());

        // Validate the CompanyContactType in the database
        List<CompanyContactType> companyContactTypeList = companyContactTypeRepository.findAll();
        assertThat(companyContactTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyContactType testCompanyContactType = companyContactTypeList.get(companyContactTypeList.size() - 1);
        assertThat(testCompanyContactType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCompanyContactTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyContactTypeRepository.findAll().size();

        // Create the CompanyContactType with an existing ID
        companyContactType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyContactTypeMockMvc.perform(post("/api/company-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyContactType)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyContactType in the database
        List<CompanyContactType> companyContactTypeList = companyContactTypeRepository.findAll();
        assertThat(companyContactTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyContactTypes() throws Exception {
        // Initialize the database
        companyContactTypeRepository.saveAndFlush(companyContactType);

        // Get all the companyContactTypeList
        restCompanyContactTypeMockMvc.perform(get("/api/company-contact-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyContactType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCompanyContactType() throws Exception {
        // Initialize the database
        companyContactTypeRepository.saveAndFlush(companyContactType);

        // Get the companyContactType
        restCompanyContactTypeMockMvc.perform(get("/api/company-contact-types/{id}", companyContactType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyContactType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyContactType() throws Exception {
        // Get the companyContactType
        restCompanyContactTypeMockMvc.perform(get("/api/company-contact-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyContactType() throws Exception {
        // Initialize the database
        companyContactTypeRepository.saveAndFlush(companyContactType);
        int databaseSizeBeforeUpdate = companyContactTypeRepository.findAll().size();

        // Update the companyContactType
        CompanyContactType updatedCompanyContactType = companyContactTypeRepository.findOne(companyContactType.getId());
        // Disconnect from session so that the updates on updatedCompanyContactType are not directly saved in db
        em.detach(updatedCompanyContactType);
        updatedCompanyContactType
            .name(UPDATED_NAME);

        restCompanyContactTypeMockMvc.perform(put("/api/company-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyContactType)))
            .andExpect(status().isOk());

        // Validate the CompanyContactType in the database
        List<CompanyContactType> companyContactTypeList = companyContactTypeRepository.findAll();
        assertThat(companyContactTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyContactType testCompanyContactType = companyContactTypeList.get(companyContactTypeList.size() - 1);
        assertThat(testCompanyContactType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyContactType() throws Exception {
        int databaseSizeBeforeUpdate = companyContactTypeRepository.findAll().size();

        // Create the CompanyContactType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyContactTypeMockMvc.perform(put("/api/company-contact-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyContactType)))
            .andExpect(status().isCreated());

        // Validate the CompanyContactType in the database
        List<CompanyContactType> companyContactTypeList = companyContactTypeRepository.findAll();
        assertThat(companyContactTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyContactType() throws Exception {
        // Initialize the database
        companyContactTypeRepository.saveAndFlush(companyContactType);
        int databaseSizeBeforeDelete = companyContactTypeRepository.findAll().size();

        // Get the companyContactType
        restCompanyContactTypeMockMvc.perform(delete("/api/company-contact-types/{id}", companyContactType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyContactType> companyContactTypeList = companyContactTypeRepository.findAll();
        assertThat(companyContactTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyContactType.class);
        CompanyContactType companyContactType1 = new CompanyContactType();
        companyContactType1.setId(1L);
        CompanyContactType companyContactType2 = new CompanyContactType();
        companyContactType2.setId(companyContactType1.getId());
        assertThat(companyContactType1).isEqualTo(companyContactType2);
        companyContactType2.setId(2L);
        assertThat(companyContactType1).isNotEqualTo(companyContactType2);
        companyContactType1.setId(null);
        assertThat(companyContactType1).isNotEqualTo(companyContactType2);
    }
}
