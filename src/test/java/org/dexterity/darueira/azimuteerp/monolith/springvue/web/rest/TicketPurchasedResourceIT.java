package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchasedAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.sameNumber;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TicketPurchasedRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TicketPurchasedService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TicketPurchasedDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TicketPurchasedMapper;
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
 * Integration tests for the {@link TicketPurchasedResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TicketPurchasedResourceIT {

    private static final String DEFAULT_BUYING_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BUYING_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DUE_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AMOUNT_OF_TICKETS = 1;
    private static final Integer UPDATED_AMOUNT_OF_TICKETS = 2;

    private static final BigDecimal DEFAULT_TAX_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TICKET_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TICKET_VALUE = new BigDecimal(2);

    private static final String DEFAULT_ACQUIRED_SEATS_NUMBERS = "AAAAAAAAAA";
    private static final String UPDATED_ACQUIRED_SEATS_NUMBERS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ticket-purchaseds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TicketPurchasedRepository ticketPurchasedRepository;

    @Mock
    private TicketPurchasedRepository ticketPurchasedRepositoryMock;

    @Autowired
    private TicketPurchasedMapper ticketPurchasedMapper;

    @Mock
    private TicketPurchasedService ticketPurchasedServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketPurchasedMockMvc;

    private TicketPurchased ticketPurchased;

    private TicketPurchased insertedTicketPurchased;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketPurchased createEntity(EntityManager em) {
        TicketPurchased ticketPurchased = new TicketPurchased()
            .buyingCode(DEFAULT_BUYING_CODE)
            .duePaymentDate(DEFAULT_DUE_PAYMENT_DATE)
            .amountOfTickets(DEFAULT_AMOUNT_OF_TICKETS)
            .taxValue(DEFAULT_TAX_VALUE)
            .ticketValue(DEFAULT_TICKET_VALUE)
            .acquiredSeatsNumbers(DEFAULT_ACQUIRED_SEATS_NUMBERS)
            .description(DEFAULT_DESCRIPTION);
        return ticketPurchased;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketPurchased createUpdatedEntity(EntityManager em) {
        TicketPurchased ticketPurchased = new TicketPurchased()
            .buyingCode(UPDATED_BUYING_CODE)
            .duePaymentDate(UPDATED_DUE_PAYMENT_DATE)
            .amountOfTickets(UPDATED_AMOUNT_OF_TICKETS)
            .taxValue(UPDATED_TAX_VALUE)
            .ticketValue(UPDATED_TICKET_VALUE)
            .acquiredSeatsNumbers(UPDATED_ACQUIRED_SEATS_NUMBERS)
            .description(UPDATED_DESCRIPTION);
        return ticketPurchased;
    }

    @BeforeEach
    public void initTest() {
        ticketPurchased = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTicketPurchased != null) {
            ticketPurchasedRepository.delete(insertedTicketPurchased);
            insertedTicketPurchased = null;
        }
    }

    @Test
    @Transactional
    void createTicketPurchased() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);
        var returnedTicketPurchasedDTO = om.readValue(
            restTicketPurchasedMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(ticketPurchasedDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TicketPurchasedDTO.class
        );

        // Validate the TicketPurchased in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTicketPurchased = ticketPurchasedMapper.toEntity(returnedTicketPurchasedDTO);
        assertTicketPurchasedUpdatableFieldsEquals(returnedTicketPurchased, getPersistedTicketPurchased(returnedTicketPurchased));

        insertedTicketPurchased = returnedTicketPurchased;
    }

    @Test
    @Transactional
    void createTicketPurchasedWithExistingId() throws Exception {
        // Create the TicketPurchased with an existing ID
        ticketPurchased.setId(1L);
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketPurchasedMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTicketPurchaseds() throws Exception {
        // Initialize the database
        insertedTicketPurchased = ticketPurchasedRepository.saveAndFlush(ticketPurchased);

        // Get all the ticketPurchasedList
        restTicketPurchasedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketPurchased.getId().intValue())))
            .andExpect(jsonPath("$.[*].buyingCode").value(hasItem(DEFAULT_BUYING_CODE)))
            .andExpect(jsonPath("$.[*].duePaymentDate").value(hasItem(DEFAULT_DUE_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountOfTickets").value(hasItem(DEFAULT_AMOUNT_OF_TICKETS)))
            .andExpect(jsonPath("$.[*].taxValue").value(hasItem(sameNumber(DEFAULT_TAX_VALUE))))
            .andExpect(jsonPath("$.[*].ticketValue").value(hasItem(sameNumber(DEFAULT_TICKET_VALUE))))
            .andExpect(jsonPath("$.[*].acquiredSeatsNumbers").value(hasItem(DEFAULT_ACQUIRED_SEATS_NUMBERS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketPurchasedsWithEagerRelationshipsIsEnabled() throws Exception {
        when(ticketPurchasedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketPurchasedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ticketPurchasedServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTicketPurchasedsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ticketPurchasedServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTicketPurchasedMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ticketPurchasedRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTicketPurchased() throws Exception {
        // Initialize the database
        insertedTicketPurchased = ticketPurchasedRepository.saveAndFlush(ticketPurchased);

        // Get the ticketPurchased
        restTicketPurchasedMockMvc
            .perform(get(ENTITY_API_URL_ID, ticketPurchased.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketPurchased.getId().intValue()))
            .andExpect(jsonPath("$.buyingCode").value(DEFAULT_BUYING_CODE))
            .andExpect(jsonPath("$.duePaymentDate").value(DEFAULT_DUE_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.amountOfTickets").value(DEFAULT_AMOUNT_OF_TICKETS))
            .andExpect(jsonPath("$.taxValue").value(sameNumber(DEFAULT_TAX_VALUE)))
            .andExpect(jsonPath("$.ticketValue").value(sameNumber(DEFAULT_TICKET_VALUE)))
            .andExpect(jsonPath("$.acquiredSeatsNumbers").value(DEFAULT_ACQUIRED_SEATS_NUMBERS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTicketPurchased() throws Exception {
        // Get the ticketPurchased
        restTicketPurchasedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTicketPurchased() throws Exception {
        // Initialize the database
        insertedTicketPurchased = ticketPurchasedRepository.saveAndFlush(ticketPurchased);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketPurchased
        TicketPurchased updatedTicketPurchased = ticketPurchasedRepository.findById(ticketPurchased.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTicketPurchased are not directly saved in db
        em.detach(updatedTicketPurchased);
        updatedTicketPurchased
            .buyingCode(UPDATED_BUYING_CODE)
            .duePaymentDate(UPDATED_DUE_PAYMENT_DATE)
            .amountOfTickets(UPDATED_AMOUNT_OF_TICKETS)
            .taxValue(UPDATED_TAX_VALUE)
            .ticketValue(UPDATED_TICKET_VALUE)
            .acquiredSeatsNumbers(UPDATED_ACQUIRED_SEATS_NUMBERS)
            .description(UPDATED_DESCRIPTION);
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(updatedTicketPurchased);

        restTicketPurchasedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketPurchasedDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isOk());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTicketPurchasedToMatchAllProperties(updatedTicketPurchased);
    }

    @Test
    @Transactional
    void putNonExistingTicketPurchased() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketPurchased.setId(longCount.incrementAndGet());

        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketPurchasedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketPurchasedDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicketPurchased() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketPurchased.setId(longCount.incrementAndGet());

        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketPurchasedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicketPurchased() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketPurchased.setId(longCount.incrementAndGet());

        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketPurchasedMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketPurchasedWithPatch() throws Exception {
        // Initialize the database
        insertedTicketPurchased = ticketPurchasedRepository.saveAndFlush(ticketPurchased);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketPurchased using partial update
        TicketPurchased partialUpdatedTicketPurchased = new TicketPurchased();
        partialUpdatedTicketPurchased.setId(ticketPurchased.getId());

        partialUpdatedTicketPurchased
            .duePaymentDate(UPDATED_DUE_PAYMENT_DATE)
            .ticketValue(UPDATED_TICKET_VALUE)
            .acquiredSeatsNumbers(UPDATED_ACQUIRED_SEATS_NUMBERS)
            .description(UPDATED_DESCRIPTION);

        restTicketPurchasedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketPurchased.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicketPurchased))
            )
            .andExpect(status().isOk());

        // Validate the TicketPurchased in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketPurchasedUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTicketPurchased, ticketPurchased),
            getPersistedTicketPurchased(ticketPurchased)
        );
    }

    @Test
    @Transactional
    void fullUpdateTicketPurchasedWithPatch() throws Exception {
        // Initialize the database
        insertedTicketPurchased = ticketPurchasedRepository.saveAndFlush(ticketPurchased);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ticketPurchased using partial update
        TicketPurchased partialUpdatedTicketPurchased = new TicketPurchased();
        partialUpdatedTicketPurchased.setId(ticketPurchased.getId());

        partialUpdatedTicketPurchased
            .buyingCode(UPDATED_BUYING_CODE)
            .duePaymentDate(UPDATED_DUE_PAYMENT_DATE)
            .amountOfTickets(UPDATED_AMOUNT_OF_TICKETS)
            .taxValue(UPDATED_TAX_VALUE)
            .ticketValue(UPDATED_TICKET_VALUE)
            .acquiredSeatsNumbers(UPDATED_ACQUIRED_SEATS_NUMBERS)
            .description(UPDATED_DESCRIPTION);

        restTicketPurchasedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketPurchased.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTicketPurchased))
            )
            .andExpect(status().isOk());

        // Validate the TicketPurchased in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTicketPurchasedUpdatableFieldsEquals(
            partialUpdatedTicketPurchased,
            getPersistedTicketPurchased(partialUpdatedTicketPurchased)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTicketPurchased() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketPurchased.setId(longCount.incrementAndGet());

        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketPurchasedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketPurchasedDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicketPurchased() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketPurchased.setId(longCount.incrementAndGet());

        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketPurchasedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicketPurchased() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ticketPurchased.setId(longCount.incrementAndGet());

        // Create the TicketPurchased
        TicketPurchasedDTO ticketPurchasedDTO = ticketPurchasedMapper.toDto(ticketPurchased);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketPurchasedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ticketPurchasedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketPurchased in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicketPurchased() throws Exception {
        // Initialize the database
        insertedTicketPurchased = ticketPurchasedRepository.saveAndFlush(ticketPurchased);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ticketPurchased
        restTicketPurchasedMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticketPurchased.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ticketPurchasedRepository.count();
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

    protected TicketPurchased getPersistedTicketPurchased(TicketPurchased ticketPurchased) {
        return ticketPurchasedRepository.findById(ticketPurchased.getId()).orElseThrow();
    }

    protected void assertPersistedTicketPurchasedToMatchAllProperties(TicketPurchased expectedTicketPurchased) {
        assertTicketPurchasedAllPropertiesEquals(expectedTicketPurchased, getPersistedTicketPurchased(expectedTicketPurchased));
    }

    protected void assertPersistedTicketPurchasedToMatchUpdatableProperties(TicketPurchased expectedTicketPurchased) {
        assertTicketPurchasedAllUpdatablePropertiesEquals(expectedTicketPurchased, getPersistedTicketPurchased(expectedTicketPurchased));
    }
}
