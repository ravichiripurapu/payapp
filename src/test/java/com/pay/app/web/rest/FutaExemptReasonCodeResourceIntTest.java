package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.FutaExemptReasonCode;
import com.pay.app.repository.FutaExemptReasonCodeRepository;
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
 * Test class for the FutaExemptReasonCodeResource REST controller.
 *
 * @see FutaExemptReasonCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class FutaExemptReasonCodeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private FutaExemptReasonCodeRepository futaExemptReasonCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFutaExemptReasonCodeMockMvc;

    private FutaExemptReasonCode futaExemptReasonCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FutaExemptReasonCodeResource futaExemptReasonCodeResource = new FutaExemptReasonCodeResource(futaExemptReasonCodeRepository);
        this.restFutaExemptReasonCodeMockMvc = MockMvcBuilders.standaloneSetup(futaExemptReasonCodeResource)
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
    public static FutaExemptReasonCode createEntity(EntityManager em) {
        FutaExemptReasonCode futaExemptReasonCode = new FutaExemptReasonCode()
            .name(DEFAULT_NAME);
        return futaExemptReasonCode;
    }

    @Before
    public void initTest() {
        futaExemptReasonCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createFutaExemptReasonCode() throws Exception {
        int databaseSizeBeforeCreate = futaExemptReasonCodeRepository.findAll().size();

        // Create the FutaExemptReasonCode
        restFutaExemptReasonCodeMockMvc.perform(post("/api/futa-exempt-reason-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(futaExemptReasonCode)))
            .andExpect(status().isCreated());

        // Validate the FutaExemptReasonCode in the database
        List<FutaExemptReasonCode> futaExemptReasonCodeList = futaExemptReasonCodeRepository.findAll();
        assertThat(futaExemptReasonCodeList).hasSize(databaseSizeBeforeCreate + 1);
        FutaExemptReasonCode testFutaExemptReasonCode = futaExemptReasonCodeList.get(futaExemptReasonCodeList.size() - 1);
        assertThat(testFutaExemptReasonCode.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createFutaExemptReasonCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = futaExemptReasonCodeRepository.findAll().size();

        // Create the FutaExemptReasonCode with an existing ID
        futaExemptReasonCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFutaExemptReasonCodeMockMvc.perform(post("/api/futa-exempt-reason-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(futaExemptReasonCode)))
            .andExpect(status().isBadRequest());

        // Validate the FutaExemptReasonCode in the database
        List<FutaExemptReasonCode> futaExemptReasonCodeList = futaExemptReasonCodeRepository.findAll();
        assertThat(futaExemptReasonCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFutaExemptReasonCodes() throws Exception {
        // Initialize the database
        futaExemptReasonCodeRepository.saveAndFlush(futaExemptReasonCode);

        // Get all the futaExemptReasonCodeList
        restFutaExemptReasonCodeMockMvc.perform(get("/api/futa-exempt-reason-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(futaExemptReasonCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getFutaExemptReasonCode() throws Exception {
        // Initialize the database
        futaExemptReasonCodeRepository.saveAndFlush(futaExemptReasonCode);

        // Get the futaExemptReasonCode
        restFutaExemptReasonCodeMockMvc.perform(get("/api/futa-exempt-reason-codes/{id}", futaExemptReasonCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(futaExemptReasonCode.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFutaExemptReasonCode() throws Exception {
        // Get the futaExemptReasonCode
        restFutaExemptReasonCodeMockMvc.perform(get("/api/futa-exempt-reason-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFutaExemptReasonCode() throws Exception {
        // Initialize the database
        futaExemptReasonCodeRepository.saveAndFlush(futaExemptReasonCode);
        int databaseSizeBeforeUpdate = futaExemptReasonCodeRepository.findAll().size();

        // Update the futaExemptReasonCode
        FutaExemptReasonCode updatedFutaExemptReasonCode = futaExemptReasonCodeRepository.findOne(futaExemptReasonCode.getId());
        // Disconnect from session so that the updates on updatedFutaExemptReasonCode are not directly saved in db
        em.detach(updatedFutaExemptReasonCode);
        updatedFutaExemptReasonCode
            .name(UPDATED_NAME);

        restFutaExemptReasonCodeMockMvc.perform(put("/api/futa-exempt-reason-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFutaExemptReasonCode)))
            .andExpect(status().isOk());

        // Validate the FutaExemptReasonCode in the database
        List<FutaExemptReasonCode> futaExemptReasonCodeList = futaExemptReasonCodeRepository.findAll();
        assertThat(futaExemptReasonCodeList).hasSize(databaseSizeBeforeUpdate);
        FutaExemptReasonCode testFutaExemptReasonCode = futaExemptReasonCodeList.get(futaExemptReasonCodeList.size() - 1);
        assertThat(testFutaExemptReasonCode.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingFutaExemptReasonCode() throws Exception {
        int databaseSizeBeforeUpdate = futaExemptReasonCodeRepository.findAll().size();

        // Create the FutaExemptReasonCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFutaExemptReasonCodeMockMvc.perform(put("/api/futa-exempt-reason-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(futaExemptReasonCode)))
            .andExpect(status().isCreated());

        // Validate the FutaExemptReasonCode in the database
        List<FutaExemptReasonCode> futaExemptReasonCodeList = futaExemptReasonCodeRepository.findAll();
        assertThat(futaExemptReasonCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFutaExemptReasonCode() throws Exception {
        // Initialize the database
        futaExemptReasonCodeRepository.saveAndFlush(futaExemptReasonCode);
        int databaseSizeBeforeDelete = futaExemptReasonCodeRepository.findAll().size();

        // Get the futaExemptReasonCode
        restFutaExemptReasonCodeMockMvc.perform(delete("/api/futa-exempt-reason-codes/{id}", futaExemptReasonCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FutaExemptReasonCode> futaExemptReasonCodeList = futaExemptReasonCodeRepository.findAll();
        assertThat(futaExemptReasonCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FutaExemptReasonCode.class);
        FutaExemptReasonCode futaExemptReasonCode1 = new FutaExemptReasonCode();
        futaExemptReasonCode1.setId(1L);
        FutaExemptReasonCode futaExemptReasonCode2 = new FutaExemptReasonCode();
        futaExemptReasonCode2.setId(futaExemptReasonCode1.getId());
        assertThat(futaExemptReasonCode1).isEqualTo(futaExemptReasonCode2);
        futaExemptReasonCode2.setId(2L);
        assertThat(futaExemptReasonCode1).isNotEqualTo(futaExemptReasonCode2);
        futaExemptReasonCode1.setId(null);
        assertThat(futaExemptReasonCode1).isNotEqualTo(futaExemptReasonCode2);
    }
}
