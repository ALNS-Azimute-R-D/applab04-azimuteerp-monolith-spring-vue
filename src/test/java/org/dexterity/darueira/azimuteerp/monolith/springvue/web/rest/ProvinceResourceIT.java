package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProvinceAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Country;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Province;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ProvinceRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ProvinceService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProvinceDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ProvinceMapper;
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
 * Integration tests for the {@link ProvinceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProvinceResourceIT {

    private static final String DEFAULT_ACRONYM = "AAA";
    private static final String UPDATED_ACRONYM = "BBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/provinces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Mock
    private ProvinceRepository provinceRepositoryMock;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Mock
    private ProvinceService provinceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProvinceMockMvc;

    private Province province;

    private Province insertedProvince;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Province createEntity(EntityManager em) {
        Province province = new Province()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .geoPolygonArea(DEFAULT_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            country = CountryResourceIT.createEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        province.setCountry(country);
        return province;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Province createUpdatedEntity(EntityManager em) {
        Province province = new Province()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            country = CountryResourceIT.createUpdatedEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        province.setCountry(country);
        return province;
    }

    @BeforeEach
    public void initTest() {
        province = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProvince != null) {
            provinceRepository.delete(insertedProvince);
            insertedProvince = null;
        }
    }

    @Test
    @Transactional
    void createProvince() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);
        var returnedProvinceDTO = om.readValue(
            restProvinceMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provinceDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProvinceDTO.class
        );

        // Validate the Province in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProvince = provinceMapper.toEntity(returnedProvinceDTO);
        assertProvinceUpdatableFieldsEquals(returnedProvince, getPersistedProvince(returnedProvince));

        insertedProvince = returnedProvince;
    }

    @Test
    @Transactional
    void createProvinceWithExistingId() throws Exception {
        // Create the Province with an existing ID
        province.setId(1L);
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provinceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        province.setAcronym(null);

        // Create the Province, which fails.
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provinceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        province.setName(null);

        // Create the Province, which fails.
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        restProvinceMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provinceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProvinces() throws Exception {
        // Initialize the database
        insertedProvince = provinceRepository.saveAndFlush(province);

        // Get all the provinceList
        restProvinceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(province.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].geoPolygonAreaContentType").value(hasItem(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].geoPolygonArea").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProvincesWithEagerRelationshipsIsEnabled() throws Exception {
        when(provinceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProvinceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(provinceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProvincesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(provinceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProvinceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(provinceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProvince() throws Exception {
        // Initialize the database
        insertedProvince = provinceRepository.saveAndFlush(province);

        // Get the province
        restProvinceMockMvc
            .perform(get(ENTITY_API_URL_ID, province.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(province.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.geoPolygonAreaContentType").value(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE))
            .andExpect(jsonPath("$.geoPolygonArea").value(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA)));
    }

    @Test
    @Transactional
    void getNonExistingProvince() throws Exception {
        // Get the province
        restProvinceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProvince() throws Exception {
        // Initialize the database
        insertedProvince = provinceRepository.saveAndFlush(province);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the province
        Province updatedProvince = provinceRepository.findById(province.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProvince are not directly saved in db
        em.detach(updatedProvince);
        updatedProvince
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        ProvinceDTO provinceDTO = provinceMapper.toDto(updatedProvince);

        restProvinceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provinceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(provinceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProvinceToMatchAllProperties(updatedProvince);
    }

    @Test
    @Transactional
    void putNonExistingProvince() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        province.setId(longCount.incrementAndGet());

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, provinceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(provinceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProvince() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        province.setId(longCount.incrementAndGet());

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(provinceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProvince() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        province.setId(longCount.incrementAndGet());

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(provinceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProvinceWithPatch() throws Exception {
        // Initialize the database
        insertedProvince = provinceRepository.saveAndFlush(province);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the province using partial update
        Province partialUpdatedProvince = new Province();
        partialUpdatedProvince.setId(province.getId());

        partialUpdatedProvince
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvince.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProvince))
            )
            .andExpect(status().isOk());

        // Validate the Province in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProvinceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProvince, province), getPersistedProvince(province));
    }

    @Test
    @Transactional
    void fullUpdateProvinceWithPatch() throws Exception {
        // Initialize the database
        insertedProvince = provinceRepository.saveAndFlush(province);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the province using partial update
        Province partialUpdatedProvince = new Province();
        partialUpdatedProvince.setId(province.getId());

        partialUpdatedProvince
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProvince.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProvince))
            )
            .andExpect(status().isOk());

        // Validate the Province in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProvinceUpdatableFieldsEquals(partialUpdatedProvince, getPersistedProvince(partialUpdatedProvince));
    }

    @Test
    @Transactional
    void patchNonExistingProvince() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        province.setId(longCount.incrementAndGet());

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, provinceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(provinceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProvince() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        province.setId(longCount.incrementAndGet());

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(provinceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProvince() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        province.setId(longCount.incrementAndGet());

        // Create the Province
        ProvinceDTO provinceDTO = provinceMapper.toDto(province);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProvinceMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(provinceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Province in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProvince() throws Exception {
        // Initialize the database
        insertedProvince = provinceRepository.saveAndFlush(province);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the province
        restProvinceMockMvc
            .perform(delete(ENTITY_API_URL_ID, province.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return provinceRepository.count();
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

    protected Province getPersistedProvince(Province province) {
        return provinceRepository.findById(province.getId()).orElseThrow();
    }

    protected void assertPersistedProvinceToMatchAllProperties(Province expectedProvince) {
        assertProvinceAllPropertiesEquals(expectedProvince, getPersistedProvince(expectedProvince));
    }

    protected void assertPersistedProvinceToMatchUpdatableProperties(Province expectedProvince) {
        assertProvinceAllUpdatablePropertiesEquals(expectedProvince, getPersistedProvince(expectedProvince));
    }
}
