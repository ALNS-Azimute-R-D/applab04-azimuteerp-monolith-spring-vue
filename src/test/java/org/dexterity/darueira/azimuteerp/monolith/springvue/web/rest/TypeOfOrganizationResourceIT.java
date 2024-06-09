package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganizationAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfOrganizationRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfOrganizationDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfOrganizationMapper;
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
 * Integration tests for the {@link TypeOfOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfOrganizationResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_HANDLER_CLAZZ = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_HANDLER_CLAZZ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfOrganizationRepository typeOfOrganizationRepository;

    @Autowired
    private TypeOfOrganizationMapper typeOfOrganizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfOrganizationMockMvc;

    private TypeOfOrganization typeOfOrganization;

    private TypeOfOrganization insertedTypeOfOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfOrganization createEntity(EntityManager em) {
        TypeOfOrganization typeOfOrganization = new TypeOfOrganization()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .businessHandlerClazz(DEFAULT_BUSINESS_HANDLER_CLAZZ);
        return typeOfOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfOrganization createUpdatedEntity(EntityManager em) {
        TypeOfOrganization typeOfOrganization = new TypeOfOrganization()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);
        return typeOfOrganization;
    }

    @BeforeEach
    public void initTest() {
        typeOfOrganization = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfOrganization != null) {
            typeOfOrganizationRepository.delete(insertedTypeOfOrganization);
            insertedTypeOfOrganization = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfOrganization() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);
        var returnedTypeOfOrganizationDTO = om.readValue(
            restTypeOfOrganizationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(typeOfOrganizationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfOrganizationDTO.class
        );

        // Validate the TypeOfOrganization in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfOrganization = typeOfOrganizationMapper.toEntity(returnedTypeOfOrganizationDTO);
        assertTypeOfOrganizationUpdatableFieldsEquals(
            returnedTypeOfOrganization,
            getPersistedTypeOfOrganization(returnedTypeOfOrganization)
        );

        insertedTypeOfOrganization = returnedTypeOfOrganization;
    }

    @Test
    @Transactional
    void createTypeOfOrganizationWithExistingId() throws Exception {
        // Create the TypeOfOrganization with an existing ID
        typeOfOrganization.setId(1L);
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfOrganization.setAcronym(null);

        // Create the TypeOfOrganization, which fails.
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        restTypeOfOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfOrganization.setName(null);

        // Create the TypeOfOrganization, which fails.
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        restTypeOfOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfOrganization.setDescription(null);

        // Create the TypeOfOrganization, which fails.
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        restTypeOfOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfOrganizations() throws Exception {
        // Initialize the database
        insertedTypeOfOrganization = typeOfOrganizationRepository.saveAndFlush(typeOfOrganization);

        // Get all the typeOfOrganizationList
        restTypeOfOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].businessHandlerClazz").value(hasItem(DEFAULT_BUSINESS_HANDLER_CLAZZ)));
    }

    @Test
    @Transactional
    void getTypeOfOrganization() throws Exception {
        // Initialize the database
        insertedTypeOfOrganization = typeOfOrganizationRepository.saveAndFlush(typeOfOrganization);

        // Get the typeOfOrganization
        restTypeOfOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfOrganization.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.businessHandlerClazz").value(DEFAULT_BUSINESS_HANDLER_CLAZZ));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfOrganization() throws Exception {
        // Get the typeOfOrganization
        restTypeOfOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfOrganization() throws Exception {
        // Initialize the database
        insertedTypeOfOrganization = typeOfOrganizationRepository.saveAndFlush(typeOfOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfOrganization
        TypeOfOrganization updatedTypeOfOrganization = typeOfOrganizationRepository.findById(typeOfOrganization.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfOrganization are not directly saved in db
        em.detach(updatedTypeOfOrganization);
        updatedTypeOfOrganization
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(updatedTypeOfOrganization);

        restTypeOfOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfOrganizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfOrganizationToMatchAllProperties(updatedTypeOfOrganization);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfOrganization.setId(longCount.incrementAndGet());

        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfOrganizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfOrganization.setId(longCount.incrementAndGet());

        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfOrganization.setId(longCount.incrementAndGet());

        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfOrganizationWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfOrganization = typeOfOrganizationRepository.saveAndFlush(typeOfOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfOrganization using partial update
        TypeOfOrganization partialUpdatedTypeOfOrganization = new TypeOfOrganization();
        partialUpdatedTypeOfOrganization.setId(typeOfOrganization.getId());

        partialUpdatedTypeOfOrganization.acronym(UPDATED_ACRONYM).businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);

        restTypeOfOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfOrganization))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfOrganizationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfOrganization, typeOfOrganization),
            getPersistedTypeOfOrganization(typeOfOrganization)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfOrganizationWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfOrganization = typeOfOrganizationRepository.saveAndFlush(typeOfOrganization);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfOrganization using partial update
        TypeOfOrganization partialUpdatedTypeOfOrganization = new TypeOfOrganization();
        partialUpdatedTypeOfOrganization.setId(typeOfOrganization.getId());

        partialUpdatedTypeOfOrganization
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .businessHandlerClazz(UPDATED_BUSINESS_HANDLER_CLAZZ);

        restTypeOfOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfOrganization))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfOrganization in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfOrganizationUpdatableFieldsEquals(
            partialUpdatedTypeOfOrganization,
            getPersistedTypeOfOrganization(partialUpdatedTypeOfOrganization)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfOrganization.setId(longCount.incrementAndGet());

        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfOrganizationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfOrganization.setId(longCount.incrementAndGet());

        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfOrganization() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfOrganization.setId(longCount.incrementAndGet());

        // Create the TypeOfOrganization
        TypeOfOrganizationDTO typeOfOrganizationDTO = typeOfOrganizationMapper.toDto(typeOfOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfOrganization in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfOrganization() throws Exception {
        // Initialize the database
        insertedTypeOfOrganization = typeOfOrganizationRepository.saveAndFlush(typeOfOrganization);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfOrganization
        restTypeOfOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfOrganization.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfOrganizationRepository.count();
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

    protected TypeOfOrganization getPersistedTypeOfOrganization(TypeOfOrganization typeOfOrganization) {
        return typeOfOrganizationRepository.findById(typeOfOrganization.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfOrganizationToMatchAllProperties(TypeOfOrganization expectedTypeOfOrganization) {
        assertTypeOfOrganizationAllPropertiesEquals(expectedTypeOfOrganization, getPersistedTypeOfOrganization(expectedTypeOfOrganization));
    }

    protected void assertPersistedTypeOfOrganizationToMatchUpdatableProperties(TypeOfOrganization expectedTypeOfOrganization) {
        assertTypeOfOrganizationAllUpdatablePropertiesEquals(
            expectedTypeOfOrganization,
            getPersistedTypeOfOrganization(expectedTypeOfOrganization)
        );
    }
}
