package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtistAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfArtistRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfArtistMapper;
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
 * Integration tests for the {@link TypeOfArtistResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfArtistResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-artists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfArtistRepository typeOfArtistRepository;

    @Autowired
    private TypeOfArtistMapper typeOfArtistMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfArtistMockMvc;

    private TypeOfArtist typeOfArtist;

    private TypeOfArtist insertedTypeOfArtist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfArtist createEntity(EntityManager em) {
        TypeOfArtist typeOfArtist = new TypeOfArtist().acronym(DEFAULT_ACRONYM).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return typeOfArtist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfArtist createUpdatedEntity(EntityManager em) {
        TypeOfArtist typeOfArtist = new TypeOfArtist().acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return typeOfArtist;
    }

    @BeforeEach
    public void initTest() {
        typeOfArtist = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfArtist != null) {
            typeOfArtistRepository.delete(insertedTypeOfArtist);
            insertedTypeOfArtist = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfArtist() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);
        var returnedTypeOfArtistDTO = om.readValue(
            restTypeOfArtistMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtistDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfArtistDTO.class
        );

        // Validate the TypeOfArtist in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfArtist = typeOfArtistMapper.toEntity(returnedTypeOfArtistDTO);
        assertTypeOfArtistUpdatableFieldsEquals(returnedTypeOfArtist, getPersistedTypeOfArtist(returnedTypeOfArtist));

        insertedTypeOfArtist = returnedTypeOfArtist;
    }

    @Test
    @Transactional
    void createTypeOfArtistWithExistingId() throws Exception {
        // Create the TypeOfArtist with an existing ID
        typeOfArtist.setId(1L);
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfArtistMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfArtist.setAcronym(null);

        // Create the TypeOfArtist, which fails.
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        restTypeOfArtistMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfArtist.setName(null);

        // Create the TypeOfArtist, which fails.
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        restTypeOfArtistMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfArtist.setDescription(null);

        // Create the TypeOfArtist, which fails.
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        restTypeOfArtistMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfArtists() throws Exception {
        // Initialize the database
        insertedTypeOfArtist = typeOfArtistRepository.saveAndFlush(typeOfArtist);

        // Get all the typeOfArtistList
        restTypeOfArtistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfArtist.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTypeOfArtist() throws Exception {
        // Initialize the database
        insertedTypeOfArtist = typeOfArtistRepository.saveAndFlush(typeOfArtist);

        // Get the typeOfArtist
        restTypeOfArtistMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfArtist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfArtist.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfArtist() throws Exception {
        // Get the typeOfArtist
        restTypeOfArtistMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfArtist() throws Exception {
        // Initialize the database
        insertedTypeOfArtist = typeOfArtistRepository.saveAndFlush(typeOfArtist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfArtist
        TypeOfArtist updatedTypeOfArtist = typeOfArtistRepository.findById(typeOfArtist.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfArtist are not directly saved in db
        em.detach(updatedTypeOfArtist);
        updatedTypeOfArtist.acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(updatedTypeOfArtist);

        restTypeOfArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfArtistDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfArtistToMatchAllProperties(updatedTypeOfArtist);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtist.setId(longCount.incrementAndGet());

        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfArtistDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtist.setId(longCount.incrementAndGet());

        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtist.setId(longCount.incrementAndGet());

        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtistMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfArtistWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfArtist = typeOfArtistRepository.saveAndFlush(typeOfArtist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfArtist using partial update
        TypeOfArtist partialUpdatedTypeOfArtist = new TypeOfArtist();
        partialUpdatedTypeOfArtist.setId(typeOfArtist.getId());

        partialUpdatedTypeOfArtist.acronym(UPDATED_ACRONYM).name(UPDATED_NAME);

        restTypeOfArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfArtist.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfArtist))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfArtist in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfArtistUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfArtist, typeOfArtist),
            getPersistedTypeOfArtist(typeOfArtist)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfArtistWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfArtist = typeOfArtistRepository.saveAndFlush(typeOfArtist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfArtist using partial update
        TypeOfArtist partialUpdatedTypeOfArtist = new TypeOfArtist();
        partialUpdatedTypeOfArtist.setId(typeOfArtist.getId());

        partialUpdatedTypeOfArtist.acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restTypeOfArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfArtist.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfArtist))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfArtist in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfArtistUpdatableFieldsEquals(partialUpdatedTypeOfArtist, getPersistedTypeOfArtist(partialUpdatedTypeOfArtist));
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtist.setId(longCount.incrementAndGet());

        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfArtistDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtist.setId(longCount.incrementAndGet());

        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfArtist.setId(longCount.incrementAndGet());

        // Create the TypeOfArtist
        TypeOfArtistDTO typeOfArtistDTO = typeOfArtistMapper.toDto(typeOfArtist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfArtistMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfArtistDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfArtist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfArtist() throws Exception {
        // Initialize the database
        insertedTypeOfArtist = typeOfArtistRepository.saveAndFlush(typeOfArtist);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfArtist
        restTypeOfArtistMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfArtist.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfArtistRepository.count();
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

    protected TypeOfArtist getPersistedTypeOfArtist(TypeOfArtist typeOfArtist) {
        return typeOfArtistRepository.findById(typeOfArtist.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfArtistToMatchAllProperties(TypeOfArtist expectedTypeOfArtist) {
        assertTypeOfArtistAllPropertiesEquals(expectedTypeOfArtist, getPersistedTypeOfArtist(expectedTypeOfArtist));
    }

    protected void assertPersistedTypeOfArtistToMatchUpdatableProperties(TypeOfArtist expectedTypeOfArtist) {
        assertTypeOfArtistAllUpdatablePropertiesEquals(expectedTypeOfArtist, getPersistedTypeOfArtist(expectedTypeOfArtist));
    }
}
