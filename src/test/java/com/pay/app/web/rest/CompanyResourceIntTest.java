package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.Company;
import com.pay.app.repository.CompanyRepository;
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
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyResourceIntTest {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_FEIN = "AAAAAAAAAA";
    private static final String UPDATED_FEIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_FULL_TIME_HOURS = 1;
    private static final Integer UPDATED_DEFAULT_FULL_TIME_HOURS = 2;

    private static final Integer DEFAULT_DEFAULT_PART_TIME_HOURS = 1;
    private static final Integer UPDATED_DEFAULT_PART_TIME_HOURS = 2;

    private static final Integer DEFAULT_DEFAULT_TEMPORARY_HOURS = 1;
    private static final Integer UPDATED_DEFAULT_TEMPORARY_HOURS = 2;

    private static final String DEFAULT_PAYROLL_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_PAYROLL_FREQUENCY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyMockMvc;

    private Company company;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyResource companyResource = new CompanyResource(companyRepository);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource)
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
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyName(DEFAULT_COMPANY_NAME)
            .website(DEFAULT_WEBSITE)
            .fein(DEFAULT_FEIN)
            .defaultFullTimeHours(DEFAULT_DEFAULT_FULL_TIME_HOURS)
            .defaultPartTimeHours(DEFAULT_DEFAULT_PART_TIME_HOURS)
            .defaultTemporaryHours(DEFAULT_DEFAULT_TEMPORARY_HOURS)
            .payrollFrequency(DEFAULT_PAYROLL_FREQUENCY)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return company;
    }

    @Before
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCompany.getFein()).isEqualTo(DEFAULT_FEIN);
        assertThat(testCompany.getDefaultFullTimeHours()).isEqualTo(DEFAULT_DEFAULT_FULL_TIME_HOURS);
        assertThat(testCompany.getDefaultPartTimeHours()).isEqualTo(DEFAULT_DEFAULT_PART_TIME_HOURS);
        assertThat(testCompany.getDefaultTemporaryHours()).isEqualTo(DEFAULT_DEFAULT_TEMPORARY_HOURS);
        assertThat(testCompany.getPayrollFrequency()).isEqualTo(DEFAULT_PAYROLL_FREQUENCY);
        assertThat(testCompany.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompany.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].fein").value(hasItem(DEFAULT_FEIN.toString())))
            .andExpect(jsonPath("$.[*].defaultFullTimeHours").value(hasItem(DEFAULT_DEFAULT_FULL_TIME_HOURS)))
            .andExpect(jsonPath("$.[*].defaultPartTimeHours").value(hasItem(DEFAULT_DEFAULT_PART_TIME_HOURS)))
            .andExpect(jsonPath("$.[*].defaultTemporaryHours").value(hasItem(DEFAULT_DEFAULT_TEMPORARY_HOURS)))
            .andExpect(jsonPath("$.[*].payrollFrequency").value(hasItem(DEFAULT_PAYROLL_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.fein").value(DEFAULT_FEIN.toString()))
            .andExpect(jsonPath("$.defaultFullTimeHours").value(DEFAULT_DEFAULT_FULL_TIME_HOURS))
            .andExpect(jsonPath("$.defaultPartTimeHours").value(DEFAULT_DEFAULT_PART_TIME_HOURS))
            .andExpect(jsonPath("$.defaultTemporaryHours").value(DEFAULT_DEFAULT_TEMPORARY_HOURS))
            .andExpect(jsonPath("$.payrollFrequency").value(DEFAULT_PAYROLL_FREQUENCY.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findOne(company.getId());
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyName(UPDATED_COMPANY_NAME)
            .website(UPDATED_WEBSITE)
            .fein(UPDATED_FEIN)
            .defaultFullTimeHours(UPDATED_DEFAULT_FULL_TIME_HOURS)
            .defaultPartTimeHours(UPDATED_DEFAULT_PART_TIME_HOURS)
            .defaultTemporaryHours(UPDATED_DEFAULT_TEMPORARY_HOURS)
            .payrollFrequency(UPDATED_PAYROLL_FREQUENCY)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompany)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCompany.getFein()).isEqualTo(UPDATED_FEIN);
        assertThat(testCompany.getDefaultFullTimeHours()).isEqualTo(UPDATED_DEFAULT_FULL_TIME_HOURS);
        assertThat(testCompany.getDefaultPartTimeHours()).isEqualTo(UPDATED_DEFAULT_PART_TIME_HOURS);
        assertThat(testCompany.getDefaultTemporaryHours()).isEqualTo(UPDATED_DEFAULT_TEMPORARY_HOURS);
        assertThat(testCompany.getPayrollFrequency()).isEqualTo(UPDATED_PAYROLL_FREQUENCY);
        assertThat(testCompany.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompany.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Get the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Company.class);
        Company company1 = new Company();
        company1.setId(1L);
        Company company2 = new Company();
        company2.setId(company1.getId());
        assertThat(company1).isEqualTo(company2);
        company2.setId(2L);
        assertThat(company1).isNotEqualTo(company2);
        company1.setId(null);
        assertThat(company1).isNotEqualTo(company2);
    }
}
