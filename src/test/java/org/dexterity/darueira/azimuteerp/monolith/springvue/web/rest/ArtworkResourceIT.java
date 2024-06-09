package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtworkRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtworkService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtworkMapper;
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
 * Integration tests for the {@link ArtworkResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArtworkResourceIT {

    private static final String DEFAULT_ARTWORK_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTWORK_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCTION_YEAR = 1;
    private static final Integer UPDATED_PRODUCTION_YEAR = 2;

    private static final Integer DEFAULT_SEASON_NUMBER = 1;
    private static final Integer UPDATED_SEASON_NUMBER = 2;

    private static final Integer DEFAULT_EPISODE_OR_SEQUENCE_NUMBER = 1;
    private static final Integer UPDATED_EPISODE_OR_SEQUENCE_NUMBER = 2;

    private static final String DEFAULT_REGISTER_ID_IN_IMDB = "AAAAAAAAAA";
    private static final String UPDATED_REGISTER_ID_IN_IMDB = "BBBBBBBBBB";

    private static final String DEFAULT_ASSETS_COLLECTION_UUID = "AAAAAAAAAA";
    private static final String UPDATED_ASSETS_COLLECTION_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artworks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Mock
    private ArtworkRepository artworkRepositoryMock;

    @Autowired
    private ArtworkMapper artworkMapper;

    @Mock
    private ArtworkService artworkServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkMockMvc;

    private Artwork artwork;

    private Artwork insertedArtwork;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artwork createEntity(EntityManager em) {
        Artwork artwork = new Artwork()
            .artworkTitle(DEFAULT_ARTWORK_TITLE)
            .productionYear(DEFAULT_PRODUCTION_YEAR)
            .seasonNumber(DEFAULT_SEASON_NUMBER)
            .episodeOrSequenceNumber(DEFAULT_EPISODE_OR_SEQUENCE_NUMBER)
            .registerIdInIMDB(DEFAULT_REGISTER_ID_IN_IMDB)
            .assetsCollectionUUID(DEFAULT_ASSETS_COLLECTION_UUID)
            .contentDetailsJSON(DEFAULT_CONTENT_DETAILS_JSON);
        // Add required entity
        TypeOfArtmedia typeOfArtmedia;
        if (TestUtil.findAll(em, TypeOfArtmedia.class).isEmpty()) {
            typeOfArtmedia = TypeOfArtmediaResourceIT.createEntity(em);
            em.persist(typeOfArtmedia);
            em.flush();
        } else {
            typeOfArtmedia = TestUtil.findAll(em, TypeOfArtmedia.class).get(0);
        }
        artwork.setTypeOfArtmedia(typeOfArtmedia);
        return artwork;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artwork createUpdatedEntity(EntityManager em) {
        Artwork artwork = new Artwork()
            .artworkTitle(UPDATED_ARTWORK_TITLE)
            .productionYear(UPDATED_PRODUCTION_YEAR)
            .seasonNumber(UPDATED_SEASON_NUMBER)
            .episodeOrSequenceNumber(UPDATED_EPISODE_OR_SEQUENCE_NUMBER)
            .registerIdInIMDB(UPDATED_REGISTER_ID_IN_IMDB)
            .assetsCollectionUUID(UPDATED_ASSETS_COLLECTION_UUID)
            .contentDetailsJSON(UPDATED_CONTENT_DETAILS_JSON);
        // Add required entity
        TypeOfArtmedia typeOfArtmedia;
        if (TestUtil.findAll(em, TypeOfArtmedia.class).isEmpty()) {
            typeOfArtmedia = TypeOfArtmediaResourceIT.createUpdatedEntity(em);
            em.persist(typeOfArtmedia);
            em.flush();
        } else {
            typeOfArtmedia = TestUtil.findAll(em, TypeOfArtmedia.class).get(0);
        }
        artwork.setTypeOfArtmedia(typeOfArtmedia);
        return artwork;
    }

    @BeforeEach
    public void initTest() {
        artwork = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedArtwork != null) {
            artworkRepository.delete(insertedArtwork);
            insertedArtwork = null;
        }
    }

    @Test
    @Transactional
    void createArtwork() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);
        var returnedArtworkDTO = om.readValue(
            restArtworkMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArtworkDTO.class
        );

        // Validate the Artwork in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArtwork = artworkMapper.toEntity(returnedArtworkDTO);
        assertArtworkUpdatableFieldsEquals(returnedArtwork, getPersistedArtwork(returnedArtwork));

        insertedArtwork = returnedArtwork;
    }

    @Test
    @Transactional
    void createArtworkWithExistingId() throws Exception {
        // Create the Artwork with an existing ID
        artwork.setId(1L);
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkArtworkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        artwork.setArtworkTitle(null);

        // Create the Artwork, which fails.
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        restArtworkMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArtworks() throws Exception {
        // Initialize the database
        insertedArtwork = artworkRepository.saveAndFlush(artwork);

        // Get all the artworkList
        restArtworkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artwork.getId().intValue())))
            .andExpect(jsonPath("$.[*].artworkTitle").value(hasItem(DEFAULT_ARTWORK_TITLE)))
            .andExpect(jsonPath("$.[*].productionYear").value(hasItem(DEFAULT_PRODUCTION_YEAR)))
            .andExpect(jsonPath("$.[*].seasonNumber").value(hasItem(DEFAULT_SEASON_NUMBER)))
            .andExpect(jsonPath("$.[*].episodeOrSequenceNumber").value(hasItem(DEFAULT_EPISODE_OR_SEQUENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].registerIdInIMDB").value(hasItem(DEFAULT_REGISTER_ID_IN_IMDB)))
            .andExpect(jsonPath("$.[*].assetsCollectionUUID").value(hasItem(DEFAULT_ASSETS_COLLECTION_UUID)))
            .andExpect(jsonPath("$.[*].contentDetailsJSON").value(hasItem(DEFAULT_CONTENT_DETAILS_JSON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtworksWithEagerRelationshipsIsEnabled() throws Exception {
        when(artworkServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtworkMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(artworkServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtworksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(artworkServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtworkMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(artworkRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArtwork() throws Exception {
        // Initialize the database
        insertedArtwork = artworkRepository.saveAndFlush(artwork);

        // Get the artwork
        restArtworkMockMvc
            .perform(get(ENTITY_API_URL_ID, artwork.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artwork.getId().intValue()))
            .andExpect(jsonPath("$.artworkTitle").value(DEFAULT_ARTWORK_TITLE))
            .andExpect(jsonPath("$.productionYear").value(DEFAULT_PRODUCTION_YEAR))
            .andExpect(jsonPath("$.seasonNumber").value(DEFAULT_SEASON_NUMBER))
            .andExpect(jsonPath("$.episodeOrSequenceNumber").value(DEFAULT_EPISODE_OR_SEQUENCE_NUMBER))
            .andExpect(jsonPath("$.registerIdInIMDB").value(DEFAULT_REGISTER_ID_IN_IMDB))
            .andExpect(jsonPath("$.assetsCollectionUUID").value(DEFAULT_ASSETS_COLLECTION_UUID))
            .andExpect(jsonPath("$.contentDetailsJSON").value(DEFAULT_CONTENT_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingArtwork() throws Exception {
        // Get the artwork
        restArtworkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtwork() throws Exception {
        // Initialize the database
        insertedArtwork = artworkRepository.saveAndFlush(artwork);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artwork
        Artwork updatedArtwork = artworkRepository.findById(artwork.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtwork are not directly saved in db
        em.detach(updatedArtwork);
        updatedArtwork
            .artworkTitle(UPDATED_ARTWORK_TITLE)
            .productionYear(UPDATED_PRODUCTION_YEAR)
            .seasonNumber(UPDATED_SEASON_NUMBER)
            .episodeOrSequenceNumber(UPDATED_EPISODE_OR_SEQUENCE_NUMBER)
            .registerIdInIMDB(UPDATED_REGISTER_ID_IN_IMDB)
            .assetsCollectionUUID(UPDATED_ASSETS_COLLECTION_UUID)
            .contentDetailsJSON(UPDATED_CONTENT_DETAILS_JSON);
        ArtworkDTO artworkDTO = artworkMapper.toDto(updatedArtwork);

        restArtworkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artworkDTO))
            )
            .andExpect(status().isOk());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArtworkToMatchAllProperties(updatedArtwork);
    }

    @Test
    @Transactional
    void putNonExistingArtwork() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artwork.setId(longCount.incrementAndGet());

        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artworkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtwork() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artwork.setId(longCount.incrementAndGet());

        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artworkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtwork() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artwork.setId(longCount.incrementAndGet());

        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkWithPatch() throws Exception {
        // Initialize the database
        insertedArtwork = artworkRepository.saveAndFlush(artwork);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artwork using partial update
        Artwork partialUpdatedArtwork = new Artwork();
        partialUpdatedArtwork.setId(artwork.getId());

        partialUpdatedArtwork
            .episodeOrSequenceNumber(UPDATED_EPISODE_OR_SEQUENCE_NUMBER)
            .registerIdInIMDB(UPDATED_REGISTER_ID_IN_IMDB)
            .assetsCollectionUUID(UPDATED_ASSETS_COLLECTION_UUID)
            .contentDetailsJSON(UPDATED_CONTENT_DETAILS_JSON);

        restArtworkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtwork.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtwork))
            )
            .andExpect(status().isOk());

        // Validate the Artwork in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtworkUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedArtwork, artwork), getPersistedArtwork(artwork));
    }

    @Test
    @Transactional
    void fullUpdateArtworkWithPatch() throws Exception {
        // Initialize the database
        insertedArtwork = artworkRepository.saveAndFlush(artwork);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artwork using partial update
        Artwork partialUpdatedArtwork = new Artwork();
        partialUpdatedArtwork.setId(artwork.getId());

        partialUpdatedArtwork
            .artworkTitle(UPDATED_ARTWORK_TITLE)
            .productionYear(UPDATED_PRODUCTION_YEAR)
            .seasonNumber(UPDATED_SEASON_NUMBER)
            .episodeOrSequenceNumber(UPDATED_EPISODE_OR_SEQUENCE_NUMBER)
            .registerIdInIMDB(UPDATED_REGISTER_ID_IN_IMDB)
            .assetsCollectionUUID(UPDATED_ASSETS_COLLECTION_UUID)
            .contentDetailsJSON(UPDATED_CONTENT_DETAILS_JSON);

        restArtworkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtwork.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtwork))
            )
            .andExpect(status().isOk());

        // Validate the Artwork in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtworkUpdatableFieldsEquals(partialUpdatedArtwork, getPersistedArtwork(partialUpdatedArtwork));
    }

    @Test
    @Transactional
    void patchNonExistingArtwork() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artwork.setId(longCount.incrementAndGet());

        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artworkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtwork() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artwork.setId(longCount.incrementAndGet());

        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artworkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtwork() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artwork.setId(longCount.incrementAndGet());

        // Create the Artwork
        ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(artworkDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artwork in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtwork() throws Exception {
        // Initialize the database
        insertedArtwork = artworkRepository.saveAndFlush(artwork);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artwork
        restArtworkMockMvc
            .perform(delete(ENTITY_API_URL_ID, artwork.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artworkRepository.count();
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

    protected Artwork getPersistedArtwork(Artwork artwork) {
        return artworkRepository.findById(artwork.getId()).orElseThrow();
    }

    protected void assertPersistedArtworkToMatchAllProperties(Artwork expectedArtwork) {
        assertArtworkAllPropertiesEquals(expectedArtwork, getPersistedArtwork(expectedArtwork));
    }

    protected void assertPersistedArtworkToMatchUpdatableProperties(Artwork expectedArtwork) {
        assertArtworkAllUpdatablePropertiesEquals(expectedArtwork, getPersistedArtwork(expectedArtwork));
    }
}
