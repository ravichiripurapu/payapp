package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.CompanyBank;
import com.pay.app.repository.CompanyBankRepository;
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
 * Test class for the CompanyBankResource REST controller.
 *
 * @see CompanyBankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class CompanyBankResourceIntTest {

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ROUTING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_PERCENT_DEPOSIT = 1F;
    private static final Float UPDATED_PERCENT_DEPOSIT = 2F;

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CompanyBankRepository companyBankRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyBankMockMvc;

    private CompanyBank companyBank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyBankResource companyBankResource = new CompanyBankResource(companyBankRepository);
        this.restCompanyBankMockMvc = MockMvcBuilders.standaloneSetup(companyBankResource)
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
    public static CompanyBank createEntity(EntityManager em) {
        CompanyBank companyBank = new CompanyBank()
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .routingNumber(DEFAULT_ROUTING_NUMBER)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .bankName(DEFAULT_BANK_NAME)
            .percentDeposit(DEFAULT_PERCENT_DEPOSIT)
            .companyCode(DEFAULT_COMPANY_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return companyBank;
    }

    @Before
    public void initTest() {
        companyBank = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyBank() throws Exception {
        int databaseSizeBeforeCreate = companyBankRepository.findAll().size();

        // Create the CompanyBank
        restCompanyBankMockMvc.perform(post("/api/company-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyBank)))
            .andExpect(status().isCreated());

        // Validate the CompanyBank in the database
        List<CompanyBank> companyBankList = companyBankRepository.findAll();
        assertThat(companyBankList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyBank testCompanyBank = companyBankList.get(companyBankList.size() - 1);
        assertThat(testCompanyBank.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testCompanyBank.getRoutingNumber()).isEqualTo(DEFAULT_ROUTING_NUMBER);
        assertThat(testCompanyBank.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCompanyBank.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCompanyBank.getPercentDeposit()).isEqualTo(DEFAULT_PERCENT_DEPOSIT);
        assertThat(testCompanyBank.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testCompanyBank.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCompanyBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCompanyBankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyBankRepository.findAll().size();

        // Create the CompanyBank with an existing ID
        companyBank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyBankMockMvc.perform(post("/api/company-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyBank)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyBank in the database
        List<CompanyBank> companyBankList = companyBankRepository.findAll();
        assertThat(companyBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyBanks() throws Exception {
        // Initialize the database
        companyBankRepository.saveAndFlush(companyBank);

        // Get all the companyBankList
        restCompanyBankMockMvc.perform(get("/api/company-banks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].routingNumber").value(hasItem(DEFAULT_ROUTING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].percentDeposit").value(hasItem(DEFAULT_PERCENT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }

    @Test
    @Transactional
    public void getCompanyBank() throws Exception {
        // Initialize the database
        companyBankRepository.saveAndFlush(companyBank);

        // Get the companyBank
        restCompanyBankMockMvc.perform(get("/api/company-banks/{id}", companyBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyBank.getId().intValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.routingNumber").value(DEFAULT_ROUTING_NUMBER.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.percentDeposit").value(DEFAULT_PERCENT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyBank() throws Exception {
        // Get the companyBank
        restCompanyBankMockMvc.perform(get("/api/company-banks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyBank() throws Exception {
        // Initialize the database
        companyBankRepository.saveAndFlush(companyBank);
        int databaseSizeBeforeUpdate = companyBankRepository.findAll().size();

        // Update the companyBank
        CompanyBank updatedCompanyBank = companyBankRepository.findOne(companyBank.getId());
        // Disconnect from session so that the updates on updatedCompanyBank are not directly saved in db
        em.detach(updatedCompanyBank);
        updatedCompanyBank
            .accountType(UPDATED_ACCOUNT_TYPE)
            .routingNumber(UPDATED_ROUTING_NUMBER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .bankName(UPDATED_BANK_NAME)
            .percentDeposit(UPDATED_PERCENT_DEPOSIT)
            .companyCode(UPDATED_COMPANY_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restCompanyBankMockMvc.perform(put("/api/company-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompanyBank)))
            .andExpect(status().isOk());

        // Validate the CompanyBank in the database
        List<CompanyBank> companyBankList = companyBankRepository.findAll();
        assertThat(companyBankList).hasSize(databaseSizeBeforeUpdate);
        CompanyBank testCompanyBank = companyBankList.get(companyBankList.size() - 1);
        assertThat(testCompanyBank.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testCompanyBank.getRoutingNumber()).isEqualTo(UPDATED_ROUTING_NUMBER);
        assertThat(testCompanyBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCompanyBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCompanyBank.getPercentDeposit()).isEqualTo(UPDATED_PERCENT_DEPOSIT);
        assertThat(testCompanyBank.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testCompanyBank.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCompanyBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyBank() throws Exception {
        int databaseSizeBeforeUpdate = companyBankRepository.findAll().size();

        // Create the CompanyBank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyBankMockMvc.perform(put("/api/company-banks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyBank)))
            .andExpect(status().isCreated());

        // Validate the CompanyBank in the database
        List<CompanyBank> companyBankList = companyBankRepository.findAll();
        assertThat(companyBankList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyBank() throws Exception {
        // Initialize the database
        companyBankRepository.saveAndFlush(companyBank);
        int databaseSizeBeforeDelete = companyBankRepository.findAll().size();

        // Get the companyBank
        restCompanyBankMockMvc.perform(delete("/api/company-banks/{id}", companyBank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyBank> companyBankList = companyBankRepository.findAll();
        assertThat(companyBankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyBank.class);
        CompanyBank companyBank1 = new CompanyBank();
        companyBank1.setId(1L);
        CompanyBank companyBank2 = new CompanyBank();
        companyBank2.setId(companyBank1.getId());
        assertThat(companyBank1).isEqualTo(companyBank2);
        companyBank2.setId(2L);
        assertThat(companyBank1).isNotEqualTo(companyBank2);
        companyBank1.setId(null);
        assertThat(companyBank1).isNotEqualTo(companyBank2);
    }
}
