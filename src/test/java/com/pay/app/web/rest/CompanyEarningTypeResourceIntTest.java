package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyEarningType;
import com.pay.app.repository.CompanyEarningTypeRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.pay.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CompanyEarningTypeResource REST controller.
 *
 * @see CompanyEarningTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyEarningTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyEarningTypeRepository companyEarningTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyEarningMockMvc;

    private CompanyEarningType companyEarningType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyEarningTypeResource companyEarningTypeResource = new CompanyEarningTypeResource(companyEarningTypeRepository);
        this.restCompanyEarningMockMvc = MockMvcBuilders.standaloneSetup(companyEarningTypeResource)
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
    public static CompanyEarningType createEntity(EntityManager em) {
        CompanyEarningType companyEarningType = new CompanyEarningType()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .abbreviation(DEFAULT_ABBREVIATION)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyEarningType;
    }

    @Before
    public void initTest() {
        companyEarningType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyEarning() throws Exception {
        int databaseSizeBeforeCreate = companyEarningTypeRepository.findAll().size();

        // Create the CompanyEarningType
        restCompanyEarningMockMvc.perform(post("/api/company-earning-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEarningType)))
            .andExpect(status().isCreated());

        // Validate the CompanyEarningType in the database
        List<CompanyEarningType> companyEarningTypeList = companyEarningTypeRepository.findAll();
        assertThat(companyEarningTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyEarningType testCompanyEarningType = companyEarningTypeList.get(companyEarningTypeList.size() - 1);
        assertThat(testCompanyEarningType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyEarningType.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testCompanyEarningType.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testCompanyEarningType.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyEarningType.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyEarningType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyEarningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyEarningTypeRepository.findAll().size();

        // Create the CompanyEarningType with an existing ID
        companyEarningType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyEarningMockMvc.perform(post("/api/company-earning-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEarningType)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEarningType in the database
        List<CompanyEarningType> companyEarningTypeList = companyEarningTypeRepository.findAll();
        assertThat(companyEarningTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyEarnings() throws Exception {
        // Initialize the database
        companyEarningTypeRepository.saveAndFlush(companyEarningType);

        // Get all the companyEarningList
        restCompanyEarningMockMvc.perform(get("/api/company-earning-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEarningType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyEarning() throws Exception {
        // Initialize the database
        companyEarningTypeRepository.saveAndFlush(companyEarningType);

        // Get the companyEarningType
        restCompanyEarningMockMvc.perform(get("/api/company-earning-types/{id}", companyEarningType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyEarningType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyEarning() throws Exception {
        // Get the companyEarningType
        restCompanyEarningMockMvc.perform(get("/api/company-earning-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyEarning() throws Exception {
        // Initialize the database
        companyEarningTypeRepository.saveAndFlush(companyEarningType);
        int databaseSizeBeforeUpdate = companyEarningTypeRepository.findAll().size();

        // Update the companyEarningType
        CompanyEarningType updatedCompanyEarningType = companyEarningTypeRepository.findOne(companyEarningType.getId());
        // Disconnect from session so that the updates on updatedCompanyEarningType are not directly saved in db
        em.detach(updatedCompanyEarningType);
        updatedCompanyEarningType
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .abbreviation(UPDATED_ABBREVIATION)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyEarningMockMvc.perform(put("/api/company-earning-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyEarningType)))
            .andExpect(status().isOk());

        // Validate the CompanyEarningType in the database
        List<CompanyEarningType> companyEarningTypeList = companyEarningTypeRepository.findAll();
        assertThat(companyEarningTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyEarningType testCompanyEarningType = companyEarningTypeList.get(companyEarningTypeList.size() - 1);
        assertThat(testCompanyEarningType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyEarningType.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCompanyEarningType.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testCompanyEarningType.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyEarningType.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyEarningType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyEarning() throws Exception {
        int databaseSizeBeforeUpdate = companyEarningTypeRepository.findAll().size();

        // Create the CompanyEarningType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyEarningMockMvc.perform(put("/api/company-earning-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEarningType)))
            .andExpect(status().isCreated());

        // Validate the CompanyEarningType in the database
        List<CompanyEarningType> companyEarningTypeList = companyEarningTypeRepository.findAll();
        assertThat(companyEarningTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyEarning() throws Exception {
        // Initialize the database
        companyEarningTypeRepository.saveAndFlush(companyEarningType);
        int databaseSizeBeforeDelete = companyEarningTypeRepository.findAll().size();

        // Get the companyEarningType
        restCompanyEarningMockMvc.perform(delete("/api/company-earning-types/{id}", companyEarningType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyEarningType> companyEarningTypeList = companyEarningTypeRepository.findAll();
        assertThat(companyEarningTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEarningType.class);
        CompanyEarningType companyEarningType1 = new CompanyEarningType();
        companyEarningType1.setId(1L);
        CompanyEarningType companyEarningType2 = new CompanyEarningType();
        companyEarningType2.setId(companyEarningType1.getId());
        assertThat(companyEarningType1).isEqualTo(companyEarningType2);
        companyEarningType2.setId(2L);
        assertThat(companyEarningType1).isNotEqualTo(companyEarningType2);
        companyEarningType1.setId(null);
        assertThat(companyEarningType1).isNotEqualTo(companyEarningType2);
    }
}
