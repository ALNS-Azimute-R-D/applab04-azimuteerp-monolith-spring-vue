package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmediaAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfArtmediaRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtmediaDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfArtmediaMapper;
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
 * Integration tests for the {@link TypeOfArtmediaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfArtmediaResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-artmedias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfArtmediaRepository typeOfArtmediaRepository;

    @Autowired
    private TypeOfArtmediaMapper typeOfArtmediaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfArtmediaMockMvc;

    private TypeOfArtmedia typeOfArtmedia;

    private TypeOfArtmedia insertedTypeOfArtmedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfArtmedia createEntity(EntityManager em) {
        TypeOfArtmedia typeOfArtmedia = new TypeOfArtmedia().acronym(DEFAULT_ACRONYM).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return typeOfArtmedia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfArtmedia createUpdatedEntity(EntityManager em) {
        TypeOfArtmedia typeOfArtmedia = new TypeOfArtmedia().acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return typeOfArtmedia;
    }

    @BeforeEach
    public void initTest() {
        typeOfArtmedia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfArtmedia != null) {
            typeOfArtmediaRepository.delete(insertedTypeOfArtmedia);
            insertedTypeOfArtmedia = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);
        var returnedTypeOfArtmediaDTO = om.readValue(
            restTypeOfArtmediaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(typeOfArtmediaDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfArtmediaDTO.class
        );

        // Validate the TypeOfArtmedia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfArtmedia = typeOfArtmediaMapper.toEntity(returnedTypeOfArtmediaDTO);
        assertTypeOfArtmediaUpdatableFieldsEquals(returnedTypeOfArtmedia, getPersistedTypeOfArtmedia(returnedTypeOfArtmedia));

        insertedTypeOfArtmedia = returnedTypeOfArtmedia;
    }

    @Test
    @Transactional
    void createTypeOfArtmediaWithExistingId() throws Exception {
        // Create the TypeOfArtmedia with an existing ID
        typeOfArtmedia.setId(1L);
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfArtmediaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfArtmedia.setAcronym(null);

        // Create the TypeOfArtmedia, which fails.
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        restTypeOfArtmediaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfArtmedia.setName(null);

        // Create the TypeOfArtmedia, which fails.
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        restTypeOfArtmediaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfArtmedia.setDescription(null);

        // Create the TypeOfArtmedia, which fails.
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        restTypeOfArtmediaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfArtmedias() throws Exception {
        // Initialize the database
        insertedTypeOfArtmedia = typeOfArtmediaRepository.saveAndFlush(typeOfArtmedia);

        // Get all the typeOfArtmediaList
        restTypeOfArtmediaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfArtmedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTypeOfArtmedia() throws Exception {
        // Initialize the database
        insertedTypeOfArtmedia = typeOfArtmediaRepository.saveAndFlush(typeOfArtmedia);

        // Get the typeOfArtmedia
        restTypeOfArtmediaMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfArtmedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfArtmedia.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfArtmedia() throws Exception {
        // Get the typeOfArtmedia
        restTypeOfArtmediaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfArtmedia() throws Exception {
        // Initialize the database
        insertedTypeOfArtmedia = typeOfArtmediaRepository.saveAndFlush(typeOfArtmedia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfArtmedia
        TypeOfArtmedia updatedTypeOfArtmedia = typeOfArtmediaRepository.findById(typeOfArtmedia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfArtmedia are not directly saved in db
        em.detach(updatedTypeOfArtmedia);
        updatedTypeOfArtmedia.acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(updatedTypeOfArtmedia);

        restTypeOfArtmediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfArtmediaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfArtmediaToMatchAllProperties(updatedTypeOfArtmedia);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtmedia.setId(longCount.incrementAndGet());

        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfArtmediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfArtmediaDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtmedia.setId(longCount.incrementAndGet());

        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtmediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtmedia.setId(longCount.incrementAndGet());

        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtmediaMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfArtmediaWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfArtmedia = typeOfArtmediaRepository.saveAndFlush(typeOfArtmedia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfArtmedia using partial update
        TypeOfArtmedia partialUpdatedTypeOfArtmedia = new TypeOfArtmedia();
        partialUpdatedTypeOfArtmedia.setId(typeOfArtmedia.getId());

        partialUpdatedTypeOfArtmedia.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restTypeOfArtmediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfArtmedia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfArtmedia))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfArtmedia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfArtmediaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfArtmedia, typeOfArtmedia),
            getPersistedTypeOfArtmedia(typeOfArtmedia)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfArtmediaWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfArtmedia = typeOfArtmediaRepository.saveAndFlush(typeOfArtmedia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfArtmedia using partial update
        TypeOfArtmedia partialUpdatedTypeOfArtmedia = new TypeOfArtmedia();
        partialUpdatedTypeOfArtmedia.setId(typeOfArtmedia.getId());

        partialUpdatedTypeOfArtmedia.acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restTypeOfArtmediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfArtmedia.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfArtmedia))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfArtmedia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfArtmediaUpdatableFieldsEquals(partialUpdatedTypeOfArtmedia, getPersistedTypeOfArtmedia(partialUpdatedTypeOfArtmedia));
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtmedia.setId(longCount.incrementAndGet());

        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfArtmediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfArtmediaDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtmedia.setId(longCount.incrementAndGet());

        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtmediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfArtmedia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtmedia.setId(longCount.incrementAndGet());

        // Create the TypeOfArtmedia
        TypeOfArtmediaDTO typeOfArtmediaDTO = typeOfArtmediaMapper.toDto(typeOfArtmedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtmediaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfArtmediaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfArtmedia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfArtmedia() throws Exception {
        // Initialize the database
        insertedTypeOfArtmedia = typeOfArtmediaRepository.saveAndFlush(typeOfArtmedia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfArtmedia
        restTypeOfArtmediaMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfArtmedia.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfArtmediaRepository.count();
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

    protected TypeOfArtmedia getPersistedTypeOfArtmedia(TypeOfArtmedia typeOfArtmedia) {
        return typeOfArtmediaRepository.findById(typeOfArtmedia.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfArtmediaToMatchAllProperties(TypeOfArtmedia expectedTypeOfArtmedia) {
        assertTypeOfArtmediaAllPropertiesEquals(expectedTypeOfArtmedia, getPersistedTypeOfArtmedia(expectedTypeOfArtmedia));
    }

    protected void assertPersistedTypeOfArtmediaToMatchUpdatableProperties(TypeOfArtmedia expectedTypeOfArtmedia) {
        assertTypeOfArtmediaAllUpdatablePropertiesEquals(expectedTypeOfArtmedia, getPersistedTypeOfArtmedia(expectedTypeOfArtmedia));
    }
}
