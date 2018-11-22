package frontdesk.com.web.rest;

import frontdesk.com.FrontdeskApp;

import frontdesk.com.domain.Pack;
import frontdesk.com.repository.PackRepository;
import frontdesk.com.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static frontdesk.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PackResource REST controller.
 *
 * @see PackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontdeskApp.class)
public class PackResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FRONT_DESK_RECEIVE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FRONT_DESK_RECEIVE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FRONT_DESK_DELIVERY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FRONT_DESK_DELIVERY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_PICKUP = "AAAAAAAAAA";
    private static final String UPDATED_NAME_PICKUP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECEIVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECEIVED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PICKUP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PICKUP = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PIXEL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PIXEL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PIXEL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PIXEL_CONTENT_TYPE = "image/png";

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPackMockMvc;

    private Pack pack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PackResource packResource = new PackResource(packRepository);
        this.restPackMockMvc = MockMvcBuilders.standaloneSetup(packResource)
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
    public static Pack createEntity(EntityManager em) {
        Pack pack = new Pack()
            .name(DEFAULT_NAME)
            .nameFrontDeskReceive(DEFAULT_NAME_FRONT_DESK_RECEIVE)
            .nameFrontDeskDelivery(DEFAULT_NAME_FRONT_DESK_DELIVERY)
            .namePickup(DEFAULT_NAME_PICKUP)
            .dateReceived(DEFAULT_DATE_RECEIVED)
            .datePickup(DEFAULT_DATE_PICKUP)
            .pixel(DEFAULT_PIXEL)
            .pixelContentType(DEFAULT_PIXEL_CONTENT_TYPE);
        return pack;
    }

    @Before
    public void initTest() {
        pack = createEntity(em);
    }

    @Test
    @Transactional
    public void createPack() throws Exception {
        int databaseSizeBeforeCreate = packRepository.findAll().size();

        // Create the Pack
        restPackMockMvc.perform(post("/api/packs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isCreated());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeCreate + 1);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPack.getNameFrontDeskReceive()).isEqualTo(DEFAULT_NAME_FRONT_DESK_RECEIVE);
        assertThat(testPack.getNameFrontDeskDelivery()).isEqualTo(DEFAULT_NAME_FRONT_DESK_DELIVERY);
        assertThat(testPack.getNamePickup()).isEqualTo(DEFAULT_NAME_PICKUP);
        assertThat(testPack.getDateReceived()).isEqualTo(DEFAULT_DATE_RECEIVED);
        assertThat(testPack.getDatePickup()).isEqualTo(DEFAULT_DATE_PICKUP);
        assertThat(testPack.getPixel()).isEqualTo(DEFAULT_PIXEL);
        assertThat(testPack.getPixelContentType()).isEqualTo(DEFAULT_PIXEL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packRepository.findAll().size();

        // Create the Pack with an existing ID
        pack.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackMockMvc.perform(post("/api/packs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacks() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        // Get all the packList
        restPackMockMvc.perform(get("/api/packs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pack.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].nameFrontDeskReceive").value(hasItem(DEFAULT_NAME_FRONT_DESK_RECEIVE.toString())))
            .andExpect(jsonPath("$.[*].nameFrontDeskDelivery").value(hasItem(DEFAULT_NAME_FRONT_DESK_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].namePickup").value(hasItem(DEFAULT_NAME_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].dateReceived").value(hasItem(DEFAULT_DATE_RECEIVED.toString())))
            .andExpect(jsonPath("$.[*].datePickup").value(hasItem(DEFAULT_DATE_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].pixelContentType").value(hasItem(DEFAULT_PIXEL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pixel").value(hasItem(Base64Utils.encodeToString(DEFAULT_PIXEL))));
    }
    
    @Test
    @Transactional
    public void getPack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        // Get the pack
        restPackMockMvc.perform(get("/api/packs/{id}", pack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pack.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.nameFrontDeskReceive").value(DEFAULT_NAME_FRONT_DESK_RECEIVE.toString()))
            .andExpect(jsonPath("$.nameFrontDeskDelivery").value(DEFAULT_NAME_FRONT_DESK_DELIVERY.toString()))
            .andExpect(jsonPath("$.namePickup").value(DEFAULT_NAME_PICKUP.toString()))
            .andExpect(jsonPath("$.dateReceived").value(DEFAULT_DATE_RECEIVED.toString()))
            .andExpect(jsonPath("$.datePickup").value(DEFAULT_DATE_PICKUP.toString()))
            .andExpect(jsonPath("$.pixelContentType").value(DEFAULT_PIXEL_CONTENT_TYPE))
            .andExpect(jsonPath("$.pixel").value(Base64Utils.encodeToString(DEFAULT_PIXEL)));
    }

    @Test
    @Transactional
    public void getNonExistingPack() throws Exception {
        // Get the pack
        restPackMockMvc.perform(get("/api/packs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Update the pack
        Pack updatedPack = packRepository.findById(pack.getId()).get();
        // Disconnect from session so that the updates on updatedPack are not directly saved in db
        em.detach(updatedPack);
        updatedPack
            .name(UPDATED_NAME)
            .nameFrontDeskReceive(UPDATED_NAME_FRONT_DESK_RECEIVE)
            .nameFrontDeskDelivery(UPDATED_NAME_FRONT_DESK_DELIVERY)
            .namePickup(UPDATED_NAME_PICKUP)
            .dateReceived(UPDATED_DATE_RECEIVED)
            .datePickup(UPDATED_DATE_PICKUP)
            .pixel(UPDATED_PIXEL)
            .pixelContentType(UPDATED_PIXEL_CONTENT_TYPE);

        restPackMockMvc.perform(put("/api/packs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPack)))
            .andExpect(status().isOk());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
        Pack testPack = packList.get(packList.size() - 1);
        assertThat(testPack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPack.getNameFrontDeskReceive()).isEqualTo(UPDATED_NAME_FRONT_DESK_RECEIVE);
        assertThat(testPack.getNameFrontDeskDelivery()).isEqualTo(UPDATED_NAME_FRONT_DESK_DELIVERY);
        assertThat(testPack.getNamePickup()).isEqualTo(UPDATED_NAME_PICKUP);
        assertThat(testPack.getDateReceived()).isEqualTo(UPDATED_DATE_RECEIVED);
        assertThat(testPack.getDatePickup()).isEqualTo(UPDATED_DATE_PICKUP);
        assertThat(testPack.getPixel()).isEqualTo(UPDATED_PIXEL);
        assertThat(testPack.getPixelContentType()).isEqualTo(UPDATED_PIXEL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPack() throws Exception {
        int databaseSizeBeforeUpdate = packRepository.findAll().size();

        // Create the Pack

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackMockMvc.perform(put("/api/packs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pack)))
            .andExpect(status().isBadRequest());

        // Validate the Pack in the database
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePack() throws Exception {
        // Initialize the database
        packRepository.saveAndFlush(pack);

        int databaseSizeBeforeDelete = packRepository.findAll().size();

        // Get the pack
        restPackMockMvc.perform(delete("/api/packs/{id}", pack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pack> packList = packRepository.findAll();
        assertThat(packList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pack.class);
        Pack pack1 = new Pack();
        pack1.setId(1L);
        Pack pack2 = new Pack();
        pack2.setId(pack1.getId());
        assertThat(pack1).isEqualTo(pack2);
        pack2.setId(2L);
        assertThat(pack1).isNotEqualTo(pack2);
        pack1.setId(null);
        assertThat(pack1).isNotEqualTo(pack2);
    }
}
