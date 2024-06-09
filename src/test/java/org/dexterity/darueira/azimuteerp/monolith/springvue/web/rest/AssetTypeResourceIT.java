package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetTypeAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetTypeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetTypeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.AssetTypeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AssetTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetTypeResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLER_CLAZZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HANDLER_CLAZZ_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asset-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetTypeRepository assetTypeRepository;

    @Autowired
    private AssetTypeMapper assetTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetTypeMockMvc;

    private AssetType assetType;

    private AssetType insertedAssetType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetType createEntity(EntityManager em) {
        AssetType assetType = new AssetType()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .handlerClazzName(DEFAULT_HANDLER_CLAZZ_NAME)
            .customAttributesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON);
        return assetType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetType createUpdatedEntity(EntityManager em) {
        AssetType assetType = new AssetType()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);
        return assetType;
    }

    @BeforeEach
    public void initTest() {
        assetType = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAssetType != null) {
            assetTypeRepository.delete(insertedAssetType);
            insertedAssetType = null;
        }
    }

    @Test
    @Transactional
    void createAssetType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);
        var returnedAssetTypeDTO = om.readValue(
            restAssetTypeMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetTypeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetTypeDTO.class
        );

        // Validate the AssetType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAssetType = assetTypeMapper.toEntity(returnedAssetTypeDTO);
        assertAssetTypeUpdatableFieldsEquals(returnedAssetType, getPersistedAssetType(returnedAssetType));

        insertedAssetType = returnedAssetType;
    }

    @Test
    @Transactional
    void createAssetTypeWithExistingId() throws Exception {
        // Create the AssetType with an existing ID
        assetType.setId(1L);
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetTypeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        assetType.setName(null);

        // Create the AssetType, which fails.
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        restAssetTypeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetTypeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAssetTypes() throws Exception {
        // Initialize the database
        insertedAssetType = assetTypeRepository.saveAndFlush(assetType);

        // Get all the assetTypeList
        restAssetTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetType.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].handlerClazzName").value(hasItem(DEFAULT_HANDLER_CLAZZ_NAME)))
            .andExpect(jsonPath("$.[*].customAttributesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON)));
    }

    @Test
    @Transactional
    void getAssetType() throws Exception {
        // Initialize the database
        insertedAssetType = assetTypeRepository.saveAndFlush(assetType);

        // Get the assetType
        restAssetTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, assetType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetType.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.handlerClazzName").value(DEFAULT_HANDLER_CLAZZ_NAME))
            .andExpect(jsonPath("$.customAttributesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTES_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingAssetType() throws Exception {
        // Get the assetType
        restAssetTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetType() throws Exception {
        // Initialize the database
        insertedAssetType = assetTypeRepository.saveAndFlush(assetType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetType
        AssetType updatedAssetType = assetTypeRepository.findById(assetType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssetType are not directly saved in db
        em.detach(updatedAssetType);
        updatedAssetType
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(updatedAssetType);

        restAssetTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetTypeToMatchAllProperties(updatedAssetType);
    }

    @Test
    @Transactional
    void putNonExistingAssetType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetType.setId(longCount.incrementAndGet());

        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetType.setId(longCount.incrementAndGet());

        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetType.setId(longCount.incrementAndGet());

        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetTypeWithPatch() throws Exception {
        // Initialize the database
        insertedAssetType = assetTypeRepository.saveAndFlush(assetType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetType using partial update
        AssetType partialUpdatedAssetType = new AssetType();
        partialUpdatedAssetType.setId(assetType.getId());

        partialUpdatedAssetType.acronym(UPDATED_ACRONYM);

        restAssetTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetType))
            )
            .andExpect(status().isOk());

        // Validate the AssetType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetType, assetType),
            getPersistedAssetType(assetType)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetTypeWithPatch() throws Exception {
        // Initialize the database
        insertedAssetType = assetTypeRepository.saveAndFlush(assetType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetType using partial update
        AssetType partialUpdatedAssetType = new AssetType();
        partialUpdatedAssetType.setId(assetType.getId());

        partialUpdatedAssetType
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME)
            .customAttributesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTES_DETAILS_JSON);

        restAssetTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetType))
            )
            .andExpect(status().isOk());

        // Validate the AssetType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetTypeUpdatableFieldsEquals(partialUpdatedAssetType, getPersistedAssetType(partialUpdatedAssetType));
    }

    @Test
    @Transactional
    void patchNonExistingAssetType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetType.setId(longCount.incrementAndGet());

        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetTypeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetType.setId(longCount.incrementAndGet());

        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetType.setId(longCount.incrementAndGet());

        // Create the AssetType
        AssetTypeDTO assetTypeDTO = assetTypeMapper.toDto(assetType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetType() throws Exception {
        // Initialize the database
        insertedAssetType = assetTypeRepository.saveAndFlush(assetType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetType
        restAssetTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetTypeRepository.count();
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

    protected AssetType getPersistedAssetType(AssetType assetType) {
        return assetTypeRepository.findById(assetType.getId()).orElseThrow();
    }

    protected void assertPersistedAssetTypeToMatchAllProperties(AssetType expectedAssetType) {
        assertAssetTypeAllPropertiesEquals(expectedAssetType, getPersistedAssetType(expectedAssetType));
    }

    protected void assertPersistedAssetTypeToMatchUpdatableProperties(AssetType expectedAssetType) {
        assertAssetTypeAllUpdatablePropertiesEquals(expectedAssetType, getPersistedAssetType(expectedAssetType));
    }
}
