package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.DistrictAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.DistrictRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.DistrictService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.DistrictDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.DistrictMapper;
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
 * Integration tests for the {@link DistrictResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DistrictResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/districts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DistrictRepository districtRepository;

    @Mock
    private DistrictRepository districtRepositoryMock;

    @Autowired
    private DistrictMapper districtMapper;

    @Mock
    private DistrictService districtServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictMockMvc;

    private District district;

    private District insertedDistrict;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createEntity(EntityManager em) {
        District district = new District()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .geoPolygonArea(DEFAULT_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        TownCity townCity;
        if (TestUtil.findAll(em, TownCity.class).isEmpty()) {
            townCity = TownCityResourceIT.createEntity(em);
            em.persist(townCity);
            em.flush();
        } else {
            townCity = TestUtil.findAll(em, TownCity.class).get(0);
        }
        district.setTownCity(townCity);
        return district;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static District createUpdatedEntity(EntityManager em) {
        District district = new District()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        TownCity townCity;
        if (TestUtil.findAll(em, TownCity.class).isEmpty()) {
            townCity = TownCityResourceIT.createUpdatedEntity(em);
            em.persist(townCity);
            em.flush();
        } else {
            townCity = TestUtil.findAll(em, TownCity.class).get(0);
        }
        district.setTownCity(townCity);
        return district;
    }

    @BeforeEach
    public void initTest() {
        district = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDistrict != null) {
            districtRepository.delete(insertedDistrict);
            insertedDistrict = null;
        }
    }

    @Test
    @Transactional
    void createDistrict() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);
        var returnedDistrictDTO = om.readValue(
            restDistrictMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DistrictDTO.class
        );

        // Validate the District in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDistrict = districtMapper.toEntity(returnedDistrictDTO);
        assertDistrictUpdatableFieldsEquals(returnedDistrict, getPersistedDistrict(returnedDistrict));

        insertedDistrict = returnedDistrict;
    }

    @Test
    @Transactional
    void createDistrictWithExistingId() throws Exception {
        // Create the District with an existing ID
        district.setId(1L);
        DistrictDTO districtDTO = districtMapper.toDto(district);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        district.setAcronym(null);

        // Create the District, which fails.
        DistrictDTO districtDTO = districtMapper.toDto(district);

        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        district.setName(null);

        // Create the District, which fails.
        DistrictDTO districtDTO = districtMapper.toDto(district);

        restDistrictMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDistricts() throws Exception {
        // Initialize the database
        insertedDistrict = districtRepository.saveAndFlush(district);

        // Get all the districtList
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(district.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].geoPolygonAreaContentType").value(hasItem(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].geoPolygonArea").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDistrictsWithEagerRelationshipsIsEnabled() throws Exception {
        when(districtServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDistrictMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(districtServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDistrictsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(districtServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDistrictMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(districtRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDistrict() throws Exception {
        // Initialize the database
        insertedDistrict = districtRepository.saveAndFlush(district);

        // Get the district
        restDistrictMockMvc
            .perform(get(ENTITY_API_URL_ID, district.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(district.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.geoPolygonAreaContentType").value(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE))
            .andExpect(jsonPath("$.geoPolygonArea").value(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA)));
    }

    @Test
    @Transactional
    void getNonExistingDistrict() throws Exception {
        // Get the district
        restDistrictMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistrict() throws Exception {
        // Initialize the database
        insertedDistrict = districtRepository.saveAndFlush(district);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the district
        District updatedDistrict = districtRepository.findById(district.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDistrict are not directly saved in db
        em.detach(updatedDistrict);
        updatedDistrict
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        DistrictDTO districtDTO = districtMapper.toDto(updatedDistrict);

        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isOk());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDistrictToMatchAllProperties(updatedDistrict);
    }

    @Test
    @Transactional
    void putNonExistingDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(districtDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistrictWithPatch() throws Exception {
        // Initialize the database
        insertedDistrict = districtRepository.saveAndFlush(district);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the district using partial update
        District partialUpdatedDistrict = new District();
        partialUpdatedDistrict.setId(district.getId());

        partialUpdatedDistrict.name(UPDATED_NAME);

        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrict.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDistrict))
            )
            .andExpect(status().isOk());

        // Validate the District in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDistrictUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDistrict, district), getPersistedDistrict(district));
    }

    @Test
    @Transactional
    void fullUpdateDistrictWithPatch() throws Exception {
        // Initialize the database
        insertedDistrict = districtRepository.saveAndFlush(district);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the district using partial update
        District partialUpdatedDistrict = new District();
        partialUpdatedDistrict.setId(district.getId());

        partialUpdatedDistrict
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrict.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDistrict))
            )
            .andExpect(status().isOk());

        // Validate the District in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDistrictUpdatableFieldsEquals(partialUpdatedDistrict, getPersistedDistrict(partialUpdatedDistrict));
    }

    @Test
    @Transactional
    void patchNonExistingDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistrict() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        district.setId(longCount.incrementAndGet());

        // Create the District
        DistrictDTO districtDTO = districtMapper.toDto(district);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(districtDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the District in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistrict() throws Exception {
        // Initialize the database
        insertedDistrict = districtRepository.saveAndFlush(district);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the district
        restDistrictMockMvc
            .perform(delete(ENTITY_API_URL_ID, district.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return districtRepository.count();
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

    protected District getPersistedDistrict(District district) {
        return districtRepository.findById(district.getId()).orElseThrow();
    }

    protected void assertPersistedDistrictToMatchAllProperties(District expectedDistrict) {
        assertDistrictAllPropertiesEquals(expectedDistrict, getPersistedDistrict(expectedDistrict));
    }

    protected void assertPersistedDistrictToMatchUpdatableProperties(District expectedDistrict) {
        assertDistrictAllUpdatablePropertiesEquals(expectedDistrict, getPersistedDistrict(expectedDistrict));
    }
}
