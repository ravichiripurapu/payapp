package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EmployeeBank;
import com.pay.app.repository.EmployeeBankRepository;
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
 * Test class for the EmployeeBankResource REST controller.
 *
 * @see EmployeeBankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeeBankResourceIntTest {

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ROUTING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_PERCENT_DEPOSIT = 1F;
    private static final Float UPDATED_PERCENT_DEPOSIT = 2F;

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private EmployeeBankRepository employeeBankRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeBankMockMvc;

    private EmployeeBank employeeBank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeBankResource employeeBankResource = new EmployeeBankResource(employeeBankRepository);
        this.restEmployeeBankMockMvc = MockMvcBuilders.standaloneSetup(employeeBankResource)
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
    public static EmployeeBank createEntity(EntityManager em) {
        EmployeeBank employeeBank = new EmployeeBank()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .routingNumber(DEFAULT_ROUTING_NUMBER)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .percentDeposit(DEFAULT_PERCENT_DEPOSIT)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return employeeBank;
    }

    @Before
    public void initTest() {
        employeeBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeBank() throws Exception {
        int databaseSizeBeforeCreate = employeeBankRepository.findAll().size();

        // Create the EmployeeBank
        restEmployeeBankMockMvc.perform(post("/api/employee-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeBank)))
            .andExpect(status().isCreated());

        // Validate the EmployeeBank in the database
        List<EmployeeBank> employeeBankList = employeeBankRepository.findAll();
        assertThat(employeeBankList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeBank testEmployeeBank = employeeBankList.get(employeeBankList.size() - 1);
        assertThat(testEmployeeBank.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testEmployeeBank.getRoutingNumber()).isEqualTo(DEFAULT_ROUTING_NUMBER);
        assertThat(testEmployeeBank.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testEmployeeBank.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testEmployeeBank.getPercentDeposit()).isEqualTo(DEFAULT_PERCENT_DEPOSIT);
        assertThat(testEmployeeBank.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeeBank.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeBankRepository.findAll().size();

        // Create the EmployeeBank with an existing ID
        employeeBank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeBankMockMvc.perform(post("/api/employee-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeBank)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeBank in the database
        List<EmployeeBank> employeeBankList = employeeBankRepository.findAll();
        assertThat(employeeBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeBanks() throws Exception {
        // Initialize the database
        employeeBankRepository.saveAndFlush(employeeBank);

        // Get all the employeeBankList
        restEmployeeBankMockMvc.perform(get("/api/employee-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].routingNumber").value(hasItem(DEFAULT_ROUTING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].percentDeposit").value(hasItem(DEFAULT_PERCENT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeBank() throws Exception {
        // Initialize the database
        employeeBankRepository.saveAndFlush(employeeBank);

        // Get the employeeBank
        restEmployeeBankMockMvc.perform(get("/api/employee-banks/{id}", employeeBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeBank.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.routingNumber").value(DEFAULT_ROUTING_NUMBER.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.percentDeposit").value(DEFAULT_PERCENT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeBank() throws Exception {
        // Get the employeeBank
        restEmployeeBankMockMvc.perform(get("/api/employee-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeBank() throws Exception {
        // Initialize the database
        employeeBankRepository.saveAndFlush(employeeBank);
        int databaseSizeBeforeUpdate = employeeBankRepository.findAll().size();

        // Update the employeeBank
        EmployeeBank updatedEmployeeBank = employeeBankRepository.findOne(employeeBank.getId());
        // Disconnect from session so that the updates on updatedEmployeeBank are not directly saved in db
        em.detach(updatedEmployeeBank);
        updatedEmployeeBank
            .accountType(UPDATED_ACCOUNT_TYPE)
            .routingNumber(UPDATED_ROUTING_NUMBER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .percentDeposit(UPDATED_PERCENT_DEPOSIT)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restEmployeeBankMockMvc.perform(put("/api/employee-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeBank)))
            .andExpect(status().isOk());

        // Validate the EmployeeBank in the database
        List<EmployeeBank> employeeBankList = employeeBankRepository.findAll();
        assertThat(employeeBankList).hasSize(databaseSizeBeforeUpdate);
        EmployeeBank testEmployeeBank = employeeBankList.get(employeeBankList.size() - 1);
        assertThat(testEmployeeBank.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testEmployeeBank.getRoutingNumber()).isEqualTo(UPDATED_ROUTING_NUMBER);
        assertThat(testEmployeeBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testEmployeeBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testEmployeeBank.getPercentDeposit()).isEqualTo(UPDATED_PERCENT_DEPOSIT);
        assertThat(testEmployeeBank.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeeBank.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeBank() throws Exception {
        int databaseSizeBeforeUpdate = employeeBankRepository.findAll().size();

        // Create the EmployeeBank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeBankMockMvc.perform(put("/api/employee-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeBank)))
            .andExpect(status().isCreated());

        // Validate the EmployeeBank in the database
        List<EmployeeBank> employeeBankList = employeeBankRepository.findAll();
        assertThat(employeeBankList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeBank() throws Exception {
        // Initialize the database
        employeeBankRepository.saveAndFlush(employeeBank);
        int databaseSizeBeforeDelete = employeeBankRepository.findAll().size();

        // Get the employeeBank
        restEmployeeBankMockMvc.perform(delete("/api/employee-banks/{id}", employeeBank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeBank> employeeBankList = employeeBankRepository.findAll();
        assertThat(employeeBankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeBank.class);
        EmployeeBank employeeBank1 = new EmployeeBank();
        employeeBank1.setId(1L);
        EmployeeBank employeeBank2 = new EmployeeBank();
        employeeBank2.setId(employeeBank1.getId());
        assertThat(employeeBank1).isEqualTo(employeeBank2);
        employeeBank2.setId(2L);
        assertThat(employeeBank1).isNotEqualTo(employeeBank2);
        employeeBank1.setId(null);
        assertThat(employeeBank1).isNotEqualTo(employeeBank2);
    }
}
