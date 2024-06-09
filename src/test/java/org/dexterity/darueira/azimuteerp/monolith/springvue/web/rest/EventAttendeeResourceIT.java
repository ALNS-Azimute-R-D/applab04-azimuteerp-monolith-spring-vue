package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendeeAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventAttendee;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.EventAttendeeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.EventAttendeeService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.EventAttendeeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.EventAttendeeMapper;
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
 * Integration tests for the {@link EventAttendeeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EventAttendeeResourceIT {

    private static final String DEFAULT_ATTENDED_AS_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDED_AS_ROLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WAS_PRESENT_IN_EVENT = false;
    private static final Boolean UPDATED_WAS_PRESENT_IN_EVENT = true;

    private static final Boolean DEFAULT_SHOULD_BUY_TICKET = false;
    private static final Boolean UPDATED_SHOULD_BUY_TICKET = true;

    private static final String DEFAULT_TICKET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TICKET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SEAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/event-attendees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EventAttendeeRepository eventAttendeeRepository;

    @Mock
    private EventAttendeeRepository eventAttendeeRepositoryMock;

    @Autowired
    private EventAttendeeMapper eventAttendeeMapper;

    @Mock
    private EventAttendeeService eventAttendeeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventAttendeeMockMvc;

    private EventAttendee eventAttendee;

    private EventAttendee insertedEventAttendee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventAttendee createEntity(EntityManager em) {
        EventAttendee eventAttendee = new EventAttendee()
            .attendedAsRole(DEFAULT_ATTENDED_AS_ROLE)
            .wasPresentInEvent(DEFAULT_WAS_PRESENT_IN_EVENT)
            .shouldBuyTicket(DEFAULT_SHOULD_BUY_TICKET)
            .ticketNumber(DEFAULT_TICKET_NUMBER)
            .seatNumber(DEFAULT_SEAT_NUMBER);
        return eventAttendee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventAttendee createUpdatedEntity(EntityManager em) {
        EventAttendee eventAttendee = new EventAttendee()
            .attendedAsRole(UPDATED_ATTENDED_AS_ROLE)
            .wasPresentInEvent(UPDATED_WAS_PRESENT_IN_EVENT)
            .shouldBuyTicket(UPDATED_SHOULD_BUY_TICKET)
            .ticketNumber(UPDATED_TICKET_NUMBER)
            .seatNumber(UPDATED_SEAT_NUMBER);
        return eventAttendee;
    }

    @BeforeEach
    public void initTest() {
        eventAttendee = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEventAttendee != null) {
            eventAttendeeRepository.delete(insertedEventAttendee);
            insertedEventAttendee = null;
        }
    }

    @Test
    @Transactional
    void createEventAttendee() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);
        var returnedEventAttendeeDTO = om.readValue(
            restEventAttendeeMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(eventAttendeeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EventAttendeeDTO.class
        );

        // Validate the EventAttendee in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEventAttendee = eventAttendeeMapper.toEntity(returnedEventAttendeeDTO);
        assertEventAttendeeUpdatableFieldsEquals(returnedEventAttendee, getPersistedEventAttendee(returnedEventAttendee));

        insertedEventAttendee = returnedEventAttendee;
    }

    @Test
    @Transactional
    void createEventAttendeeWithExistingId() throws Exception {
        // Create the EventAttendee with an existing ID
        eventAttendee.setId(1L);
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAttendedAsRoleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventAttendee.setAttendedAsRole(null);

        // Create the EventAttendee, which fails.
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        restEventAttendeeMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventAttendees() throws Exception {
        // Initialize the database
        insertedEventAttendee = eventAttendeeRepository.saveAndFlush(eventAttendee);

        // Get all the eventAttendeeList
        restEventAttendeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventAttendee.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendedAsRole").value(hasItem(DEFAULT_ATTENDED_AS_ROLE)))
            .andExpect(jsonPath("$.[*].wasPresentInEvent").value(hasItem(DEFAULT_WAS_PRESENT_IN_EVENT.booleanValue())))
            .andExpect(jsonPath("$.[*].shouldBuyTicket").value(hasItem(DEFAULT_SHOULD_BUY_TICKET.booleanValue())))
            .andExpect(jsonPath("$.[*].ticketNumber").value(hasItem(DEFAULT_TICKET_NUMBER)))
            .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(DEFAULT_SEAT_NUMBER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventAttendeesWithEagerRelationshipsIsEnabled() throws Exception {
        when(eventAttendeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventAttendeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(eventAttendeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEventAttendeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(eventAttendeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEventAttendeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(eventAttendeeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEventAttendee() throws Exception {
        // Initialize the database
        insertedEventAttendee = eventAttendeeRepository.saveAndFlush(eventAttendee);

        // Get the eventAttendee
        restEventAttendeeMockMvc
            .perform(get(ENTITY_API_URL_ID, eventAttendee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventAttendee.getId().intValue()))
            .andExpect(jsonPath("$.attendedAsRole").value(DEFAULT_ATTENDED_AS_ROLE))
            .andExpect(jsonPath("$.wasPresentInEvent").value(DEFAULT_WAS_PRESENT_IN_EVENT.booleanValue()))
            .andExpect(jsonPath("$.shouldBuyTicket").value(DEFAULT_SHOULD_BUY_TICKET.booleanValue()))
            .andExpect(jsonPath("$.ticketNumber").value(DEFAULT_TICKET_NUMBER))
            .andExpect(jsonPath("$.seatNumber").value(DEFAULT_SEAT_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingEventAttendee() throws Exception {
        // Get the eventAttendee
        restEventAttendeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventAttendee() throws Exception {
        // Initialize the database
        insertedEventAttendee = eventAttendeeRepository.saveAndFlush(eventAttendee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventAttendee
        EventAttendee updatedEventAttendee = eventAttendeeRepository.findById(eventAttendee.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEventAttendee are not directly saved in db
        em.detach(updatedEventAttendee);
        updatedEventAttendee
            .attendedAsRole(UPDATED_ATTENDED_AS_ROLE)
            .wasPresentInEvent(UPDATED_WAS_PRESENT_IN_EVENT)
            .shouldBuyTicket(UPDATED_SHOULD_BUY_TICKET)
            .ticketNumber(UPDATED_TICKET_NUMBER)
            .seatNumber(UPDATED_SEAT_NUMBER);
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(updatedEventAttendee);

        restEventAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventAttendeeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEventAttendeeToMatchAllProperties(updatedEventAttendee);
    }

    @Test
    @Transactional
    void putNonExistingEventAttendee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventAttendee.setId(longCount.incrementAndGet());

        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventAttendeeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventAttendee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventAttendee.setId(longCount.incrementAndGet());

        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventAttendee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventAttendee.setId(longCount.incrementAndGet());

        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventAttendeeMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventAttendeeWithPatch() throws Exception {
        // Initialize the database
        insertedEventAttendee = eventAttendeeRepository.saveAndFlush(eventAttendee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventAttendee using partial update
        EventAttendee partialUpdatedEventAttendee = new EventAttendee();
        partialUpdatedEventAttendee.setId(eventAttendee.getId());

        partialUpdatedEventAttendee
            .wasPresentInEvent(UPDATED_WAS_PRESENT_IN_EVENT)
            .shouldBuyTicket(UPDATED_SHOULD_BUY_TICKET)
            .seatNumber(UPDATED_SEAT_NUMBER);

        restEventAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventAttendee.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventAttendee))
            )
            .andExpect(status().isOk());

        // Validate the EventAttendee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventAttendeeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEventAttendee, eventAttendee),
            getPersistedEventAttendee(eventAttendee)
        );
    }

    @Test
    @Transactional
    void fullUpdateEventAttendeeWithPatch() throws Exception {
        // Initialize the database
        insertedEventAttendee = eventAttendeeRepository.saveAndFlush(eventAttendee);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventAttendee using partial update
        EventAttendee partialUpdatedEventAttendee = new EventAttendee();
        partialUpdatedEventAttendee.setId(eventAttendee.getId());

        partialUpdatedEventAttendee
            .attendedAsRole(UPDATED_ATTENDED_AS_ROLE)
            .wasPresentInEvent(UPDATED_WAS_PRESENT_IN_EVENT)
            .shouldBuyTicket(UPDATED_SHOULD_BUY_TICKET)
            .ticketNumber(UPDATED_TICKET_NUMBER)
            .seatNumber(UPDATED_SEAT_NUMBER);

        restEventAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventAttendee.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventAttendee))
            )
            .andExpect(status().isOk());

        // Validate the EventAttendee in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventAttendeeUpdatableFieldsEquals(partialUpdatedEventAttendee, getPersistedEventAttendee(partialUpdatedEventAttendee));
    }

    @Test
    @Transactional
    void patchNonExistingEventAttendee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventAttendee.setId(longCount.incrementAndGet());

        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventAttendeeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventAttendee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventAttendee.setId(longCount.incrementAndGet());

        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventAttendee() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventAttendee.setId(longCount.incrementAndGet());

        // Create the EventAttendee
        EventAttendeeDTO eventAttendeeDTO = eventAttendeeMapper.toDto(eventAttendee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventAttendeeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventAttendeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventAttendee in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventAttendee() throws Exception {
        // Initialize the database
        insertedEventAttendee = eventAttendeeRepository.saveAndFlush(eventAttendee);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eventAttendee
        restEventAttendeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventAttendee.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eventAttendeeRepository.count();
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

    protected EventAttendee getPersistedEventAttendee(EventAttendee eventAttendee) {
        return eventAttendeeRepository.findById(eventAttendee.getId()).orElseThrow();
    }

    protected void assertPersistedEventAttendeeToMatchAllProperties(EventAttendee expectedEventAttendee) {
        assertEventAttendeeAllPropertiesEquals(expectedEventAttendee, getPersistedEventAttendee(expectedEventAttendee));
    }

    protected void assertPersistedEventAttendeeToMatchUpdatableProperties(EventAttendee expectedEventAttendee) {
        assertEventAttendeeAllUpdatablePropertiesEquals(expectedEventAttendee, getPersistedEventAttendee(expectedEventAttendee));
    }
}
