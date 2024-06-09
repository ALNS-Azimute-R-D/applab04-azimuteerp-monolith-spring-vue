package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.VenueAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.VenueRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.VenueService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.VenueDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.VenueMapper;
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
 * Integration tests for the {@link VenueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VenueResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GEO_POINT_LOCATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GEO_POINT_LOCATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GEO_POINT_LOCATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GEO_POINT_LOCATION_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/venues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VenueRepository venueRepository;

    @Mock
    private VenueRepository venueRepositoryMock;

    @Autowired
    private VenueMapper venueMapper;

    @Mock
    private VenueService venueServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVenueMockMvc;

    private Venue venue;

    private Venue insertedVenue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venue createEntity(EntityManager em) {
        Venue venue = new Venue()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .geoPointLocation(DEFAULT_GEO_POINT_LOCATION)
            .geoPointLocationContentType(DEFAULT_GEO_POINT_LOCATION_CONTENT_TYPE);
        // Add required entity
        TypeOfVenue typeOfVenue;
        if (TestUtil.findAll(em, TypeOfVenue.class).isEmpty()) {
            typeOfVenue = TypeOfVenueResourceIT.createEntity(em);
            em.persist(typeOfVenue);
            em.flush();
        } else {
            typeOfVenue = TestUtil.findAll(em, TypeOfVenue.class).get(0);
        }
        venue.setTypeOfVenue(typeOfVenue);
        return venue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venue createUpdatedEntity(EntityManager em) {
        Venue venue = new Venue()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .geoPointLocation(UPDATED_GEO_POINT_LOCATION)
            .geoPointLocationContentType(UPDATED_GEO_POINT_LOCATION_CONTENT_TYPE);
        // Add required entity
        TypeOfVenue typeOfVenue;
        if (TestUtil.findAll(em, TypeOfVenue.class).isEmpty()) {
            typeOfVenue = TypeOfVenueResourceIT.createUpdatedEntity(em);
            em.persist(typeOfVenue);
            em.flush();
        } else {
            typeOfVenue = TestUtil.findAll(em, TypeOfVenue.class).get(0);
        }
        venue.setTypeOfVenue(typeOfVenue);
        return venue;
    }

    @BeforeEach
    public void initTest() {
        venue = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedVenue != null) {
            venueRepository.delete(insertedVenue);
            insertedVenue = null;
        }
    }

    @Test
    @Transactional
    void createVenue() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);
        var returnedVenueDTO = om.readValue(
            restVenueMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venueDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VenueDTO.class
        );

        // Validate the Venue in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVenue = venueMapper.toEntity(returnedVenueDTO);
        assertVenueUpdatableFieldsEquals(returnedVenue, getPersistedVenue(returnedVenue));

        insertedVenue = returnedVenue;
    }

    @Test
    @Transactional
    void createVenueWithExistingId() throws Exception {
        // Create the Venue with an existing ID
        venue.setId(1L);
        VenueDTO venueDTO = venueMapper.toDto(venue);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenueMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        venue.setName(null);

        // Create the Venue, which fails.
        VenueDTO venueDTO = venueMapper.toDto(venue);

        restVenueMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venueDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVenues() throws Exception {
        // Initialize the database
        insertedVenue = venueRepository.saveAndFlush(venue);

        // Get all the venueList
        restVenueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venue.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].geoPointLocationContentType").value(hasItem(DEFAULT_GEO_POINT_LOCATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].geoPointLocation").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_GEO_POINT_LOCATION))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVenuesWithEagerRelationshipsIsEnabled() throws Exception {
        when(venueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVenueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(venueServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVenuesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(venueServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVenueMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(venueRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVenue() throws Exception {
        // Initialize the database
        insertedVenue = venueRepository.saveAndFlush(venue);

        // Get the venue
        restVenueMockMvc
            .perform(get(ENTITY_API_URL_ID, venue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(venue.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.geoPointLocationContentType").value(DEFAULT_GEO_POINT_LOCATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.geoPointLocation").value(Base64.getEncoder().encodeToString(DEFAULT_GEO_POINT_LOCATION)));
    }

    @Test
    @Transactional
    void getNonExistingVenue() throws Exception {
        // Get the venue
        restVenueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVenue() throws Exception {
        // Initialize the database
        insertedVenue = venueRepository.saveAndFlush(venue);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the venue
        Venue updatedVenue = venueRepository.findById(venue.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVenue are not directly saved in db
        em.detach(updatedVenue);
        updatedVenue
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .geoPointLocation(UPDATED_GEO_POINT_LOCATION)
            .geoPointLocationContentType(UPDATED_GEO_POINT_LOCATION_CONTENT_TYPE);
        VenueDTO venueDTO = venueMapper.toDto(updatedVenue);

        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venueDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(venueDTO))
            )
            .andExpect(status().isOk());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVenueToMatchAllProperties(updatedVenue);
    }

    @Test
    @Transactional
    void putNonExistingVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        venue.setId(longCount.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venueDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        venue.setId(longCount.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        venue.setId(longCount.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVenueWithPatch() throws Exception {
        // Initialize the database
        insertedVenue = venueRepository.saveAndFlush(venue);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the venue using partial update
        Venue partialUpdatedVenue = new Venue();
        partialUpdatedVenue.setId(venue.getId());

        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenue.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVenue))
            )
            .andExpect(status().isOk());

        // Validate the Venue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVenueUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVenue, venue), getPersistedVenue(venue));
    }

    @Test
    @Transactional
    void fullUpdateVenueWithPatch() throws Exception {
        // Initialize the database
        insertedVenue = venueRepository.saveAndFlush(venue);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the venue using partial update
        Venue partialUpdatedVenue = new Venue();
        partialUpdatedVenue.setId(venue.getId());

        partialUpdatedVenue
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .geoPointLocation(UPDATED_GEO_POINT_LOCATION)
            .geoPointLocationContentType(UPDATED_GEO_POINT_LOCATION_CONTENT_TYPE);

        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenue.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVenue))
            )
            .andExpect(status().isOk());

        // Validate the Venue in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVenueUpdatableFieldsEquals(partialUpdatedVenue, getPersistedVenue(partialUpdatedVenue));
    }

    @Test
    @Transactional
    void patchNonExistingVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        venue.setId(longCount.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, venueDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        venue.setId(longCount.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(venueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVenue() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        venue.setId(longCount.incrementAndGet());

        // Create the Venue
        VenueDTO venueDTO = venueMapper.toDto(venue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenueMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(venueDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venue in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVenue() throws Exception {
        // Initialize the database
        insertedVenue = venueRepository.saveAndFlush(venue);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the venue
        restVenueMockMvc
            .perform(delete(ENTITY_API_URL_ID, venue.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return venueRepository.count();
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

    protected Venue getPersistedVenue(Venue venue) {
        return venueRepository.findById(venue.getId()).orElseThrow();
    }

    protected void assertPersistedVenueToMatchAllProperties(Venue expectedVenue) {
        assertVenueAllPropertiesEquals(expectedVenue, getPersistedVenue(expectedVenue));
    }

    protected void assertPersistedVenueToMatchUpdatableProperties(Venue expectedVenue) {
        assertVenueAllUpdatablePropertiesEquals(expectedVenue, getPersistedVenue(expectedVenue));
    }
}
