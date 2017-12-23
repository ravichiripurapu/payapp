package com.pay.app.web.rest;

import com.pay.app.PayappApp;

import com.pay.app.domain.LocalTaxes;
import com.pay.app.repository.LocalTaxesRepository;
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
 * Test class for the LocalTaxesResource REST controller.
 *
 * @see LocalTaxesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayappApp.class)
public class LocalTaxesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private LocalTaxesRepository localTaxesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalTaxesMockMvc;

    private LocalTaxes localTaxes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalTaxesResource localTaxesResource = new LocalTaxesResource(localTaxesRepository);
        this.restLocalTaxesMockMvc = MockMvcBuilders.standaloneSetup(localTaxesResource)
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
    public static LocalTaxes createEntity(EntityManager em) {
        LocalTaxes localTaxes = new LocalTaxes()
            .name(DEFAULT_NAME);
        return localTaxes;
    }

    @Before
    public void initTest() {
        localTaxes = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalTaxes() throws Exception {
        int databaseSizeBeforeCreate = localTaxesRepository.findAll().size();

        // Create the LocalTaxes
        restLocalTaxesMockMvc.perform(post("/api/local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localTaxes)))
            .andExpect(status().isCreated());

        // Validate the LocalTaxes in the database
        List<LocalTaxes> localTaxesList = localTaxesRepository.findAll();
        assertThat(localTaxesList).hasSize(databaseSizeBeforeCreate + 1);
        LocalTaxes testLocalTaxes = localTaxesList.get(localTaxesList.size() - 1);
        assertThat(testLocalTaxes.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createLocalTaxesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localTaxesRepository.findAll().size();

        // Create the LocalTaxes with an existing ID
        localTaxes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalTaxesMockMvc.perform(post("/api/local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localTaxes)))
            .andExpect(status().isBadRequest());

        // Validate the LocalTaxes in the database
        List<LocalTaxes> localTaxesList = localTaxesRepository.findAll();
        assertThat(localTaxesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocalTaxes() throws Exception {
        // Initialize the database
        localTaxesRepository.saveAndFlush(localTaxes);

        // Get all the localTaxesList
        restLocalTaxesMockMvc.perform(get("/api/local-taxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localTaxes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getLocalTaxes() throws Exception {
        // Initialize the database
        localTaxesRepository.saveAndFlush(localTaxes);

        // Get the localTaxes
        restLocalTaxesMockMvc.perform(get("/api/local-taxes/{id}", localTaxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(localTaxes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocalTaxes() throws Exception {
        // Get the localTaxes
        restLocalTaxesMockMvc.perform(get("/api/local-taxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalTaxes() throws Exception {
        // Initialize the database
        localTaxesRepository.saveAndFlush(localTaxes);
        int databaseSizeBeforeUpdate = localTaxesRepository.findAll().size();

        // Update the localTaxes
        LocalTaxes updatedLocalTaxes = localTaxesRepository.findOne(localTaxes.getId());
        // Disconnect from session so that the updates on updatedLocalTaxes are not directly saved in db
        em.detach(updatedLocalTaxes);
        updatedLocalTaxes
            .name(UPDATED_NAME);

        restLocalTaxesMockMvc.perform(put("/api/local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocalTaxes)))
            .andExpect(status().isOk());

        // Validate the LocalTaxes in the database
        List<LocalTaxes> localTaxesList = localTaxesRepository.findAll();
        assertThat(localTaxesList).hasSize(databaseSizeBeforeUpdate);
        LocalTaxes testLocalTaxes = localTaxesList.get(localTaxesList.size() - 1);
        assertThat(testLocalTaxes.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalTaxes() throws Exception {
        int databaseSizeBeforeUpdate = localTaxesRepository.findAll().size();

        // Create the LocalTaxes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalTaxesMockMvc.perform(put("/api/local-taxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(localTaxes)))
            .andExpect(status().isCreated());

        // Validate the LocalTaxes in the database
        List<LocalTaxes> localTaxesList = localTaxesRepository.findAll();
        assertThat(localTaxesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocalTaxes() throws Exception {
        // Initialize the database
        localTaxesRepository.saveAndFlush(localTaxes);
        int databaseSizeBeforeDelete = localTaxesRepository.findAll().size();

        // Get the localTaxes
        restLocalTaxesMockMvc.perform(delete("/api/local-taxes/{id}", localTaxes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LocalTaxes> localTaxesList = localTaxesRepository.findAll();
        assertThat(localTaxesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalTaxes.class);
        LocalTaxes localTaxes1 = new LocalTaxes();
        localTaxes1.setId(1L);
        LocalTaxes localTaxes2 = new LocalTaxes();
        localTaxes2.setId(localTaxes1.getId());
        assertThat(localTaxes1).isEqualTo(localTaxes2);
        localTaxes2.setId(2L);
        assertThat(localTaxes1).isNotEqualTo(localTaxes2);
        localTaxes1.setId(null);
        assertThat(localTaxes1).isNotEqualTo(localTaxes2);
    }
}
