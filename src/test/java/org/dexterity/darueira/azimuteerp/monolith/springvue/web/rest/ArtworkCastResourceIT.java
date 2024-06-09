package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCastAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtworkCastRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtworkCastService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkCastDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtworkCastMapper;
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
 * Integration tests for the {@link ArtworkCastResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ArtworkCastResourceIT {

    private static final Integer DEFAULT_ORDER_OF_APPEARANCE = 1;
    private static final Integer UPDATED_ORDER_OF_APPEARANCE = 2;

    private static final String DEFAULT_CHARACTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_ASSET_UUID = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_ASSET_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_CHARACTER_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTER_DETAILS_JSON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artwork-casts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtworkCastRepository artworkCastRepository;

    @Mock
    private ArtworkCastRepository artworkCastRepositoryMock;

    @Autowired
    private ArtworkCastMapper artworkCastMapper;

    @Mock
    private ArtworkCastService artworkCastServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkCastMockMvc;

    private ArtworkCast artworkCast;

    private ArtworkCast insertedArtworkCast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkCast createEntity(EntityManager em) {
        ArtworkCast artworkCast = new ArtworkCast()
            .orderOfAppearance(DEFAULT_ORDER_OF_APPEARANCE)
            .characterName(DEFAULT_CHARACTER_NAME)
            .mainAssetUUID(DEFAULT_MAIN_ASSET_UUID)
            .characterDetailsJSON(DEFAULT_CHARACTER_DETAILS_JSON);
        // Add required entity
        Artwork artwork;
        if (TestUtil.findAll(em, Artwork.class).isEmpty()) {
            artwork = ArtworkResourceIT.createEntity(em);
            em.persist(artwork);
            em.flush();
        } else {
            artwork = TestUtil.findAll(em, Artwork.class).get(0);
        }
        artworkCast.setArtwork(artwork);
        // Add required entity
        Artist artist;
        if (TestUtil.findAll(em, Artist.class).isEmpty()) {
            artist = ArtistResourceIT.createEntity(em);
            em.persist(artist);
            em.flush();
        } else {
            artist = TestUtil.findAll(em, Artist.class).get(0);
        }
        artworkCast.setArtist(artist);
        return artworkCast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkCast createUpdatedEntity(EntityManager em) {
        ArtworkCast artworkCast = new ArtworkCast()
            .orderOfAppearance(UPDATED_ORDER_OF_APPEARANCE)
            .characterName(UPDATED_CHARACTER_NAME)
            .mainAssetUUID(UPDATED_MAIN_ASSET_UUID)
            .characterDetailsJSON(UPDATED_CHARACTER_DETAILS_JSON);
        // Add required entity
        Artwork artwork;
        if (TestUtil.findAll(em, Artwork.class).isEmpty()) {
            artwork = ArtworkResourceIT.createUpdatedEntity(em);
            em.persist(artwork);
            em.flush();
        } else {
            artwork = TestUtil.findAll(em, Artwork.class).get(0);
        }
        artworkCast.setArtwork(artwork);
        // Add required entity
        Artist artist;
        if (TestUtil.findAll(em, Artist.class).isEmpty()) {
            artist = ArtistResourceIT.createUpdatedEntity(em);
            em.persist(artist);
            em.flush();
        } else {
            artist = TestUtil.findAll(em, Artist.class).get(0);
        }
        artworkCast.setArtist(artist);
        return artworkCast;
    }

    @BeforeEach
    public void initTest() {
        artworkCast = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedArtworkCast != null) {
            artworkCastRepository.delete(insertedArtworkCast);
            insertedArtworkCast = null;
        }
    }

    @Test
    @Transactional
    void createArtworkCast() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);
        var returnedArtworkCastDTO = om.readValue(
            restArtworkCastMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkCastDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArtworkCastDTO.class
        );

        // Validate the ArtworkCast in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArtworkCast = artworkCastMapper.toEntity(returnedArtworkCastDTO);
        assertArtworkCastUpdatableFieldsEquals(returnedArtworkCast, getPersistedArtworkCast(returnedArtworkCast));

        insertedArtworkCast = returnedArtworkCast;
    }

    @Test
    @Transactional
    void createArtworkCastWithExistingId() throws Exception {
        // Create the ArtworkCast with an existing ID
        artworkCast.setId(1L);
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkCastMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkCasts() throws Exception {
        // Initialize the database
        insertedArtworkCast = artworkCastRepository.saveAndFlush(artworkCast);

        // Get all the artworkCastList
        restArtworkCastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkCast.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderOfAppearance").value(hasItem(DEFAULT_ORDER_OF_APPEARANCE)))
            .andExpect(jsonPath("$.[*].characterName").value(hasItem(DEFAULT_CHARACTER_NAME)))
            .andExpect(jsonPath("$.[*].mainAssetUUID").value(hasItem(DEFAULT_MAIN_ASSET_UUID)))
            .andExpect(jsonPath("$.[*].characterDetailsJSON").value(hasItem(DEFAULT_CHARACTER_DETAILS_JSON)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtworkCastsWithEagerRelationshipsIsEnabled() throws Exception {
        when(artworkCastServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtworkCastMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(artworkCastServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllArtworkCastsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(artworkCastServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArtworkCastMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(artworkCastRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getArtworkCast() throws Exception {
        // Initialize the database
        insertedArtworkCast = artworkCastRepository.saveAndFlush(artworkCast);

        // Get the artworkCast
        restArtworkCastMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkCast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkCast.getId().intValue()))
            .andExpect(jsonPath("$.orderOfAppearance").value(DEFAULT_ORDER_OF_APPEARANCE))
            .andExpect(jsonPath("$.characterName").value(DEFAULT_CHARACTER_NAME))
            .andExpect(jsonPath("$.mainAssetUUID").value(DEFAULT_MAIN_ASSET_UUID))
            .andExpect(jsonPath("$.characterDetailsJSON").value(DEFAULT_CHARACTER_DETAILS_JSON));
    }

    @Test
    @Transactional
    void getNonExistingArtworkCast() throws Exception {
        // Get the artworkCast
        restArtworkCastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkCast() throws Exception {
        // Initialize the database
        insertedArtworkCast = artworkCastRepository.saveAndFlush(artworkCast);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artworkCast
        ArtworkCast updatedArtworkCast = artworkCastRepository.findById(artworkCast.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtworkCast are not directly saved in db
        em.detach(updatedArtworkCast);
        updatedArtworkCast
            .orderOfAppearance(UPDATED_ORDER_OF_APPEARANCE)
            .characterName(UPDATED_CHARACTER_NAME)
            .mainAssetUUID(UPDATED_MAIN_ASSET_UUID)
            .characterDetailsJSON(UPDATED_CHARACTER_DETAILS_JSON);
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(updatedArtworkCast);

        restArtworkCastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkCastDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArtworkCastToMatchAllProperties(updatedArtworkCast);
    }

    @Test
    @Transactional
    void putNonExistingArtworkCast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artworkCast.setId(longCount.incrementAndGet());

        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkCastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkCastDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkCast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artworkCast.setId(longCount.incrementAndGet());

        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkCast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artworkCast.setId(longCount.incrementAndGet());

        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCastMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artworkCastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkCastWithPatch() throws Exception {
        // Initialize the database
        insertedArtworkCast = artworkCastRepository.saveAndFlush(artworkCast);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artworkCast using partial update
        ArtworkCast partialUpdatedArtworkCast = new ArtworkCast();
        partialUpdatedArtworkCast.setId(artworkCast.getId());

        partialUpdatedArtworkCast.characterName(UPDATED_CHARACTER_NAME);

        restArtworkCastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkCast.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtworkCast))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkCast in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtworkCastUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArtworkCast, artworkCast),
            getPersistedArtworkCast(artworkCast)
        );
    }

    @Test
    @Transactional
    void fullUpdateArtworkCastWithPatch() throws Exception {
        // Initialize the database
        insertedArtworkCast = artworkCastRepository.saveAndFlush(artworkCast);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artworkCast using partial update
        ArtworkCast partialUpdatedArtworkCast = new ArtworkCast();
        partialUpdatedArtworkCast.setId(artworkCast.getId());

        partialUpdatedArtworkCast
            .orderOfAppearance(UPDATED_ORDER_OF_APPEARANCE)
            .characterName(UPDATED_CHARACTER_NAME)
            .mainAssetUUID(UPDATED_MAIN_ASSET_UUID)
            .characterDetailsJSON(UPDATED_CHARACTER_DETAILS_JSON);

        restArtworkCastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkCast.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtworkCast))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkCast in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtworkCastUpdatableFieldsEquals(partialUpdatedArtworkCast, getPersistedArtworkCast(partialUpdatedArtworkCast));
    }

    @Test
    @Transactional
    void patchNonExistingArtworkCast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artworkCast.setId(longCount.incrementAndGet());

        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkCastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkCastDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkCast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artworkCast.setId(longCount.incrementAndGet());

        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkCast() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artworkCast.setId(longCount.incrementAndGet());

        // Create the ArtworkCast
        ArtworkCastDTO artworkCastDTO = artworkCastMapper.toDto(artworkCast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCastMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(artworkCastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkCast in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkCast() throws Exception {
        // Initialize the database
        insertedArtworkCast = artworkCastRepository.saveAndFlush(artworkCast);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artworkCast
        restArtworkCastMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkCast.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artworkCastRepository.count();
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

    protected ArtworkCast getPersistedArtworkCast(ArtworkCast artworkCast) {
        return artworkCastRepository.findById(artworkCast.getId()).orElseThrow();
    }

    protected void assertPersistedArtworkCastToMatchAllProperties(ArtworkCast expectedArtworkCast) {
        assertArtworkCastAllPropertiesEquals(expectedArtworkCast, getPersistedArtworkCast(expectedArtworkCast));
    }

    protected void assertPersistedArtworkCastToMatchUpdatableProperties(ArtworkCast expectedArtworkCast) {
        assertArtworkCastAllUpdatablePropertiesEquals(expectedArtworkCast, getPersistedArtworkCast(expectedArtworkCast));
    }
}
