package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyContact;
import com.pay.app.repository.CompanyContactRepository;
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
 * Test class for the CompanyContactResource REST controller.
 *
 * @see CompanyContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyContactResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SSN = "AAAAAAAAAA";
    private static final String UPDATED_SSN = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CONTACT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CONTACT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_VERIFICATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyContactRepository companyContactRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyContactMockMvc;

    private CompanyContact companyContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyContactResource companyContactResource = new CompanyContactResource(companyContactRepository);
        this.restCompanyContactMockMvc = MockMvcBuilders.standaloneSetup(companyContactResource)
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
    public static CompanyContact createEntity(EntityManager em) {
        CompanyContact companyContact = new CompanyContact()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .homePhone(DEFAULT_HOME_PHONE)
            .dob(DEFAULT_DOB)
            .ssn(DEFAULT_SSN)
            .companyContactType(DEFAULT_COMPANY_CONTACT_TYPE)
            .verificationCode(DEFAULT_VERIFICATION_CODE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyContact;
    }

    @Before
    public void initTest() {
        companyContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyContact() throws Exception {
        int databaseSizeBeforeCreate = companyContactRepository.findAll().size();

        // Create the CompanyContact
        restCompanyContactMockMvc.perform(post("/api/company-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyContact)))
            .andExpect(status().isCreated());

        // Validate the CompanyContact in the database
        List<CompanyContact> companyContactList = companyContactRepository.findAll();
        assertThat(companyContactList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyContact testCompanyContact = companyContactList.get(companyContactList.size() - 1);
        assertThat(testCompanyContact.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCompanyContact.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCompanyContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompanyContact.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testCompanyContact.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testCompanyContact.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testCompanyContact.getSsn()).isEqualTo(DEFAULT_SSN);
        assertThat(testCompanyContact.getCompanyContactType()).isEqualTo(DEFAULT_COMPANY_CONTACT_TYPE);
        assertThat(testCompanyContact.getVerificationCode()).isEqualTo(DEFAULT_VERIFICATION_CODE);
        assertThat(testCompanyContact.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyContact.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyContact.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyContactRepository.findAll().size();

        // Create the CompanyContact with an existing ID
        companyContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyContactMockMvc.perform(post("/api/company-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyContact)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyContact in the database
        List<CompanyContact> companyContactList = companyContactRepository.findAll();
        assertThat(companyContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyContacts() throws Exception {
        // Initialize the database
        companyContactRepository.saveAndFlush(companyContact);

        // Get all the companyContactList
        restCompanyContactMockMvc.perform(get("/api/company-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].ssn").value(hasItem(DEFAULT_SSN.toString())))
            .andExpect(jsonPath("$.[*].companyContactType").value(hasItem(DEFAULT_COMPANY_CONTACT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].verificationCode").value(hasItem(DEFAULT_VERIFICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyContact() throws Exception {
        // Initialize the database
        companyContactRepository.saveAndFlush(companyContact);

        // Get the companyContact
        restCompanyContactMockMvc.perform(get("/api/company-contacts/{id}", companyContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyContact.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.ssn").value(DEFAULT_SSN.toString()))
            .andExpect(jsonPath("$.companyContactType").value(DEFAULT_COMPANY_CONTACT_TYPE.toString()))
            .andExpect(jsonPath("$.verificationCode").value(DEFAULT_VERIFICATION_CODE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyContact() throws Exception {
        // Get the companyContact
        restCompanyContactMockMvc.perform(get("/api/company-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyContact() throws Exception {
        // Initialize the database
        companyContactRepository.saveAndFlush(companyContact);
        int databaseSizeBeforeUpdate = companyContactRepository.findAll().size();

        // Update the companyContact
        CompanyContact updatedCompanyContact = companyContactRepository.findOne(companyContact.getId());
        // Disconnect from session so that the updates on updatedCompanyContact are not directly saved in db
        em.detach(updatedCompanyContact);
        updatedCompanyContact
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .homePhone(UPDATED_HOME_PHONE)
            .dob(UPDATED_DOB)
            .ssn(UPDATED_SSN)
            .companyContactType(UPDATED_COMPANY_CONTACT_TYPE)
            .verificationCode(UPDATED_VERIFICATION_CODE)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyContactMockMvc.perform(put("/api/company-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyContact)))
            .andExpect(status().isOk());

        // Validate the CompanyContact in the database
        List<CompanyContact> companyContactList = companyContactRepository.findAll();
        assertThat(companyContactList).hasSize(databaseSizeBeforeUpdate);
        CompanyContact testCompanyContact = companyContactList.get(companyContactList.size() - 1);
        assertThat(testCompanyContact.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCompanyContact.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCompanyContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompanyContact.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testCompanyContact.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testCompanyContact.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testCompanyContact.getSsn()).isEqualTo(UPDATED_SSN);
        assertThat(testCompanyContact.getCompanyContactType()).isEqualTo(UPDATED_COMPANY_CONTACT_TYPE);
        assertThat(testCompanyContact.getVerificationCode()).isEqualTo(UPDATED_VERIFICATION_CODE);
        assertThat(testCompanyContact.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyContact.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyContact.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyContact() throws Exception {
        int databaseSizeBeforeUpdate = companyContactRepository.findAll().size();

        // Create the CompanyContact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyContactMockMvc.perform(put("/api/company-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyContact)))
            .andExpect(status().isCreated());

        // Validate the CompanyContact in the database
        List<CompanyContact> companyContactList = companyContactRepository.findAll();
        assertThat(companyContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyContact() throws Exception {
        // Initialize the database
        companyContactRepository.saveAndFlush(companyContact);
        int databaseSizeBeforeDelete = companyContactRepository.findAll().size();

        // Get the companyContact
        restCompanyContactMockMvc.perform(delete("/api/company-contacts/{id}", companyContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyContact> companyContactList = companyContactRepository.findAll();
        assertThat(companyContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyContact.class);
        CompanyContact companyContact1 = new CompanyContact();
        companyContact1.setId(1L);
        CompanyContact companyContact2 = new CompanyContact();
        companyContact2.setId(companyContact1.getId());
        assertThat(companyContact1).isEqualTo(companyContact2);
        companyContact2.setId(2L);
        assertThat(companyContact1).isNotEqualTo(companyContact2);
        companyContact1.setId(null);
        assertThat(companyContact1).isNotEqualTo(companyContact2);
    }
}
