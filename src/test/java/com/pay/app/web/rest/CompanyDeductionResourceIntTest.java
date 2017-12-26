package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyDeduction;
import com.pay.app.repository.CompanyDeductionRepository;
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
 * Test class for the CompanyDeductionResource REST controller.
 *
 * @see CompanyDeductionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyDeductionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DEDUCTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEDUCTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEDUCTION_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DEDUCTION_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyDeductionRepository companyDeductionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyDeductionMockMvc;

    private CompanyDeduction companyDeduction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyDeductionResource companyDeductionResource = new CompanyDeductionResource(companyDeductionRepository);
        this.restCompanyDeductionMockMvc = MockMvcBuilders.standaloneSetup(companyDeductionResource)
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
    public static CompanyDeduction createEntity(EntityManager em) {
        CompanyDeduction companyDeduction = new CompanyDeduction()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .deductionType(DEFAULT_DEDUCTION_TYPE)
            .deductionSubType(DEFAULT_DEDUCTION_SUB_TYPE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyDeduction;
    }

    @Before
    public void initTest() {
        companyDeduction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDeduction() throws Exception {
        int databaseSizeBeforeCreate = companyDeductionRepository.findAll().size();

        // Create the CompanyDeduction
        restCompanyDeductionMockMvc.perform(post("/api/company-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDeduction)))
            .andExpect(status().isCreated());

        // Validate the CompanyDeduction in the database
        List<CompanyDeduction> companyDeductionList = companyDeductionRepository.findAll();
        assertThat(companyDeductionList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDeduction testCompanyDeduction = companyDeductionList.get(companyDeductionList.size() - 1);
        assertThat(testCompanyDeduction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyDeduction.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testCompanyDeduction.getDeductionType()).isEqualTo(DEFAULT_DEDUCTION_TYPE);
        assertThat(testCompanyDeduction.getDeductionSubType()).isEqualTo(DEFAULT_DEDUCTION_SUB_TYPE);
        assertThat(testCompanyDeduction.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyDeduction.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyDeduction.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyDeductionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyDeductionRepository.findAll().size();

        // Create the CompanyDeduction with an existing ID
        companyDeduction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyDeductionMockMvc.perform(post("/api/company-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDeduction)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyDeduction in the database
        List<CompanyDeduction> companyDeductionList = companyDeductionRepository.findAll();
        assertThat(companyDeductionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyDeductions() throws Exception {
        // Initialize the database
        companyDeductionRepository.saveAndFlush(companyDeduction);

        // Get all the companyDeductionList
        restCompanyDeductionMockMvc.perform(get("/api/company-deductions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyDeduction.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].deductionType").value(hasItem(DEFAULT_DEDUCTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].deductionSubType").value(hasItem(DEFAULT_DEDUCTION_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyDeduction() throws Exception {
        // Initialize the database
        companyDeductionRepository.saveAndFlush(companyDeduction);

        // Get the companyDeduction
        restCompanyDeductionMockMvc.perform(get("/api/company-deductions/{id}", companyDeduction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDeduction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.deductionType").value(DEFAULT_DEDUCTION_TYPE.toString()))
            .andExpect(jsonPath("$.deductionSubType").value(DEFAULT_DEDUCTION_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDeduction() throws Exception {
        // Get the companyDeduction
        restCompanyDeductionMockMvc.perform(get("/api/company-deductions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDeduction() throws Exception {
        // Initialize the database
        companyDeductionRepository.saveAndFlush(companyDeduction);
        int databaseSizeBeforeUpdate = companyDeductionRepository.findAll().size();

        // Update the companyDeduction
        CompanyDeduction updatedCompanyDeduction = companyDeductionRepository.findOne(companyDeduction.getId());
        // Disconnect from session so that the updates on updatedCompanyDeduction are not directly saved in db
        em.detach(updatedCompanyDeduction);
        updatedCompanyDeduction
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .deductionType(UPDATED_DEDUCTION_TYPE)
            .deductionSubType(UPDATED_DEDUCTION_SUB_TYPE)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyDeductionMockMvc.perform(put("/api/company-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyDeduction)))
            .andExpect(status().isOk());

        // Validate the CompanyDeduction in the database
        List<CompanyDeduction> companyDeductionList = companyDeductionRepository.findAll();
        assertThat(companyDeductionList).hasSize(databaseSizeBeforeUpdate);
        CompanyDeduction testCompanyDeduction = companyDeductionList.get(companyDeductionList.size() - 1);
        assertThat(testCompanyDeduction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyDeduction.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCompanyDeduction.getDeductionType()).isEqualTo(UPDATED_DEDUCTION_TYPE);
        assertThat(testCompanyDeduction.getDeductionSubType()).isEqualTo(UPDATED_DEDUCTION_SUB_TYPE);
        assertThat(testCompanyDeduction.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyDeduction.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyDeduction.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyDeduction() throws Exception {
        int databaseSizeBeforeUpdate = companyDeductionRepository.findAll().size();

        // Create the CompanyDeduction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyDeductionMockMvc.perform(put("/api/company-deductions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDeduction)))
            .andExpect(status().isCreated());

        // Validate the CompanyDeduction in the database
        List<CompanyDeduction> companyDeductionList = companyDeductionRepository.findAll();
        assertThat(companyDeductionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyDeduction() throws Exception {
        // Initialize the database
        companyDeductionRepository.saveAndFlush(companyDeduction);
        int databaseSizeBeforeDelete = companyDeductionRepository.findAll().size();

        // Get the companyDeduction
        restCompanyDeductionMockMvc.perform(delete("/api/company-deductions/{id}", companyDeduction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDeduction> companyDeductionList = companyDeductionRepository.findAll();
        assertThat(companyDeductionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDeduction.class);
        CompanyDeduction companyDeduction1 = new CompanyDeduction();
        companyDeduction1.setId(1L);
        CompanyDeduction companyDeduction2 = new CompanyDeduction();
        companyDeduction2.setId(companyDeduction1.getId());
        assertThat(companyDeduction1).isEqualTo(companyDeduction2);
        companyDeduction2.setId(2L);
        assertThat(companyDeduction1).isNotEqualTo(companyDeduction2);
        companyDeduction1.setId(null);
        assertThat(companyDeduction1).isNotEqualTo(companyDeduction2);
    }
}
