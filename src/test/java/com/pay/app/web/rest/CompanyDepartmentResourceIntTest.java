package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyDepartment;
import com.pay.app.repository.CompanyDepartmentRepository;
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
 * Test class for the CompanyDepartmentResource REST controller.
 *
 * @see CompanyDepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyDepartmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyDepartmentRepository companyDepartmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyDepartmentMockMvc;

    private CompanyDepartment companyDepartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyDepartmentResource companyDepartmentResource = new CompanyDepartmentResource(companyDepartmentRepository);
        this.restCompanyDepartmentMockMvc = MockMvcBuilders.standaloneSetup(companyDepartmentResource)
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
    public static CompanyDepartment createEntity(EntityManager em) {
        CompanyDepartment companyDepartment = new CompanyDepartment()
            .name(DEFAULT_NAME)
            .departmentCode(DEFAULT_DEPARTMENT_CODE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyDepartment;
    }

    @Before
    public void initTest() {
        companyDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDepartment() throws Exception {
        int databaseSizeBeforeCreate = companyDepartmentRepository.findAll().size();

        // Create the CompanyDepartment
        restCompanyDepartmentMockMvc.perform(post("/api/company-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDepartment)))
            .andExpect(status().isCreated());

        // Validate the CompanyDepartment in the database
        List<CompanyDepartment> companyDepartmentList = companyDepartmentRepository.findAll();
        assertThat(companyDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDepartment testCompanyDepartment = companyDepartmentList.get(companyDepartmentList.size() - 1);
        assertThat(testCompanyDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyDepartment.getDepartmentCode()).isEqualTo(DEFAULT_DEPARTMENT_CODE);
        assertThat(testCompanyDepartment.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyDepartment.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyDepartment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyDepartmentRepository.findAll().size();

        // Create the CompanyDepartment with an existing ID
        companyDepartment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyDepartmentMockMvc.perform(post("/api/company-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDepartment)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyDepartment in the database
        List<CompanyDepartment> companyDepartmentList = companyDepartmentRepository.findAll();
        assertThat(companyDepartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyDepartments() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);

        // Get all the companyDepartmentList
        restCompanyDepartmentMockMvc.perform(get("/api/company-departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyDepartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].departmentCode").value(hasItem(DEFAULT_DEPARTMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyDepartment() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);

        // Get the companyDepartment
        restCompanyDepartmentMockMvc.perform(get("/api/company-departments/{id}", companyDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDepartment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.departmentCode").value(DEFAULT_DEPARTMENT_CODE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDepartment() throws Exception {
        // Get the companyDepartment
        restCompanyDepartmentMockMvc.perform(get("/api/company-departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDepartment() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);
        int databaseSizeBeforeUpdate = companyDepartmentRepository.findAll().size();

        // Update the companyDepartment
        CompanyDepartment updatedCompanyDepartment = companyDepartmentRepository.findOne(companyDepartment.getId());
        // Disconnect from session so that the updates on updatedCompanyDepartment are not directly saved in db
        em.detach(updatedCompanyDepartment);
        updatedCompanyDepartment
            .name(UPDATED_NAME)
            .departmentCode(UPDATED_DEPARTMENT_CODE)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyDepartmentMockMvc.perform(put("/api/company-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyDepartment)))
            .andExpect(status().isOk());

        // Validate the CompanyDepartment in the database
        List<CompanyDepartment> companyDepartmentList = companyDepartmentRepository.findAll();
        assertThat(companyDepartmentList).hasSize(databaseSizeBeforeUpdate);
        CompanyDepartment testCompanyDepartment = companyDepartmentList.get(companyDepartmentList.size() - 1);
        assertThat(testCompanyDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyDepartment.getDepartmentCode()).isEqualTo(UPDATED_DEPARTMENT_CODE);
        assertThat(testCompanyDepartment.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyDepartment.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyDepartment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyDepartment() throws Exception {
        int databaseSizeBeforeUpdate = companyDepartmentRepository.findAll().size();

        // Create the CompanyDepartment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyDepartmentMockMvc.perform(put("/api/company-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDepartment)))
            .andExpect(status().isCreated());

        // Validate the CompanyDepartment in the database
        List<CompanyDepartment> companyDepartmentList = companyDepartmentRepository.findAll();
        assertThat(companyDepartmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyDepartment() throws Exception {
        // Initialize the database
        companyDepartmentRepository.saveAndFlush(companyDepartment);
        int databaseSizeBeforeDelete = companyDepartmentRepository.findAll().size();

        // Get the companyDepartment
        restCompanyDepartmentMockMvc.perform(delete("/api/company-departments/{id}", companyDepartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDepartment> companyDepartmentList = companyDepartmentRepository.findAll();
        assertThat(companyDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDepartment.class);
        CompanyDepartment companyDepartment1 = new CompanyDepartment();
        companyDepartment1.setId(1L);
        CompanyDepartment companyDepartment2 = new CompanyDepartment();
        companyDepartment2.setId(companyDepartment1.getId());
        assertThat(companyDepartment1).isEqualTo(companyDepartment2);
        companyDepartment2.setId(2L);
        assertThat(companyDepartment1).isNotEqualTo(companyDepartment2);
        companyDepartment1.setId(null);
        assertThat(companyDepartment1).isNotEqualTo(companyDepartment2);
    }
}
