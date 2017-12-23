package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EmployeeStateTax;
import com.pay.app.repository.EmployeeStateTaxRepository;
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
 * Test class for the EmployeeStateTaxResource REST controller.
 *
 * @see EmployeeStateTaxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeeStateTaxResourceIntTest {

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_FILING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_FILING_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXEMPT_FROM_WITH_HOLDING = false;
    private static final Boolean UPDATED_EXEMPT_FROM_WITH_HOLDING = true;

    private static final Boolean DEFAULT_EXEMPT_FROM_SUI = false;
    private static final Boolean UPDATED_EXEMPT_FROM_SUI = true;

    private static final Boolean DEFAULT_EXEMPT_FROM_FUTA = false;
    private static final Boolean UPDATED_EXEMPT_FROM_FUTA = true;

    private static final String DEFAULT_FUTA_EXEMPT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_FUTA_EXEMPT_REASON = "BBBBBBBBBB";

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
    private EmployeeStateTaxRepository employeeStateTaxRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeStateTaxMockMvc;

    private EmployeeStateTax employeeStateTax;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeStateTaxResource employeeStateTaxResource = new EmployeeStateTaxResource(employeeStateTaxRepository);
        this.restEmployeeStateTaxMockMvc = MockMvcBuilders.standaloneSetup(employeeStateTaxResource)
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
    public static EmployeeStateTax createEntity(EntityManager em) {
        EmployeeStateTax employeeStateTax = new EmployeeStateTax()
            .state(DEFAULT_STATE)
            .filingStatus(DEFAULT_FILING_STATUS)
            .exemptFromWithHolding(DEFAULT_EXEMPT_FROM_WITH_HOLDING)
            .exemptFromSUI(DEFAULT_EXEMPT_FROM_SUI)
            .exemptFromFUTA(DEFAULT_EXEMPT_FROM_FUTA)
            .futaExemptReason(DEFAULT_FUTA_EXEMPT_REASON)
            .allowances(DEFAULT_ALLOWANCES)
            .extraWithHolding(DEFAULT_EXTRA_WITH_HOLDING)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return employeeStateTax;
    }

    @Before
    public void initTest() {
        employeeStateTax = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeStateTax() throws Exception {
        int databaseSizeBeforeCreate = employeeStateTaxRepository.findAll().size();

        // Create the EmployeeStateTax
        restEmployeeStateTaxMockMvc.perform(post("/api/employee-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeStateTax)))
            .andExpect(status().isCreated());

        // Validate the EmployeeStateTax in the database
        List<EmployeeStateTax> employeeStateTaxList = employeeStateTaxRepository.findAll();
        assertThat(employeeStateTaxList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeStateTax testEmployeeStateTax = employeeStateTaxList.get(employeeStateTaxList.size() - 1);
        assertThat(testEmployeeStateTax.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEmployeeStateTax.getFilingStatus()).isEqualTo(DEFAULT_FILING_STATUS);
        assertThat(testEmployeeStateTax.isExemptFromWithHolding()).isEqualTo(DEFAULT_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testEmployeeStateTax.isExemptFromSUI()).isEqualTo(DEFAULT_EXEMPT_FROM_SUI);
        assertThat(testEmployeeStateTax.isExemptFromFUTA()).isEqualTo(DEFAULT_EXEMPT_FROM_FUTA);
        assertThat(testEmployeeStateTax.getFutaExemptReason()).isEqualTo(DEFAULT_FUTA_EXEMPT_REASON);
        assertThat(testEmployeeStateTax.getAllowances()).isEqualTo(DEFAULT_ALLOWANCES);
        assertThat(testEmployeeStateTax.getExtraWithHolding()).isEqualTo(DEFAULT_EXTRA_WITH_HOLDING);
        assertThat(testEmployeeStateTax.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeeStateTax.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeStateTax.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeStateTaxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeStateTaxRepository.findAll().size();

        // Create the EmployeeStateTax with an existing ID
        employeeStateTax.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeStateTaxMockMvc.perform(post("/api/employee-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeStateTax)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeStateTax in the database
        List<EmployeeStateTax> employeeStateTaxList = employeeStateTaxRepository.findAll();
        assertThat(employeeStateTaxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeStateTaxes() throws Exception {
        // Initialize the database
        employeeStateTaxRepository.saveAndFlush(employeeStateTax);

        // Get all the employeeStateTaxList
        restEmployeeStateTaxMockMvc.perform(get("/api/employee-state-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeStateTax.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].filingStatus").value(hasItem(DEFAULT_FILING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].exemptFromWithHolding").value(hasItem(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue())))
            .andExpect(jsonPath("$.[*].exemptFromSUI").value(hasItem(DEFAULT_EXEMPT_FROM_SUI.booleanValue())))
            .andExpect(jsonPath("$.[*].exemptFromFUTA").value(hasItem(DEFAULT_EXEMPT_FROM_FUTA.booleanValue())))
            .andExpect(jsonPath("$.[*].futaExemptReason").value(hasItem(DEFAULT_FUTA_EXEMPT_REASON.toString())))
            .andExpect(jsonPath("$.[*].allowances").value(hasItem(DEFAULT_ALLOWANCES)))
            .andExpect(jsonPath("$.[*].extraWithHolding").value(hasItem(DEFAULT_EXTRA_WITH_HOLDING.doubleValue())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeStateTax() throws Exception {
        // Initialize the database
        employeeStateTaxRepository.saveAndFlush(employeeStateTax);

        // Get the employeeStateTax
        restEmployeeStateTaxMockMvc.perform(get("/api/employee-state-taxes/{id}", employeeStateTax.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeStateTax.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.filingStatus").value(DEFAULT_FILING_STATUS.toString()))
            .andExpect(jsonPath("$.exemptFromWithHolding").value(DEFAULT_EXEMPT_FROM_WITH_HOLDING.booleanValue()))
            .andExpect(jsonPath("$.exemptFromSUI").value(DEFAULT_EXEMPT_FROM_SUI.booleanValue()))
            .andExpect(jsonPath("$.exemptFromFUTA").value(DEFAULT_EXEMPT_FROM_FUTA.booleanValue()))
            .andExpect(jsonPath("$.futaExemptReason").value(DEFAULT_FUTA_EXEMPT_REASON.toString()))
            .andExpect(jsonPath("$.allowances").value(DEFAULT_ALLOWANCES))
            .andExpect(jsonPath("$.extraWithHolding").value(DEFAULT_EXTRA_WITH_HOLDING.doubleValue()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeStateTax() throws Exception {
        // Get the employeeStateTax
        restEmployeeStateTaxMockMvc.perform(get("/api/employee-state-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeStateTax() throws Exception {
        // Initialize the database
        employeeStateTaxRepository.saveAndFlush(employeeStateTax);
        int databaseSizeBeforeUpdate = employeeStateTaxRepository.findAll().size();

        // Update the employeeStateTax
        EmployeeStateTax updatedEmployeeStateTax = employeeStateTaxRepository.findOne(employeeStateTax.getId());
        // Disconnect from session so that the updates on updatedEmployeeStateTax are not directly saved in db
        em.detach(updatedEmployeeStateTax);
        updatedEmployeeStateTax
            .state(UPDATED_STATE)
            .filingStatus(UPDATED_FILING_STATUS)
            .exemptFromWithHolding(UPDATED_EXEMPT_FROM_WITH_HOLDING)
            .exemptFromSUI(UPDATED_EXEMPT_FROM_SUI)
            .exemptFromFUTA(UPDATED_EXEMPT_FROM_FUTA)
            .futaExemptReason(UPDATED_FUTA_EXEMPT_REASON)
            .allowances(UPDATED_ALLOWANCES)
            .extraWithHolding(UPDATED_EXTRA_WITH_HOLDING)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restEmployeeStateTaxMockMvc.perform(put("/api/employee-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeStateTax)))
            .andExpect(status().isOk());

        // Validate the EmployeeStateTax in the database
        List<EmployeeStateTax> employeeStateTaxList = employeeStateTaxRepository.findAll();
        assertThat(employeeStateTaxList).hasSize(databaseSizeBeforeUpdate);
        EmployeeStateTax testEmployeeStateTax = employeeStateTaxList.get(employeeStateTaxList.size() - 1);
        assertThat(testEmployeeStateTax.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEmployeeStateTax.getFilingStatus()).isEqualTo(UPDATED_FILING_STATUS);
        assertThat(testEmployeeStateTax.isExemptFromWithHolding()).isEqualTo(UPDATED_EXEMPT_FROM_WITH_HOLDING);
        assertThat(testEmployeeStateTax.isExemptFromSUI()).isEqualTo(UPDATED_EXEMPT_FROM_SUI);
        assertThat(testEmployeeStateTax.isExemptFromFUTA()).isEqualTo(UPDATED_EXEMPT_FROM_FUTA);
        assertThat(testEmployeeStateTax.getFutaExemptReason()).isEqualTo(UPDATED_FUTA_EXEMPT_REASON);
        assertThat(testEmployeeStateTax.getAllowances()).isEqualTo(UPDATED_ALLOWANCES);
        assertThat(testEmployeeStateTax.getExtraWithHolding()).isEqualTo(UPDATED_EXTRA_WITH_HOLDING);
        assertThat(testEmployeeStateTax.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeeStateTax.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeStateTax.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeStateTax() throws Exception {
        int databaseSizeBeforeUpdate = employeeStateTaxRepository.findAll().size();

        // Create the EmployeeStateTax

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeStateTaxMockMvc.perform(put("/api/employee-state-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeStateTax)))
            .andExpect(status().isCreated());

        // Validate the EmployeeStateTax in the database
        List<EmployeeStateTax> employeeStateTaxList = employeeStateTaxRepository.findAll();
        assertThat(employeeStateTaxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeStateTax() throws Exception {
        // Initialize the database
        employeeStateTaxRepository.saveAndFlush(employeeStateTax);
        int databaseSizeBeforeDelete = employeeStateTaxRepository.findAll().size();

        // Get the employeeStateTax
        restEmployeeStateTaxMockMvc.perform(delete("/api/employee-state-taxes/{id}", employeeStateTax.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeStateTax> employeeStateTaxList = employeeStateTaxRepository.findAll();
        assertThat(employeeStateTaxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeStateTax.class);
        EmployeeStateTax employeeStateTax1 = new EmployeeStateTax();
        employeeStateTax1.setId(1L);
        EmployeeStateTax employeeStateTax2 = new EmployeeStateTax();
        employeeStateTax2.setId(employeeStateTax1.getId());
        assertThat(employeeStateTax1).isEqualTo(employeeStateTax2);
        employeeStateTax2.setId(2L);
        assertThat(employeeStateTax1).isNotEqualTo(employeeStateTax2);
        employeeStateTax1.setId(null);
        assertThat(employeeStateTax1).isNotEqualTo(employeeStateTax2);
    }
}
