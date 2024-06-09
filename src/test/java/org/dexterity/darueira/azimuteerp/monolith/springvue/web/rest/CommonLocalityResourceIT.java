package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocalityAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.District;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.CommonLocalityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.CommonLocalityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CommonLocalityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.CommonLocalityMapper;
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
 * Integration tests for the {@link CommonLocalityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CommonLocalityResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBB";

    private static final byte[] DEFAULT_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GEO_POLYGON_AREA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/common-localities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CommonLocalityRepository commonLocalityRepository;

    @Mock
    private CommonLocalityRepository commonLocalityRepositoryMock;

    @Autowired
    private CommonLocalityMapper commonLocalityMapper;

    @Mock
    private CommonLocalityService commonLocalityServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommonLocalityMockMvc;

    private CommonLocality commonLocality;

    private CommonLocality insertedCommonLocality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonLocality createEntity(EntityManager em) {
        CommonLocality commonLocality = new CommonLocality()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .houseNumber(DEFAULT_HOUSE_NUMBER)
            .locationName(DEFAULT_LOCATION_NAME)
            .postalCode(DEFAULT_POSTAL_CODE)
            .geoPolygonArea(DEFAULT_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        commonLocality.setDistrict(district);
        return commonLocality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommonLocality createUpdatedEntity(EntityManager em) {
        CommonLocality commonLocality = new CommonLocality()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        // Add required entity
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createUpdatedEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        commonLocality.setDistrict(district);
        return commonLocality;
    }

    @BeforeEach
    public void initTest() {
        commonLocality = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCommonLocality != null) {
            commonLocalityRepository.delete(insertedCommonLocality);
            insertedCommonLocality = null;
        }
    }

    @Test
    @Transactional
    void createCommonLocality() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);
        var returnedCommonLocalityDTO = om.readValue(
            restCommonLocalityMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(commonLocalityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CommonLocalityDTO.class
        );

        // Validate the CommonLocality in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCommonLocality = commonLocalityMapper.toEntity(returnedCommonLocalityDTO);
        assertCommonLocalityUpdatableFieldsEquals(returnedCommonLocality, getPersistedCommonLocality(returnedCommonLocality));

        insertedCommonLocality = returnedCommonLocality;
    }

    @Test
    @Transactional
    void createCommonLocalityWithExistingId() throws Exception {
        // Create the CommonLocality with an existing ID
        commonLocality.setId(1L);
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommonLocalityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        commonLocality.setAcronym(null);

        // Create the CommonLocality, which fails.
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        restCommonLocalityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        commonLocality.setName(null);

        // Create the CommonLocality, which fails.
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        restCommonLocalityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetAddressIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        commonLocality.setStreetAddress(null);

        // Create the CommonLocality, which fails.
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        restCommonLocalityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        commonLocality.setPostalCode(null);

        // Create the CommonLocality, which fails.
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        restCommonLocalityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCommonLocalities() throws Exception {
        // Initialize the database
        insertedCommonLocality = commonLocalityRepository.saveAndFlush(commonLocality);

        // Get all the commonLocalityList
        restCommonLocalityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commonLocality.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].houseNumber").value(hasItem(DEFAULT_HOUSE_NUMBER)))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].geoPolygonAreaContentType").value(hasItem(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].geoPolygonArea").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommonLocalitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(commonLocalityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommonLocalityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(commonLocalityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCommonLocalitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(commonLocalityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCommonLocalityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(commonLocalityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCommonLocality() throws Exception {
        // Initialize the database
        insertedCommonLocality = commonLocalityRepository.saveAndFlush(commonLocality);

        // Get the commonLocality
        restCommonLocalityMockMvc
            .perform(get(ENTITY_API_URL_ID, commonLocality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commonLocality.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_HOUSE_NUMBER))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.geoPolygonAreaContentType").value(DEFAULT_GEO_POLYGON_AREA_CONTENT_TYPE))
            .andExpect(jsonPath("$.geoPolygonArea").value(Base64.getEncoder().encodeToString(DEFAULT_GEO_POLYGON_AREA)));
    }

    @Test
    @Transactional
    void getNonExistingCommonLocality() throws Exception {
        // Get the commonLocality
        restCommonLocalityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCommonLocality() throws Exception {
        // Initialize the database
        insertedCommonLocality = commonLocalityRepository.saveAndFlush(commonLocality);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commonLocality
        CommonLocality updatedCommonLocality = commonLocalityRepository.findById(commonLocality.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCommonLocality are not directly saved in db
        em.detach(updatedCommonLocality);
        updatedCommonLocality
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(updatedCommonLocality);

        restCommonLocalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commonLocalityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isOk());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCommonLocalityToMatchAllProperties(updatedCommonLocality);
    }

    @Test
    @Transactional
    void putNonExistingCommonLocality() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonLocality.setId(longCount.incrementAndGet());

        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonLocalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commonLocalityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommonLocality() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonLocality.setId(longCount.incrementAndGet());

        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonLocalityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommonLocality() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonLocality.setId(longCount.incrementAndGet());

        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonLocalityMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommonLocalityWithPatch() throws Exception {
        // Initialize the database
        insertedCommonLocality = commonLocalityRepository.saveAndFlush(commonLocality);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commonLocality using partial update
        CommonLocality partialUpdatedCommonLocality = new CommonLocality();
        partialUpdatedCommonLocality.setId(commonLocality.getId());

        partialUpdatedCommonLocality
            .name(UPDATED_NAME)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restCommonLocalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommonLocality.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommonLocality))
            )
            .andExpect(status().isOk());

        // Validate the CommonLocality in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommonLocalityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCommonLocality, commonLocality),
            getPersistedCommonLocality(commonLocality)
        );
    }

    @Test
    @Transactional
    void fullUpdateCommonLocalityWithPatch() throws Exception {
        // Initialize the database
        insertedCommonLocality = commonLocalityRepository.saveAndFlush(commonLocality);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the commonLocality using partial update
        CommonLocality partialUpdatedCommonLocality = new CommonLocality();
        partialUpdatedCommonLocality.setId(commonLocality.getId());

        partialUpdatedCommonLocality
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .houseNumber(UPDATED_HOUSE_NUMBER)
            .locationName(UPDATED_LOCATION_NAME)
            .postalCode(UPDATED_POSTAL_CODE)
            .geoPolygonArea(UPDATED_GEO_POLYGON_AREA)
            .geoPolygonAreaContentType(UPDATED_GEO_POLYGON_AREA_CONTENT_TYPE);

        restCommonLocalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommonLocality.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCommonLocality))
            )
            .andExpect(status().isOk());

        // Validate the CommonLocality in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCommonLocalityUpdatableFieldsEquals(partialUpdatedCommonLocality, getPersistedCommonLocality(partialUpdatedCommonLocality));
    }

    @Test
    @Transactional
    void patchNonExistingCommonLocality() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonLocality.setId(longCount.incrementAndGet());

        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommonLocalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commonLocalityDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommonLocality() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonLocality.setId(longCount.incrementAndGet());

        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonLocalityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommonLocality() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        commonLocality.setId(longCount.incrementAndGet());

        // Create the CommonLocality
        CommonLocalityDTO commonLocalityDTO = commonLocalityMapper.toDto(commonLocality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommonLocalityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(commonLocalityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommonLocality in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommonLocality() throws Exception {
        // Initialize the database
        insertedCommonLocality = commonLocalityRepository.saveAndFlush(commonLocality);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the commonLocality
        restCommonLocalityMockMvc
            .perform(delete(ENTITY_API_URL_ID, commonLocality.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return commonLocalityRepository.count();
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

    protected CommonLocality getPersistedCommonLocality(CommonLocality commonLocality) {
        return commonLocalityRepository.findById(commonLocality.getId()).orElseThrow();
    }

    protected void assertPersistedCommonLocalityToMatchAllProperties(CommonLocality expectedCommonLocality) {
        assertCommonLocalityAllPropertiesEquals(expectedCommonLocality, getPersistedCommonLocality(expectedCommonLocality));
    }

    protected void assertPersistedCommonLocalityToMatchUpdatableProperties(CommonLocality expectedCommonLocality) {
        assertCommonLocalityAllUpdatablePropertiesEquals(expectedCommonLocality, getPersistedCommonLocality(expectedCommonLocality));
    }
}
