package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgramAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.EventProgramRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.EventProgramService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.EventProgramDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.EventProgramMapper;
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
 * Integration tests for the {@link EventProgramResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EventProgramResourceIT {

    private static final Double DEFAULT_PERCENTAGE_EXECUTION = 1D;
    private static final Double UPDATED_PERCENTAGE_EXECUTION = 2D;

    private static final String ENTITY_API_URL = "/api/event-programs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EventProgramRepository eventProgramRepository;

    @Mock
    private EventProgramRepository eventProgramRepositoryMock;

    @Autowired
    private EventProgramMapper eventProgramMapper;

    @Mock
    private EventProgramService eventProgramServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventProgramMockMvc;

    private EventProgram eventProgram;

    private EventProgram insertedEventProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventProgram createEntity(EntityManager em) {
        EventProgram eventProgram = new EventProgram().percentageExecution(DEFAULT_PERCENTAGE_EXECUTION);
        return eventProgram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventProgram createUpdatedEntity(EntityManager em) {
        EventProgram eventProgram = new EventProgram().percentageExecution(UPDATED_PERCENTAGE_EXECUTION);
        return eventProgram;
    }

    @BeforeEach
    public void initTest() {
        eventProgram = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEventProgram != null) {
            eventProgramRepository.delete(insertedEventProgram);
            insertedEventProgram = null;
        }
    }

    @Test
    @Transactional
    void createEventProgram() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);
        var returnedEventProgramDTO = om.readValue(
            restEventProgramMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventProgramDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EventProgramDTO.class
        );

        // Validate the EventProgram in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEventProgram = eventProgramMapper.toEntity(returnedEventProgramDTO);
        assertEventProgramUpdatableFieldsEquals(returnedEventProgram, getPersistedEventProgram(returnedEventProgram));

        insertedEventProgram = returnedEventProgram;
    }

    @Test
    @Transactional
    void createEventProgramWithExistingId() throws Exception {
        // Create the EventProgram with an existing ID
        eventProgram.setId(1L);
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventProgramMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventPrograms() throws Exception {
        // Initialize the database
        insertedEventProgram = eventProgramRepository.saveAndFlush(eventProgram);

        // Get all the eventProgramList
        restEventProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventProgram.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentageExecution").value(hasItem(DEFAULT_PERCENTAGE_EXECUTION.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventProgramsWithEagerRelationshipsIsEnabled() throws Exception {
        when(eventProgramServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventProgramMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(eventProgramServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventProgramsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(eventProgramServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventProgramMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(eventProgramRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEventProgram() throws Exception {
        // Initialize the database
        insertedEventProgram = eventProgramRepository.saveAndFlush(eventProgram);

        // Get the eventProgram
        restEventProgramMockMvc
            .perform(get(ENTITY_API_URL_ID, eventProgram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventProgram.getId().intValue()))
            .andExpect(jsonPath("$.percentageExecution").value(DEFAULT_PERCENTAGE_EXECUTION.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventProgram() throws Exception {
        // Get the eventProgram
        restEventProgramMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventProgram() throws Exception {
        // Initialize the database
        insertedEventProgram = eventProgramRepository.saveAndFlush(eventProgram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventProgram
        EventProgram updatedEventProgram = eventProgramRepository.findById(eventProgram.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEventProgram are not directly saved in db
        em.detach(updatedEventProgram);
        updatedEventProgram.percentageExecution(UPDATED_PERCENTAGE_EXECUTION);
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(updatedEventProgram);

        restEventProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventProgramDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEventProgramToMatchAllProperties(updatedEventProgram);
    }

    @Test
    @Transactional
    void putNonExistingEventProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventProgram.setId(longCount.incrementAndGet());

        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventProgramDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventProgram.setId(longCount.incrementAndGet());

        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventProgram.setId(longCount.incrementAndGet());

        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventProgramMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventProgramWithPatch() throws Exception {
        // Initialize the database
        insertedEventProgram = eventProgramRepository.saveAndFlush(eventProgram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventProgram using partial update
        EventProgram partialUpdatedEventProgram = new EventProgram();
        partialUpdatedEventProgram.setId(eventProgram.getId());

        restEventProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventProgram.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventProgram))
            )
            .andExpect(status().isOk());

        // Validate the EventProgram in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventProgramUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEventProgram, eventProgram),
            getPersistedEventProgram(eventProgram)
        );
    }

    @Test
    @Transactional
    void fullUpdateEventProgramWithPatch() throws Exception {
        // Initialize the database
        insertedEventProgram = eventProgramRepository.saveAndFlush(eventProgram);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventProgram using partial update
        EventProgram partialUpdatedEventProgram = new EventProgram();
        partialUpdatedEventProgram.setId(eventProgram.getId());

        partialUpdatedEventProgram.percentageExecution(UPDATED_PERCENTAGE_EXECUTION);

        restEventProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventProgram.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventProgram))
            )
            .andExpect(status().isOk());

        // Validate the EventProgram in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventProgramUpdatableFieldsEquals(partialUpdatedEventProgram, getPersistedEventProgram(partialUpdatedEventProgram));
    }

    @Test
    @Transactional
    void patchNonExistingEventProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventProgram.setId(longCount.incrementAndGet());

        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventProgramDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventProgram.setId(longCount.incrementAndGet());

        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventProgram.setId(longCount.incrementAndGet());

        // Create the EventProgram
        EventProgramDTO eventProgramDTO = eventProgramMapper.toDto(eventProgram);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventProgramMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventProgramDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventProgram in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventProgram() throws Exception {
        // Initialize the database
        insertedEventProgram = eventProgramRepository.saveAndFlush(eventProgram);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eventProgram
        restEventProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventProgram.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eventProgramRepository.count();
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

    protected EventProgram getPersistedEventProgram(EventProgram eventProgram) {
        return eventProgramRepository.findById(eventProgram.getId()).orElseThrow();
    }

    protected void assertPersistedEventProgramToMatchAllProperties(EventProgram expectedEventProgram) {
        assertEventProgramAllPropertiesEquals(expectedEventProgram, getPersistedEventProgram(expectedEventProgram));
    }

    protected void assertPersistedEventProgramToMatchUpdatableProperties(EventProgram expectedEventProgram) {
        assertEventProgramAllUpdatablePropertiesEquals(expectedEventProgram, getPersistedEventProgram(expectedEventProgram));
    }
}
