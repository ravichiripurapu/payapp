package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyTaxInfo;
import com.pay.app.repository.CompanyTaxInfoRepository;
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
 * Test class for the CompanyTaxInfoResource REST controller.
 *
 * @see CompanyTaxInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyTaxInfoResourceIntTest {

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
    private CompanyTaxInfoRepository companyTaxInfoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyTaxInfoMockMvc;

    private CompanyTaxInfo companyTaxInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyTaxInfoResource companyTaxInfoResource = new CompanyTaxInfoResource(companyTaxInfoRepository);
        this.restCompanyTaxInfoMockMvc = MockMvcBuilders.standaloneSetup(companyTaxInfoResource)
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
    public static CompanyTaxInfo createEntity(EntityManager em) {
        CompanyTaxInfo companyTaxInfo = new CompanyTaxInfo()
            .stateCode(DEFAULT_STATE_CODE)
            .taxId(DEFAULT_TAX_ID)
            .rate(DEFAULT_RATE)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .status(DEFAULT_STATUS)
            .depositFrequency(DEFAULT_DEPOSIT_FREQUENCY)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyTaxInfo;
    }

    @Before
    public void initTest() {
        companyTaxInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyTaxInfo() throws Exception {
        int databaseSizeBeforeCreate = companyTaxInfoRepository.findAll().size();

        // Create the CompanyTaxInfo
        restCompanyTaxInfoMockMvc.perform(post("/api/company-tax-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTaxInfo)))
            .andExpect(status().isCreated());

        // Validate the CompanyTaxInfo in the database
        List<CompanyTaxInfo> companyTaxInfoList = companyTaxInfoRepository.findAll();
        assertThat(companyTaxInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyTaxInfo testCompanyTaxInfo = companyTaxInfoList.get(companyTaxInfoList.size() - 1);
        assertThat(testCompanyTaxInfo.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testCompanyTaxInfo.getTaxId()).isEqualTo(DEFAULT_TAX_ID);
        assertThat(testCompanyTaxInfo.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCompanyTaxInfo.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testCompanyTaxInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCompanyTaxInfo.getDepositFrequency()).isEqualTo(DEFAULT_DEPOSIT_FREQUENCY);
        assertThat(testCompanyTaxInfo.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyTaxInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyTaxInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyTaxInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyTaxInfoRepository.findAll().size();

        // Create the CompanyTaxInfo with an existing ID
        companyTaxInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyTaxInfoMockMvc.perform(post("/api/company-tax-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTaxInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyTaxInfo in the database
        List<CompanyTaxInfo> companyTaxInfoList = companyTaxInfoRepository.findAll();
        assertThat(companyTaxInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyTaxInfoes() throws Exception {
        // Initialize the database
        companyTaxInfoRepository.saveAndFlush(companyTaxInfo);

        // Get all the companyTaxInfoList
        restCompanyTaxInfoMockMvc.perform(get("/api/company-tax-info?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyTaxInfo.getId().intValue())))
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
    public void getCompanyTaxInfo() throws Exception {
        // Initialize the database
        companyTaxInfoRepository.saveAndFlush(companyTaxInfo);

        // Get the companyTaxInfo
        restCompanyTaxInfoMockMvc.perform(get("/api/company-tax-info/{id}", companyTaxInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyTaxInfo.getId().intValue()))
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
    public void getNonExistingCompanyTaxInfo() throws Exception {
        // Get the companyTaxInfo
        restCompanyTaxInfoMockMvc.perform(get("/api/company-tax-info/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyTaxInfo() throws Exception {
        // Initialize the database
        companyTaxInfoRepository.saveAndFlush(companyTaxInfo);
        int databaseSizeBeforeUpdate = companyTaxInfoRepository.findAll().size();

        // Update the companyTaxInfo
        CompanyTaxInfo updatedCompanyTaxInfo = companyTaxInfoRepository.findOne(companyTaxInfo.getId());
        // Disconnect from session so that the updates on updatedCompanyTaxInfo are not directly saved in db
        em.detach(updatedCompanyTaxInfo);
        updatedCompanyTaxInfo            .stateCode(UPDATED_STATE_CODE)
            .taxId(UPDATED_TAX_ID)
            .rate(UPDATED_RATE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .status(UPDATED_STATUS)
            .depositFrequency(UPDATED_DEPOSIT_FREQUENCY)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyTaxInfoMockMvc.perform(put("/api/company-tax-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyTaxInfo)))
            .andExpect(status().isOk());

        // Validate the CompanyTaxInfo in the database
        List<CompanyTaxInfo> companyTaxInfoList = companyTaxInfoRepository.findAll();
        assertThat(companyTaxInfoList).hasSize(databaseSizeBeforeUpdate);
        CompanyTaxInfo testCompanyTaxInfo = companyTaxInfoList.get(companyTaxInfoList.size() - 1);
        assertThat(testCompanyTaxInfo.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testCompanyTaxInfo.getTaxId()).isEqualTo(UPDATED_TAX_ID);
        assertThat(testCompanyTaxInfo.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCompanyTaxInfo.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testCompanyTaxInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCompanyTaxInfo.getDepositFrequency()).isEqualTo(UPDATED_DEPOSIT_FREQUENCY);
        assertThat(testCompanyTaxInfo.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyTaxInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyTaxInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyTaxInfo() throws Exception {
        int databaseSizeBeforeUpdate = companyTaxInfoRepository.findAll().size();

        // Create the CompanyTaxInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyTaxInfoMockMvc.perform(put("/api/company-tax-info")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyTaxInfo)))
            .andExpect(status().isCreated());

        // Validate the CompanyTaxInfo in the database
        List<CompanyTaxInfo> companyTaxInfoList = companyTaxInfoRepository.findAll();
        assertThat(companyTaxInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyTaxInfo() throws Exception {
        // Initialize the database
        companyTaxInfoRepository.saveAndFlush(companyTaxInfo);
        int databaseSizeBeforeDelete = companyTaxInfoRepository.findAll().size();

        // Get the companyTaxInfo
        restCompanyTaxInfoMockMvc.perform(delete("/api/company-tax-info/{id}", companyTaxInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyTaxInfo> companyTaxInfoList = companyTaxInfoRepository.findAll();
        assertThat(companyTaxInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyTaxInfo.class);
        CompanyTaxInfo companyTaxInfo1 = new CompanyTaxInfo();
        companyTaxInfo1.setId(1L);
        CompanyTaxInfo companyTaxInfo2 = new CompanyTaxInfo();
        companyTaxInfo2.setId(companyTaxInfo1.getId());
        assertThat(companyTaxInfo1).isEqualTo(companyTaxInfo2);
        companyTaxInfo2.setId(2L);
        assertThat(companyTaxInfo1).isNotEqualTo(companyTaxInfo2);
        companyTaxInfo1.setId(null);
        assertThat(companyTaxInfo1).isNotEqualTo(companyTaxInfo2);
    }
}
