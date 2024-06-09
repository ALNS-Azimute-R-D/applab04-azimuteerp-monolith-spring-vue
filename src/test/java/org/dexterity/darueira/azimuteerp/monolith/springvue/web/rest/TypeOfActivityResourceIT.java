package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivityAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfActivityMapper;
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
 * Integration tests for the {@link TypeOfActivityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfActivityResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLER_CLAZZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HANDLER_CLAZZ_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfActivityRepository typeOfActivityRepository;

    @Autowired
    private TypeOfActivityMapper typeOfActivityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfActivityMockMvc;

    private TypeOfActivity typeOfActivity;

    private TypeOfActivity insertedTypeOfActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfActivity createEntity(EntityManager em) {
        TypeOfActivity typeOfActivity = new TypeOfActivity()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .handlerClazzName(DEFAULT_HANDLER_CLAZZ_NAME);
        return typeOfActivity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfActivity createUpdatedEntity(EntityManager em) {
        TypeOfActivity typeOfActivity = new TypeOfActivity()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);
        return typeOfActivity;
    }

    @BeforeEach
    public void initTest() {
        typeOfActivity = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfActivity != null) {
            typeOfActivityRepository.delete(insertedTypeOfActivity);
            insertedTypeOfActivity = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfActivity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);
        var returnedTypeOfActivityDTO = om.readValue(
            restTypeOfActivityMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(typeOfActivityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfActivityDTO.class
        );

        // Validate the TypeOfActivity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfActivity = typeOfActivityMapper.toEntity(returnedTypeOfActivityDTO);
        assertTypeOfActivityUpdatableFieldsEquals(returnedTypeOfActivity, getPersistedTypeOfActivity(returnedTypeOfActivity));

        insertedTypeOfActivity = returnedTypeOfActivity;
    }

    @Test
    @Transactional
    void createTypeOfActivityWithExistingId() throws Exception {
        // Create the TypeOfActivity with an existing ID
        typeOfActivity.setId(1L);
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfActivityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfActivity.setName(null);

        // Create the TypeOfActivity, which fails.
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        restTypeOfActivityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfActivities() throws Exception {
        // Initialize the database
        insertedTypeOfActivity = typeOfActivityRepository.saveAndFlush(typeOfActivity);

        // Get all the typeOfActivityList
        restTypeOfActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].handlerClazzName").value(hasItem(DEFAULT_HANDLER_CLAZZ_NAME)));
    }

    @Test
    @Transactional
    void getTypeOfActivity() throws Exception {
        // Initialize the database
        insertedTypeOfActivity = typeOfActivityRepository.saveAndFlush(typeOfActivity);

        // Get the typeOfActivity
        restTypeOfActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfActivity.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.handlerClazzName").value(DEFAULT_HANDLER_CLAZZ_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfActivity() throws Exception {
        // Get the typeOfActivity
        restTypeOfActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfActivity() throws Exception {
        // Initialize the database
        insertedTypeOfActivity = typeOfActivityRepository.saveAndFlush(typeOfActivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfActivity
        TypeOfActivity updatedTypeOfActivity = typeOfActivityRepository.findById(typeOfActivity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfActivity are not directly saved in db
        em.detach(updatedTypeOfActivity);
        updatedTypeOfActivity
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(updatedTypeOfActivity);

        restTypeOfActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfActivityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfActivityToMatchAllProperties(updatedTypeOfActivity);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfActivity.setId(longCount.incrementAndGet());

        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfActivityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfActivity.setId(longCount.incrementAndGet());

        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfActivity.setId(longCount.incrementAndGet());

        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfActivityMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfActivityWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfActivity = typeOfActivityRepository.saveAndFlush(typeOfActivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfActivity using partial update
        TypeOfActivity partialUpdatedTypeOfActivity = new TypeOfActivity();
        partialUpdatedTypeOfActivity.setId(typeOfActivity.getId());

        partialUpdatedTypeOfActivity.handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);

        restTypeOfActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfActivity))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfActivity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfActivityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfActivity, typeOfActivity),
            getPersistedTypeOfActivity(typeOfActivity)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfActivityWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfActivity = typeOfActivityRepository.saveAndFlush(typeOfActivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfActivity using partial update
        TypeOfActivity partialUpdatedTypeOfActivity = new TypeOfActivity();
        partialUpdatedTypeOfActivity.setId(typeOfActivity.getId());

        partialUpdatedTypeOfActivity
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);

        restTypeOfActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfActivity))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfActivity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfActivityUpdatableFieldsEquals(partialUpdatedTypeOfActivity, getPersistedTypeOfActivity(partialUpdatedTypeOfActivity));
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfActivity.setId(longCount.incrementAndGet());

        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfActivityDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfActivity.setId(longCount.incrementAndGet());

        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfActivity.setId(longCount.incrementAndGet());

        // Create the TypeOfActivity
        TypeOfActivityDTO typeOfActivityDTO = typeOfActivityMapper.toDto(typeOfActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfActivityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfActivity() throws Exception {
        // Initialize the database
        insertedTypeOfActivity = typeOfActivityRepository.saveAndFlush(typeOfActivity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfActivity
        restTypeOfActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfActivity.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfActivityRepository.count();
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

    protected TypeOfActivity getPersistedTypeOfActivity(TypeOfActivity typeOfActivity) {
        return typeOfActivityRepository.findById(typeOfActivity.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfActivityToMatchAllProperties(TypeOfActivity expectedTypeOfActivity) {
        assertTypeOfActivityAllPropertiesEquals(expectedTypeOfActivity, getPersistedTypeOfActivity(expectedTypeOfActivity));
    }

    protected void assertPersistedTypeOfActivityToMatchUpdatableProperties(TypeOfActivity expectedTypeOfActivity) {
        assertTypeOfActivityAllUpdatablePropertiesEquals(expectedTypeOfActivity, getPersistedTypeOfActivity(expectedTypeOfActivity));
    }
}
