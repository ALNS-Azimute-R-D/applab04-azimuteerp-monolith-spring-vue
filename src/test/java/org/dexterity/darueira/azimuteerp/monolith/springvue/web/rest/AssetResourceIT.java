package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.PreferredPurposeEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StatusAssetEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StorageTypeEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.AssetMapper;
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
 * Integration tests for the {@link AssetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final StorageTypeEnum DEFAULT_STORAGE_TYPE_USED = StorageTypeEnum.BLOB_IN_DB;
    private static final StorageTypeEnum UPDATED_STORAGE_TYPE_USED = StorageTypeEnum.LOCAL_FILESYSTEM;

    private static final String DEFAULT_FULL_FILENAME_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_FILENAME_PATH = "BBBBBBBBBB";

    private static final StatusAssetEnum DEFAULT_STATUS = StatusAssetEnum.ENABLED;
    private static final StatusAssetEnum UPDATED_STATUS = StatusAssetEnum.DISABLED;

    private static final PreferredPurposeEnum DEFAULT_PREFERRED_PURPOSE = PreferredPurposeEnum.USER_AVATAR;
    private static final PreferredPurposeEnum UPDATED_PREFERRED_PURPOSE = PreferredPurposeEnum.LOGO_IMG;

    private static final byte[] DEFAULT_ASSET_CONTENT_AS_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSET_CONTENT_AS_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE = "image/png";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetRepository assetRepository;

    @Mock
    private AssetRepository assetRepositoryMock;

    @Autowired
    private AssetMapper assetMapper;

    @Mock
    private AssetService assetServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetMockMvc;

    private Asset asset;

    private Asset insertedAsset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .name(DEFAULT_NAME)
            .storageTypeUsed(DEFAULT_STORAGE_TYPE_USED)
            .fullFilenamePath(DEFAULT_FULL_FILENAME_PATH)
            .status(DEFAULT_STATUS)
            .preferredPurpose(DEFAULT_PREFERRED_PURPOSE)
            .assetContentAsBlob(DEFAULT_ASSET_CONTENT_AS_BLOB)
            .assetContentAsBlobContentType(DEFAULT_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        AssetType assetType;
        if (TestUtil.findAll(em, AssetType.class).isEmpty()) {
            assetType = AssetTypeResourceIT.createEntity(em);
            em.persist(assetType);
            em.flush();
        } else {
            assetType = TestUtil.findAll(em, AssetType.class).get(0);
        }
        asset.setAssetType(assetType);
        return asset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asset createUpdatedEntity(EntityManager em) {
        Asset asset = new Asset()
            .name(UPDATED_NAME)
            .storageTypeUsed(UPDATED_STORAGE_TYPE_USED)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .status(UPDATED_STATUS)
            .preferredPurpose(UPDATED_PREFERRED_PURPOSE)
            .assetContentAsBlob(UPDATED_ASSET_CONTENT_AS_BLOB)
            .assetContentAsBlobContentType(UPDATED_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        AssetType assetType;
        if (TestUtil.findAll(em, AssetType.class).isEmpty()) {
            assetType = AssetTypeResourceIT.createUpdatedEntity(em);
            em.persist(assetType);
            em.flush();
        } else {
            assetType = TestUtil.findAll(em, AssetType.class).get(0);
        }
        asset.setAssetType(assetType);
        return asset;
    }

    @BeforeEach
    public void initTest() {
        asset = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAsset != null) {
            assetRepository.delete(insertedAsset);
            insertedAsset = null;
        }
    }

    @Test
    @Transactional
    void createAsset() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);
        var returnedAssetDTO = om.readValue(
            restAssetMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetDTO.class
        );

        // Validate the Asset in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAsset = assetMapper.toEntity(returnedAssetDTO);
        assertAssetUpdatableFieldsEquals(returnedAsset, getPersistedAsset(returnedAsset));

        insertedAsset = returnedAsset;
    }

    @Test
    @Transactional
    void createAssetWithExistingId() throws Exception {
        // Create the Asset with an existing ID
        asset.setId(1L);
        AssetDTO assetDTO = assetMapper.toDto(asset);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setName(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asset.setActivationStatus(null);

        // Create the Asset, which fails.
        AssetDTO assetDTO = assetMapper.toDto(asset);

        restAssetMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssets() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].storageTypeUsed").value(hasItem(DEFAULT_STORAGE_TYPE_USED.toString())))
            .andExpect(jsonPath("$.[*].fullFilenamePath").value(hasItem(DEFAULT_FULL_FILENAME_PATH)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].preferredPurpose").value(hasItem(DEFAULT_PREFERRED_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].assetContentAsBlobContentType").value(hasItem(DEFAULT_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE)))
            .andExpect(
                jsonPath("$.[*].assetContentAsBlob").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_ASSET_CONTENT_AS_BLOB)))
            )
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(assetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assetRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAsset() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc
            .perform(get(ENTITY_API_URL_ID, asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.storageTypeUsed").value(DEFAULT_STORAGE_TYPE_USED.toString()))
            .andExpect(jsonPath("$.fullFilenamePath").value(DEFAULT_FULL_FILENAME_PATH))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.preferredPurpose").value(DEFAULT_PREFERRED_PURPOSE.toString()))
            .andExpect(jsonPath("$.assetContentAsBlobContentType").value(DEFAULT_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.assetContentAsBlob").value(Base64.getEncoder().encodeToString(DEFAULT_ASSET_CONTENT_AS_BLOB)))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAsset() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asset
        Asset updatedAsset = assetRepository.findById(asset.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAsset are not directly saved in db
        em.detach(updatedAsset);
        updatedAsset
            .name(UPDATED_NAME)
            .storageTypeUsed(UPDATED_STORAGE_TYPE_USED)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .status(UPDATED_STATUS)
            .preferredPurpose(UPDATED_PREFERRED_PURPOSE)
            .assetContentAsBlob(UPDATED_ASSET_CONTENT_AS_BLOB)
            .assetContentAsBlobContentType(UPDATED_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        AssetDTO assetDTO = assetMapper.toDto(updatedAsset);

        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetToMatchAllProperties(updatedAsset);
    }

    @Test
    @Transactional
    void putNonExistingAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetWithPatch() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asset using partial update
        Asset partialUpdatedAsset = new Asset();
        partialUpdatedAsset.setId(asset.getId());

        partialUpdatedAsset
            .assetContentAsBlob(UPDATED_ASSET_CONTENT_AS_BLOB)
            .assetContentAsBlobContentType(UPDATED_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsset.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsset))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAsset, asset), getPersistedAsset(asset));
    }

    @Test
    @Transactional
    void fullUpdateAssetWithPatch() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asset using partial update
        Asset partialUpdatedAsset = new Asset();
        partialUpdatedAsset.setId(asset.getId());

        partialUpdatedAsset
            .name(UPDATED_NAME)
            .storageTypeUsed(UPDATED_STORAGE_TYPE_USED)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .status(UPDATED_STATUS)
            .preferredPurpose(UPDATED_PREFERRED_PURPOSE)
            .assetContentAsBlob(UPDATED_ASSET_CONTENT_AS_BLOB)
            .assetContentAsBlobContentType(UPDATED_ASSET_CONTENT_AS_BLOB_CONTENT_TYPE)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsset.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsset))
            )
            .andExpect(status().isOk());

        // Validate the Asset in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetUpdatableFieldsEquals(partialUpdatedAsset, getPersistedAsset(partialUpdatedAsset));
    }

    @Test
    @Transactional
    void patchNonExistingAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsset() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asset.setId(longCount.incrementAndGet());

        // Create the Asset
        AssetDTO assetDTO = assetMapper.toDto(asset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asset in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsset() throws Exception {
        // Initialize the database
        insertedAsset = assetRepository.saveAndFlush(asset);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the asset
        restAssetMockMvc
            .perform(delete(ENTITY_API_URL_ID, asset.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetRepository.count();
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

    protected Asset getPersistedAsset(Asset asset) {
        return assetRepository.findById(asset.getId()).orElseThrow();
    }

    protected void assertPersistedAssetToMatchAllProperties(Asset expectedAsset) {
        assertAssetAllPropertiesEquals(expectedAsset, getPersistedAsset(expectedAsset));
    }

    protected void assertPersistedAssetToMatchUpdatableProperties(Asset expectedAsset) {
        assertAssetAllUpdatablePropertiesEquals(expectedAsset, getPersistedAsset(expectedAsset));
    }
}
