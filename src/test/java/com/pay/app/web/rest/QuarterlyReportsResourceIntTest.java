package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.QuarterlyReports;
import com.pay.app.repository.QuarterlyReportsRepository;
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
 * Test class for the QuarterlyReportsResource REST controller.
 *
 * @see QuarterlyReportsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class QuarterlyReportsResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_QUARTER_NUMBER = 1;
    private static final Integer UPDATED_QUARTER_NUMBER = 2;

    private static final String DEFAULT_QUATERLY_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_QUATERLY_REPORT = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private QuarterlyReportsRepository quarterlyReportsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuarterlyReportsMockMvc;

    private QuarterlyReports quarterlyReports;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuarterlyReportsResource quarterlyReportsResource = new QuarterlyReportsResource(quarterlyReportsRepository);
        this.restQuarterlyReportsMockMvc = MockMvcBuilders.standaloneSetup(quarterlyReportsResource)
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
    public static QuarterlyReports createEntity(EntityManager em) {
        QuarterlyReports quarterlyReports = new QuarterlyReports()
            .year(DEFAULT_YEAR)
            .quarterNumber(DEFAULT_QUARTER_NUMBER)
            .quarterlyReportLocation(DEFAULT_QUATERLY_REPORT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return quarterlyReports;
    }

    @Before
    public void initTest() {
        quarterlyReports = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuarterlyReports() throws Exception {
        int databaseSizeBeforeCreate = quarterlyReportsRepository.findAll().size();

        // Create the QuarterlyReports
        restQuarterlyReportsMockMvc.perform(post("/api/quarterly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarterlyReports)))
            .andExpect(status().isCreated());

        // Validate the QuarterlyReports in the database
        List<QuarterlyReports> quarterlyReportsList = quarterlyReportsRepository.findAll();
        assertThat(quarterlyReportsList).hasSize(databaseSizeBeforeCreate + 1);
        QuarterlyReports testQuarterlyReports = quarterlyReportsList.get(quarterlyReportsList.size() - 1);
        assertThat(testQuarterlyReports.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testQuarterlyReports.getQuarterNumber()).isEqualTo(DEFAULT_QUARTER_NUMBER);
        assertThat(testQuarterlyReports.getQuarterlyReportLocation()).isEqualTo(DEFAULT_QUATERLY_REPORT);
        assertThat(testQuarterlyReports.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testQuarterlyReports.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testQuarterlyReports.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createQuarterlyReportsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quarterlyReportsRepository.findAll().size();

        // Create the QuarterlyReports with an existing ID
        quarterlyReports.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuarterlyReportsMockMvc.perform(post("/api/quarterly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarterlyReports)))
            .andExpect(status().isBadRequest());

        // Validate the QuarterlyReports in the database
        List<QuarterlyReports> quarterlyReportsList = quarterlyReportsRepository.findAll();
        assertThat(quarterlyReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuarterlyReports() throws Exception {
        // Initialize the database
        quarterlyReportsRepository.saveAndFlush(quarterlyReports);

        // Get all the quarterlyReportsList
        restQuarterlyReportsMockMvc.perform(get("/api/quarterly-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quarterlyReports.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].quarterNumber").value(hasItem(DEFAULT_QUARTER_NUMBER)))
            .andExpect(jsonPath("$.[*].quaterlyReport").value(hasItem(DEFAULT_QUATERLY_REPORT.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getQuarterlyReports() throws Exception {
        // Initialize the database
        quarterlyReportsRepository.saveAndFlush(quarterlyReports);

        // Get the quarterlyReports
        restQuarterlyReportsMockMvc.perform(get("/api/quarterly-reports/{id}", quarterlyReports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quarterlyReports.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.quarterNumber").value(DEFAULT_QUARTER_NUMBER))
            .andExpect(jsonPath("$.quaterlyReport").value(DEFAULT_QUATERLY_REPORT.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuarterlyReports() throws Exception {
        // Get the quarterlyReports
        restQuarterlyReportsMockMvc.perform(get("/api/quarterly-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuarterlyReports() throws Exception {
        // Initialize the database
        quarterlyReportsRepository.saveAndFlush(quarterlyReports);
        int databaseSizeBeforeUpdate = quarterlyReportsRepository.findAll().size();

        // Update the quarterlyReports
        QuarterlyReports updatedQuarterlyReports = quarterlyReportsRepository.findOne(quarterlyReports.getId());
        // Disconnect from session so that the updates on updatedQuarterlyReports are not directly saved in db
        em.detach(updatedQuarterlyReports);
        updatedQuarterlyReports
            .year(UPDATED_YEAR)
            .quarterNumber(UPDATED_QUARTER_NUMBER)
            .quarterlyReportLocation(UPDATED_QUATERLY_REPORT)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restQuarterlyReportsMockMvc.perform(put("/api/quarterly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuarterlyReports)))
            .andExpect(status().isOk());

        // Validate the QuarterlyReports in the database
        List<QuarterlyReports> quarterlyReportsList = quarterlyReportsRepository.findAll();
        assertThat(quarterlyReportsList).hasSize(databaseSizeBeforeUpdate);
        QuarterlyReports testQuarterlyReports = quarterlyReportsList.get(quarterlyReportsList.size() - 1);
        assertThat(testQuarterlyReports.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testQuarterlyReports.getQuarterNumber()).isEqualTo(UPDATED_QUARTER_NUMBER);
        assertThat(testQuarterlyReports.getQuarterlyReportLocation()).isEqualTo(UPDATED_QUATERLY_REPORT);
        assertThat(testQuarterlyReports.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testQuarterlyReports.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testQuarterlyReports.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingQuarterlyReports() throws Exception {
        int databaseSizeBeforeUpdate = quarterlyReportsRepository.findAll().size();

        // Create the QuarterlyReports

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuarterlyReportsMockMvc.perform(put("/api/quarterly-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quarterlyReports)))
            .andExpect(status().isCreated());

        // Validate the QuarterlyReports in the database
        List<QuarterlyReports> quarterlyReportsList = quarterlyReportsRepository.findAll();
        assertThat(quarterlyReportsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuarterlyReports() throws Exception {
        // Initialize the database
        quarterlyReportsRepository.saveAndFlush(quarterlyReports);
        int databaseSizeBeforeDelete = quarterlyReportsRepository.findAll().size();

        // Get the quarterlyReports
        restQuarterlyReportsMockMvc.perform(delete("/api/quarterly-reports/{id}", quarterlyReports.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuarterlyReports> quarterlyReportsList = quarterlyReportsRepository.findAll();
        assertThat(quarterlyReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuarterlyReports.class);
        QuarterlyReports quarterlyReports1 = new QuarterlyReports();
        quarterlyReports1.setId(1L);
        QuarterlyReports quarterlyReports2 = new QuarterlyReports();
        quarterlyReports2.setId(quarterlyReports1.getId());
        assertThat(quarterlyReports1).isEqualTo(quarterlyReports2);
        quarterlyReports2.setId(2L);
        assertThat(quarterlyReports1).isNotEqualTo(quarterlyReports2);
        quarterlyReports1.setId(null);
        assertThat(quarterlyReports1).isNotEqualTo(quarterlyReports2);
    }
}
