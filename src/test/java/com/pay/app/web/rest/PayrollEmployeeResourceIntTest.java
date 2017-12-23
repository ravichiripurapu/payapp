package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollEmployee;
import com.pay.app.repository.PayrollEmployeeRepository;
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
 * Test class for the PayrollEmployeeResource REST controller.
 *
 * @see PayrollEmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollEmployeeResourceIntTest {

    private static final String DEFAULT_COMPANY_SCHEDULE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_SCHEDULE = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME_TAX_STATE = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_TAX_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_UNEMPLOYMENT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_UNEMPLOYMENT_STATE = "BBBBBBBBBB";

    private static final Float DEFAULT_NET_PAY = 1F;
    private static final Float UPDATED_NET_PAY = 2F;

    private static final String DEFAULT_PAY_STUB = "AAAAAAAAAA";
    private static final String UPDATED_PAY_STUB = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private PayrollEmployeeRepository payrollEmployeeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollEmployeeMockMvc;

    private PayrollEmployee payrollEmployee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollEmployeeResource payrollEmployeeResource = new PayrollEmployeeResource(payrollEmployeeRepository);
        this.restPayrollEmployeeMockMvc = MockMvcBuilders.standaloneSetup(payrollEmployeeResource)
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
    public static PayrollEmployee createEntity(EntityManager em) {
        PayrollEmployee payrollEmployee = new PayrollEmployee()
            .companySchedule(DEFAULT_COMPANY_SCHEDULE)
            .incomeTaxState(DEFAULT_INCOME_TAX_STATE)
            .unemploymentState(DEFAULT_UNEMPLOYMENT_STATE)
            .netPay(DEFAULT_NET_PAY)
            .payStub(DEFAULT_PAY_STUB)
            .companyCode(DEFAULT_COMPANY_CODE)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return payrollEmployee;
    }

    @Before
    public void initTest() {
        payrollEmployee = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollEmployee() throws Exception {
        int databaseSizeBeforeCreate = payrollEmployeeRepository.findAll().size();

        // Create the PayrollEmployee
        restPayrollEmployeeMockMvc.perform(post("/api/payroll-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployee)))
            .andExpect(status().isCreated());

        // Validate the PayrollEmployee in the database
        List<PayrollEmployee> payrollEmployeeList = payrollEmployeeRepository.findAll();
        assertThat(payrollEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollEmployee testPayrollEmployee = payrollEmployeeList.get(payrollEmployeeList.size() - 1);
        assertThat(testPayrollEmployee.getCompanySchedule()).isEqualTo(DEFAULT_COMPANY_SCHEDULE);
        assertThat(testPayrollEmployee.getIncomeTaxState()).isEqualTo(DEFAULT_INCOME_TAX_STATE);
        assertThat(testPayrollEmployee.getUnemploymentState()).isEqualTo(DEFAULT_UNEMPLOYMENT_STATE);
        assertThat(testPayrollEmployee.getNetPay()).isEqualTo(DEFAULT_NET_PAY);
        assertThat(testPayrollEmployee.getPayStub()).isEqualTo(DEFAULT_PAY_STUB);
        assertThat(testPayrollEmployee.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testPayrollEmployee.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testPayrollEmployee.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollEmployeeRepository.findAll().size();

        // Create the PayrollEmployee with an existing ID
        payrollEmployee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollEmployeeMockMvc.perform(post("/api/payroll-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollEmployee in the database
        List<PayrollEmployee> payrollEmployeeList = payrollEmployeeRepository.findAll();
        assertThat(payrollEmployeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollEmployees() throws Exception {
        // Initialize the database
        payrollEmployeeRepository.saveAndFlush(payrollEmployee);

        // Get all the payrollEmployeeList
        restPayrollEmployeeMockMvc.perform(get("/api/payroll-employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].companySchedule").value(hasItem(DEFAULT_COMPANY_SCHEDULE.toString())))
            .andExpect(jsonPath("$.[*].incomeTaxState").value(hasItem(DEFAULT_INCOME_TAX_STATE.toString())))
            .andExpect(jsonPath("$.[*].unemploymentState").value(hasItem(DEFAULT_UNEMPLOYMENT_STATE.toString())))
            .andExpect(jsonPath("$.[*].netPay").value(hasItem(DEFAULT_NET_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].payStub").value(hasItem(DEFAULT_PAY_STUB.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getPayrollEmployee() throws Exception {
        // Initialize the database
        payrollEmployeeRepository.saveAndFlush(payrollEmployee);

        // Get the payrollEmployee
        restPayrollEmployeeMockMvc.perform(get("/api/payroll-employees/{id}", payrollEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollEmployee.getId().intValue()))
            .andExpect(jsonPath("$.companySchedule").value(DEFAULT_COMPANY_SCHEDULE.toString()))
            .andExpect(jsonPath("$.incomeTaxState").value(DEFAULT_INCOME_TAX_STATE.toString()))
            .andExpect(jsonPath("$.unemploymentState").value(DEFAULT_UNEMPLOYMENT_STATE.toString()))
            .andExpect(jsonPath("$.netPay").value(DEFAULT_NET_PAY.doubleValue()))
            .andExpect(jsonPath("$.payStub").value(DEFAULT_PAY_STUB.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollEmployee() throws Exception {
        // Get the payrollEmployee
        restPayrollEmployeeMockMvc.perform(get("/api/payroll-employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollEmployee() throws Exception {
        // Initialize the database
        payrollEmployeeRepository.saveAndFlush(payrollEmployee);
        int databaseSizeBeforeUpdate = payrollEmployeeRepository.findAll().size();

        // Update the payrollEmployee
        PayrollEmployee updatedPayrollEmployee = payrollEmployeeRepository.findOne(payrollEmployee.getId());
        // Disconnect from session so that the updates on updatedPayrollEmployee are not directly saved in db
        em.detach(updatedPayrollEmployee);
        updatedPayrollEmployee
            .companySchedule(UPDATED_COMPANY_SCHEDULE)
            .incomeTaxState(UPDATED_INCOME_TAX_STATE)
            .unemploymentState(UPDATED_UNEMPLOYMENT_STATE)
            .netPay(UPDATED_NET_PAY)
            .payStub(UPDATED_PAY_STUB)
            .companyCode(UPDATED_COMPANY_CODE)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollEmployeeMockMvc.perform(put("/api/payroll-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollEmployee)))
            .andExpect(status().isOk());

        // Validate the PayrollEmployee in the database
        List<PayrollEmployee> payrollEmployeeList = payrollEmployeeRepository.findAll();
        assertThat(payrollEmployeeList).hasSize(databaseSizeBeforeUpdate);
        PayrollEmployee testPayrollEmployee = payrollEmployeeList.get(payrollEmployeeList.size() - 1);
        assertThat(testPayrollEmployee.getCompanySchedule()).isEqualTo(UPDATED_COMPANY_SCHEDULE);
        assertThat(testPayrollEmployee.getIncomeTaxState()).isEqualTo(UPDATED_INCOME_TAX_STATE);
        assertThat(testPayrollEmployee.getUnemploymentState()).isEqualTo(UPDATED_UNEMPLOYMENT_STATE);
        assertThat(testPayrollEmployee.getNetPay()).isEqualTo(UPDATED_NET_PAY);
        assertThat(testPayrollEmployee.getPayStub()).isEqualTo(UPDATED_PAY_STUB);
        assertThat(testPayrollEmployee.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testPayrollEmployee.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testPayrollEmployee.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollEmployee() throws Exception {
        int databaseSizeBeforeUpdate = payrollEmployeeRepository.findAll().size();

        // Create the PayrollEmployee

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollEmployeeMockMvc.perform(put("/api/payroll-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollEmployee)))
            .andExpect(status().isCreated());

        // Validate the PayrollEmployee in the database
        List<PayrollEmployee> payrollEmployeeList = payrollEmployeeRepository.findAll();
        assertThat(payrollEmployeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollEmployee() throws Exception {
        // Initialize the database
        payrollEmployeeRepository.saveAndFlush(payrollEmployee);
        int databaseSizeBeforeDelete = payrollEmployeeRepository.findAll().size();

        // Get the payrollEmployee
        restPayrollEmployeeMockMvc.perform(delete("/api/payroll-employees/{id}", payrollEmployee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollEmployee> payrollEmployeeList = payrollEmployeeRepository.findAll();
        assertThat(payrollEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollEmployee.class);
        PayrollEmployee payrollEmployee1 = new PayrollEmployee();
        payrollEmployee1.setId(1L);
        PayrollEmployee payrollEmployee2 = new PayrollEmployee();
        payrollEmployee2.setId(payrollEmployee1.getId());
        assertThat(payrollEmployee1).isEqualTo(payrollEmployee2);
        payrollEmployee2.setId(2L);
        assertThat(payrollEmployee1).isNotEqualTo(payrollEmployee2);
        payrollEmployee1.setId(null);
        assertThat(payrollEmployee1).isNotEqualTo(payrollEmployee2);
    }
}
