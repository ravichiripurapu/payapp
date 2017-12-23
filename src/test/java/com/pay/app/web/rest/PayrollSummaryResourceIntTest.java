package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollSummary;
import com.pay.app.repository.PayrollSummaryRepository;
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
 * Test class for the PayrollSummaryResource REST controller.
 *
 * @see PayrollSummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollSummaryResourceIntTest {

    private static final Float DEFAULT_DIRECT_DEPOSIT_AMOUNT = 1F;
    private static final Float UPDATED_DIRECT_DEPOSIT_AMOUNT = 2F;

    private static final Float DEFAULT_PAID_BY_CHECK_AMOUNT = 1F;
    private static final Float UPDATED_PAID_BY_CHECK_AMOUNT = 2F;

    private static final Float DEFAULT_EMPLOYEE_DEDUCTIONS = 1F;
    private static final Float UPDATED_EMPLOYEE_DEDUCTIONS = 2F;

    private static final Float DEFAULT_EMPLOYER_DEDUCTIONS = 1F;
    private static final Float UPDATED_EMPLOYER_DEDUCTIONS = 2F;

    private static final Float DEFAULT_EMPLOYEE_TAXES = 1F;
    private static final Float UPDATED_EMPLOYEE_TAXES = 2F;

    private static final Float DEFAULT_EMPLOYER_TAXES = 1F;
    private static final Float UPDATED_EMPLOYER_TAXES = 2F;

    private static final Float DEFAULT_PAYROLL_PROCESSING_FEE = 1F;
    private static final Float UPDATED_PAYROLL_PROCESSING_FEE = 2F;

    private static final Float DEFAULT_TOTAL_CASH_REQUIREMENTS = 1F;
    private static final Float UPDATED_TOTAL_CASH_REQUIREMENTS = 2F;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private PayrollSummaryRepository payrollSummaryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollSummaryMockMvc;

    private PayrollSummary payrollSummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollSummaryResource payrollSummaryResource = new PayrollSummaryResource(payrollSummaryRepository);
        this.restPayrollSummaryMockMvc = MockMvcBuilders.standaloneSetup(payrollSummaryResource)
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
    public static PayrollSummary createEntity(EntityManager em) {
        PayrollSummary payrollSummary = new PayrollSummary()
            .directDepositAmount(DEFAULT_DIRECT_DEPOSIT_AMOUNT)
            .paidByCheckAmount(DEFAULT_PAID_BY_CHECK_AMOUNT)
            .employeeDeductions(DEFAULT_EMPLOYEE_DEDUCTIONS)
            .employerDeductions(DEFAULT_EMPLOYER_DEDUCTIONS)
            .employeeTaxes(DEFAULT_EMPLOYEE_TAXES)
            .employerTaxes(DEFAULT_EMPLOYER_TAXES)
            .payrollProcessingFee(DEFAULT_PAYROLL_PROCESSING_FEE)
            .totalCashRequirements(DEFAULT_TOTAL_CASH_REQUIREMENTS)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return payrollSummary;
    }

    @Before
    public void initTest() {
        payrollSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollSummary() throws Exception {
        int databaseSizeBeforeCreate = payrollSummaryRepository.findAll().size();

        // Create the PayrollSummary
        restPayrollSummaryMockMvc.perform(post("/api/payroll-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollSummary)))
            .andExpect(status().isCreated());

        // Validate the PayrollSummary in the database
        List<PayrollSummary> payrollSummaryList = payrollSummaryRepository.findAll();
        assertThat(payrollSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollSummary testPayrollSummary = payrollSummaryList.get(payrollSummaryList.size() - 1);
        assertThat(testPayrollSummary.getDirectDepositAmount()).isEqualTo(DEFAULT_DIRECT_DEPOSIT_AMOUNT);
        assertThat(testPayrollSummary.getPaidByCheckAmount()).isEqualTo(DEFAULT_PAID_BY_CHECK_AMOUNT);
        assertThat(testPayrollSummary.getEmployeeDeductions()).isEqualTo(DEFAULT_EMPLOYEE_DEDUCTIONS);
        assertThat(testPayrollSummary.getEmployerDeductions()).isEqualTo(DEFAULT_EMPLOYER_DEDUCTIONS);
        assertThat(testPayrollSummary.getEmployeeTaxes()).isEqualTo(DEFAULT_EMPLOYEE_TAXES);
        assertThat(testPayrollSummary.getEmployerTaxes()).isEqualTo(DEFAULT_EMPLOYER_TAXES);
        assertThat(testPayrollSummary.getPayrollProcessingFee()).isEqualTo(DEFAULT_PAYROLL_PROCESSING_FEE);
        assertThat(testPayrollSummary.getTotalCashRequirements()).isEqualTo(DEFAULT_TOTAL_CASH_REQUIREMENTS);
        assertThat(testPayrollSummary.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testPayrollSummary.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollSummary.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollSummaryRepository.findAll().size();

        // Create the PayrollSummary with an existing ID
        payrollSummary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollSummaryMockMvc.perform(post("/api/payroll-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollSummary)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollSummary in the database
        List<PayrollSummary> payrollSummaryList = payrollSummaryRepository.findAll();
        assertThat(payrollSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollSummaries() throws Exception {
        // Initialize the database
        payrollSummaryRepository.saveAndFlush(payrollSummary);

        // Get all the payrollSummaryList
        restPayrollSummaryMockMvc.perform(get("/api/payroll-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].directDepositAmount").value(hasItem(DEFAULT_DIRECT_DEPOSIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidByCheckAmount").value(hasItem(DEFAULT_PAID_BY_CHECK_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeDeductions").value(hasItem(DEFAULT_EMPLOYEE_DEDUCTIONS.doubleValue())))
            .andExpect(jsonPath("$.[*].employerDeductions").value(hasItem(DEFAULT_EMPLOYER_DEDUCTIONS.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeTaxes").value(hasItem(DEFAULT_EMPLOYEE_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].employerTaxes").value(hasItem(DEFAULT_EMPLOYER_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].payrollProcessingFee").value(hasItem(DEFAULT_PAYROLL_PROCESSING_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalCashRequirements").value(hasItem(DEFAULT_TOTAL_CASH_REQUIREMENTS.doubleValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getPayrollSummary() throws Exception {
        // Initialize the database
        payrollSummaryRepository.saveAndFlush(payrollSummary);

        // Get the payrollSummary
        restPayrollSummaryMockMvc.perform(get("/api/payroll-summaries/{id}", payrollSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollSummary.getId().intValue()))
            .andExpect(jsonPath("$.directDepositAmount").value(DEFAULT_DIRECT_DEPOSIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paidByCheckAmount").value(DEFAULT_PAID_BY_CHECK_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.employeeDeductions").value(DEFAULT_EMPLOYEE_DEDUCTIONS.doubleValue()))
            .andExpect(jsonPath("$.employerDeductions").value(DEFAULT_EMPLOYER_DEDUCTIONS.doubleValue()))
            .andExpect(jsonPath("$.employeeTaxes").value(DEFAULT_EMPLOYEE_TAXES.doubleValue()))
            .andExpect(jsonPath("$.employerTaxes").value(DEFAULT_EMPLOYER_TAXES.doubleValue()))
            .andExpect(jsonPath("$.payrollProcessingFee").value(DEFAULT_PAYROLL_PROCESSING_FEE.doubleValue()))
            .andExpect(jsonPath("$.totalCashRequirements").value(DEFAULT_TOTAL_CASH_REQUIREMENTS.doubleValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollSummary() throws Exception {
        // Get the payrollSummary
        restPayrollSummaryMockMvc.perform(get("/api/payroll-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollSummary() throws Exception {
        // Initialize the database
        payrollSummaryRepository.saveAndFlush(payrollSummary);
        int databaseSizeBeforeUpdate = payrollSummaryRepository.findAll().size();

        // Update the payrollSummary
        PayrollSummary updatedPayrollSummary = payrollSummaryRepository.findOne(payrollSummary.getId());
        // Disconnect from session so that the updates on updatedPayrollSummary are not directly saved in db
        em.detach(updatedPayrollSummary);
        updatedPayrollSummary
            .directDepositAmount(UPDATED_DIRECT_DEPOSIT_AMOUNT)
            .paidByCheckAmount(UPDATED_PAID_BY_CHECK_AMOUNT)
            .employeeDeductions(UPDATED_EMPLOYEE_DEDUCTIONS)
            .employerDeductions(UPDATED_EMPLOYER_DEDUCTIONS)
            .employeeTaxes(UPDATED_EMPLOYEE_TAXES)
            .employerTaxes(UPDATED_EMPLOYER_TAXES)
            .payrollProcessingFee(UPDATED_PAYROLL_PROCESSING_FEE)
            .totalCashRequirements(UPDATED_TOTAL_CASH_REQUIREMENTS)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollSummaryMockMvc.perform(put("/api/payroll-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollSummary)))
            .andExpect(status().isOk());

        // Validate the PayrollSummary in the database
        List<PayrollSummary> payrollSummaryList = payrollSummaryRepository.findAll();
        assertThat(payrollSummaryList).hasSize(databaseSizeBeforeUpdate);
        PayrollSummary testPayrollSummary = payrollSummaryList.get(payrollSummaryList.size() - 1);
        assertThat(testPayrollSummary.getDirectDepositAmount()).isEqualTo(UPDATED_DIRECT_DEPOSIT_AMOUNT);
        assertThat(testPayrollSummary.getPaidByCheckAmount()).isEqualTo(UPDATED_PAID_BY_CHECK_AMOUNT);
        assertThat(testPayrollSummary.getEmployeeDeductions()).isEqualTo(UPDATED_EMPLOYEE_DEDUCTIONS);
        assertThat(testPayrollSummary.getEmployerDeductions()).isEqualTo(UPDATED_EMPLOYER_DEDUCTIONS);
        assertThat(testPayrollSummary.getEmployeeTaxes()).isEqualTo(UPDATED_EMPLOYEE_TAXES);
        assertThat(testPayrollSummary.getEmployerTaxes()).isEqualTo(UPDATED_EMPLOYER_TAXES);
        assertThat(testPayrollSummary.getPayrollProcessingFee()).isEqualTo(UPDATED_PAYROLL_PROCESSING_FEE);
        assertThat(testPayrollSummary.getTotalCashRequirements()).isEqualTo(UPDATED_TOTAL_CASH_REQUIREMENTS);
        assertThat(testPayrollSummary.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testPayrollSummary.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollSummary.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollSummary() throws Exception {
        int databaseSizeBeforeUpdate = payrollSummaryRepository.findAll().size();

        // Create the PayrollSummary

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollSummaryMockMvc.perform(put("/api/payroll-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollSummary)))
            .andExpect(status().isCreated());

        // Validate the PayrollSummary in the database
        List<PayrollSummary> payrollSummaryList = payrollSummaryRepository.findAll();
        assertThat(payrollSummaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollSummary() throws Exception {
        // Initialize the database
        payrollSummaryRepository.saveAndFlush(payrollSummary);
        int databaseSizeBeforeDelete = payrollSummaryRepository.findAll().size();

        // Get the payrollSummary
        restPayrollSummaryMockMvc.perform(delete("/api/payroll-summaries/{id}", payrollSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollSummary> payrollSummaryList = payrollSummaryRepository.findAll();
        assertThat(payrollSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollSummary.class);
        PayrollSummary payrollSummary1 = new PayrollSummary();
        payrollSummary1.setId(1L);
        PayrollSummary payrollSummary2 = new PayrollSummary();
        payrollSummary2.setId(payrollSummary1.getId());
        assertThat(payrollSummary1).isEqualTo(payrollSummary2);
        payrollSummary2.setId(2L);
        assertThat(payrollSummary1).isNotEqualTo(payrollSummary2);
        payrollSummary1.setId(null);
        assertThat(payrollSummary1).isNotEqualTo(payrollSummary2);
    }
}
