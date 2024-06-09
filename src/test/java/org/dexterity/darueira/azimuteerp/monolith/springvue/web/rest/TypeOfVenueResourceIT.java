package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenueAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfVenueRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfVenueDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfVenueMapper;
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
 * Integration tests for the {@link TypeOfVenueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfVenueResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLER_CLAZZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HANDLER_CLAZZ_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-venues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfVenueRepository typeOfVenueRepository;

    @Autowired
    private TypeOfVenueMapper typeOfVenueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfVenueMockMvc;

    private TypeOfVenue typeOfVenue;

    private TypeOfVenue insertedTypeOfVenue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfVenue createEntity(EntityManager em) {
        TypeOfVenue typeOfVenue = new TypeOfVenue()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .handlerClazzName(DEFAULT_HANDLER_CLAZZ_NAME);
        return typeOfVenue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfVenue createUpdatedEntity(EntityManager em) {
        TypeOfVenue typeOfVenue = new TypeOfVenue()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);
        return typeOfVenue;
    }

    @BeforeEach
    public void initTest() {
        typeOfVenue = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfVenue != null) {
            typeOfVenueRepository.delete(insertedTypeOfVenue);
            insertedTypeOfVenue = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfVenue() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);
        var returnedTypeOfVenueDTO = om.readValue(
            restTypeOfVenueMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfVenueDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfVenueDTO.class
        );

        // Validate the TypeOfVenue in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfVenue = typeOfVenueMapper.toEntity(returnedTypeOfVenueDTO);
        assertTypeOfVenueUpdatableFieldsEquals(returnedTypeOfVenue, getPersistedTypeOfVenue(returnedTypeOfVenue));

        insertedTypeOfVenue = returnedTypeOfVenue;
    }

    @Test
    @Transactional
    void createTypeOfVenueWithExistingId() throws Exception {
        // Create the TypeOfVenue with an existing ID
        typeOfVenue.setId(1L);
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfVenueMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfVenue.setName(null);

        // Create the TypeOfVenue, which fails.
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        restTypeOfVenueMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfVenues() throws Exception {
        // Initialize the database
        insertedTypeOfVenue = typeOfVenueRepository.saveAndFlush(typeOfVenue);

        // Get all the typeOfVenueList
        restTypeOfVenueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfVenue.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].handlerClazzName").value(hasItem(DEFAULT_HANDLER_CLAZZ_NAME)));
    }

    @Test
    @Transactional
    void getTypeOfVenue() throws Exception {
        // Initialize the database
        insertedTypeOfVenue = typeOfVenueRepository.saveAndFlush(typeOfVenue);

        // Get the typeOfVenue
        restTypeOfVenueMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfVenue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfVenue.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.handlerClazzName").value(DEFAULT_HANDLER_CLAZZ_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfVenue() throws Exception {
        // Get the typeOfVenue
        restTypeOfVenueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfVenue() throws Exception {
        // Initialize the database
        insertedTypeOfVenue = typeOfVenueRepository.saveAndFlush(typeOfVenue);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfVenue
        TypeOfVenue updatedTypeOfVenue = typeOfVenueRepository.findById(typeOfVenue.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfVenue are not directly saved in db
        em.detach(updatedTypeOfVenue);
        updatedTypeOfVenue
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(updatedTypeOfVenue);

        restTypeOfVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfVenueDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfVenueToMatchAllProperties(updatedTypeOfVenue);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfVenue.setId(longCount.incrementAndGet());

        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfVenueDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfVenue.setId(longCount.incrementAndGet());

        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfVenue.setId(longCount.incrementAndGet());

        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfVenueMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfVenueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfVenueWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfVenue = typeOfVenueRepository.saveAndFlush(typeOfVenue);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfVenue using partial update
        TypeOfVenue partialUpdatedTypeOfVenue = new TypeOfVenue();
        partialUpdatedTypeOfVenue.setId(typeOfVenue.getId());

        partialUpdatedTypeOfVenue.acronym(UPDATED_ACRONYM);

        restTypeOfVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfVenue.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfVenue))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfVenue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfVenueUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfVenue, typeOfVenue),
            getPersistedTypeOfVenue(typeOfVenue)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfVenueWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfVenue = typeOfVenueRepository.saveAndFlush(typeOfVenue);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfVenue using partial update
        TypeOfVenue partialUpdatedTypeOfVenue = new TypeOfVenue();
        partialUpdatedTypeOfVenue.setId(typeOfVenue.getId());

        partialUpdatedTypeOfVenue
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);

        restTypeOfVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfVenue.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfVenue))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfVenue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfVenueUpdatableFieldsEquals(partialUpdatedTypeOfVenue, getPersistedTypeOfVenue(partialUpdatedTypeOfVenue));
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfVenue.setId(longCount.incrementAndGet());

        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfVenueDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfVenue.setId(longCount.incrementAndGet());

        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfVenue.setId(longCount.incrementAndGet());

        // Create the TypeOfVenue
        TypeOfVenueDTO typeOfVenueDTO = typeOfVenueMapper.toDto(typeOfVenue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfVenueMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(typeOfVenueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfVenue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfVenue() throws Exception {
        // Initialize the database
        insertedTypeOfVenue = typeOfVenueRepository.saveAndFlush(typeOfVenue);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfVenue
        restTypeOfVenueMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfVenue.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfVenueRepository.count();
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

    protected TypeOfVenue getPersistedTypeOfVenue(TypeOfVenue typeOfVenue) {
        return typeOfVenueRepository.findById(typeOfVenue.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfVenueToMatchAllProperties(TypeOfVenue expectedTypeOfVenue) {
        assertTypeOfVenueAllPropertiesEquals(expectedTypeOfVenue, getPersistedTypeOfVenue(expectedTypeOfVenue));
    }

    protected void assertPersistedTypeOfVenueToMatchUpdatableProperties(TypeOfVenue expectedTypeOfVenue) {
        assertTypeOfVenueAllUpdatablePropertiesEquals(expectedTypeOfVenue, getPersistedTypeOfVenue(expectedTypeOfVenue));
    }
}
