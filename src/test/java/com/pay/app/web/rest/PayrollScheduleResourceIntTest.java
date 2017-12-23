package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollSchedule;
import com.pay.app.repository.PayrollScheduleRepository;
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
 * Test class for the PayrollScheduleResource REST controller.
 *
 * @see PayrollScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollScheduleResourceIntTest {

    private static final LocalDate DEFAULT_CHECK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHECK_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIOD_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_END = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIOD_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPROVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PAYMENT_STATUS = false;
    private static final Boolean UPDATED_PAYMENT_STATUS = true;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private PayrollScheduleRepository payrollScheduleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollScheduleMockMvc;

    private PayrollSchedule payrollSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollScheduleResource payrollScheduleResource = new PayrollScheduleResource(payrollScheduleRepository);
        this.restPayrollScheduleMockMvc = MockMvcBuilders.standaloneSetup(payrollScheduleResource)
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
    public static PayrollSchedule createEntity(EntityManager em) {
        PayrollSchedule payrollSchedule = new PayrollSchedule()
            .checkDate(DEFAULT_CHECK_DATE)
            .periodEnd(DEFAULT_PERIOD_END)
            .periodStart(DEFAULT_PERIOD_START)
            .approveDate(DEFAULT_APPROVE_DATE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .year(DEFAULT_YEAR)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return payrollSchedule;
    }

    @Before
    public void initTest() {
        payrollSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollSchedule() throws Exception {
        int databaseSizeBeforeCreate = payrollScheduleRepository.findAll().size();

        // Create the PayrollSchedule
        restPayrollScheduleMockMvc.perform(post("/api/payroll-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollSchedule)))
            .andExpect(status().isCreated());

        // Validate the PayrollSchedule in the database
        List<PayrollSchedule> payrollScheduleList = payrollScheduleRepository.findAll();
        assertThat(payrollScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollSchedule testPayrollSchedule = payrollScheduleList.get(payrollScheduleList.size() - 1);
        assertThat(testPayrollSchedule.getCheckDate()).isEqualTo(DEFAULT_CHECK_DATE);
        assertThat(testPayrollSchedule.getPeriodEnd()).isEqualTo(DEFAULT_PERIOD_END);
        assertThat(testPayrollSchedule.getPeriodStart()).isEqualTo(DEFAULT_PERIOD_START);
        assertThat(testPayrollSchedule.getApproveDate()).isEqualTo(DEFAULT_APPROVE_DATE);
        assertThat(testPayrollSchedule.isPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPayrollSchedule.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPayrollSchedule.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testPayrollSchedule.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollSchedule.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createPayrollScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollScheduleRepository.findAll().size();

        // Create the PayrollSchedule with an existing ID
        payrollSchedule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollScheduleMockMvc.perform(post("/api/payroll-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollSchedule)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollSchedule in the database
        List<PayrollSchedule> payrollScheduleList = payrollScheduleRepository.findAll();
        assertThat(payrollScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollSchedules() throws Exception {
        // Initialize the database
        payrollScheduleRepository.saveAndFlush(payrollSchedule);

        // Get all the payrollScheduleList
        restPayrollScheduleMockMvc.perform(get("/api/payroll-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkDate").value(hasItem(DEFAULT_CHECK_DATE.toString())))
            .andExpect(jsonPath("$.[*].periodEnd").value(hasItem(DEFAULT_PERIOD_END.toString())))
            .andExpect(jsonPath("$.[*].periodStart").value(hasItem(DEFAULT_PERIOD_START.toString())))
            .andExpect(jsonPath("$.[*].approveDate").value(hasItem(DEFAULT_APPROVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getPayrollSchedule() throws Exception {
        // Initialize the database
        payrollScheduleRepository.saveAndFlush(payrollSchedule);

        // Get the payrollSchedule
        restPayrollScheduleMockMvc.perform(get("/api/payroll-schedules/{id}", payrollSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollSchedule.getId().intValue()))
            .andExpect(jsonPath("$.checkDate").value(DEFAULT_CHECK_DATE.toString()))
            .andExpect(jsonPath("$.periodEnd").value(DEFAULT_PERIOD_END.toString()))
            .andExpect(jsonPath("$.periodStart").value(DEFAULT_PERIOD_START.toString()))
            .andExpect(jsonPath("$.approveDate").value(DEFAULT_APPROVE_DATE.toString()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollSchedule() throws Exception {
        // Get the payrollSchedule
        restPayrollScheduleMockMvc.perform(get("/api/payroll-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollSchedule() throws Exception {
        // Initialize the database
        payrollScheduleRepository.saveAndFlush(payrollSchedule);
        int databaseSizeBeforeUpdate = payrollScheduleRepository.findAll().size();

        // Update the payrollSchedule
        PayrollSchedule updatedPayrollSchedule = payrollScheduleRepository.findOne(payrollSchedule.getId());
        // Disconnect from session so that the updates on updatedPayrollSchedule are not directly saved in db
        em.detach(updatedPayrollSchedule);
        updatedPayrollSchedule
            .checkDate(UPDATED_CHECK_DATE)
            .periodEnd(UPDATED_PERIOD_END)
            .periodStart(UPDATED_PERIOD_START)
            .approveDate(UPDATED_APPROVE_DATE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .year(UPDATED_YEAR)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollScheduleMockMvc.perform(put("/api/payroll-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollSchedule)))
            .andExpect(status().isOk());

        // Validate the PayrollSchedule in the database
        List<PayrollSchedule> payrollScheduleList = payrollScheduleRepository.findAll();
        assertThat(payrollScheduleList).hasSize(databaseSizeBeforeUpdate);
        PayrollSchedule testPayrollSchedule = payrollScheduleList.get(payrollScheduleList.size() - 1);
        assertThat(testPayrollSchedule.getCheckDate()).isEqualTo(UPDATED_CHECK_DATE);
        assertThat(testPayrollSchedule.getPeriodEnd()).isEqualTo(UPDATED_PERIOD_END);
        assertThat(testPayrollSchedule.getPeriodStart()).isEqualTo(UPDATED_PERIOD_START);
        assertThat(testPayrollSchedule.getApproveDate()).isEqualTo(UPDATED_APPROVE_DATE);
        assertThat(testPayrollSchedule.isPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPayrollSchedule.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPayrollSchedule.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testPayrollSchedule.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollSchedule.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollSchedule() throws Exception {
        int databaseSizeBeforeUpdate = payrollScheduleRepository.findAll().size();

        // Create the PayrollSchedule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollScheduleMockMvc.perform(put("/api/payroll-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollSchedule)))
            .andExpect(status().isCreated());

        // Validate the PayrollSchedule in the database
        List<PayrollSchedule> payrollScheduleList = payrollScheduleRepository.findAll();
        assertThat(payrollScheduleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollSchedule() throws Exception {
        // Initialize the database
        payrollScheduleRepository.saveAndFlush(payrollSchedule);
        int databaseSizeBeforeDelete = payrollScheduleRepository.findAll().size();

        // Get the payrollSchedule
        restPayrollScheduleMockMvc.perform(delete("/api/payroll-schedules/{id}", payrollSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollSchedule> payrollScheduleList = payrollScheduleRepository.findAll();
        assertThat(payrollScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollSchedule.class);
        PayrollSchedule payrollSchedule1 = new PayrollSchedule();
        payrollSchedule1.setId(1L);
        PayrollSchedule payrollSchedule2 = new PayrollSchedule();
        payrollSchedule2.setId(payrollSchedule1.getId());
        assertThat(payrollSchedule1).isEqualTo(payrollSchedule2);
        payrollSchedule2.setId(2L);
        assertThat(payrollSchedule1).isNotEqualTo(payrollSchedule2);
        payrollSchedule1.setId(null);
        assertThat(payrollSchedule1).isNotEqualTo(payrollSchedule2);
    }
}
