package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyStateTax;
import com.pay.app.repository.CompanyStateTaxRepository;
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
 * Test class for the CompanyStateTaxResource REST controller.
 *
 * @see CompanyStateTaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyStateTaxResourceIntTest {

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_ID = "AAAAAAAAAA";
    private static final String UPDATED_TAX_ID = "BBBBBBBBBB";

    private static final Float DEFAULT_RATE = 1F;
    private static final Float UPDATED_RATE = 2F;

    private static final LocalDate DEFAULT_EFFECTIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSIT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyStateTaxRepository companyStateTaxRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyStateTaxMockMvc;

    private CompanyStateTax companyStateTax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyStateTaxResource companyStateTaxResource = new CompanyStateTaxResource(companyStateTaxRepository);
        this.restCompanyStateTaxMockMvc = MockMvcBuilders.standaloneSetup(companyStateTaxResource)
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
    public static CompanyStateTax createEntity(EntityManager em) {
        CompanyStateTax companyStateTax = new CompanyStateTax()
            .stateCode(DEFAULT_STATE_CODE)
            .taxId(DEFAULT_TAX_ID)
            .rate(DEFAULT_RATE)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .status(DEFAULT_STATUS)
            .depositFrequency(DEFAULT_DEPOSIT_FREQUENCY)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyStateTax;
    }

    @Before
    public void initTest() {
        companyStateTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyStateTax() throws Exception {
        int databaseSizeBeforeCreate = companyStateTaxRepository.findAll().size();

        // Create the CompanyStateTax
        restCompanyStateTaxMockMvc.perform(post("/api/company-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStateTax)))
            .andExpect(status().isCreated());

        // Validate the CompanyStateTax in the database
        List<CompanyStateTax> companyStateTaxList = companyStateTaxRepository.findAll();
        assertThat(companyStateTaxList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyStateTax testCompanyStateTax = companyStateTaxList.get(companyStateTaxList.size() - 1);
        assertThat(testCompanyStateTax.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testCompanyStateTax.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testCompanyStateTax.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCompanyStateTax.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testCompanyStateTax.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompanyStateTax.getDepositFrequency()).isEqualTo(DEFAULT_DEPOSIT_FREQUENCY);
        assertThat(testCompanyStateTax.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyStateTax.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyStateTax.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyStateTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyStateTaxRepository.findAll().size();

        // Create the CompanyStateTax with an existing ID
        companyStateTax.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyStateTaxMockMvc.perform(post("/api/company-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStateTax)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyStateTax in the database
        List<CompanyStateTax> companyStateTaxList = companyStateTaxRepository.findAll();
        assertThat(companyStateTaxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyStateTaxes() throws Exception {
        // Initialize the database
        companyStateTaxRepository.saveAndFlush(companyStateTax);

        // Get all the companyStateTaxList
        restCompanyStateTaxMockMvc.perform(get("/api/company-state-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyStateTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE.toString())))
            .andExpect(jsonPath("$.[*].taxId").value(hasItem(DEFAULT_TAX_ID.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].depositFrequency").value(hasItem(DEFAULT_DEPOSIT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyStateTax() throws Exception {
        // Initialize the database
        companyStateTaxRepository.saveAndFlush(companyStateTax);

        // Get the companyStateTax
        restCompanyStateTaxMockMvc.perform(get("/api/company-state-taxes/{id}", companyStateTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyStateTax.getId().intValue()))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE.toString()))
            .andExpect(jsonPath("$.taxId").value(DEFAULT_TAX_ID.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.depositFrequency").value(DEFAULT_DEPOSIT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyStateTax() throws Exception {
        // Get the companyStateTax
        restCompanyStateTaxMockMvc.perform(get("/api/company-state-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyStateTax() throws Exception {
        // Initialize the database
        companyStateTaxRepository.saveAndFlush(companyStateTax);
        int databaseSizeBeforeUpdate = companyStateTaxRepository.findAll().size();

        // Update the companyStateTax
        CompanyStateTax updatedCompanyStateTax = companyStateTaxRepository.findOne(companyStateTax.getId());
        // Disconnect from session so that the updates on updatedCompanyStateTax are not directly saved in db
        em.detach(updatedCompanyStateTax);
        updatedCompanyStateTax
            .stateCode(UPDATED_STATE_CODE)
            .taxId(UPDATED_TAX_ID)
            .rate(UPDATED_RATE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .status(UPDATED_STATUS)
            .depositFrequency(UPDATED_DEPOSIT_FREQUENCY)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyStateTaxMockMvc.perform(put("/api/company-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyStateTax)))
            .andExpect(status().isOk());

        // Validate the CompanyStateTax in the database
        List<CompanyStateTax> companyStateTaxList = companyStateTaxRepository.findAll();
        assertThat(companyStateTaxList).hasSize(databaseSizeBeforeUpdate);
        CompanyStateTax testCompanyStateTax = companyStateTaxList.get(companyStateTaxList.size() - 1);
        assertThat(testCompanyStateTax.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCompanyStateTax.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testCompanyStateTax.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCompanyStateTax.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testCompanyStateTax.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanyStateTax.getDepositFrequency()).isEqualTo(UPDATED_DEPOSIT_FREQUENCY);
        assertThat(testCompanyStateTax.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyStateTax.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyStateTax.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyStateTax() throws Exception {
        int databaseSizeBeforeUpdate = companyStateTaxRepository.findAll().size();

        // Create the CompanyStateTax

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyStateTaxMockMvc.perform(put("/api/company-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStateTax)))
            .andExpect(status().isCreated());

        // Validate the CompanyStateTax in the database
        List<CompanyStateTax> companyStateTaxList = companyStateTaxRepository.findAll();
        assertThat(companyStateTaxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyStateTax() throws Exception {
        // Initialize the database
        companyStateTaxRepository.saveAndFlush(companyStateTax);
        int databaseSizeBeforeDelete = companyStateTaxRepository.findAll().size();

        // Get the companyStateTax
        restCompanyStateTaxMockMvc.perform(delete("/api/company-state-taxes/{id}", companyStateTax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyStateTax> companyStateTaxList = companyStateTaxRepository.findAll();
        assertThat(companyStateTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyStateTax.class);
        CompanyStateTax companyStateTax1 = new CompanyStateTax();
        companyStateTax1.setId(1L);
        CompanyStateTax companyStateTax2 = new CompanyStateTax();
        companyStateTax2.setId(companyStateTax1.getId());
        assertThat(companyStateTax1).isEqualTo(companyStateTax2);
        companyStateTax2.setId(2L);
        assertThat(companyStateTax1).isNotEqualTo(companyStateTax2);
        companyStateTax1.setId(null);
        assertThat(companyStateTax1).isNotEqualTo(companyStateTax2);
    }
}
