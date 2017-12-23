package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollEmployeeTaxes;
import com.pay.app.repository.PayrollEmployeeTaxesRepository;
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
 * Test class for the PayrollEmployeeTaxesResource REST controller.
 *
 * @see PayrollEmployeeTaxesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollEmployeeTaxesResourceIntTest {

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
    private PayrollEmployeeTaxesRepository payrollEmployeeTaxesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollEmployeeTaxesMockMvc;

    private PayrollEmployeeTaxes payrollEmployeeTaxes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollEmployeeTaxesResource payrollEmployeeTaxesResource = new PayrollEmployeeTaxesResource(payrollEmployeeTaxesRepository);
        this.restPayrollEmployeeTaxesMockMvc = MockMvcBuilders.standaloneSetup(payrollEmployeeTaxesResource)
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
    public static PayrollEmployeeTaxes createEntity(EntityManager em) {
        PayrollEmployeeTaxes payrollEmployeeTaxes = new PayrollEmployeeTaxes()
            .taxCode(DEFAULT_TAX_CODE)
            .periodAmount(DEFAULT_PERIOD_AMOUNT)
            .ytdAmount(DEFAULT_YTD_AMOUNT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return payrollEmployeeTaxes;
    }

    @Before
    public void initTest() {
        payrollEmployeeTaxes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollEmployeeTaxes() throws Exception {
        int databaseSizeBeforeCreate = payrollEmployeeTaxesRepository.findAll().size();

        // Create the PayrollEmployeeTaxes
        restPayrollEmployeeTaxesMockMvc.perform(post("/api/payroll-employee-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployeeTaxes)))
            .andExpect(status().isCreated());

        // Validate the PayrollEmployeeTaxes in the database
        List<PayrollEmployeeTaxes> payrollEmployeeTaxesList = payrollEmployeeTaxesRepository.findAll();
        assertThat(payrollEmployeeTaxesList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollEmployeeTaxes testPayrollEmployeeTaxes = payrollEmployeeTaxesList.get(payrollEmployeeTaxesList.size() - 1);
        assertThat(testPayrollEmployeeTaxes.getTaxCode()).isEqualTo(DEFAULT_TAX_CODE);
        assertThat(testPayrollEmployeeTaxes.getPeriodAmount()).isEqualTo(DEFAULT_PERIOD_AMOUNT);
        assertThat(testPayrollEmployeeTaxes.getYtdAmount()).isEqualTo(DEFAULT_YTD_AMOUNT);
        assertThat(testPayrollEmployeeTaxes.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testPayrollEmployeeTaxes.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testPayrollEmployeeTaxes.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollEmployeeTaxes.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollEmployeeTaxesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollEmployeeTaxesRepository.findAll().size();

        // Create the PayrollEmployeeTaxes with an existing ID
        payrollEmployeeTaxes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollEmployeeTaxesMockMvc.perform(post("/api/payroll-employee-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployeeTaxes)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollEmployeeTaxes in the database
        List<PayrollEmployeeTaxes> payrollEmployeeTaxesList = payrollEmployeeTaxesRepository.findAll();
        assertThat(payrollEmployeeTaxesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollEmployeeTaxes() throws Exception {
        // Initialize the database
        payrollEmployeeTaxesRepository.saveAndFlush(payrollEmployeeTaxes);

        // Get all the payrollEmployeeTaxesList
        restPayrollEmployeeTaxesMockMvc.perform(get("/api/payroll-employee-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollEmployeeTaxes.getId().intValue())))
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
    public void getPayrollEmployeeTaxes() throws Exception {
        // Initialize the database
        payrollEmployeeTaxesRepository.saveAndFlush(payrollEmployeeTaxes);

        // Get the payrollEmployeeTaxes
        restPayrollEmployeeTaxesMockMvc.perform(get("/api/payroll-employee-taxes/{id}", payrollEmployeeTaxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollEmployeeTaxes.getId().intValue()))
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
    public void getNonExistingPayrollEmployeeTaxes() throws Exception {
        // Get the payrollEmployeeTaxes
        restPayrollEmployeeTaxesMockMvc.perform(get("/api/payroll-employee-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollEmployeeTaxes() throws Exception {
        // Initialize the database
        payrollEmployeeTaxesRepository.saveAndFlush(payrollEmployeeTaxes);
        int databaseSizeBeforeUpdate = payrollEmployeeTaxesRepository.findAll().size();

        // Update the payrollEmployeeTaxes
        PayrollEmployeeTaxes updatedPayrollEmployeeTaxes = payrollEmployeeTaxesRepository.findOne(payrollEmployeeTaxes.getId());
        // Disconnect from session so that the updates on updatedPayrollEmployeeTaxes are not directly saved in db
        em.detach(updatedPayrollEmployeeTaxes);
        updatedPayrollEmployeeTaxes
            .taxCode(UPDATED_TAX_CODE)
            .periodAmount(UPDATED_PERIOD_AMOUNT)
            .ytdAmount(UPDATED_YTD_AMOUNT)
            .companyCode(UPDATED_COMPANY_CODE)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollEmployeeTaxesMockMvc.perform(put("/api/payroll-employee-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollEmployeeTaxes)))
            .andExpect(status().isOk());

        // Validate the PayrollEmployeeTaxes in the database
        List<PayrollEmployeeTaxes> payrollEmployeeTaxesList = payrollEmployeeTaxesRepository.findAll();
        assertThat(payrollEmployeeTaxesList).hasSize(databaseSizeBeforeUpdate);
        PayrollEmployeeTaxes testPayrollEmployeeTaxes = payrollEmployeeTaxesList.get(payrollEmployeeTaxesList.size() - 1);
        assertThat(testPayrollEmployeeTaxes.getTaxCode()).isEqualTo(UPDATED_TAX_CODE);
        assertThat(testPayrollEmployeeTaxes.getPeriodAmount()).isEqualTo(UPDATED_PERIOD_AMOUNT);
        assertThat(testPayrollEmployeeTaxes.getYtdAmount()).isEqualTo(UPDATED_YTD_AMOUNT);
        assertThat(testPayrollEmployeeTaxes.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testPayrollEmployeeTaxes.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testPayrollEmployeeTaxes.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollEmployeeTaxes.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollEmployeeTaxes() throws Exception {
        int databaseSizeBeforeUpdate = payrollEmployeeTaxesRepository.findAll().size();

        // Create the PayrollEmployeeTaxes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollEmployeeTaxesMockMvc.perform(put("/api/payroll-employee-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployeeTaxes)))
            .andExpect(status().isCreated());

        // Validate the PayrollEmployeeTaxes in the database
        List<PayrollEmployeeTaxes> payrollEmployeeTaxesList = payrollEmployeeTaxesRepository.findAll();
        assertThat(payrollEmployeeTaxesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollEmployeeTaxes() throws Exception {
        // Initialize the database
        payrollEmployeeTaxesRepository.saveAndFlush(payrollEmployeeTaxes);
        int databaseSizeBeforeDelete = payrollEmployeeTaxesRepository.findAll().size();

        // Get the payrollEmployeeTaxes
        restPayrollEmployeeTaxesMockMvc.perform(delete("/api/payroll-employee-taxes/{id}", payrollEmployeeTaxes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollEmployeeTaxes> payrollEmployeeTaxesList = payrollEmployeeTaxesRepository.findAll();
        assertThat(payrollEmployeeTaxesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollEmployeeTaxes.class);
        PayrollEmployeeTaxes payrollEmployeeTaxes1 = new PayrollEmployeeTaxes();
        payrollEmployeeTaxes1.setId(1L);
        PayrollEmployeeTaxes payrollEmployeeTaxes2 = new PayrollEmployeeTaxes();
        payrollEmployeeTaxes2.setId(payrollEmployeeTaxes1.getId());
        assertThat(payrollEmployeeTaxes1).isEqualTo(payrollEmployeeTaxes2);
        payrollEmployeeTaxes2.setId(2L);
        assertThat(payrollEmployeeTaxes1).isNotEqualTo(payrollEmployeeTaxes2);
        payrollEmployeeTaxes1.setId(null);
        assertThat(payrollEmployeeTaxes1).isNotEqualTo(payrollEmployeeTaxes2);
    }
}
