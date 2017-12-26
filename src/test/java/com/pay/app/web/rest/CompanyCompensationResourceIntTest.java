package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyCompensation;
import com.pay.app.repository.CompanyCompensationRepository;
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
 * Test class for the CompanyCompensationResource REST controller.
 *
 * @see CompanyCompensationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyCompensationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_COMPENSATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPENSATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyCompensationRepository companyCompensationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyCompensationMockMvc;

    private CompanyCompensation companyCompensation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyCompensationResource companyCompensationResource = new CompanyCompensationResource(companyCompensationRepository);
        this.restCompanyCompensationMockMvc = MockMvcBuilders.standaloneSetup(companyCompensationResource)
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
    public static CompanyCompensation createEntity(EntityManager em) {
        CompanyCompensation companyCompensation = new CompanyCompensation()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .compensationType(DEFAULT_COMPENSATION_TYPE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyCompensation;
    }

    @Before
    public void initTest() {
        companyCompensation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyCompensation() throws Exception {
        int databaseSizeBeforeCreate = companyCompensationRepository.findAll().size();

        // Create the CompanyCompensation
        restCompanyCompensationMockMvc.perform(post("/api/company-compensations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCompensation)))
            .andExpect(status().isCreated());

        // Validate the CompanyCompensation in the database
        List<CompanyCompensation> companyCompensationList = companyCompensationRepository.findAll();
        assertThat(companyCompensationList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyCompensation testCompanyCompensation = companyCompensationList.get(companyCompensationList.size() - 1);
        assertThat(testCompanyCompensation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyCompensation.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testCompanyCompensation.getCompensationType()).isEqualTo(DEFAULT_COMPENSATION_TYPE);
        assertThat(testCompanyCompensation.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyCompensation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyCompensation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyCompensationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyCompensationRepository.findAll().size();

        // Create the CompanyCompensation with an existing ID
        companyCompensation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCompensationMockMvc.perform(post("/api/company-compensations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCompensation)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyCompensation in the database
        List<CompanyCompensation> companyCompensationList = companyCompensationRepository.findAll();
        assertThat(companyCompensationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyCompensations() throws Exception {
        // Initialize the database
        companyCompensationRepository.saveAndFlush(companyCompensation);

        // Get all the companyCompensationList
        restCompanyCompensationMockMvc.perform(get("/api/company-compensations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCompensation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].compensationType").value(hasItem(DEFAULT_COMPENSATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyCompensation() throws Exception {
        // Initialize the database
        companyCompensationRepository.saveAndFlush(companyCompensation);

        // Get the companyCompensation
        restCompanyCompensationMockMvc.perform(get("/api/company-compensations/{id}", companyCompensation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyCompensation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.compensationType").value(DEFAULT_COMPENSATION_TYPE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyCompensation() throws Exception {
        // Get the companyCompensation
        restCompanyCompensationMockMvc.perform(get("/api/company-compensations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyCompensation() throws Exception {
        // Initialize the database
        companyCompensationRepository.saveAndFlush(companyCompensation);
        int databaseSizeBeforeUpdate = companyCompensationRepository.findAll().size();

        // Update the companyCompensation
        CompanyCompensation updatedCompanyCompensation = companyCompensationRepository.findOne(companyCompensation.getId());
        // Disconnect from session so that the updates on updatedCompanyCompensation are not directly saved in db
        em.detach(updatedCompanyCompensation);
        updatedCompanyCompensation
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .compensationType(UPDATED_COMPENSATION_TYPE)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyCompensationMockMvc.perform(put("/api/company-compensations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyCompensation)))
            .andExpect(status().isOk());

        // Validate the CompanyCompensation in the database
        List<CompanyCompensation> companyCompensationList = companyCompensationRepository.findAll();
        assertThat(companyCompensationList).hasSize(databaseSizeBeforeUpdate);
        CompanyCompensation testCompanyCompensation = companyCompensationList.get(companyCompensationList.size() - 1);
        assertThat(testCompanyCompensation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyCompensation.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCompanyCompensation.getCompensationType()).isEqualTo(UPDATED_COMPENSATION_TYPE);
        assertThat(testCompanyCompensation.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyCompensation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyCompensation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyCompensation() throws Exception {
        int databaseSizeBeforeUpdate = companyCompensationRepository.findAll().size();

        // Create the CompanyCompensation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyCompensationMockMvc.perform(put("/api/company-compensations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyCompensation)))
            .andExpect(status().isCreated());

        // Validate the CompanyCompensation in the database
        List<CompanyCompensation> companyCompensationList = companyCompensationRepository.findAll();
        assertThat(companyCompensationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyCompensation() throws Exception {
        // Initialize the database
        companyCompensationRepository.saveAndFlush(companyCompensation);
        int databaseSizeBeforeDelete = companyCompensationRepository.findAll().size();

        // Get the companyCompensation
        restCompanyCompensationMockMvc.perform(delete("/api/company-compensations/{id}", companyCompensation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyCompensation> companyCompensationList = companyCompensationRepository.findAll();
        assertThat(companyCompensationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCompensation.class);
        CompanyCompensation companyCompensation1 = new CompanyCompensation();
        companyCompensation1.setId(1L);
        CompanyCompensation companyCompensation2 = new CompanyCompensation();
        companyCompensation2.setId(companyCompensation1.getId());
        assertThat(companyCompensation1).isEqualTo(companyCompensation2);
        companyCompensation2.setId(2L);
        assertThat(companyCompensation1).isNotEqualTo(companyCompensation2);
        companyCompensation1.setId(null);
        assertThat(companyCompensation1).isNotEqualTo(companyCompensation2);
    }
}
