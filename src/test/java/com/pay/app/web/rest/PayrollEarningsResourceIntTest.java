package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollEarnings;
import com.pay.app.repository.PayrollEarningsRepository;
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
 * Test class for the PayrollEarningsResource REST controller.
 *
 * @see PayrollEarningsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollEarningsResourceIntTest {

    private static final String DEFAULT_EARNINGS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EARNINGS_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_HOURS = 1F;
    private static final Float UPDATED_HOURS = 2F;

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
    private PayrollEarningsRepository payrollEarningsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollEarningsMockMvc;

    private PayrollEarnings payrollEarnings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollEarningsResource payrollEarningsResource = new PayrollEarningsResource(payrollEarningsRepository);
        this.restPayrollEarningsMockMvc = MockMvcBuilders.standaloneSetup(payrollEarningsResource)
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
    public static PayrollEarnings createEntity(EntityManager em) {
        PayrollEarnings payrollEarnings = new PayrollEarnings()
            .earningsCode(DEFAULT_EARNINGS_CODE)
            .hours(DEFAULT_HOURS)
            .periodAmount(DEFAULT_PERIOD_AMOUNT)
            .ytdAmount(DEFAULT_YTD_AMOUNT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return payrollEarnings;
    }

    @Before
    public void initTest() {
        payrollEarnings = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollEarnings() throws Exception {
        int databaseSizeBeforeCreate = payrollEarningsRepository.findAll().size();

        // Create the PayrollEarnings
        restPayrollEarningsMockMvc.perform(post("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEarnings)))
            .andExpect(status().isCreated());

        // Validate the PayrollEarnings in the database
        List<PayrollEarnings> payrollEarningsList = payrollEarningsRepository.findAll();
        assertThat(payrollEarningsList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollEarnings testPayrollEarnings = payrollEarningsList.get(payrollEarningsList.size() - 1);
        assertThat(testPayrollEarnings.getEarningsCode()).isEqualTo(DEFAULT_EARNINGS_CODE);
        assertThat(testPayrollEarnings.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testPayrollEarnings.getPeriodAmount()).isEqualTo(DEFAULT_PERIOD_AMOUNT);
        assertThat(testPayrollEarnings.getYtdAmount()).isEqualTo(DEFAULT_YTD_AMOUNT);
        assertThat(testPayrollEarnings.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testPayrollEarnings.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testPayrollEarnings.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollEarnings.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollEarningsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollEarningsRepository.findAll().size();

        // Create the PayrollEarnings with an existing ID
        payrollEarnings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollEarningsMockMvc.perform(post("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEarnings)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollEarnings in the database
        List<PayrollEarnings> payrollEarningsList = payrollEarningsRepository.findAll();
        assertThat(payrollEarningsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollEarnings() throws Exception {
        // Initialize the database
        payrollEarningsRepository.saveAndFlush(payrollEarnings);

        // Get all the payrollEarningsList
        restPayrollEarningsMockMvc.perform(get("/api/payroll-earnings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollEarnings.getId().intValue())))
            .andExpect(jsonPath("$.[*].earningsCode").value(hasItem(DEFAULT_EARNINGS_CODE.toString())))
            .andExpect(jsonPath("$.[*].hours").value(hasItem(DEFAULT_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].periodAmount").value(hasItem(DEFAULT_PERIOD_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].ytdAmount").value(hasItem(DEFAULT_YTD_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getPayrollEarnings() throws Exception {
        // Initialize the database
        payrollEarningsRepository.saveAndFlush(payrollEarnings);

        // Get the payrollEarnings
        restPayrollEarningsMockMvc.perform(get("/api/payroll-earnings/{id}", payrollEarnings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollEarnings.getId().intValue()))
            .andExpect(jsonPath("$.earningsCode").value(DEFAULT_EARNINGS_CODE.toString()))
            .andExpect(jsonPath("$.hours").value(DEFAULT_HOURS.doubleValue()))
            .andExpect(jsonPath("$.periodAmount").value(DEFAULT_PERIOD_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.ytdAmount").value(DEFAULT_YTD_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollEarnings() throws Exception {
        // Get the payrollEarnings
        restPayrollEarningsMockMvc.perform(get("/api/payroll-earnings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollEarnings() throws Exception {
        // Initialize the database
        payrollEarningsRepository.saveAndFlush(payrollEarnings);
        int databaseSizeBeforeUpdate = payrollEarningsRepository.findAll().size();

        // Update the payrollEarnings
        PayrollEarnings updatedPayrollEarnings = payrollEarningsRepository.findOne(payrollEarnings.getId());
        // Disconnect from session so that the updates on updatedPayrollEarnings are not directly saved in db
        em.detach(updatedPayrollEarnings);
        updatedPayrollEarnings
            .earningsCode(UPDATED_EARNINGS_CODE)
            .hours(UPDATED_HOURS)
            .periodAmount(UPDATED_PERIOD_AMOUNT)
            .ytdAmount(UPDATED_YTD_AMOUNT)
            .companyCode(UPDATED_COMPANY_CODE)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollEarningsMockMvc.perform(put("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollEarnings)))
            .andExpect(status().isOk());

        // Validate the PayrollEarnings in the database
        List<PayrollEarnings> payrollEarningsList = payrollEarningsRepository.findAll();
        assertThat(payrollEarningsList).hasSize(databaseSizeBeforeUpdate);
        PayrollEarnings testPayrollEarnings = payrollEarningsList.get(payrollEarningsList.size() - 1);
        assertThat(testPayrollEarnings.getEarningsCode()).isEqualTo(UPDATED_EARNINGS_CODE);
        assertThat(testPayrollEarnings.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testPayrollEarnings.getPeriodAmount()).isEqualTo(UPDATED_PERIOD_AMOUNT);
        assertThat(testPayrollEarnings.getYtdAmount()).isEqualTo(UPDATED_YTD_AMOUNT);
        assertThat(testPayrollEarnings.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testPayrollEarnings.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testPayrollEarnings.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollEarnings.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollEarnings() throws Exception {
        int databaseSizeBeforeUpdate = payrollEarningsRepository.findAll().size();

        // Create the PayrollEarnings

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollEarningsMockMvc.perform(put("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEarnings)))
            .andExpect(status().isCreated());

        // Validate the PayrollEarnings in the database
        List<PayrollEarnings> payrollEarningsList = payrollEarningsRepository.findAll();
        assertThat(payrollEarningsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollEarnings() throws Exception {
        // Initialize the database
        payrollEarningsRepository.saveAndFlush(payrollEarnings);
        int databaseSizeBeforeDelete = payrollEarningsRepository.findAll().size();

        // Get the payrollEarnings
        restPayrollEarningsMockMvc.perform(delete("/api/payroll-earnings/{id}", payrollEarnings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollEarnings> payrollEarningsList = payrollEarningsRepository.findAll();
        assertThat(payrollEarningsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollEarnings.class);
        PayrollEarnings payrollEarnings1 = new PayrollEarnings();
        payrollEarnings1.setId(1L);
        PayrollEarnings payrollEarnings2 = new PayrollEarnings();
        payrollEarnings2.setId(payrollEarnings1.getId());
        assertThat(payrollEarnings1).isEqualTo(payrollEarnings2);
        payrollEarnings2.setId(2L);
        assertThat(payrollEarnings1).isNotEqualTo(payrollEarnings2);
        payrollEarnings1.setId(null);
        assertThat(payrollEarnings1).isNotEqualTo(payrollEarnings2);
    }
}
