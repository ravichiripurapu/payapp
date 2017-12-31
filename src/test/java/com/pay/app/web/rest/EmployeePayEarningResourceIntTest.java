package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EarningCode;
import com.pay.app.domain.EmployeePayEarning;
import com.pay.app.repository.EmployeePayEarningRepository;
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
 * Test class for the RunPayrollResource REST controller.
 *
 * @see RunPayrollResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeePayEarningResourceIntTest {

    private static final EarningCode DEFAULT_EARNINGS_CODE = EarningCode.PAY_BONUS;
    private static final EarningCode UPDATED_EARNINGS_CODE = EarningCode.PAY_COMMISSION;

    private static final Float DEFAULT_HOURS = 1F;
    private static final Float UPDATED_HOURS = 2F;

    private static final Double DEFAULT_PERIOD_AMOUNT = 1D;
    private static final Double UPDATED_PERIOD_AMOUNT = 2D;

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
    private EmployeePayEarningRepository employeePayEarningRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollEarningsMockMvc;

    private EmployeePayEarning employeePayEarning;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RunPayrollResource runPayrollResource = new RunPayrollResource(employeePayEarningRepository);
        this.restPayrollEarningsMockMvc = MockMvcBuilders.standaloneSetup(runPayrollResource)
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
    public static EmployeePayEarning createEntity(EntityManager em) {
        EmployeePayEarning employeePayEarning = new EmployeePayEarning()
            .earningCode(DEFAULT_EARNINGS_CODE)
            .hours(DEFAULT_HOURS)
            .periodAmount(DEFAULT_PERIOD_AMOUNT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return employeePayEarning;
    }

    @Before
    public void initTest() {
        employeePayEarning = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollEarnings() throws Exception {
        int databaseSizeBeforeCreate = employeePayEarningRepository.findAll().size();

        // Create the EmployeePayEarning
        restPayrollEarningsMockMvc.perform(post("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePayEarning)))
            .andExpect(status().isCreated());

        // Validate the EmployeePayEarning in the database
        List<EmployeePayEarning> employeePayEarningList = employeePayEarningRepository.findAll();
        assertThat(employeePayEarningList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeePayEarning testEmployeePayEarning = employeePayEarningList.get(employeePayEarningList.size() - 1);
        assertThat(testEmployeePayEarning.getEarningCode()).isEqualTo(DEFAULT_EARNINGS_CODE);
        assertThat(testEmployeePayEarning.getHours()).isEqualTo(DEFAULT_HOURS);
        assertThat(testEmployeePayEarning.getPeriodAmount()).isEqualTo(DEFAULT_PERIOD_AMOUNT);
        assertThat(testEmployeePayEarning.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testEmployeePayEarning.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeePayEarning.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeePayEarning.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollEarningsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeePayEarningRepository.findAll().size();

        // Create the EmployeePayEarning with an existing ID
        employeePayEarning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollEarningsMockMvc.perform(post("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePayEarning)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePayEarning in the database
        List<EmployeePayEarning> employeePayEarningList = employeePayEarningRepository.findAll();
        assertThat(employeePayEarningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollEarnings() throws Exception {
        // Initialize the database
        employeePayEarningRepository.saveAndFlush(employeePayEarning);

        // Get all the payrollEarningsList
        restPayrollEarningsMockMvc.perform(get("/api/payroll-earnings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeePayEarning.getId().intValue())))
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
        employeePayEarningRepository.saveAndFlush(employeePayEarning);

        // Get the employeePayEarning
        restPayrollEarningsMockMvc.perform(get("/api/payroll-earnings/{id}", employeePayEarning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeePayEarning.getId().intValue()))
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
        // Get the employeePayEarning
        restPayrollEarningsMockMvc.perform(get("/api/payroll-earnings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollEarnings() throws Exception {
        // Initialize the database
        employeePayEarningRepository.saveAndFlush(employeePayEarning);
        int databaseSizeBeforeUpdate = employeePayEarningRepository.findAll().size();

        // Update the employeePayEarning
        EmployeePayEarning updatedEmployeePayEarning = employeePayEarningRepository.findOne(employeePayEarning.getId());
        // Disconnect from session so that the updates on updatedEmployeePayEarning are not directly saved in db
        em.detach(updatedEmployeePayEarning);
        updatedEmployeePayEarning
            .earningCode(UPDATED_EARNINGS_CODE)
            .hours(UPDATED_HOURS)
            .periodAmount(UPDATED_PERIOD_AMOUNT)
            .companyCode(UPDATED_COMPANY_CODE)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollEarningsMockMvc.perform(put("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeePayEarning)))
            .andExpect(status().isOk());

        // Validate the EmployeePayEarning in the database
        List<EmployeePayEarning> employeePayEarningList = employeePayEarningRepository.findAll();
        assertThat(employeePayEarningList).hasSize(databaseSizeBeforeUpdate);
        EmployeePayEarning testEmployeePayEarning = employeePayEarningList.get(employeePayEarningList.size() - 1);
        assertThat(testEmployeePayEarning.getEarningCode()).isEqualTo(UPDATED_EARNINGS_CODE);
        assertThat(testEmployeePayEarning.getHours()).isEqualTo(UPDATED_HOURS);
        assertThat(testEmployeePayEarning.getPeriodAmount()).isEqualTo(UPDATED_PERIOD_AMOUNT);
        assertThat(testEmployeePayEarning.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testEmployeePayEarning.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeePayEarning.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeePayEarning.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollEarnings() throws Exception {
        int databaseSizeBeforeUpdate = employeePayEarningRepository.findAll().size();

        // Create the EmployeePayEarning

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollEarningsMockMvc.perform(put("/api/payroll-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePayEarning)))
            .andExpect(status().isCreated());

        // Validate the EmployeePayEarning in the database
        List<EmployeePayEarning> employeePayEarningList = employeePayEarningRepository.findAll();
        assertThat(employeePayEarningList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollEarnings() throws Exception {
        // Initialize the database
        employeePayEarningRepository.saveAndFlush(employeePayEarning);
        int databaseSizeBeforeDelete = employeePayEarningRepository.findAll().size();

        // Get the employeePayEarning
        restPayrollEarningsMockMvc.perform(delete("/api/payroll-earnings/{id}", employeePayEarning.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeePayEarning> employeePayEarningList = employeePayEarningRepository.findAll();
        assertThat(employeePayEarningList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeePayEarning.class);
        EmployeePayEarning employeePayEarning1 = new EmployeePayEarning();
        employeePayEarning1.setId(1L);
        EmployeePayEarning employeePayEarning2 = new EmployeePayEarning();
        employeePayEarning2.setId(employeePayEarning1.getId());
        assertThat(employeePayEarning1).isEqualTo(employeePayEarning2);
        employeePayEarning2.setId(2L);
        assertThat(employeePayEarning1).isNotEqualTo(employeePayEarning2);
        employeePayEarning1.setId(null);
        assertThat(employeePayEarning1).isNotEqualTo(employeePayEarning2);
    }
}
