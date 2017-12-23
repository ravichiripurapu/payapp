package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyEarning;
import com.pay.app.repository.CompanyEarningRepository;
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
 * Test class for the CompanyEarningResource REST controller.
 *
 * @see CompanyEarningResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyEarningResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyEarningRepository companyEarningRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyEarningMockMvc;

    private CompanyEarning companyEarning;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyEarningResource companyEarningResource = new CompanyEarningResource(companyEarningRepository);
        this.restCompanyEarningMockMvc = MockMvcBuilders.standaloneSetup(companyEarningResource)
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
    public static CompanyEarning createEntity(EntityManager em) {
        CompanyEarning companyEarning = new CompanyEarning()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .abbreviation(DEFAULT_ABBREVIATION)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyEarning;
    }

    @Before
    public void initTest() {
        companyEarning = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyEarning() throws Exception {
        int databaseSizeBeforeCreate = companyEarningRepository.findAll().size();

        // Create the CompanyEarning
        restCompanyEarningMockMvc.perform(post("/api/company-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEarning)))
            .andExpect(status().isCreated());

        // Validate the CompanyEarning in the database
        List<CompanyEarning> companyEarningList = companyEarningRepository.findAll();
        assertThat(companyEarningList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyEarning testCompanyEarning = companyEarningList.get(companyEarningList.size() - 1);
        assertThat(testCompanyEarning.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyEarning.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testCompanyEarning.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
        assertThat(testCompanyEarning.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyEarning.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyEarning.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyEarningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyEarningRepository.findAll().size();

        // Create the CompanyEarning with an existing ID
        companyEarning.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyEarningMockMvc.perform(post("/api/company-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEarning)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyEarning in the database
        List<CompanyEarning> companyEarningList = companyEarningRepository.findAll();
        assertThat(companyEarningList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyEarnings() throws Exception {
        // Initialize the database
        companyEarningRepository.saveAndFlush(companyEarning);

        // Get all the companyEarningList
        restCompanyEarningMockMvc.perform(get("/api/company-earnings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEarning.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyEarning() throws Exception {
        // Initialize the database
        companyEarningRepository.saveAndFlush(companyEarning);

        // Get the companyEarning
        restCompanyEarningMockMvc.perform(get("/api/company-earnings/{id}", companyEarning.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyEarning.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyEarning() throws Exception {
        // Get the companyEarning
        restCompanyEarningMockMvc.perform(get("/api/company-earnings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyEarning() throws Exception {
        // Initialize the database
        companyEarningRepository.saveAndFlush(companyEarning);
        int databaseSizeBeforeUpdate = companyEarningRepository.findAll().size();

        // Update the companyEarning
        CompanyEarning updatedCompanyEarning = companyEarningRepository.findOne(companyEarning.getId());
        // Disconnect from session so that the updates on updatedCompanyEarning are not directly saved in db
        em.detach(updatedCompanyEarning);
        updatedCompanyEarning
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .abbreviation(UPDATED_ABBREVIATION)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyEarningMockMvc.perform(put("/api/company-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyEarning)))
            .andExpect(status().isOk());

        // Validate the CompanyEarning in the database
        List<CompanyEarning> companyEarningList = companyEarningRepository.findAll();
        assertThat(companyEarningList).hasSize(databaseSizeBeforeUpdate);
        CompanyEarning testCompanyEarning = companyEarningList.get(companyEarningList.size() - 1);
        assertThat(testCompanyEarning.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyEarning.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testCompanyEarning.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
        assertThat(testCompanyEarning.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyEarning.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyEarning.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyEarning() throws Exception {
        int databaseSizeBeforeUpdate = companyEarningRepository.findAll().size();

        // Create the CompanyEarning

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyEarningMockMvc.perform(put("/api/company-earnings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyEarning)))
            .andExpect(status().isCreated());

        // Validate the CompanyEarning in the database
        List<CompanyEarning> companyEarningList = companyEarningRepository.findAll();
        assertThat(companyEarningList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyEarning() throws Exception {
        // Initialize the database
        companyEarningRepository.saveAndFlush(companyEarning);
        int databaseSizeBeforeDelete = companyEarningRepository.findAll().size();

        // Get the companyEarning
        restCompanyEarningMockMvc.perform(delete("/api/company-earnings/{id}", companyEarning.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyEarning> companyEarningList = companyEarningRepository.findAll();
        assertThat(companyEarningList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEarning.class);
        CompanyEarning companyEarning1 = new CompanyEarning();
        companyEarning1.setId(1L);
        CompanyEarning companyEarning2 = new CompanyEarning();
        companyEarning2.setId(companyEarning1.getId());
        assertThat(companyEarning1).isEqualTo(companyEarning2);
        companyEarning2.setId(2L);
        assertThat(companyEarning1).isNotEqualTo(companyEarning2);
        companyEarning1.setId(null);
        assertThat(companyEarning1).isNotEqualTo(companyEarning2);
    }
}
