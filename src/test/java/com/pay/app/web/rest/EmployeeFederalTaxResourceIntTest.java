package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EmployeeFederalTax;
import com.pay.app.repository.EmployeeFederalTaxRepository;
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
 * Test class for the EmployeeFederalTaxResource REST controller.
 *
 * @see EmployeeFederalTaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeeFederalTaxResourceIntTest {

    private static final String DEFAULT_FILING_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FILING_STATUS_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXEMPT_FROM_WITH_HOLDING = false;
    private static final Boolean UPDATED_EXEMPT_FROM_WITH_HOLDING = true;

    private static final Integer DEFAULT_ALLOWANCES = 1;
    private static final Integer UPDATED_ALLOWANCES = 2;

    private static final Float DEFAULT_EXTRA_WITH_HOLDING = 1F;
    private static final Float UPDATED_EXTRA_WITH_HOLDING = 2F;

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private EmployeeFederalTaxRepository employeeFederalTaxRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeFederalTaxMockMvc;

    private EmployeeFederalTax employeeFederalTax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeFederalTaxResource employeeFederalTaxResource = new EmployeeFederalTaxResource(employeeFederalTaxRepository);
        this.restEmployeeFederalTaxMockMvc = MockMvcBuilders.standaloneSetup(employeeFederalTaxResource)
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
    public static EmployeeFederalTax createEntity(EntityManager em) {
        EmployeeFederalTax employeeFederalTax = new EmployeeFederalTax()
            .filingStatusCode(DEFAULT_FILING_STATUS_CODE)
            .exemptFromWithHolding(DEFAULT_EXEMPT_FROM_WITH_HOLDING)
            .allowances(DEFAULT_ALLOWANCES)
            .extraWithHolding(DEFAULT_EXTRA_WITH_HOLDING)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return employeeFederalTax;
    }

    @Before
    public void initTest() {
        employeeFederalTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeFederalTax() throws Exception {
        int databaseSizeBeforeCreate = employeeFederalTaxRepository.findAll().size();

        // Create the EmployeeFederalTax
        restEmployeeFederalTaxMockMvc.perform(post("/api/employee-federal-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeFederalTax)))
            .andExpect(status().isCreated());

        // Validate the EmployeeFederalTax in the database
        List<EmployeeFederalTax> employeeFederalTaxList = employeeFederalTaxRepository.findAll();
        assertThat(employeeFederalTaxList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeFederalTax testEmployeeFederalTax = employeeFederalTaxList.get(employeeFederalTaxList.size() - 1);
        assertThat(testEmployeeFederalTax.getFilingStatusCode()).isEqualTo(DEFAULT_FILING_STATUS_CODE);
        assertThat(testEmployeeFederalTax.isExemptFromWithHolding()).isEqualTo(DEFAULT_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testEmployeeFederalTax.getAllowances()).isEqualTo(DEFAULT_ALLOWANCES);
        assertThat(testEmployeeFederalTax.getExtraWithHolding()).isEqualTo(DEFAULT_EXTRA_WITH_HOLDING);
        assertThat(testEmployeeFederalTax.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeeFederalTax.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeFederalTax.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeFederalTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeFederalTaxRepository.findAll().size();

        // Create the EmployeeFederalTax with an existing ID
        employeeFederalTax.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeFederalTaxMockMvc.perform(post("/api/employee-federal-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeFederalTax)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeFederalTax in the database
        List<EmployeeFederalTax> employeeFederalTaxList = employeeFederalTaxRepository.findAll();
        assertThat(employeeFederalTaxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeFederalTaxes() throws Exception {
        // Initialize the database
        employeeFederalTaxRepository.saveAndFlush(employeeFederalTax);

        // Get all the employeeFederalTaxList
        restEmployeeFederalTaxMockMvc.perform(get("/api/employee-federal-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeFederalTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].filingStatusCode").value(hasItem(DEFAULT_FILING_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].exemptFromWithHolding").value(hasItem(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue())))
            .andExpect(jsonPath("$.[*].allowances").value(hasItem(DEFAULT_ALLOWANCES)))
            .andExpect(jsonPath("$.[*].extraWithHolding").value(hasItem(DEFAULT_EXTRA_WITH_HOLDING.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeFederalTax() throws Exception {
        // Initialize the database
        employeeFederalTaxRepository.saveAndFlush(employeeFederalTax);

        // Get the employeeFederalTax
        restEmployeeFederalTaxMockMvc.perform(get("/api/employee-federal-taxes/{id}", employeeFederalTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeFederalTax.getId().intValue()))
            .andExpect(jsonPath("$.filingStatusCode").value(DEFAULT_FILING_STATUS_CODE.toString()))
            .andExpect(jsonPath("$.exemptFromWithHolding").value(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue()))
            .andExpect(jsonPath("$.allowances").value(DEFAULT_ALLOWANCES))
            .andExpect(jsonPath("$.extraWithHolding").value(DEFAULT_EXTRA_WITH_HOLDING.doubleValue()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeFederalTax() throws Exception {
        // Get the employeeFederalTax
        restEmployeeFederalTaxMockMvc.perform(get("/api/employee-federal-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeFederalTax() throws Exception {
        // Initialize the database
        employeeFederalTaxRepository.saveAndFlush(employeeFederalTax);
        int databaseSizeBeforeUpdate = employeeFederalTaxRepository.findAll().size();

        // Update the employeeFederalTax
        EmployeeFederalTax updatedEmployeeFederalTax = employeeFederalTaxRepository.findOne(employeeFederalTax.getId());
        // Disconnect from session so that the updates on updatedEmployeeFederalTax are not directly saved in db
        em.detach(updatedEmployeeFederalTax);
        updatedEmployeeFederalTax
            .filingStatusCode(UPDATED_FILING_STATUS_CODE)
            .exemptFromWithHolding(UPDATED_EXEMPT_FROM_WITH_HOLDING)
            .allowances(UPDATED_ALLOWANCES)
            .extraWithHolding(UPDATED_EXTRA_WITH_HOLDING)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restEmployeeFederalTaxMockMvc.perform(put("/api/employee-federal-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeFederalTax)))
            .andExpect(status().isOk());

        // Validate the EmployeeFederalTax in the database
        List<EmployeeFederalTax> employeeFederalTaxList = employeeFederalTaxRepository.findAll();
        assertThat(employeeFederalTaxList).hasSize(databaseSizeBeforeUpdate);
        EmployeeFederalTax testEmployeeFederalTax = employeeFederalTaxList.get(employeeFederalTaxList.size() - 1);
        assertThat(testEmployeeFederalTax.getFilingStatusCode()).isEqualTo(UPDATED_FILING_STATUS_CODE);
        assertThat(testEmployeeFederalTax.isExemptFromWithHolding()).isEqualTo(UPDATED_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testEmployeeFederalTax.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testEmployeeFederalTax.getExtraWithHolding()).isEqualTo(UPDATED_EXTRA_WITH_HOLDING);
        assertThat(testEmployeeFederalTax.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeeFederalTax.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeFederalTax.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeFederalTax() throws Exception {
        int databaseSizeBeforeUpdate = employeeFederalTaxRepository.findAll().size();

        // Create the EmployeeFederalTax

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeFederalTaxMockMvc.perform(put("/api/employee-federal-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeFederalTax)))
            .andExpect(status().isCreated());

        // Validate the EmployeeFederalTax in the database
        List<EmployeeFederalTax> employeeFederalTaxList = employeeFederalTaxRepository.findAll();
        assertThat(employeeFederalTaxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeFederalTax() throws Exception {
        // Initialize the database
        employeeFederalTaxRepository.saveAndFlush(employeeFederalTax);
        int databaseSizeBeforeDelete = employeeFederalTaxRepository.findAll().size();

        // Get the employeeFederalTax
        restEmployeeFederalTaxMockMvc.perform(delete("/api/employee-federal-taxes/{id}", employeeFederalTax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeFederalTax> employeeFederalTaxList = employeeFederalTaxRepository.findAll();
        assertThat(employeeFederalTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeFederalTax.class);
        EmployeeFederalTax employeeFederalTax1 = new EmployeeFederalTax();
        employeeFederalTax1.setId(1L);
        EmployeeFederalTax employeeFederalTax2 = new EmployeeFederalTax();
        employeeFederalTax2.setId(employeeFederalTax1.getId());
        assertThat(employeeFederalTax1).isEqualTo(employeeFederalTax2);
        employeeFederalTax2.setId(2L);
        assertThat(employeeFederalTax1).isNotEqualTo(employeeFederalTax2);
        employeeFederalTax1.setId(null);
        assertThat(employeeFederalTax1).isNotEqualTo(employeeFederalTax2);
    }
}
