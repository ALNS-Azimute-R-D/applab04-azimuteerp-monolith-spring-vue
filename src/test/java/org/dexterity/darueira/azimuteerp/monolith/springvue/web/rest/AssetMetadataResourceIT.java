package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadataAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadata;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetMetadataRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetMetadataService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetMetadataDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.AssetMetadataMapper;
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
 * Integration tests for the {@link AssetMetadataResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AssetMetadataResourceIT {

    private static final String DEFAULT_METADATA_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_METADATA_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asset-metadata";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetMetadataRepository assetMetadataRepository;

    @Mock
    private AssetMetadataRepository assetMetadataRepositoryMock;

    @Autowired
    private AssetMetadataMapper assetMetadataMapper;

    @Mock
    private AssetMetadataService assetMetadataServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetMetadataMockMvc;

    private AssetMetadata assetMetadata;

    private AssetMetadata insertedAssetMetadata;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetMetadata createEntity(EntityManager em) {
        AssetMetadata assetMetadata = new AssetMetadata().metadataDetailsJSON(DEFAULT_METADATA_DETAILS_JSON);
        // Add required entity
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            asset = AssetResourceIT.createEntity(em);
            em.persist(asset);
            em.flush();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        assetMetadata.setAsset(asset);
        return assetMetadata;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetMetadata createUpdatedEntity(EntityManager em) {
        AssetMetadata assetMetadata = new AssetMetadata().metadataDetailsJSON(UPDATED_METADATA_DETAILS_JSON);
        // Add required entity
        Asset asset;
        if (TestUtil.findAll(em, Asset.class).isEmpty()) {
            asset = AssetResourceIT.createUpdatedEntity(em);
            em.persist(asset);
            em.flush();
        } else {
            asset = TestUtil.findAll(em, Asset.class).get(0);
        }
        assetMetadata.setAsset(asset);
        return assetMetadata;
    }

    @BeforeEach
    public void initTest() {
        assetMetadata = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAssetMetadata != null) {
            assetMetadataRepository.delete(insertedAssetMetadata);
            insertedAssetMetadata = null;
        }
    }

    @Test
    @Transactional
    void createAssetMetadata() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);
        var returnedAssetMetadataDTO = om.readValue(
            restAssetMetadataMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(assetMetadataDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetMetadataDTO.class
        );

        // Validate the AssetMetadata in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAssetMetadata = assetMetadataMapper.toEntity(returnedAssetMetadataDTO);
        assertAssetMetadataUpdatableFieldsEquals(returnedAssetMetadata, getPersistedAssetMetadata(returnedAssetMetadata));

        insertedAssetMetadata = returnedAssetMetadata;
    }

    @Test
    @Transactional
    void createAssetMetadataWithExistingId() throws Exception {
        // Create the AssetMetadata with an existing ID
        assetMetadata.setId(1L);
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMetadataMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetMetadata() throws Exception {
        // Initialize the database
        insertedAssetMetadata = assetMetadataRepository.saveAndFlush(assetMetadata);

        // Get all the assetMetadataList
        restAssetMetadataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetMetadata.getId().intValue())))
            .andExpect(jsonPath("$.[*].metadataDetailsJSON").value(hasItem(DEFAULT_METADATA_DETAILS_JSON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetMetadataWithEagerRelationshipsIsEnabled() throws Exception {
        when(assetMetadataServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetMetadataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(assetMetadataServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAssetMetadataWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(assetMetadataServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAssetMetadataMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(assetMetadataRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAssetMetadata() throws Exception {
        // Initialize the database
        insertedAssetMetadata = assetMetadataRepository.saveAndFlush(assetMetadata);

        // Get the assetMetadata
        restAssetMetadataMockMvc
            .perform(get(ENTITY_API_URL_ID, assetMetadata.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetMetadata.getId().intValue()))
            .andExpect(jsonPath("$.metadataDetailsJSON").value(DEFAULT_METADATA_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingAssetMetadata() throws Exception {
        // Get the assetMetadata
        restAssetMetadataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetMetadata() throws Exception {
        // Initialize the database
        insertedAssetMetadata = assetMetadataRepository.saveAndFlush(assetMetadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetMetadata
        AssetMetadata updatedAssetMetadata = assetMetadataRepository.findById(assetMetadata.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssetMetadata are not directly saved in db
        em.detach(updatedAssetMetadata);
        updatedAssetMetadata.metadataDetailsJSON(UPDATED_METADATA_DETAILS_JSON);
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(updatedAssetMetadata);

        restAssetMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetMetadataDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetMetadataToMatchAllProperties(updatedAssetMetadata);
    }

    @Test
    @Transactional
    void putNonExistingAssetMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMetadata.setId(longCount.incrementAndGet());

        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetMetadataDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMetadata.setId(longCount.incrementAndGet());

        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMetadataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMetadata.setId(longCount.incrementAndGet());

        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMetadataMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetMetadataWithPatch() throws Exception {
        // Initialize the database
        insertedAssetMetadata = assetMetadataRepository.saveAndFlush(assetMetadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetMetadata using partial update
        AssetMetadata partialUpdatedAssetMetadata = new AssetMetadata();
        partialUpdatedAssetMetadata.setId(assetMetadata.getId());

        restAssetMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetMetadata))
            )
            .andExpect(status().isOk());

        // Validate the AssetMetadata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetMetadataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetMetadata, assetMetadata),
            getPersistedAssetMetadata(assetMetadata)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetMetadataWithPatch() throws Exception {
        // Initialize the database
        insertedAssetMetadata = assetMetadataRepository.saveAndFlush(assetMetadata);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetMetadata using partial update
        AssetMetadata partialUpdatedAssetMetadata = new AssetMetadata();
        partialUpdatedAssetMetadata.setId(assetMetadata.getId());

        partialUpdatedAssetMetadata.metadataDetailsJSON(UPDATED_METADATA_DETAILS_JSON);

        restAssetMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetMetadata.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetMetadata))
            )
            .andExpect(status().isOk());

        // Validate the AssetMetadata in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetMetadataUpdatableFieldsEquals(partialUpdatedAssetMetadata, getPersistedAssetMetadata(partialUpdatedAssetMetadata));
    }

    @Test
    @Transactional
    void patchNonExistingAssetMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMetadata.setId(longCount.incrementAndGet());

        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetMetadataDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMetadata.setId(longCount.incrementAndGet());

        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetMetadata() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetMetadata.setId(longCount.incrementAndGet());

        // Create the AssetMetadata
        AssetMetadataDTO assetMetadataDTO = assetMetadataMapper.toDto(assetMetadata);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetMetadataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetMetadataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetMetadata in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetMetadata() throws Exception {
        // Initialize the database
        insertedAssetMetadata = assetMetadataRepository.saveAndFlush(assetMetadata);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetMetadata
        restAssetMetadataMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetMetadata.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetMetadataRepository.count();
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

    protected AssetMetadata getPersistedAssetMetadata(AssetMetadata assetMetadata) {
        return assetMetadataRepository.findById(assetMetadata.getId()).orElseThrow();
    }

    protected void assertPersistedAssetMetadataToMatchAllProperties(AssetMetadata expectedAssetMetadata) {
        assertAssetMetadataAllPropertiesEquals(expectedAssetMetadata, getPersistedAssetMetadata(expectedAssetMetadata));
    }

    protected void assertPersistedAssetMetadataToMatchUpdatableProperties(AssetMetadata expectedAssetMetadata) {
        assertAssetMetadataAllUpdatablePropertiesEquals(expectedAssetMetadata, getPersistedAssetMetadata(expectedAssetMetadata));
    }
}
