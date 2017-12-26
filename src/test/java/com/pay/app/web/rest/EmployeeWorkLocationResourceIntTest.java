package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.EmployeeWorkLocation;
import com.pay.app.repository.EmployeeWorkLocationRepository;
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
 * Test class for the EmployeeWorkLocationResource REST controller.
 *
 * @see EmployeeWorkLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class EmployeeWorkLocationResourceIntTest {

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
    private EmployeeWorkLocationRepository employeeWorkLocationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeWorkLocationMockMvc;

    private EmployeeWorkLocation employeeWorkLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeWorkLocationResource employeeWorkLocationResource = new EmployeeWorkLocationResource(employeeWorkLocationRepository);
        this.restEmployeeWorkLocationMockMvc = MockMvcBuilders.standaloneSetup(employeeWorkLocationResource)
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
    public static EmployeeWorkLocation createEntity(EntityManager em) {
        EmployeeWorkLocation employeeWorkLocation = new EmployeeWorkLocation()
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
        return employeeWorkLocation;
    }

    @Before
    public void initTest() {
        employeeWorkLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeWorkLocation() throws Exception {
        int databaseSizeBeforeCreate = employeeWorkLocationRepository.findAll().size();

        // Create the EmployeeWorkLocation
        restEmployeeWorkLocationMockMvc.perform(post("/api/employee-work-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeWorkLocation)))
            .andExpect(status().isCreated());

        // Validate the EmployeeWorkLocation in the database
        List<EmployeeWorkLocation> employeeWorkLocationList = employeeWorkLocationRepository.findAll();
        assertThat(employeeWorkLocationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeWorkLocation testEmployeeWorkLocation = employeeWorkLocationList.get(employeeWorkLocationList.size() - 1);
        assertThat(testEmployeeWorkLocation.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testEmployeeWorkLocation.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testEmployeeWorkLocation.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testEmployeeWorkLocation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testEmployeeWorkLocation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testEmployeeWorkLocation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testEmployeeWorkLocation.getLocationCode()).isEqualTo(DEFAULT_LOCATION_CODE);
        assertThat(testEmployeeWorkLocation.getEmployeeCode()).isEqualTo(DEFAULT_EMPLOYEE_CODE);
        assertThat(testEmployeeWorkLocation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmployeeWorkLocation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeWorkLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeWorkLocationRepository.findAll().size();

        // Create the EmployeeWorkLocation with an existing ID
        employeeWorkLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeWorkLocationMockMvc.perform(post("/api/employee-work-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeWorkLocation)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeWorkLocation in the database
        List<EmployeeWorkLocation> employeeWorkLocationList = employeeWorkLocationRepository.findAll();
        assertThat(employeeWorkLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeWorkLocations() throws Exception {
        // Initialize the database
        employeeWorkLocationRepository.saveAndFlush(employeeWorkLocation);

        // Get all the employeeWorkLocationList
        restEmployeeWorkLocationMockMvc.perform(get("/api/employee-work-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeWorkLocation.getId().intValue())))
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
    public void getEmployeeWorkLocation() throws Exception {
        // Initialize the database
        employeeWorkLocationRepository.saveAndFlush(employeeWorkLocation);

        // Get the employeeWorkLocation
        restEmployeeWorkLocationMockMvc.perform(get("/api/employee-work-locations/{id}", employeeWorkLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeWorkLocation.getId().intValue()))
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
    public void getNonExistingEmployeeWorkLocation() throws Exception {
        // Get the employeeWorkLocation
        restEmployeeWorkLocationMockMvc.perform(get("/api/employee-work-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeWorkLocation() throws Exception {
        // Initialize the database
        employeeWorkLocationRepository.saveAndFlush(employeeWorkLocation);
        int databaseSizeBeforeUpdate = employeeWorkLocationRepository.findAll().size();

        // Update the employeeWorkLocation
        EmployeeWorkLocation updatedEmployeeWorkLocation = employeeWorkLocationRepository.findOne(employeeWorkLocation.getId());
        // Disconnect from session so that the updates on updatedEmployeeWorkLocation are not directly saved in db
        em.detach(updatedEmployeeWorkLocation);
        updatedEmployeeWorkLocation
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

        restEmployeeWorkLocationMockMvc.perform(put("/api/employee-work-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeWorkLocation)))
            .andExpect(status().isOk());

        // Validate the EmployeeWorkLocation in the database
        List<EmployeeWorkLocation> employeeWorkLocationList = employeeWorkLocationRepository.findAll();
        assertThat(employeeWorkLocationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeWorkLocation testEmployeeWorkLocation = employeeWorkLocationList.get(employeeWorkLocationList.size() - 1);
        assertThat(testEmployeeWorkLocation.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testEmployeeWorkLocation.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testEmployeeWorkLocation.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testEmployeeWorkLocation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testEmployeeWorkLocation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testEmployeeWorkLocation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testEmployeeWorkLocation.getLocationCode()).isEqualTo(UPDATED_LOCATION_CODE);
        assertThat(testEmployeeWorkLocation.getEmployeeCode()).isEqualTo(UPDATED_EMPLOYEE_CODE);
        assertThat(testEmployeeWorkLocation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmployeeWorkLocation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeWorkLocation() throws Exception {
        int databaseSizeBeforeUpdate = employeeWorkLocationRepository.findAll().size();

        // Create the EmployeeWorkLocation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeWorkLocationMockMvc.perform(put("/api/employee-work-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeWorkLocation)))
            .andExpect(status().isCreated());

        // Validate the EmployeeWorkLocation in the database
        List<EmployeeWorkLocation> employeeWorkLocationList = employeeWorkLocationRepository.findAll();
        assertThat(employeeWorkLocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeWorkLocation() throws Exception {
        // Initialize the database
        employeeWorkLocationRepository.saveAndFlush(employeeWorkLocation);
        int databaseSizeBeforeDelete = employeeWorkLocationRepository.findAll().size();

        // Get the employeeWorkLocation
        restEmployeeWorkLocationMockMvc.perform(delete("/api/employee-work-locations/{id}", employeeWorkLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeWorkLocation> employeeWorkLocationList = employeeWorkLocationRepository.findAll();
        assertThat(employeeWorkLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeWorkLocation.class);
        EmployeeWorkLocation employeeWorkLocation1 = new EmployeeWorkLocation();
        employeeWorkLocation1.setId(1L);
        EmployeeWorkLocation employeeWorkLocation2 = new EmployeeWorkLocation();
        employeeWorkLocation2.setId(employeeWorkLocation1.getId());
        assertThat(employeeWorkLocation1).isEqualTo(employeeWorkLocation2);
        employeeWorkLocation2.setId(2L);
        assertThat(employeeWorkLocation1).isNotEqualTo(employeeWorkLocation2);
        employeeWorkLocation1.setId(null);
        assertThat(employeeWorkLocation1).isNotEqualTo(employeeWorkLocation2);
    }
}
