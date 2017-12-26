package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyLocalTax;
import com.pay.app.repository.CompanyLocalTaxRepository;
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
 * Test class for the CompanyLocalTaxResource REST controller.
 *
 * @see CompanyLocalTaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyLocalTaxResourceIntTest {

    private static final String DEFAULT_FILING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_FILING_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXEMPT_FROM_WITH_HOLDING = false;
    private static final Boolean UPDATED_EXEMPT_FROM_WITH_HOLDING = true;

    private static final Integer DEFAULT_ALLOWANCES = 1;
    private static final Integer UPDATED_ALLOWANCES = 2;

    private static final Float DEFAULT_EXTRA_WITH_HOLDING = 1F;
    private static final Float UPDATED_EXTRA_WITH_HOLDING = 2F;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyLocalTaxRepository companyLocalTaxRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyLocalTaxMockMvc;

    private CompanyLocalTax companyLocalTax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyLocalTaxResource companyLocalTaxResource = new CompanyLocalTaxResource(companyLocalTaxRepository);
        this.restCompanyLocalTaxMockMvc = MockMvcBuilders.standaloneSetup(companyLocalTaxResource)
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
    public static CompanyLocalTax createEntity(EntityManager em) {
        CompanyLocalTax companyLocalTax = new CompanyLocalTax()
            .filingStatus(DEFAULT_FILING_STATUS)
            .exemptFromWithHolding(DEFAULT_EXEMPT_FROM_WITH_HOLDING)
            .allowances(DEFAULT_ALLOWANCES)
            .extraWithHolding(DEFAULT_EXTRA_WITH_HOLDING)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyLocalTax;
    }

    @Before
    public void initTest() {
        companyLocalTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyLocalTax() throws Exception {
        int databaseSizeBeforeCreate = companyLocalTaxRepository.findAll().size();

        // Create the CompanyLocalTax
        restCompanyLocalTaxMockMvc.perform(post("/api/company-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocalTax)))
            .andExpect(status().isCreated());

        // Validate the CompanyLocalTax in the database
        List<CompanyLocalTax> companyLocalTaxList = companyLocalTaxRepository.findAll();
        assertThat(companyLocalTaxList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyLocalTax testCompanyLocalTax = companyLocalTaxList.get(companyLocalTaxList.size() - 1);
        assertThat(testCompanyLocalTax.getFilingStatus()).isEqualTo(DEFAULT_FILING_STATUS);
        assertThat(testCompanyLocalTax.isExemptFromWithHolding()).isEqualTo(DEFAULT_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testCompanyLocalTax.getAllowances()).isEqualTo(DEFAULT_ALLOWANCES);
        assertThat(testCompanyLocalTax.getExtraWithHolding()).isEqualTo(DEFAULT_EXTRA_WITH_HOLDING);
        assertThat(testCompanyLocalTax.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyLocalTax.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyLocalTax.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyLocalTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyLocalTaxRepository.findAll().size();

        // Create the CompanyLocalTax with an existing ID
        companyLocalTax.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyLocalTaxMockMvc.perform(post("/api/company-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocalTax)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyLocalTax in the database
        List<CompanyLocalTax> companyLocalTaxList = companyLocalTaxRepository.findAll();
        assertThat(companyLocalTaxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyLocalTaxes() throws Exception {
        // Initialize the database
        companyLocalTaxRepository.saveAndFlush(companyLocalTax);

        // Get all the companyLocalTaxList
        restCompanyLocalTaxMockMvc.perform(get("/api/company-local-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyLocalTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].filingStatus").value(hasItem(DEFAULT_FILING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].exemptFromWithHolding").value(hasItem(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue())))
            .andExpect(jsonPath("$.[*].allowances").value(hasItem(DEFAULT_ALLOWANCES)))
            .andExpect(jsonPath("$.[*].extraWithHolding").value(hasItem(DEFAULT_EXTRA_WITH_HOLDING.doubleValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyLocalTax() throws Exception {
        // Initialize the database
        companyLocalTaxRepository.saveAndFlush(companyLocalTax);

        // Get the companyLocalTax
        restCompanyLocalTaxMockMvc.perform(get("/api/company-local-taxes/{id}", companyLocalTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyLocalTax.getId().intValue()))
            .andExpect(jsonPath("$.filingStatus").value(DEFAULT_FILING_STATUS.toString()))
            .andExpect(jsonPath("$.exemptFromWithHolding").value(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue()))
            .andExpect(jsonPath("$.allowances").value(DEFAULT_ALLOWANCES))
            .andExpect(jsonPath("$.extraWithHolding").value(DEFAULT_EXTRA_WITH_HOLDING.doubleValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyLocalTax() throws Exception {
        // Get the companyLocalTax
        restCompanyLocalTaxMockMvc.perform(get("/api/company-local-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyLocalTax() throws Exception {
        // Initialize the database
        companyLocalTaxRepository.saveAndFlush(companyLocalTax);
        int databaseSizeBeforeUpdate = companyLocalTaxRepository.findAll().size();

        // Update the companyLocalTax
        CompanyLocalTax updatedCompanyLocalTax = companyLocalTaxRepository.findOne(companyLocalTax.getId());
        // Disconnect from session so that the updates on updatedCompanyLocalTax are not directly saved in db
        em.detach(updatedCompanyLocalTax);
        updatedCompanyLocalTax
            .filingStatus(UPDATED_FILING_STATUS)
            .exemptFromWithHolding(UPDATED_EXEMPT_FROM_WITH_HOLDING)
            .allowances(UPDATED_ALLOWANCES)
            .extraWithHolding(UPDATED_EXTRA_WITH_HOLDING)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyLocalTaxMockMvc.perform(put("/api/company-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyLocalTax)))
            .andExpect(status().isOk());

        // Validate the CompanyLocalTax in the database
        List<CompanyLocalTax> companyLocalTaxList = companyLocalTaxRepository.findAll();
        assertThat(companyLocalTaxList).hasSize(databaseSizeBeforeUpdate);
        CompanyLocalTax testCompanyLocalTax = companyLocalTaxList.get(companyLocalTaxList.size() - 1);
        assertThat(testCompanyLocalTax.getFilingStatus()).isEqualTo(UPDATED_FILING_STATUS);
        assertThat(testCompanyLocalTax.isExemptFromWithHolding()).isEqualTo(UPDATED_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testCompanyLocalTax.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testCompanyLocalTax.getExtraWithHolding()).isEqualTo(UPDATED_EXTRA_WITH_HOLDING);
        assertThat(testCompanyLocalTax.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyLocalTax.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyLocalTax.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyLocalTax() throws Exception {
        int databaseSizeBeforeUpdate = companyLocalTaxRepository.findAll().size();

        // Create the CompanyLocalTax

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyLocalTaxMockMvc.perform(put("/api/company-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocalTax)))
            .andExpect(status().isCreated());

        // Validate the CompanyLocalTax in the database
        List<CompanyLocalTax> companyLocalTaxList = companyLocalTaxRepository.findAll();
        assertThat(companyLocalTaxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyLocalTax() throws Exception {
        // Initialize the database
        companyLocalTaxRepository.saveAndFlush(companyLocalTax);
        int databaseSizeBeforeDelete = companyLocalTaxRepository.findAll().size();

        // Get the companyLocalTax
        restCompanyLocalTaxMockMvc.perform(delete("/api/company-local-taxes/{id}", companyLocalTax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyLocalTax> companyLocalTaxList = companyLocalTaxRepository.findAll();
        assertThat(companyLocalTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLocalTax.class);
        CompanyLocalTax companyLocalTax1 = new CompanyLocalTax();
        companyLocalTax1.setId(1L);
        CompanyLocalTax companyLocalTax2 = new CompanyLocalTax();
        companyLocalTax2.setId(companyLocalTax1.getId());
        assertThat(companyLocalTax1).isEqualTo(companyLocalTax2);
        companyLocalTax2.setId(2L);
        assertThat(companyLocalTax1).isNotEqualTo(companyLocalTax2);
        companyLocalTax1.setId(null);
        assertThat(companyLocalTax1).isNotEqualTo(companyLocalTax2);
    }
}
