package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEventAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfEventRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfEventDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfEventMapper;
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
 * Integration tests for the {@link TypeOfEventResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfEventResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLER_CLAZZ_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HANDLER_CLAZZ_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-events";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfEventRepository typeOfEventRepository;

    @Autowired
    private TypeOfEventMapper typeOfEventMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfEventMockMvc;

    private TypeOfEvent typeOfEvent;

    private TypeOfEvent insertedTypeOfEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfEvent createEntity(EntityManager em) {
        TypeOfEvent typeOfEvent = new TypeOfEvent()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .handlerClazzName(DEFAULT_HANDLER_CLAZZ_NAME);
        return typeOfEvent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfEvent createUpdatedEntity(EntityManager em) {
        TypeOfEvent typeOfEvent = new TypeOfEvent()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);
        return typeOfEvent;
    }

    @BeforeEach
    public void initTest() {
        typeOfEvent = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfEvent != null) {
            typeOfEventRepository.delete(insertedTypeOfEvent);
            insertedTypeOfEvent = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfEvent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);
        var returnedTypeOfEventDTO = om.readValue(
            restTypeOfEventMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfEventDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfEventDTO.class
        );

        // Validate the TypeOfEvent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfEvent = typeOfEventMapper.toEntity(returnedTypeOfEventDTO);
        assertTypeOfEventUpdatableFieldsEquals(returnedTypeOfEvent, getPersistedTypeOfEvent(returnedTypeOfEvent));

        insertedTypeOfEvent = returnedTypeOfEvent;
    }

    @Test
    @Transactional
    void createTypeOfEventWithExistingId() throws Exception {
        // Create the TypeOfEvent with an existing ID
        typeOfEvent.setId(1L);
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfEventMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfEvent.setName(null);

        // Create the TypeOfEvent, which fails.
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        restTypeOfEventMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfEvents() throws Exception {
        // Initialize the database
        insertedTypeOfEvent = typeOfEventRepository.saveAndFlush(typeOfEvent);

        // Get all the typeOfEventList
        restTypeOfEventMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].handlerClazzName").value(hasItem(DEFAULT_HANDLER_CLAZZ_NAME)));
    }

    @Test
    @Transactional
    void getTypeOfEvent() throws Exception {
        // Initialize the database
        insertedTypeOfEvent = typeOfEventRepository.saveAndFlush(typeOfEvent);

        // Get the typeOfEvent
        restTypeOfEventMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfEvent.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.handlerClazzName").value(DEFAULT_HANDLER_CLAZZ_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfEvent() throws Exception {
        // Get the typeOfEvent
        restTypeOfEventMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfEvent() throws Exception {
        // Initialize the database
        insertedTypeOfEvent = typeOfEventRepository.saveAndFlush(typeOfEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfEvent
        TypeOfEvent updatedTypeOfEvent = typeOfEventRepository.findById(typeOfEvent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfEvent are not directly saved in db
        em.detach(updatedTypeOfEvent);
        updatedTypeOfEvent
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(updatedTypeOfEvent);

        restTypeOfEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfEventDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfEventToMatchAllProperties(updatedTypeOfEvent);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfEvent.setId(longCount.incrementAndGet());

        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfEventDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfEvent.setId(longCount.incrementAndGet());

        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfEventMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfEvent.setId(longCount.incrementAndGet());

        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfEventMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfEventDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfEventWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfEvent = typeOfEventRepository.saveAndFlush(typeOfEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfEvent using partial update
        TypeOfEvent partialUpdatedTypeOfEvent = new TypeOfEvent();
        partialUpdatedTypeOfEvent.setId(typeOfEvent.getId());

        partialUpdatedTypeOfEvent.name(UPDATED_NAME).handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);

        restTypeOfEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfEvent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfEvent))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfEventUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfEvent, typeOfEvent),
            getPersistedTypeOfEvent(typeOfEvent)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfEventWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfEvent = typeOfEventRepository.saveAndFlush(typeOfEvent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfEvent using partial update
        TypeOfEvent partialUpdatedTypeOfEvent = new TypeOfEvent();
        partialUpdatedTypeOfEvent.setId(typeOfEvent.getId());

        partialUpdatedTypeOfEvent
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .handlerClazzName(UPDATED_HANDLER_CLAZZ_NAME);

        restTypeOfEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfEvent.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfEvent))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfEvent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfEventUpdatableFieldsEquals(partialUpdatedTypeOfEvent, getPersistedTypeOfEvent(partialUpdatedTypeOfEvent));
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfEvent.setId(longCount.incrementAndGet());

        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfEventDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfEvent.setId(longCount.incrementAndGet());

        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfEventMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfEvent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfEvent.setId(longCount.incrementAndGet());

        // Create the TypeOfEvent
        TypeOfEventDTO typeOfEventDTO = typeOfEventMapper.toDto(typeOfEvent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfEventMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(typeOfEventDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfEvent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfEvent() throws Exception {
        // Initialize the database
        insertedTypeOfEvent = typeOfEventRepository.saveAndFlush(typeOfEvent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfEvent
        restTypeOfEventMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfEvent.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfEventRepository.count();
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

    protected TypeOfEvent getPersistedTypeOfEvent(TypeOfEvent typeOfEvent) {
        return typeOfEventRepository.findById(typeOfEvent.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfEventToMatchAllProperties(TypeOfEvent expectedTypeOfEvent) {
        assertTypeOfEventAllPropertiesEquals(expectedTypeOfEvent, getPersistedTypeOfEvent(expectedTypeOfEvent));
    }

    protected void assertPersistedTypeOfEventToMatchUpdatableProperties(TypeOfEvent expectedTypeOfEvent) {
        assertTypeOfEventAllUpdatablePropertiesEquals(expectedTypeOfEvent, getPersistedTypeOfEvent(expectedTypeOfEvent));
    }
}
