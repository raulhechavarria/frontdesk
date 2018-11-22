package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Yard;
import io.github.jhipster.application.repository.YardRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.sameInstant;
import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the YardResource REST controller.
 *
 * @see YardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class YardResourceIntTest {

    private static final String DEFAULT_STREETANDNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREETANDNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREQUENCE_SUMMER = 1;
    private static final Integer UPDATED_FREQUENCE_SUMMER = 2;

    private static final Integer DEFAULT_FREQUENCE_WINTER = 1;
    private static final Integer UPDATED_FREQUENCE_WINTER = 2;

    private static final ZonedDateTime DEFAULT_DATE_DONE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DONE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restYardMockMvc;

    private Yard yard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final YardResource yardResource = new YardResource(yardRepository);
        this.restYardMockMvc = MockMvcBuilders.standaloneSetup(yardResource)
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
    public static Yard createEntity(EntityManager em) {
        Yard yard = new Yard()
            .streetandnumber(DEFAULT_STREETANDNUMBER)
            .city(DEFAULT_CITY)
            .frequenceSummer(DEFAULT_FREQUENCE_SUMMER)
            .frequenceWinter(DEFAULT_FREQUENCE_WINTER)
            .dateDone(DEFAULT_DATE_DONE);
        return yard;
    }

    @Before
    public void initTest() {
        yard = createEntity(em);
    }

    @Test
    @Transactional
    public void createYard() throws Exception {
        int databaseSizeBeforeCreate = yardRepository.findAll().size();

        // Create the Yard
        restYardMockMvc.perform(post("/api/yards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yard)))
            .andExpect(status().isCreated());

        // Validate the Yard in the database
        List<Yard> yardList = yardRepository.findAll();
        assertThat(yardList).hasSize(databaseSizeBeforeCreate + 1);
        Yard testYard = yardList.get(yardList.size() - 1);
        assertThat(testYard.getStreetandnumber()).isEqualTo(DEFAULT_STREETANDNUMBER);
        assertThat(testYard.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testYard.getFrequenceSummer()).isEqualTo(DEFAULT_FREQUENCE_SUMMER);
        assertThat(testYard.getFrequenceWinter()).isEqualTo(DEFAULT_FREQUENCE_WINTER);
        assertThat(testYard.getDateDone()).isEqualTo(DEFAULT_DATE_DONE);
    }

    @Test
    @Transactional
    public void createYardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = yardRepository.findAll().size();

        // Create the Yard with an existing ID
        yard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restYardMockMvc.perform(post("/api/yards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yard)))
            .andExpect(status().isBadRequest());

        // Validate the Yard in the database
        List<Yard> yardList = yardRepository.findAll();
        assertThat(yardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllYards() throws Exception {
        // Initialize the database
        yardRepository.saveAndFlush(yard);

        // Get all the yardList
        restYardMockMvc.perform(get("/api/yards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(yard.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetandnumber").value(hasItem(DEFAULT_STREETANDNUMBER.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].frequenceSummer").value(hasItem(DEFAULT_FREQUENCE_SUMMER)))
            .andExpect(jsonPath("$.[*].frequenceWinter").value(hasItem(DEFAULT_FREQUENCE_WINTER)))
            .andExpect(jsonPath("$.[*].dateDone").value(hasItem(sameInstant(DEFAULT_DATE_DONE))));
    }
    
    @Test
    @Transactional
    public void getYard() throws Exception {
        // Initialize the database
        yardRepository.saveAndFlush(yard);

        // Get the yard
        restYardMockMvc.perform(get("/api/yards/{id}", yard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(yard.getId().intValue()))
            .andExpect(jsonPath("$.streetandnumber").value(DEFAULT_STREETANDNUMBER.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.frequenceSummer").value(DEFAULT_FREQUENCE_SUMMER))
            .andExpect(jsonPath("$.frequenceWinter").value(DEFAULT_FREQUENCE_WINTER))
            .andExpect(jsonPath("$.dateDone").value(sameInstant(DEFAULT_DATE_DONE)));
    }

    @Test
    @Transactional
    public void getNonExistingYard() throws Exception {
        // Get the yard
        restYardMockMvc.perform(get("/api/yards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYard() throws Exception {
        // Initialize the database
        yardRepository.saveAndFlush(yard);

        int databaseSizeBeforeUpdate = yardRepository.findAll().size();

        // Update the yard
        Yard updatedYard = yardRepository.findById(yard.getId()).get();
        // Disconnect from session so that the updates on updatedYard are not directly saved in db
        em.detach(updatedYard);
        updatedYard
            .streetandnumber(UPDATED_STREETANDNUMBER)
            .city(UPDATED_CITY)
            .frequenceSummer(UPDATED_FREQUENCE_SUMMER)
            .frequenceWinter(UPDATED_FREQUENCE_WINTER)
            .dateDone(UPDATED_DATE_DONE);

        restYardMockMvc.perform(put("/api/yards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedYard)))
            .andExpect(status().isOk());

        // Validate the Yard in the database
        List<Yard> yardList = yardRepository.findAll();
        assertThat(yardList).hasSize(databaseSizeBeforeUpdate);
        Yard testYard = yardList.get(yardList.size() - 1);
        assertThat(testYard.getStreetandnumber()).isEqualTo(UPDATED_STREETANDNUMBER);
        assertThat(testYard.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testYard.getFrequenceSummer()).isEqualTo(UPDATED_FREQUENCE_SUMMER);
        assertThat(testYard.getFrequenceWinter()).isEqualTo(UPDATED_FREQUENCE_WINTER);
        assertThat(testYard.getDateDone()).isEqualTo(UPDATED_DATE_DONE);
    }

    @Test
    @Transactional
    public void updateNonExistingYard() throws Exception {
        int databaseSizeBeforeUpdate = yardRepository.findAll().size();

        // Create the Yard

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restYardMockMvc.perform(put("/api/yards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(yard)))
            .andExpect(status().isBadRequest());

        // Validate the Yard in the database
        List<Yard> yardList = yardRepository.findAll();
        assertThat(yardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteYard() throws Exception {
        // Initialize the database
        yardRepository.saveAndFlush(yard);

        int databaseSizeBeforeDelete = yardRepository.findAll().size();

        // Get the yard
        restYardMockMvc.perform(delete("/api/yards/{id}", yard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Yard> yardList = yardRepository.findAll();
        assertThat(yardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Yard.class);
        Yard yard1 = new Yard();
        yard1.setId(1L);
        Yard yard2 = new Yard();
        yard2.setId(yard1.getId());
        assertThat(yard1).isEqualTo(yard2);
        yard2.setId(2L);
        assertThat(yard1).isNotEqualTo(yard2);
        yard1.setId(null);
        assertThat(yard1).isNotEqualTo(yard2);
    }
}
