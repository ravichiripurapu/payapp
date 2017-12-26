package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyLocation;
import com.pay.app.repository.CompanyLocationRepository;
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
 * Test class for the CompanyLocationResource REST controller.
 *
 * @see CompanyLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyLocationResourceIntTest {

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HEADQUARTERS = false;
    private static final Boolean UPDATED_HEADQUARTERS = true;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyLocationRepository companyLocationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyLocationMockMvc;

    private CompanyLocation companyLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyLocationResource companyLocationResource = new CompanyLocationResource(companyLocationRepository);
        this.restCompanyLocationMockMvc = MockMvcBuilders.standaloneSetup(companyLocationResource)
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
    public static CompanyLocation createEntity(EntityManager em) {
        CompanyLocation companyLocation = new CompanyLocation()
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .zip(DEFAULT_ZIP)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .headquarters(DEFAULT_HEADQUARTERS)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyLocation;
    }

    @Before
    public void initTest() {
        companyLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyLocation() throws Exception {
        int databaseSizeBeforeCreate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation
        restCompanyLocationMockMvc.perform(post("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocation)))
            .andExpect(status().isCreated());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyLocation testCompanyLocation = companyLocationList.get(companyLocationList.size() - 1);
        assertThat(testCompanyLocation.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testCompanyLocation.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testCompanyLocation.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testCompanyLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCompanyLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCompanyLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCompanyLocation.isHeadquarters()).isEqualTo(DEFAULT_HEADQUARTERS);
        assertThat(testCompanyLocation.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyLocation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation with an existing ID
        companyLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyLocationMockMvc.perform(post("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocation)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyLocations() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        // Get all the companyLocationList
        restCompanyLocationMockMvc.perform(get("/api/company-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].headquarters").value(hasItem(DEFAULT_HEADQUARTERS.booleanValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);

        // Get the companyLocation
        restCompanyLocationMockMvc.perform(get("/api/company-locations/{id}", companyLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyLocation.getId().intValue()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.headquarters").value(DEFAULT_HEADQUARTERS.booleanValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyLocation() throws Exception {
        // Get the companyLocation
        restCompanyLocationMockMvc.perform(get("/api/company-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);
        int databaseSizeBeforeUpdate = companyLocationRepository.findAll().size();

        // Update the companyLocation
        CompanyLocation updatedCompanyLocation = companyLocationRepository.findOne(companyLocation.getId());
        // Disconnect from session so that the updates on updatedCompanyLocation are not directly saved in db
        em.detach(updatedCompanyLocation);
        updatedCompanyLocation
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .zip(UPDATED_ZIP)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .headquarters(UPDATED_HEADQUARTERS)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyLocationMockMvc.perform(put("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyLocation)))
            .andExpect(status().isOk());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeUpdate);
        CompanyLocation testCompanyLocation = companyLocationList.get(companyLocationList.size() - 1);
        assertThat(testCompanyLocation.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testCompanyLocation.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testCompanyLocation.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testCompanyLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCompanyLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCompanyLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCompanyLocation.isHeadquarters()).isEqualTo(UPDATED_HEADQUARTERS);
        assertThat(testCompanyLocation.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyLocation() throws Exception {
        int databaseSizeBeforeUpdate = companyLocationRepository.findAll().size();

        // Create the CompanyLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyLocationMockMvc.perform(put("/api/company-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyLocation)))
            .andExpect(status().isCreated());

        // Validate the CompanyLocation in the database
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyLocation() throws Exception {
        // Initialize the database
        companyLocationRepository.saveAndFlush(companyLocation);
        int databaseSizeBeforeDelete = companyLocationRepository.findAll().size();

        // Get the companyLocation
        restCompanyLocationMockMvc.perform(delete("/api/company-locations/{id}", companyLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyLocation> companyLocationList = companyLocationRepository.findAll();
        assertThat(companyLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLocation.class);
        CompanyLocation companyLocation1 = new CompanyLocation();
        companyLocation1.setId(1L);
        CompanyLocation companyLocation2 = new CompanyLocation();
        companyLocation2.setId(companyLocation1.getId());
        assertThat(companyLocation1).isEqualTo(companyLocation2);
        companyLocation2.setId(2L);
        assertThat(companyLocation1).isNotEqualTo(companyLocation2);
        companyLocation1.setId(null);
        assertThat(companyLocation1).isNotEqualTo(companyLocation2);
    }
}
