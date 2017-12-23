package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.PayrollFrequency;
import com.pay.app.repository.PayrollFrequencyRepository;
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
import java.util.List;

import static com.pay.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PayrollFrequencyResource REST controller.
 *
 * @see PayrollFrequencyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class PayrollFrequencyResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PayrollFrequencyRepository payrollFrequencyRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPayrollFrequencyMockMvc;

    private PayrollFrequency payrollFrequency;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PayrollFrequencyResource payrollFrequencyResource = new PayrollFrequencyResource(payrollFrequencyRepository);
        this.restPayrollFrequencyMockMvc = MockMvcBuilders.standaloneSetup(payrollFrequencyResource)
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
    public static PayrollFrequency createEntity(EntityManager em) {
        PayrollFrequency payrollFrequency = new PayrollFrequency()
            .name(DEFAULT_NAME);
        return payrollFrequency;
    }

    @Before
    public void initTest() {
        payrollFrequency = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayrollFrequency() throws Exception {
        int databaseSizeBeforeCreate = payrollFrequencyRepository.findAll().size();

        // Create the PayrollFrequency
        restPayrollFrequencyMockMvc.perform(post("/api/payroll-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollFrequency)))
            .andExpect(status().isCreated());

        // Validate the PayrollFrequency in the database
        List<PayrollFrequency> payrollFrequencyList = payrollFrequencyRepository.findAll();
        assertThat(payrollFrequencyList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollFrequency testPayrollFrequency = payrollFrequencyList.get(payrollFrequencyList.size() - 1);
        assertThat(testPayrollFrequency.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPayrollFrequencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = payrollFrequencyRepository.findAll().size();

        // Create the PayrollFrequency with an existing ID
        payrollFrequency.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollFrequencyMockMvc.perform(post("/api/payroll-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollFrequency)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollFrequency in the database
        List<PayrollFrequency> payrollFrequencyList = payrollFrequencyRepository.findAll();
        assertThat(payrollFrequencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPayrollFrequencies() throws Exception {
        // Initialize the database
        payrollFrequencyRepository.saveAndFlush(payrollFrequency);

        // Get all the payrollFrequencyList
        restPayrollFrequencyMockMvc.perform(get("/api/payroll-frequencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollFrequency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPayrollFrequency() throws Exception {
        // Initialize the database
        payrollFrequencyRepository.saveAndFlush(payrollFrequency);

        // Get the payrollFrequency
        restPayrollFrequencyMockMvc.perform(get("/api/payroll-frequencies/{id}", payrollFrequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(payrollFrequency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayrollFrequency() throws Exception {
        // Get the payrollFrequency
        restPayrollFrequencyMockMvc.perform(get("/api/payroll-frequencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayrollFrequency() throws Exception {
        // Initialize the database
        payrollFrequencyRepository.saveAndFlush(payrollFrequency);
        int databaseSizeBeforeUpdate = payrollFrequencyRepository.findAll().size();

        // Update the payrollFrequency
        PayrollFrequency updatedPayrollFrequency = payrollFrequencyRepository.findOne(payrollFrequency.getId());
        // Disconnect from session so that the updates on updatedPayrollFrequency are not directly saved in db
        em.detach(updatedPayrollFrequency);
        updatedPayrollFrequency
            .name(UPDATED_NAME);

        restPayrollFrequencyMockMvc.perform(put("/api/payroll-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayrollFrequency)))
            .andExpect(status().isOk());

        // Validate the PayrollFrequency in the database
        List<PayrollFrequency> payrollFrequencyList = payrollFrequencyRepository.findAll();
        assertThat(payrollFrequencyList).hasSize(databaseSizeBeforeUpdate);
        PayrollFrequency testPayrollFrequency = payrollFrequencyList.get(payrollFrequencyList.size() - 1);
        assertThat(testPayrollFrequency.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPayrollFrequency() throws Exception {
        int databaseSizeBeforeUpdate = payrollFrequencyRepository.findAll().size();

        // Create the PayrollFrequency

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPayrollFrequencyMockMvc.perform(put("/api/payroll-frequencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(payrollFrequency)))
            .andExpect(status().isCreated());

        // Validate the PayrollFrequency in the database
        List<PayrollFrequency> payrollFrequencyList = payrollFrequencyRepository.findAll();
        assertThat(payrollFrequencyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePayrollFrequency() throws Exception {
        // Initialize the database
        payrollFrequencyRepository.saveAndFlush(payrollFrequency);
        int databaseSizeBeforeDelete = payrollFrequencyRepository.findAll().size();

        // Get the payrollFrequency
        restPayrollFrequencyMockMvc.perform(delete("/api/payroll-frequencies/{id}", payrollFrequency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PayrollFrequency> payrollFrequencyList = payrollFrequencyRepository.findAll();
        assertThat(payrollFrequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollFrequency.class);
        PayrollFrequency payrollFrequency1 = new PayrollFrequency();
        payrollFrequency1.setId(1L);
        PayrollFrequency payrollFrequency2 = new PayrollFrequency();
        payrollFrequency2.setId(payrollFrequency1.getId());
        assertThat(payrollFrequency1).isEqualTo(payrollFrequency2);
        payrollFrequency2.setId(2L);
        assertThat(payrollFrequency1).isNotEqualTo(payrollFrequency2);
        payrollFrequency1.setId(null);
        assertThat(payrollFrequency1).isNotEqualTo(payrollFrequency2);
    }
}
