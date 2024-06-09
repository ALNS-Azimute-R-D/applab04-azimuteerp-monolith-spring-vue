package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCityAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Province;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TownCityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TownCityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TownCityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TownCityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TownCityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TownCityResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/town-cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TownCityRepository townCityRepository;

    @Mock
    private TownCityRepository townCityRepositoryMock;

    @Autowired
    private TownCityMapper townCityMapper;

    @Mock
    private TownCityService townCityServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTownCityMockMvc;

    private TownCity townCity;

    private TownCity insertedTownCity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownCity createEntity(EntityManager em) {
        TownCity townCity = new TownCity()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .geoPolygonArea(DEFAULT_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            province = ProvinceResourceIT.createEntity(em);
            em.persist(province);
            em.flush();
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        townCity.setProvince(province);
        return townCity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TownCity createUpdatedEntity(EntityManager em) {
        TownCity townCity = new TownCity()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        Province province;
        if (TestUtil.findAll(em, Province.class).isEmpty()) {
            province = ProvinceResourceIT.createUpdatedEntity(em);
            em.persist(province);
            em.flush();
        } else {
            province = TestUtil.findAll(em, Province.class).get(0);
        }
        townCity.setProvince(province);
        return townCity;
    }

    @BeforeEach
    public void initTest() {
        townCity = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTownCity != null) {
            townCityRepository.delete(insertedTownCity);
            insertedTownCity = null;
        }
    }

    @Test
    @Transactional
    void createTownCity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);
        var returnedTownCityDTO = om.readValue(
            restTownCityMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(townCityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TownCityDTO.class
        );

        // Validate the TownCity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTownCity = townCityMapper.toEntity(returnedTownCityDTO);
        assertTownCityUpdatableFieldsEquals(returnedTownCity, getPersistedTownCity(returnedTownCity));

        insertedTownCity = returnedTownCity;
    }

    @Test
    @Transactional
    void createTownCityWithExistingId() throws Exception {
        // Create the TownCity with an existing ID
        townCity.setId(1L);
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTownCityMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(townCityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        townCity.setAcronym(null);

        // Create the TownCity, which fails.
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        restTownCityMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(townCityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        townCity.setName(null);

        // Create the TownCity, which fails.
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        restTownCityMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(townCityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTownCities() throws Exception {
        // Initialize the database
        insertedTownCity = townCityRepository.saveAndFlush(townCity);

        // Get all the townCityList
        restTownCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(townCity.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].geoPolygonAreaContentType").value(hasItem(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].geoPolygonArea").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTownCitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(townCityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTownCityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(townCityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTownCitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(townCityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTownCityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(townCityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTownCity() throws Exception {
        // Initialize the database
        insertedTownCity = townCityRepository.saveAndFlush(townCity);

        // Get the townCity
        restTownCityMockMvc
            .perform(get(ENTITY_API_URL_ID, townCity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(townCity.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.geoPolygonAreaContentType").value(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE))
            .andExpect(jsonPath("$.geoPolygonArea").value(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA)));
    }

    @Test
    @Transactional
    void getNonExistingTownCity() throws Exception {
        // Get the townCity
        restTownCityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTownCity() throws Exception {
        // Initialize the database
        insertedTownCity = townCityRepository.saveAndFlush(townCity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the townCity
        TownCity updatedTownCity = townCityRepository.findById(townCity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTownCity are not directly saved in db
        em.detach(updatedTownCity);
        updatedTownCity
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        TownCityDTO townCityDTO = townCityMapper.toDto(updatedTownCity);

        restTownCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, townCityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(townCityDTO))
            )
            .andExpect(status().isOk());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTownCityToMatchAllProperties(updatedTownCity);
    }

    @Test
    @Transactional
    void putNonExistingTownCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        townCity.setId(longCount.incrementAndGet());

        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, townCityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(townCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTownCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        townCity.setId(longCount.incrementAndGet());

        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTownCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(townCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTownCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        townCity.setId(longCount.incrementAndGet());

        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTownCityMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(townCityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTownCityWithPatch() throws Exception {
        // Initialize the database
        insertedTownCity = townCityRepository.saveAndFlush(townCity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the townCity using partial update
        TownCity partialUpdatedTownCity = new TownCity();
        partialUpdatedTownCity.setId(townCity.getId());

        partialUpdatedTownCity
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restTownCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTownCity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTownCity))
            )
            .andExpect(status().isOk());

        // Validate the TownCity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTownCityUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTownCity, townCity), getPersistedTownCity(townCity));
    }

    @Test
    @Transactional
    void fullUpdateTownCityWithPatch() throws Exception {
        // Initialize the database
        insertedTownCity = townCityRepository.saveAndFlush(townCity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the townCity using partial update
        TownCity partialUpdatedTownCity = new TownCity();
        partialUpdatedTownCity.setId(townCity.getId());

        partialUpdatedTownCity
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restTownCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTownCity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTownCity))
            )
            .andExpect(status().isOk());

        // Validate the TownCity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTownCityUpdatableFieldsEquals(partialUpdatedTownCity, getPersistedTownCity(partialUpdatedTownCity));
    }

    @Test
    @Transactional
    void patchNonExistingTownCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        townCity.setId(longCount.incrementAndGet());

        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTownCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, townCityDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(townCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTownCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        townCity.setId(longCount.incrementAndGet());

        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTownCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(townCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTownCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        townCity.setId(longCount.incrementAndGet());

        // Create the TownCity
        TownCityDTO townCityDTO = townCityMapper.toDto(townCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTownCityMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(townCityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TownCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTownCity() throws Exception {
        // Initialize the database
        insertedTownCity = townCityRepository.saveAndFlush(townCity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the townCity
        restTownCityMockMvc
            .perform(delete(ENTITY_API_URL_ID, townCity.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return townCityRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected TownCity getPersistedTownCity(TownCity townCity) {
        return townCityRepository.findById(townCity.getId()).orElseThrow();
    }

    protected void assertPersistedTownCityToMatchAllProperties(TownCity expectedTownCity) {
        assertTownCityAllPropertiesEquals(expectedTownCity, getPersistedTownCity(expectedTownCity));
    }

    protected void assertPersistedTownCityToMatchUpdatableProperties(TownCity expectedTownCity) {
        assertTownCityAllUpdatablePropertiesEquals(expectedTownCity, getPersistedTownCity(expectedTownCity));
    }
}
