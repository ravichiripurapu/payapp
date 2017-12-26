package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EmployeeResidenceLocation;
import com.pay.app.repository.EmployeeResidenceLocationRepository;
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
 * Test class for the EmployeeResidenceLocationResource REST controller.
 *
 * @see EmployeeResidenceLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeeResidenceLocationResourceIntTest {

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

    private static final String DEFAULT_LOCATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private EmployeeResidenceLocationRepository employeeResidenceLocationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeResidenceLocationMockMvc;

    private EmployeeResidenceLocation employeeResidenceLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResidenceLocationResource employeeResidenceLocationResource = new EmployeeResidenceLocationResource(employeeResidenceLocationRepository);
        this.restEmployeeResidenceLocationMockMvc = MockMvcBuilders.standaloneSetup(employeeResidenceLocationResource)
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
    public static EmployeeResidenceLocation createEntity(EntityManager em) {
        EmployeeResidenceLocation employeeResidenceLocation = new EmployeeResidenceLocation()
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .zip(DEFAULT_ZIP)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .locationCode(DEFAULT_LOCATION_CODE)
            .employeeCode(DEFAULT_EMPLOYEE_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return employeeResidenceLocation;
    }

    @Before
    public void initTest() {
        employeeResidenceLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeResidenceLocation() throws Exception {
        int databaseSizeBeforeCreate = employeeResidenceLocationRepository.findAll().size();

        // Create the EmployeeResidenceLocation
        restEmployeeResidenceLocationMockMvc.perform(post("/api/employee-residence-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeResidenceLocation)))
            .andExpect(status().isCreated());

        // Validate the EmployeeResidenceLocation in the database
        List<EmployeeResidenceLocation> employeeResidenceLocationList = employeeResidenceLocationRepository.findAll();
        assertThat(employeeResidenceLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeResidenceLocation testEmployeeResidenceLocation = employeeResidenceLocationList.get(employeeResidenceLocationList.size() - 1);
        assertThat(testEmployeeResidenceLocation.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testEmployeeResidenceLocation.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testEmployeeResidenceLocation.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testEmployeeResidenceLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmployeeResidenceLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEmployeeResidenceLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEmployeeResidenceLocation.getLocationCode()).isEqualTo(DEFAULT_LOCATION_CODE);
        assertThat(testEmployeeResidenceLocation.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeeResidenceLocation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeResidenceLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeResidenceLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeResidenceLocationRepository.findAll().size();

        // Create the EmployeeResidenceLocation with an existing ID
        employeeResidenceLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeResidenceLocationMockMvc.perform(post("/api/employee-residence-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeResidenceLocation)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeResidenceLocation in the database
        List<EmployeeResidenceLocation> employeeResidenceLocationList = employeeResidenceLocationRepository.findAll();
        assertThat(employeeResidenceLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeResidenceLocations() throws Exception {
        // Initialize the database
        employeeResidenceLocationRepository.saveAndFlush(employeeResidenceLocation);

        // Get all the employeeResidenceLocationList
        restEmployeeResidenceLocationMockMvc.perform(get("/api/employee-residence-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeResidenceLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].locationCode").value(hasItem(DEFAULT_LOCATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].employeeCode").value(hasItem(DEFAULT_EMPLOYEE_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getEmployeeResidenceLocation() throws Exception {
        // Initialize the database
        employeeResidenceLocationRepository.saveAndFlush(employeeResidenceLocation);

        // Get the employeeResidenceLocation
        restEmployeeResidenceLocationMockMvc.perform(get("/api/employee-residence-locations/{id}", employeeResidenceLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeResidenceLocation.getId().intValue()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.locationCode").value(DEFAULT_LOCATION_CODE.toString()))
            .andExpect(jsonPath("$.employeeCode").value(DEFAULT_EMPLOYEE_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeResidenceLocation() throws Exception {
        // Get the employeeResidenceLocation
        restEmployeeResidenceLocationMockMvc.perform(get("/api/employee-residence-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeResidenceLocation() throws Exception {
        // Initialize the database
        employeeResidenceLocationRepository.saveAndFlush(employeeResidenceLocation);
        int databaseSizeBeforeUpdate = employeeResidenceLocationRepository.findAll().size();

        // Update the employeeResidenceLocation
        EmployeeResidenceLocation updatedEmployeeResidenceLocation = employeeResidenceLocationRepository.findOne(employeeResidenceLocation.getId());
        // Disconnect from session so that the updates on updatedEmployeeResidenceLocation are not directly saved in db
        em.detach(updatedEmployeeResidenceLocation);
        updatedEmployeeResidenceLocation
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .zip(UPDATED_ZIP)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .locationCode(UPDATED_LOCATION_CODE)
            .employeeCode(UPDATED_EMPLOYEE_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restEmployeeResidenceLocationMockMvc.perform(put("/api/employee-residence-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeResidenceLocation)))
            .andExpect(status().isOk());

        // Validate the EmployeeResidenceLocation in the database
        List<EmployeeResidenceLocation> employeeResidenceLocationList = employeeResidenceLocationRepository.findAll();
        assertThat(employeeResidenceLocationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeResidenceLocation testEmployeeResidenceLocation = employeeResidenceLocationList.get(employeeResidenceLocationList.size() - 1);
        assertThat(testEmployeeResidenceLocation.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testEmployeeResidenceLocation.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testEmployeeResidenceLocation.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testEmployeeResidenceLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployeeResidenceLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEmployeeResidenceLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmployeeResidenceLocation.getLocationCode()).isEqualTo(UPDATED_LOCATION_CODE);
        assertThat(testEmployeeResidenceLocation.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeeResidenceLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeResidenceLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeResidenceLocation() throws Exception {
        int databaseSizeBeforeUpdate = employeeResidenceLocationRepository.findAll().size();

        // Create the EmployeeResidenceLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeResidenceLocationMockMvc.perform(put("/api/employee-residence-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeResidenceLocation)))
            .andExpect(status().isCreated());

        // Validate the EmployeeResidenceLocation in the database
        List<EmployeeResidenceLocation> employeeResidenceLocationList = employeeResidenceLocationRepository.findAll();
        assertThat(employeeResidenceLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeResidenceLocation() throws Exception {
        // Initialize the database
        employeeResidenceLocationRepository.saveAndFlush(employeeResidenceLocation);
        int databaseSizeBeforeDelete = employeeResidenceLocationRepository.findAll().size();

        // Get the employeeResidenceLocation
        restEmployeeResidenceLocationMockMvc.perform(delete("/api/employee-residence-locations/{id}", employeeResidenceLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeResidenceLocation> employeeResidenceLocationList = employeeResidenceLocationRepository.findAll();
        assertThat(employeeResidenceLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeResidenceLocation.class);
        EmployeeResidenceLocation employeeResidenceLocation1 = new EmployeeResidenceLocation();
        employeeResidenceLocation1.setId(1L);
        EmployeeResidenceLocation employeeResidenceLocation2 = new EmployeeResidenceLocation();
        employeeResidenceLocation2.setId(employeeResidenceLocation1.getId());
        assertThat(employeeResidenceLocation1).isEqualTo(employeeResidenceLocation2);
        employeeResidenceLocation2.setId(2L);
        assertThat(employeeResidenceLocation1).isNotEqualTo(employeeResidenceLocation2);
        employeeResidenceLocation1.setId(null);
        assertThat(employeeResidenceLocation1).isNotEqualTo(employeeResidenceLocation2);
    }
}
