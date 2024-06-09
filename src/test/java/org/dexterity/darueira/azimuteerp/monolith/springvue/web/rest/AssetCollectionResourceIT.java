package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollectionAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetCollectionRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetCollectionService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetCollectionDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.AssetCollectionMapper;
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
 * Integration tests for the {@link AssetCollectionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssetCollectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_FILENAME_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_FILENAME_PATH = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/asset-collections";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetCollectionRepository assetCollectionRepository;

    @Mock
    private AssetCollectionRepository assetCollectionRepositoryMock;

    @Autowired
    private AssetCollectionMapper assetCollectionMapper;

    @Mock
    private AssetCollectionService assetCollectionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetCollectionMockMvc;

    private AssetCollection assetCollection;

    private AssetCollection insertedAssetCollection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetCollection createEntity(EntityManager em) {
        AssetCollection assetCollection = new AssetCollection()
            .name(DEFAULT_NAME)
            .fullFilenamePath(DEFAULT_FULL_FILENAME_PATH)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return assetCollection;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetCollection createUpdatedEntity(EntityManager em) {
        AssetCollection assetCollection = new AssetCollection()
            .name(UPDATED_NAME)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return assetCollection;
    }

    @BeforeEach
    public void initTest() {
        assetCollection = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAssetCollection != null) {
            assetCollectionRepository.delete(insertedAssetCollection);
            insertedAssetCollection = null;
        }
    }

    @Test
    @Transactional
    void createAssetCollection() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);
        var returnedAssetCollectionDTO = om.readValue(
            restAssetCollectionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(assetCollectionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetCollectionDTO.class
        );

        // Validate the AssetCollection in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAssetCollection = assetCollectionMapper.toEntity(returnedAssetCollectionDTO);
        assertAssetCollectionUpdatableFieldsEquals(returnedAssetCollection, getPersistedAssetCollection(returnedAssetCollection));

        insertedAssetCollection = returnedAssetCollection;
    }

    @Test
    @Transactional
    void createAssetCollectionWithExistingId() throws Exception {
        // Create the AssetCollection with an existing ID
        assetCollection.setId(1L);
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assetCollection.setName(null);

        // Create the AssetCollection, which fails.
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        restAssetCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assetCollection.setActivationStatus(null);

        // Create the AssetCollection, which fails.
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        restAssetCollectionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssetCollections() throws Exception {
        // Initialize the database
        insertedAssetCollection = assetCollectionRepository.saveAndFlush(assetCollection);

        // Get all the assetCollectionList
        restAssetCollectionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetCollection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fullFilenamePath").value(hasItem(DEFAULT_FULL_FILENAME_PATH)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetCollectionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(assetCollectionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetCollectionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assetCollectionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetCollectionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assetCollectionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetCollectionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assetCollectionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAssetCollection() throws Exception {
        // Initialize the database
        insertedAssetCollection = assetCollectionRepository.saveAndFlush(assetCollection);

        // Get the assetCollection
        restAssetCollectionMockMvc
            .perform(get(ENTITY_API_URL_ID, assetCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetCollection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fullFilenamePath").value(DEFAULT_FULL_FILENAME_PATH))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAssetCollection() throws Exception {
        // Get the assetCollection
        restAssetCollectionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetCollection() throws Exception {
        // Initialize the database
        insertedAssetCollection = assetCollectionRepository.saveAndFlush(assetCollection);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetCollection
        AssetCollection updatedAssetCollection = assetCollectionRepository.findById(assetCollection.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssetCollection are not directly saved in db
        em.detach(updatedAssetCollection);
        updatedAssetCollection.name(UPDATED_NAME).fullFilenamePath(UPDATED_FULL_FILENAME_PATH).activationStatus(UPDATED_ACTIVATION_STATUS);
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(updatedAssetCollection);

        restAssetCollectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetCollectionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetCollectionToMatchAllProperties(updatedAssetCollection);
    }

    @Test
    @Transactional
    void putNonExistingAssetCollection() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetCollection.setId(longCount.incrementAndGet());

        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetCollectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetCollectionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetCollection() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetCollection.setId(longCount.incrementAndGet());

        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetCollectionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetCollection() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetCollection.setId(longCount.incrementAndGet());

        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetCollectionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetCollectionWithPatch() throws Exception {
        // Initialize the database
        insertedAssetCollection = assetCollectionRepository.saveAndFlush(assetCollection);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetCollection using partial update
        AssetCollection partialUpdatedAssetCollection = new AssetCollection();
        partialUpdatedAssetCollection.setId(assetCollection.getId());

        partialUpdatedAssetCollection.name(UPDATED_NAME).fullFilenamePath(UPDATED_FULL_FILENAME_PATH);

        restAssetCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetCollection.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetCollection))
            )
            .andExpect(status().isOk());

        // Validate the AssetCollection in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetCollectionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetCollection, assetCollection),
            getPersistedAssetCollection(assetCollection)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetCollectionWithPatch() throws Exception {
        // Initialize the database
        insertedAssetCollection = assetCollectionRepository.saveAndFlush(assetCollection);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetCollection using partial update
        AssetCollection partialUpdatedAssetCollection = new AssetCollection();
        partialUpdatedAssetCollection.setId(assetCollection.getId());

        partialUpdatedAssetCollection
            .name(UPDATED_NAME)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restAssetCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetCollection.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetCollection))
            )
            .andExpect(status().isOk());

        // Validate the AssetCollection in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetCollectionUpdatableFieldsEquals(
            partialUpdatedAssetCollection,
            getPersistedAssetCollection(partialUpdatedAssetCollection)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAssetCollection() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetCollection.setId(longCount.incrementAndGet());

        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetCollectionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetCollection() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetCollection.setId(longCount.incrementAndGet());

        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetCollection() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetCollection.setId(longCount.incrementAndGet());

        // Create the AssetCollection
        AssetCollectionDTO assetCollectionDTO = assetCollectionMapper.toDto(assetCollection);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetCollectionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetCollectionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetCollection in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetCollection() throws Exception {
        // Initialize the database
        insertedAssetCollection = assetCollectionRepository.saveAndFlush(assetCollection);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetCollection
        restAssetCollectionMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetCollection.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetCollectionRepository.count();
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

    protected AssetCollection getPersistedAssetCollection(AssetCollection assetCollection) {
        return assetCollectionRepository.findById(assetCollection.getId()).orElseThrow();
    }

    protected void assertPersistedAssetCollectionToMatchAllProperties(AssetCollection expectedAssetCollection) {
        assertAssetCollectionAllPropertiesEquals(expectedAssetCollection, getPersistedAssetCollection(expectedAssetCollection));
    }

    protected void assertPersistedAssetCollectionToMatchUpdatableProperties(AssetCollection expectedAssetCollection) {
        assertAssetCollectionAllUpdatablePropertiesEquals(expectedAssetCollection, getPersistedAssetCollection(expectedAssetCollection));
    }
}
