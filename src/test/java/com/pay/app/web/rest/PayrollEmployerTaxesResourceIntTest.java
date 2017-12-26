package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollEmployerTaxes;
import com.pay.app.repository.PayrollEmployerTaxesRepository;
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
 * Test class for the PayrollEmployerTaxesResource REST controller.
 *
 * @see PayrollEmployerTaxesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollEmployerTaxesResourceIntTest {

    private static final String DEFAULT_TAX_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TAX_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_PERIOD_AMOUNT = 1F;
    private static final Float UPDATED_PERIOD_AMOUNT = 2F;

    private static final Float DEFAULT_YTD_AMOUNT = 1F;
    private static final Float UPDATED_YTD_AMOUNT = 2F;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private PayrollEmployerTaxesRepository payrollEmployerTaxesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollEmployerTaxesMockMvc;

    private PayrollEmployerTaxes payrollEmployerTaxes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollEmployerTaxesResource payrollEmployerTaxesResource = new PayrollEmployerTaxesResource(payrollEmployerTaxesRepository);
        this.restPayrollEmployerTaxesMockMvc = MockMvcBuilders.standaloneSetup(payrollEmployerTaxesResource)
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
    public static PayrollEmployerTaxes createEntity(EntityManager em) {
        PayrollEmployerTaxes payrollEmployerTaxes = new PayrollEmployerTaxes()
            .taxCode(DEFAULT_TAX_CODE)
            .periodAmount(DEFAULT_PERIOD_AMOUNT)
            .ytdAmount(DEFAULT_YTD_AMOUNT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return payrollEmployerTaxes;
    }

    @Before
    public void initTest() {
        payrollEmployerTaxes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollEmployerTaxes() throws Exception {
        int databaseSizeBeforeCreate = payrollEmployerTaxesRepository.findAll().size();

        // Create the PayrollEmployerTaxes
        restPayrollEmployerTaxesMockMvc.perform(post("/api/payroll-employer-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployerTaxes)))
            .andExpect(status().isCreated());

        // Validate the PayrollEmployerTaxes in the database
        List<PayrollEmployerTaxes> payrollEmployerTaxesList = payrollEmployerTaxesRepository.findAll();
        assertThat(payrollEmployerTaxesList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollEmployerTaxes testPayrollEmployerTaxes = payrollEmployerTaxesList.get(payrollEmployerTaxesList.size() - 1);
        assertThat(testPayrollEmployerTaxes.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testPayrollEmployerTaxes.getPeriodAmount()).isEqualTo(DEFAULT_PERIOD_AMOUNT);
        assertThat(testPayrollEmployerTaxes.getYtdAmount()).isEqualTo(DEFAULT_YTD_AMOUNT);
        assertThat(testPayrollEmployerTaxes.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testPayrollEmployerTaxes.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testPayrollEmployerTaxes.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollEmployerTaxes.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollEmployerTaxesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollEmployerTaxesRepository.findAll().size();

        // Create the PayrollEmployerTaxes with an existing ID
        payrollEmployerTaxes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollEmployerTaxesMockMvc.perform(post("/api/payroll-employer-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployerTaxes)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollEmployerTaxes in the database
        List<PayrollEmployerTaxes> payrollEmployerTaxesList = payrollEmployerTaxesRepository.findAll();
        assertThat(payrollEmployerTaxesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollEmployerTaxes() throws Exception {
        // Initialize the database
        payrollEmployerTaxesRepository.saveAndFlush(payrollEmployerTaxes);

        // Get all the payrollEmployerTaxesList
        restPayrollEmployerTaxesMockMvc.perform(get("/api/payroll-employer-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollEmployerTaxes.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxCode").value(hasItem(DEFAULT_TAX_CODE.toString())))
            .andExpect(jsonPath("$.[*].periodAmount").value(hasItem(DEFAULT_PERIOD_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].ytdAmount").value(hasItem(DEFAULT_YTD_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getPayrollEmployerTaxes() throws Exception {
        // Initialize the database
        payrollEmployerTaxesRepository.saveAndFlush(payrollEmployerTaxes);

        // Get the payrollEmployerTaxes
        restPayrollEmployerTaxesMockMvc.perform(get("/api/payroll-employer-taxes/{id}", payrollEmployerTaxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollEmployerTaxes.getId().intValue()))
            .andExpect(jsonPath("$.taxCode").value(DEFAULT_TAX_CODE.toString()))
            .andExpect(jsonPath("$.periodAmount").value(DEFAULT_PERIOD_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.ytdAmount").value(DEFAULT_YTD_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollEmployerTaxes() throws Exception {
        // Get the payrollEmployerTaxes
        restPayrollEmployerTaxesMockMvc.perform(get("/api/payroll-employer-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollEmployerTaxes() throws Exception {
        // Initialize the database
        payrollEmployerTaxesRepository.saveAndFlush(payrollEmployerTaxes);
        int databaseSizeBeforeUpdate = payrollEmployerTaxesRepository.findAll().size();

        // Update the payrollEmployerTaxes
        PayrollEmployerTaxes updatedPayrollEmployerTaxes = payrollEmployerTaxesRepository.findOne(payrollEmployerTaxes.getId());
        // Disconnect from session so that the updates on updatedPayrollEmployerTaxes are not directly saved in db
        em.detach(updatedPayrollEmployerTaxes);
        updatedPayrollEmployerTaxes
            .taxCode(UPDATED_TAX_CODE)
            .periodAmount(UPDATED_PERIOD_AMOUNT)
            .ytdAmount(UPDATED_YTD_AMOUNT)
            .companyCode(UPDATED_COMPANY_CODE)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollEmployerTaxesMockMvc.perform(put("/api/payroll-employer-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollEmployerTaxes)))
            .andExpect(status().isOk());

        // Validate the PayrollEmployerTaxes in the database
        List<PayrollEmployerTaxes> payrollEmployerTaxesList = payrollEmployerTaxesRepository.findAll();
        assertThat(payrollEmployerTaxesList).hasSize(databaseSizeBeforeUpdate);
        PayrollEmployerTaxes testPayrollEmployerTaxes = payrollEmployerTaxesList.get(payrollEmployerTaxesList.size() - 1);
        assertThat(testPayrollEmployerTaxes.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testPayrollEmployerTaxes.getPeriodAmount()).isEqualTo(UPDATED_PERIOD_AMOUNT);
        assertThat(testPayrollEmployerTaxes.getYtdAmount()).isEqualTo(UPDATED_YTD_AMOUNT);
        assertThat(testPayrollEmployerTaxes.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testPayrollEmployerTaxes.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testPayrollEmployerTaxes.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollEmployerTaxes.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollEmployerTaxes() throws Exception {
        int databaseSizeBeforeUpdate = payrollEmployerTaxesRepository.findAll().size();

        // Create the PayrollEmployerTaxes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollEmployerTaxesMockMvc.perform(put("/api/payroll-employer-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployerTaxes)))
            .andExpect(status().isCreated());

        // Validate the PayrollEmployerTaxes in the database
        List<PayrollEmployerTaxes> payrollEmployerTaxesList = payrollEmployerTaxesRepository.findAll();
        assertThat(payrollEmployerTaxesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollEmployerTaxes() throws Exception {
        // Initialize the database
        payrollEmployerTaxesRepository.saveAndFlush(payrollEmployerTaxes);
        int databaseSizeBeforeDelete = payrollEmployerTaxesRepository.findAll().size();

        // Get the payrollEmployerTaxes
        restPayrollEmployerTaxesMockMvc.perform(delete("/api/payroll-employer-taxes/{id}", payrollEmployerTaxes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollEmployerTaxes> payrollEmployerTaxesList = payrollEmployerTaxesRepository.findAll();
        assertThat(payrollEmployerTaxesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollEmployerTaxes.class);
        PayrollEmployerTaxes payrollEmployerTaxes1 = new PayrollEmployerTaxes();
        payrollEmployerTaxes1.setId(1L);
        PayrollEmployerTaxes payrollEmployerTaxes2 = new PayrollEmployerTaxes();
        payrollEmployerTaxes2.setId(payrollEmployerTaxes1.getId());
        assertThat(payrollEmployerTaxes1).isEqualTo(payrollEmployerTaxes2);
        payrollEmployerTaxes2.setId(2L);
        assertThat(payrollEmployerTaxes1).isNotEqualTo(payrollEmployerTaxes2);
        payrollEmployerTaxes1.setId(null);
        assertThat(payrollEmployerTaxes1).isNotEqualTo(payrollEmployerTaxes2);
    }
}
