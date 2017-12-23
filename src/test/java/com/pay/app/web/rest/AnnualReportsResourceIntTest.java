package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.AnnualReports;
import com.pay.app.repository.AnnualReportsRepository;
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
 * Test class for the AnnualReportsResource REST controller.
 *
 * @see AnnualReportsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class AnnualReportsResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_ANNUAL_REPORT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ANNUAL_REPORT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ANNUAL_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_ANNUAL_REPORT = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private AnnualReportsRepository annualReportsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnnualReportsMockMvc;

    private AnnualReports annualReports;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnualReportsResource annualReportsResource = new AnnualReportsResource(annualReportsRepository);
        this.restAnnualReportsMockMvc = MockMvcBuilders.standaloneSetup(annualReportsResource)
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
    public static AnnualReports createEntity(EntityManager em) {
        AnnualReports annualReports = new AnnualReports()
            .year(DEFAULT_YEAR)
            .annualReportType(DEFAULT_ANNUAL_REPORT_TYPE)
            .annualReport(DEFAULT_ANNUAL_REPORT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return annualReports;
    }

    @Before
    public void initTest() {
        annualReports = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnualReports() throws Exception {
        int databaseSizeBeforeCreate = annualReportsRepository.findAll().size();

        // Create the AnnualReports
        restAnnualReportsMockMvc.perform(post("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReports)))
            .andExpect(status().isCreated());

        // Validate the AnnualReports in the database
        List<AnnualReports> annualReportsList = annualReportsRepository.findAll();
        assertThat(annualReportsList).hasSize(databaseSizeBeforeCreate + 1);
        AnnualReports testAnnualReports = annualReportsList.get(annualReportsList.size() - 1);
        assertThat(testAnnualReports.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAnnualReports.getAnnualReportType()).isEqualTo(DEFAULT_ANNUAL_REPORT_TYPE);
        assertThat(testAnnualReports.getAnnualReport()).isEqualTo(DEFAULT_ANNUAL_REPORT);
        assertThat(testAnnualReports.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testAnnualReports.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAnnualReports.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createAnnualReportsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annualReportsRepository.findAll().size();

        // Create the AnnualReports with an existing ID
        annualReports.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnualReportsMockMvc.perform(post("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReports)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualReports in the database
        List<AnnualReports> annualReportsList = annualReportsRepository.findAll();
        assertThat(annualReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnnualReports() throws Exception {
        // Initialize the database
        annualReportsRepository.saveAndFlush(annualReports);

        // Get all the annualReportsList
        restAnnualReportsMockMvc.perform(get("/api/annual-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annualReports.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].annualReportType").value(hasItem(DEFAULT_ANNUAL_REPORT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].annualReport").value(hasItem(DEFAULT_ANNUAL_REPORT.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getAnnualReports() throws Exception {
        // Initialize the database
        annualReportsRepository.saveAndFlush(annualReports);

        // Get the annualReports
        restAnnualReportsMockMvc.perform(get("/api/annual-reports/{id}", annualReports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annualReports.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.annualReportType").value(DEFAULT_ANNUAL_REPORT_TYPE.toString()))
            .andExpect(jsonPath("$.annualReport").value(DEFAULT_ANNUAL_REPORT.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnualReports() throws Exception {
        // Get the annualReports
        restAnnualReportsMockMvc.perform(get("/api/annual-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnualReports() throws Exception {
        // Initialize the database
        annualReportsRepository.saveAndFlush(annualReports);
        int databaseSizeBeforeUpdate = annualReportsRepository.findAll().size();

        // Update the annualReports
        AnnualReports updatedAnnualReports = annualReportsRepository.findOne(annualReports.getId());
        // Disconnect from session so that the updates on updatedAnnualReports are not directly saved in db
        em.detach(updatedAnnualReports);
        updatedAnnualReports
            .year(UPDATED_YEAR)
            .annualReportType(UPDATED_ANNUAL_REPORT_TYPE)
            .annualReport(UPDATED_ANNUAL_REPORT)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restAnnualReportsMockMvc.perform(put("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnualReports)))
            .andExpect(status().isOk());

        // Validate the AnnualReports in the database
        List<AnnualReports> annualReportsList = annualReportsRepository.findAll();
        assertThat(annualReportsList).hasSize(databaseSizeBeforeUpdate);
        AnnualReports testAnnualReports = annualReportsList.get(annualReportsList.size() - 1);
        assertThat(testAnnualReports.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAnnualReports.getAnnualReportType()).isEqualTo(UPDATED_ANNUAL_REPORT_TYPE);
        assertThat(testAnnualReports.getAnnualReport()).isEqualTo(UPDATED_ANNUAL_REPORT);
        assertThat(testAnnualReports.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testAnnualReports.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAnnualReports.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnualReports() throws Exception {
        int databaseSizeBeforeUpdate = annualReportsRepository.findAll().size();

        // Create the AnnualReports

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnnualReportsMockMvc.perform(put("/api/annual-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualReports)))
            .andExpect(status().isCreated());

        // Validate the AnnualReports in the database
        List<AnnualReports> annualReportsList = annualReportsRepository.findAll();
        assertThat(annualReportsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnnualReports() throws Exception {
        // Initialize the database
        annualReportsRepository.saveAndFlush(annualReports);
        int databaseSizeBeforeDelete = annualReportsRepository.findAll().size();

        // Get the annualReports
        restAnnualReportsMockMvc.perform(delete("/api/annual-reports/{id}", annualReports.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AnnualReports> annualReportsList = annualReportsRepository.findAll();
        assertThat(annualReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualReports.class);
        AnnualReports annualReports1 = new AnnualReports();
        annualReports1.setId(1L);
        AnnualReports annualReports2 = new AnnualReports();
        annualReports2.setId(annualReports1.getId());
        assertThat(annualReports1).isEqualTo(annualReports2);
        annualReports2.setId(2L);
        assertThat(annualReports1).isNotEqualTo(annualReports2);
        annualReports1.setId(null);
        assertThat(annualReports1).isNotEqualTo(annualReports2);
    }
}
