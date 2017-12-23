package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.W2Reports;
import com.pay.app.repository.W2ReportsRepository;
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
 * Test class for the W2ReportsResource REST controller.
 *
 * @see W2ReportsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class W2ReportsResourceIntTest {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_W_2_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_W_2_REPORT = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private W2ReportsRepository w2ReportsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restW2ReportsMockMvc;

    private W2Reports w2Reports;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final W2ReportsResource w2ReportsResource = new W2ReportsResource(w2ReportsRepository);
        this.restW2ReportsMockMvc = MockMvcBuilders.standaloneSetup(w2ReportsResource)
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
    public static W2Reports createEntity(EntityManager em) {
        W2Reports w2Reports = new W2Reports()
            .year(DEFAULT_YEAR)
            .w2Report(DEFAULT_W_2_REPORT)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return w2Reports;
    }

    @Before
    public void initTest() {
        w2Reports = createEntity(em);
    }

    @Test
    @Transactional
    public void createW2Reports() throws Exception {
        int databaseSizeBeforeCreate = w2ReportsRepository.findAll().size();

        // Create the W2Reports
        restW2ReportsMockMvc.perform(post("/api/w-2-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(w2Reports)))
            .andExpect(status().isCreated());

        // Validate the W2Reports in the database
        List<W2Reports> w2ReportsList = w2ReportsRepository.findAll();
        assertThat(w2ReportsList).hasSize(databaseSizeBeforeCreate + 1);
        W2Reports testW2Reports = w2ReportsList.get(w2ReportsList.size() - 1);
        assertThat(testW2Reports.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testW2Reports.getw2Report()).isEqualTo(DEFAULT_W_2_REPORT);
        assertThat(testW2Reports.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testW2Reports.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testW2Reports.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testW2Reports.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createW2ReportsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = w2ReportsRepository.findAll().size();

        // Create the W2Reports with an existing ID
        w2Reports.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restW2ReportsMockMvc.perform(post("/api/w-2-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(w2Reports)))
            .andExpect(status().isBadRequest());

        // Validate the W2Reports in the database
        List<W2Reports> w2ReportsList = w2ReportsRepository.findAll();
        assertThat(w2ReportsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllW2Reports() throws Exception {
        // Initialize the database
        w2ReportsRepository.saveAndFlush(w2Reports);

        // Get all the w2ReportsList
        restW2ReportsMockMvc.perform(get("/api/w-2-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(w2Reports.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].w2Report").value(hasItem(DEFAULT_W_2_REPORT.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getW2Reports() throws Exception {
        // Initialize the database
        w2ReportsRepository.saveAndFlush(w2Reports);

        // Get the w2Reports
        restW2ReportsMockMvc.perform(get("/api/w-2-reports/{id}", w2Reports.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(w2Reports.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.w2Report").value(DEFAULT_W_2_REPORT.toString()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingW2Reports() throws Exception {
        // Get the w2Reports
        restW2ReportsMockMvc.perform(get("/api/w-2-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateW2Reports() throws Exception {
        // Initialize the database
        w2ReportsRepository.saveAndFlush(w2Reports);
        int databaseSizeBeforeUpdate = w2ReportsRepository.findAll().size();

        // Update the w2Reports
        W2Reports updatedW2Reports = w2ReportsRepository.findOne(w2Reports.getId());
        // Disconnect from session so that the updates on updatedW2Reports are not directly saved in db
        em.detach(updatedW2Reports);
        updatedW2Reports
            .year(UPDATED_YEAR)
            .w2Report(UPDATED_W_2_REPORT)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restW2ReportsMockMvc.perform(put("/api/w-2-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedW2Reports)))
            .andExpect(status().isOk());

        // Validate the W2Reports in the database
        List<W2Reports> w2ReportsList = w2ReportsRepository.findAll();
        assertThat(w2ReportsList).hasSize(databaseSizeBeforeUpdate);
        W2Reports testW2Reports = w2ReportsList.get(w2ReportsList.size() - 1);
        assertThat(testW2Reports.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testW2Reports.getw2Report()).isEqualTo(UPDATED_W_2_REPORT);
        assertThat(testW2Reports.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testW2Reports.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testW2Reports.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testW2Reports.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingW2Reports() throws Exception {
        int databaseSizeBeforeUpdate = w2ReportsRepository.findAll().size();

        // Create the W2Reports

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restW2ReportsMockMvc.perform(put("/api/w-2-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(w2Reports)))
            .andExpect(status().isCreated());

        // Validate the W2Reports in the database
        List<W2Reports> w2ReportsList = w2ReportsRepository.findAll();
        assertThat(w2ReportsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteW2Reports() throws Exception {
        // Initialize the database
        w2ReportsRepository.saveAndFlush(w2Reports);
        int databaseSizeBeforeDelete = w2ReportsRepository.findAll().size();

        // Get the w2Reports
        restW2ReportsMockMvc.perform(delete("/api/w-2-reports/{id}", w2Reports.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<W2Reports> w2ReportsList = w2ReportsRepository.findAll();
        assertThat(w2ReportsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(W2Reports.class);
        W2Reports w2Reports1 = new W2Reports();
        w2Reports1.setId(1L);
        W2Reports w2Reports2 = new W2Reports();
        w2Reports2.setId(w2Reports1.getId());
        assertThat(w2Reports1).isEqualTo(w2Reports2);
        w2Reports2.setId(2L);
        assertThat(w2Reports1).isNotEqualTo(w2Reports2);
        w2Reports1.setId(null);
        assertThat(w2Reports1).isNotEqualTo(w2Reports2);
    }
}
