package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmpAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.StatusRawProcessingEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.RawAssetProcTmpRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.RawAssetProcTmpService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.RawAssetProcTmpDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.RawAssetProcTmpMapper;
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
 * Integration tests for the {@link RawAssetProcTmpResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RawAssetProcTmpResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final StatusRawProcessingEnum DEFAULT_STATUS_RAW_PROCESSING = StatusRawProcessingEnum.UPLOADING;
    private static final StatusRawProcessingEnum UPDATED_STATUS_RAW_PROCESSING = StatusRawProcessingEnum.UPLOADED;

    private static final String DEFAULT_FULL_FILENAME_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FULL_FILENAME_PATH = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ASSET_RAW_CONTENT_AS_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSET_RAW_CONTENT_AS_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/raw-asset-proc-tmps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RawAssetProcTmpRepository rawAssetProcTmpRepository;

    @Mock
    private RawAssetProcTmpRepository rawAssetProcTmpRepositoryMock;

    @Autowired
    private RawAssetProcTmpMapper rawAssetProcTmpMapper;

    @Mock
    private RawAssetProcTmpService rawAssetProcTmpServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawAssetProcTmpMockMvc;

    private RawAssetProcTmp rawAssetProcTmp;

    private RawAssetProcTmp insertedRawAssetProcTmp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawAssetProcTmp createEntity(EntityManager em) {
        RawAssetProcTmp rawAssetProcTmp = new RawAssetProcTmp()
            .name(DEFAULT_NAME)
            .statusRawProcessing(DEFAULT_STATUS_RAW_PROCESSING)
            .fullFilenamePath(DEFAULT_FULL_FILENAME_PATH)
            .assetRawContentAsBlob(DEFAULT_ASSET_RAW_CONTENT_AS_BLOB)
            .assetRawContentAsBlobContentType(DEFAULT_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON);
        // Add required entity
        AssetType assetType;
        if (TestUtil.findAll(em, AssetType.class).isEmpty()) {
            assetType = AssetTypeResourceIT.createEntity(em);
            em.persist(assetType);
            em.flush();
        } else {
            assetType = TestUtil.findAll(em, AssetType.class).get(0);
        }
        rawAssetProcTmp.setAssetType(assetType);
        return rawAssetProcTmp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawAssetProcTmp createUpdatedEntity(EntityManager em) {
        RawAssetProcTmp rawAssetProcTmp = new RawAssetProcTmp()
            .name(UPDATED_NAME)
            .statusRawProcessing(UPDATED_STATUS_RAW_PROCESSING)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .assetRawContentAsBlob(UPDATED_ASSET_RAW_CONTENT_AS_BLOB)
            .assetRawContentAsBlobContentType(UPDATED_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);
        // Add required entity
        AssetType assetType;
        if (TestUtil.findAll(em, AssetType.class).isEmpty()) {
            assetType = AssetTypeResourceIT.createUpdatedEntity(em);
            em.persist(assetType);
            em.flush();
        } else {
            assetType = TestUtil.findAll(em, AssetType.class).get(0);
        }
        rawAssetProcTmp.setAssetType(assetType);
        return rawAssetProcTmp;
    }

    @BeforeEach
    public void initTest() {
        rawAssetProcTmp = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRawAssetProcTmp != null) {
            rawAssetProcTmpRepository.delete(insertedRawAssetProcTmp);
            insertedRawAssetProcTmp = null;
        }
    }

    @Test
    @Transactional
    void createRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);
        var returnedRawAssetProcTmpDTO = om.readValue(
            restRawAssetProcTmpMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RawAssetProcTmpDTO.class
        );

        // Validate the RawAssetProcTmp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRawAssetProcTmp = rawAssetProcTmpMapper.toEntity(returnedRawAssetProcTmpDTO);
        assertRawAssetProcTmpUpdatableFieldsEquals(returnedRawAssetProcTmp, getPersistedRawAssetProcTmp(returnedRawAssetProcTmp));

        insertedRawAssetProcTmp = returnedRawAssetProcTmp;
    }

    @Test
    @Transactional
    void createRawAssetProcTmpWithExistingId() throws Exception {
        // Create the RawAssetProcTmp with an existing ID
        rawAssetProcTmp.setId(1L);
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawAssetProcTmpMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        rawAssetProcTmp.setName(null);

        // Create the RawAssetProcTmp, which fails.
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        restRawAssetProcTmpMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRawAssetProcTmps() throws Exception {
        // Initialize the database
        insertedRawAssetProcTmp = rawAssetProcTmpRepository.saveAndFlush(rawAssetProcTmp);

        // Get all the rawAssetProcTmpList
        restRawAssetProcTmpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawAssetProcTmp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].statusRawProcessing").value(hasItem(DEFAULT_STATUS_RAW_PROCESSING.toString())))
            .andExpect(jsonPath("$.[*].fullFilenamePath").value(hasItem(DEFAULT_FULL_FILENAME_PATH)))
            .andExpect(jsonPath("$.[*].assetRawContentAsBlobContentType").value(hasItem(DEFAULT_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE)))
            .andExpect(
                jsonPath("$.[*].assetRawContentAsBlob").value(
                    hasItem(Base64.getEncoder().encodeToString(DEFAULT_ASSET_RAW_CONTENT_AS_BLOB))
                )
            )
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRawAssetProcTmpsWithEagerRelationshipsIsEnabled() throws Exception {
        when(rawAssetProcTmpServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRawAssetProcTmpMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(rawAssetProcTmpServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRawAssetProcTmpsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(rawAssetProcTmpServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRawAssetProcTmpMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(rawAssetProcTmpRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRawAssetProcTmp() throws Exception {
        // Initialize the database
        insertedRawAssetProcTmp = rawAssetProcTmpRepository.saveAndFlush(rawAssetProcTmp);

        // Get the rawAssetProcTmp
        restRawAssetProcTmpMockMvc
            .perform(get(ENTITY_API_URL_ID, rawAssetProcTmp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawAssetProcTmp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.statusRawProcessing").value(DEFAULT_STATUS_RAW_PROCESSING.toString()))
            .andExpect(jsonPath("$.fullFilenamePath").value(DEFAULT_FULL_FILENAME_PATH))
            .andExpect(jsonPath("$.assetRawContentAsBlobContentType").value(DEFAULT_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.assetRawContentAsBlob").value(Base64.getEncoder().encodeToString(DEFAULT_ASSET_RAW_CONTENT_AS_BLOB)))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingRawAssetProcTmp() throws Exception {
        // Get the rawAssetProcTmp
        restRawAssetProcTmpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRawAssetProcTmp() throws Exception {
        // Initialize the database
        insertedRawAssetProcTmp = rawAssetProcTmpRepository.saveAndFlush(rawAssetProcTmp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rawAssetProcTmp
        RawAssetProcTmp updatedRawAssetProcTmp = rawAssetProcTmpRepository.findById(rawAssetProcTmp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRawAssetProcTmp are not directly saved in db
        em.detach(updatedRawAssetProcTmp);
        updatedRawAssetProcTmp
            .name(UPDATED_NAME)
            .statusRawProcessing(UPDATED_STATUS_RAW_PROCESSING)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .assetRawContentAsBlob(UPDATED_ASSET_RAW_CONTENT_AS_BLOB)
            .assetRawContentAsBlobContentType(UPDATED_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(updatedRawAssetProcTmp);

        restRawAssetProcTmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawAssetProcTmpDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isOk());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRawAssetProcTmpToMatchAllProperties(updatedRawAssetProcTmp);
    }

    @Test
    @Transactional
    void putNonExistingRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rawAssetProcTmp.setId(longCount.incrementAndGet());

        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawAssetProcTmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawAssetProcTmpDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rawAssetProcTmp.setId(longCount.incrementAndGet());

        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawAssetProcTmpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rawAssetProcTmp.setId(longCount.incrementAndGet());

        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawAssetProcTmpMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawAssetProcTmpWithPatch() throws Exception {
        // Initialize the database
        insertedRawAssetProcTmp = rawAssetProcTmpRepository.saveAndFlush(rawAssetProcTmp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rawAssetProcTmp using partial update
        RawAssetProcTmp partialUpdatedRawAssetProcTmp = new RawAssetProcTmp();
        partialUpdatedRawAssetProcTmp.setId(rawAssetProcTmp.getId());

        partialUpdatedRawAssetProcTmp
            .name(UPDATED_NAME)
            .statusRawProcessing(UPDATED_STATUS_RAW_PROCESSING)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .assetRawContentAsBlob(UPDATED_ASSET_RAW_CONTENT_AS_BLOB)
            .assetRawContentAsBlobContentType(UPDATED_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE);

        restRawAssetProcTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawAssetProcTmp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRawAssetProcTmp))
            )
            .andExpect(status().isOk());

        // Validate the RawAssetProcTmp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRawAssetProcTmpUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRawAssetProcTmp, rawAssetProcTmp),
            getPersistedRawAssetProcTmp(rawAssetProcTmp)
        );
    }

    @Test
    @Transactional
    void fullUpdateRawAssetProcTmpWithPatch() throws Exception {
        // Initialize the database
        insertedRawAssetProcTmp = rawAssetProcTmpRepository.saveAndFlush(rawAssetProcTmp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rawAssetProcTmp using partial update
        RawAssetProcTmp partialUpdatedRawAssetProcTmp = new RawAssetProcTmp();
        partialUpdatedRawAssetProcTmp.setId(rawAssetProcTmp.getId());

        partialUpdatedRawAssetProcTmp
            .name(UPDATED_NAME)
            .statusRawProcessing(UPDATED_STATUS_RAW_PROCESSING)
            .fullFilenamePath(UPDATED_FULL_FILENAME_PATH)
            .assetRawContentAsBlob(UPDATED_ASSET_RAW_CONTENT_AS_BLOB)
            .assetRawContentAsBlobContentType(UPDATED_ASSET_RAW_CONTENT_AS_BLOB_CONTENT_TYPE)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);

        restRawAssetProcTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawAssetProcTmp.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRawAssetProcTmp))
            )
            .andExpect(status().isOk());

        // Validate the RawAssetProcTmp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRawAssetProcTmpUpdatableFieldsEquals(
            partialUpdatedRawAssetProcTmp,
            getPersistedRawAssetProcTmp(partialUpdatedRawAssetProcTmp)
        );
    }

    @Test
    @Transactional
    void patchNonExistingRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rawAssetProcTmp.setId(longCount.incrementAndGet());

        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawAssetProcTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawAssetProcTmpDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rawAssetProcTmp.setId(longCount.incrementAndGet());

        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawAssetProcTmpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawAssetProcTmp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rawAssetProcTmp.setId(longCount.incrementAndGet());

        // Create the RawAssetProcTmp
        RawAssetProcTmpDTO rawAssetProcTmpDTO = rawAssetProcTmpMapper.toDto(rawAssetProcTmp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawAssetProcTmpMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rawAssetProcTmpDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawAssetProcTmp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawAssetProcTmp() throws Exception {
        // Initialize the database
        insertedRawAssetProcTmp = rawAssetProcTmpRepository.saveAndFlush(rawAssetProcTmp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rawAssetProcTmp
        restRawAssetProcTmpMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawAssetProcTmp.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rawAssetProcTmpRepository.count();
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

    protected RawAssetProcTmp getPersistedRawAssetProcTmp(RawAssetProcTmp rawAssetProcTmp) {
        return rawAssetProcTmpRepository.findById(rawAssetProcTmp.getId()).orElseThrow();
    }

    protected void assertPersistedRawAssetProcTmpToMatchAllProperties(RawAssetProcTmp expectedRawAssetProcTmp) {
        assertRawAssetProcTmpAllPropertiesEquals(expectedRawAssetProcTmp, getPersistedRawAssetProcTmp(expectedRawAssetProcTmp));
    }

    protected void assertPersistedRawAssetProcTmpToMatchUpdatableProperties(RawAssetProcTmp expectedRawAssetProcTmp) {
        assertRawAssetProcTmpAllUpdatablePropertiesEquals(expectedRawAssetProcTmp, getPersistedRawAssetProcTmp(expectedRawAssetProcTmp));
    }
}
