package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EmployeeLocalTax;
import com.pay.app.repository.EmployeeLocalTaxRepository;
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
 * Test class for the EmployeeLocalTaxResource REST controller.
 *
 * @see EmployeeLocalTaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeeLocalTaxResourceIntTest {

    private static final String DEFAULT_FILING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_FILING_STATUS = "BBBBBBBBBB";

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
    private EmployeeLocalTaxRepository employeeLocalTaxRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeLocalTaxMockMvc;

    private EmployeeLocalTax employeeLocalTax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeLocalTaxResource employeeLocalTaxResource = new EmployeeLocalTaxResource(employeeLocalTaxRepository);
        this.restEmployeeLocalTaxMockMvc = MockMvcBuilders.standaloneSetup(employeeLocalTaxResource)
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
    public static EmployeeLocalTax createEntity(EntityManager em) {
        EmployeeLocalTax employeeLocalTax = new EmployeeLocalTax()
            .filingStatus(DEFAULT_FILING_STATUS)
            .exemptFromWithHolding(DEFAULT_EXEMPT_FROM_WITH_HOLDING)
            .allowances(DEFAULT_ALLOWANCES)
            .extraWithHolding(DEFAULT_EXTRA_WITH_HOLDING)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return employeeLocalTax;
    }

    @Before
    public void initTest() {
        employeeLocalTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeLocalTax() throws Exception {
        int databaseSizeBeforeCreate = employeeLocalTaxRepository.findAll().size();

        // Create the EmployeeLocalTax
        restEmployeeLocalTaxMockMvc.perform(post("/api/employee-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocalTax)))
            .andExpect(status().isCreated());

        // Validate the EmployeeLocalTax in the database
        List<EmployeeLocalTax> employeeLocalTaxList = employeeLocalTaxRepository.findAll();
        assertThat(employeeLocalTaxList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLocalTax testEmployeeLocalTax = employeeLocalTaxList.get(employeeLocalTaxList.size() - 1);
        assertThat(testEmployeeLocalTax.getFilingStatus()).isEqualTo(DEFAULT_FILING_STATUS);
        assertThat(testEmployeeLocalTax.isExemptFromWithHolding()).isEqualTo(DEFAULT_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testEmployeeLocalTax.getAllowances()).isEqualTo(DEFAULT_ALLOWANCES);
        assertThat(testEmployeeLocalTax.getExtraWithHolding()).isEqualTo(DEFAULT_EXTRA_WITH_HOLDING);
        assertThat(testEmployeeLocalTax.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeeLocalTax.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeLocalTax.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeLocalTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeLocalTaxRepository.findAll().size();

        // Create the EmployeeLocalTax with an existing ID
        employeeLocalTax.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeLocalTaxMockMvc.perform(post("/api/employee-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocalTax)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLocalTax in the database
        List<EmployeeLocalTax> employeeLocalTaxList = employeeLocalTaxRepository.findAll();
        assertThat(employeeLocalTaxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeLocalTaxes() throws Exception {
        // Initialize the database
        employeeLocalTaxRepository.saveAndFlush(employeeLocalTax);

        // Get all the employeeLocalTaxList
        restEmployeeLocalTaxMockMvc.perform(get("/api/employee-local-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLocalTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].filingStatus").value(hasItem(DEFAULT_FILING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].exemptFromWithHolding").value(hasItem(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue())))
            .andExpect(jsonPath("$.[*].allowances").value(hasItem(DEFAULT_ALLOWANCES)))
            .andExpect(jsonPath("$.[*].extraWithHolding").value(hasItem(DEFAULT_EXTRA_WITH_HOLDING.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeLocalTax() throws Exception {
        // Initialize the database
        employeeLocalTaxRepository.saveAndFlush(employeeLocalTax);

        // Get the employeeLocalTax
        restEmployeeLocalTaxMockMvc.perform(get("/api/employee-local-taxes/{id}", employeeLocalTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeLocalTax.getId().intValue()))
            .andExpect(jsonPath("$.filingStatus").value(DEFAULT_FILING_STATUS.toString()))
            .andExpect(jsonPath("$.exemptFromWithHolding").value(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue()))
            .andExpect(jsonPath("$.allowances").value(DEFAULT_ALLOWANCES))
            .andExpect(jsonPath("$.extraWithHolding").value(DEFAULT_EXTRA_WITH_HOLDING.doubleValue()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeLocalTax() throws Exception {
        // Get the employeeLocalTax
        restEmployeeLocalTaxMockMvc.perform(get("/api/employee-local-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLocalTax() throws Exception {
        // Initialize the database
        employeeLocalTaxRepository.saveAndFlush(employeeLocalTax);
        int databaseSizeBeforeUpdate = employeeLocalTaxRepository.findAll().size();

        // Update the employeeLocalTax
        EmployeeLocalTax updatedEmployeeLocalTax = employeeLocalTaxRepository.findOne(employeeLocalTax.getId());
        // Disconnect from session so that the updates on updatedEmployeeLocalTax are not directly saved in db
        em.detach(updatedEmployeeLocalTax);
        updatedEmployeeLocalTax
            .filingStatus(UPDATED_FILING_STATUS)
            .exemptFromWithHolding(UPDATED_EXEMPT_FROM_WITH_HOLDING)
            .allowances(UPDATED_ALLOWANCES)
            .extraWithHolding(UPDATED_EXTRA_WITH_HOLDING)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restEmployeeLocalTaxMockMvc.perform(put("/api/employee-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeLocalTax)))
            .andExpect(status().isOk());

        // Validate the EmployeeLocalTax in the database
        List<EmployeeLocalTax> employeeLocalTaxList = employeeLocalTaxRepository.findAll();
        assertThat(employeeLocalTaxList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLocalTax testEmployeeLocalTax = employeeLocalTaxList.get(employeeLocalTaxList.size() - 1);
        assertThat(testEmployeeLocalTax.getFilingStatus()).isEqualTo(UPDATED_FILING_STATUS);
        assertThat(testEmployeeLocalTax.isExemptFromWithHolding()).isEqualTo(UPDATED_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testEmployeeLocalTax.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testEmployeeLocalTax.getExtraWithHolding()).isEqualTo(UPDATED_EXTRA_WITH_HOLDING);
        assertThat(testEmployeeLocalTax.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeeLocalTax.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeLocalTax.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeLocalTax() throws Exception {
        int databaseSizeBeforeUpdate = employeeLocalTaxRepository.findAll().size();

        // Create the EmployeeLocalTax

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeLocalTaxMockMvc.perform(put("/api/employee-local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeLocalTax)))
            .andExpect(status().isCreated());

        // Validate the EmployeeLocalTax in the database
        List<EmployeeLocalTax> employeeLocalTaxList = employeeLocalTaxRepository.findAll();
        assertThat(employeeLocalTaxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeLocalTax() throws Exception {
        // Initialize the database
        employeeLocalTaxRepository.saveAndFlush(employeeLocalTax);
        int databaseSizeBeforeDelete = employeeLocalTaxRepository.findAll().size();

        // Get the employeeLocalTax
        restEmployeeLocalTaxMockMvc.perform(delete("/api/employee-local-taxes/{id}", employeeLocalTax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeLocalTax> employeeLocalTaxList = employeeLocalTaxRepository.findAll();
        assertThat(employeeLocalTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLocalTax.class);
        EmployeeLocalTax employeeLocalTax1 = new EmployeeLocalTax();
        employeeLocalTax1.setId(1L);
        EmployeeLocalTax employeeLocalTax2 = new EmployeeLocalTax();
        employeeLocalTax2.setId(employeeLocalTax1.getId());
        assertThat(employeeLocalTax1).isEqualTo(employeeLocalTax2);
        employeeLocalTax2.setId(2L);
        assertThat(employeeLocalTax1).isNotEqualTo(employeeLocalTax2);
        employeeLocalTax1.setId(null);
        assertThat(employeeLocalTax1).isNotEqualTo(employeeLocalTax2);
    }
}
