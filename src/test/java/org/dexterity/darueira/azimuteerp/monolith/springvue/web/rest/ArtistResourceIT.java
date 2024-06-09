package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtistAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtistRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtistService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtistMapper;
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
 * Integration tests for the {@link ArtistResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArtistResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIOGRAPHY_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_BIOGRAPHY_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtistRepository artistRepository;

    @Mock
    private ArtistRepository artistRepositoryMock;

    @Autowired
    private ArtistMapper artistMapper;

    @Mock
    private ArtistService artistServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistMockMvc;

    private Artist artist;

    private Artist insertedArtist;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artist createEntity(EntityManager em) {
        Artist artist = new Artist()
            .acronym(DEFAULT_ACRONYM)
            .publicName(DEFAULT_PUBLIC_NAME)
            .realName(DEFAULT_REAL_NAME)
            .biographyDetailsJSON(DEFAULT_BIOGRAPHY_DETAILS_JSON);
        // Add required entity
        TypeOfArtmedia typeOfArtmedia;
        if (TestUtil.findAll(em, TypeOfArtmedia.class).isEmpty()) {
            typeOfArtmedia = TypeOfArtmediaResourceIT.createEntity(em);
            em.persist(typeOfArtmedia);
            em.flush();
        } else {
            typeOfArtmedia = TestUtil.findAll(em, TypeOfArtmedia.class).get(0);
        }
        artist.setTypeOfArtmedia(typeOfArtmedia);
        // Add required entity
        TypeOfArtist typeOfArtist;
        if (TestUtil.findAll(em, TypeOfArtist.class).isEmpty()) {
            typeOfArtist = TypeOfArtistResourceIT.createEntity(em);
            em.persist(typeOfArtist);
            em.flush();
        } else {
            typeOfArtist = TestUtil.findAll(em, TypeOfArtist.class).get(0);
        }
        artist.setTypeOfArtist(typeOfArtist);
        return artist;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artist createUpdatedEntity(EntityManager em) {
        Artist artist = new Artist()
            .acronym(UPDATED_ACRONYM)
            .publicName(UPDATED_PUBLIC_NAME)
            .realName(UPDATED_REAL_NAME)
            .biographyDetailsJSON(UPDATED_BIOGRAPHY_DETAILS_JSON);
        // Add required entity
        TypeOfArtmedia typeOfArtmedia;
        if (TestUtil.findAll(em, TypeOfArtmedia.class).isEmpty()) {
            typeOfArtmedia = TypeOfArtmediaResourceIT.createUpdatedEntity(em);
            em.persist(typeOfArtmedia);
            em.flush();
        } else {
            typeOfArtmedia = TestUtil.findAll(em, TypeOfArtmedia.class).get(0);
        }
        artist.setTypeOfArtmedia(typeOfArtmedia);
        // Add required entity
        TypeOfArtist typeOfArtist;
        if (TestUtil.findAll(em, TypeOfArtist.class).isEmpty()) {
            typeOfArtist = TypeOfArtistResourceIT.createUpdatedEntity(em);
            em.persist(typeOfArtist);
            em.flush();
        } else {
            typeOfArtist = TestUtil.findAll(em, TypeOfArtist.class).get(0);
        }
        artist.setTypeOfArtist(typeOfArtist);
        return artist;
    }

    @BeforeEach
    public void initTest() {
        artist = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedArtist != null) {
            artistRepository.delete(insertedArtist);
            insertedArtist = null;
        }
    }

    @Test
    @Transactional
    void createArtist() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);
        var returnedArtistDTO = om.readValue(
            restArtistMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artistDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArtistDTO.class
        );

        // Validate the Artist in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArtist = artistMapper.toEntity(returnedArtistDTO);
        assertArtistUpdatableFieldsEquals(returnedArtist, getPersistedArtist(returnedArtist));

        insertedArtist = returnedArtist;
    }

    @Test
    @Transactional
    void createArtistWithExistingId() throws Exception {
        // Create the Artist with an existing ID
        artist.setId(1L);
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        artist.setAcronym(null);

        // Create the Artist, which fails.
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        restArtistMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artistDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPublicNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        artist.setPublicName(null);

        // Create the Artist, which fails.
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        restArtistMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artistDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArtists() throws Exception {
        // Initialize the database
        insertedArtist = artistRepository.saveAndFlush(artist);

        // Get all the artistList
        restArtistMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artist.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].publicName").value(hasItem(DEFAULT_PUBLIC_NAME)))
            .andExpect(jsonPath("$.[*].realName").value(hasItem(DEFAULT_REAL_NAME)))
            .andExpect(jsonPath("$.[*].biographyDetailsJSON").value(hasItem(DEFAULT_BIOGRAPHY_DETAILS_JSON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtistsWithEagerRelationshipsIsEnabled() throws Exception {
        when(artistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtistMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(artistServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtistsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(artistServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtistMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(artistRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArtist() throws Exception {
        // Initialize the database
        insertedArtist = artistRepository.saveAndFlush(artist);

        // Get the artist
        restArtistMockMvc
            .perform(get(ENTITY_API_URL_ID, artist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artist.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.publicName").value(DEFAULT_PUBLIC_NAME))
            .andExpect(jsonPath("$.realName").value(DEFAULT_REAL_NAME))
            .andExpect(jsonPath("$.biographyDetailsJSON").value(DEFAULT_BIOGRAPHY_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingArtist() throws Exception {
        // Get the artist
        restArtistMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtist() throws Exception {
        // Initialize the database
        insertedArtist = artistRepository.saveAndFlush(artist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artist
        Artist updatedArtist = artistRepository.findById(artist.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtist are not directly saved in db
        em.detach(updatedArtist);
        updatedArtist
            .acronym(UPDATED_ACRONYM)
            .publicName(UPDATED_PUBLIC_NAME)
            .realName(UPDATED_REAL_NAME)
            .biographyDetailsJSON(UPDATED_BIOGRAPHY_DETAILS_JSON);
        ArtistDTO artistDTO = artistMapper.toDto(updatedArtist);

        restArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artistDTO))
            )
            .andExpect(status().isOk());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArtistToMatchAllProperties(updatedArtist);
    }

    @Test
    @Transactional
    void putNonExistingArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artist.setId(longCount.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artist.setId(longCount.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artist.setId(longCount.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artistDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistWithPatch() throws Exception {
        // Initialize the database
        insertedArtist = artistRepository.saveAndFlush(artist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artist using partial update
        Artist partialUpdatedArtist = new Artist();
        partialUpdatedArtist.setId(artist.getId());

        partialUpdatedArtist.publicName(UPDATED_PUBLIC_NAME).realName(UPDATED_REAL_NAME);

        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtist.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtist))
            )
            .andExpect(status().isOk());

        // Validate the Artist in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtistUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedArtist, artist), getPersistedArtist(artist));
    }

    @Test
    @Transactional
    void fullUpdateArtistWithPatch() throws Exception {
        // Initialize the database
        insertedArtist = artistRepository.saveAndFlush(artist);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artist using partial update
        Artist partialUpdatedArtist = new Artist();
        partialUpdatedArtist.setId(artist.getId());

        partialUpdatedArtist
            .acronym(UPDATED_ACRONYM)
            .publicName(UPDATED_PUBLIC_NAME)
            .realName(UPDATED_REAL_NAME)
            .biographyDetailsJSON(UPDATED_BIOGRAPHY_DETAILS_JSON);

        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtist.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtist))
            )
            .andExpect(status().isOk());

        // Validate the Artist in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtistUpdatableFieldsEquals(partialUpdatedArtist, getPersistedArtist(partialUpdatedArtist));
    }

    @Test
    @Transactional
    void patchNonExistingArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artist.setId(longCount.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artist.setId(longCount.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artistDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtist() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artist.setId(longCount.incrementAndGet());

        // Create the Artist
        ArtistDTO artistDTO = artistMapper.toDto(artist);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(artistDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artist in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtist() throws Exception {
        // Initialize the database
        insertedArtist = artistRepository.saveAndFlush(artist);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artist
        restArtistMockMvc
            .perform(delete(ENTITY_API_URL_ID, artist.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artistRepository.count();
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

    protected Artist getPersistedArtist(Artist artist) {
        return artistRepository.findById(artist.getId()).orElseThrow();
    }

    protected void assertPersistedArtistToMatchAllProperties(Artist expectedArtist) {
        assertArtistAllPropertiesEquals(expectedArtist, getPersistedArtist(expectedArtist));
    }

    protected void assertPersistedArtistToMatchUpdatableProperties(Artist expectedArtist) {
        assertArtistAllUpdatablePropertiesEquals(expectedArtist, getPersistedArtist(expectedArtist));
    }
}
