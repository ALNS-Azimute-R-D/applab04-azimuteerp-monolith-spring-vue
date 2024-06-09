package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenreAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtisticGenreRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtisticGenreDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtisticGenreMapper;
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
 * Integration tests for the {@link ArtisticGenreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtisticGenreResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artistic-genres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ArtisticGenreRepository artisticGenreRepository;

    @Autowired
    private ArtisticGenreMapper artisticGenreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtisticGenreMockMvc;

    private ArtisticGenre artisticGenre;

    private ArtisticGenre insertedArtisticGenre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtisticGenre createEntity(EntityManager em) {
        ArtisticGenre artisticGenre = new ArtisticGenre().acronym(DEFAULT_ACRONYM).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return artisticGenre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtisticGenre createUpdatedEntity(EntityManager em) {
        ArtisticGenre artisticGenre = new ArtisticGenre().acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return artisticGenre;
    }

    @BeforeEach
    public void initTest() {
        artisticGenre = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedArtisticGenre != null) {
            artisticGenreRepository.delete(insertedArtisticGenre);
            insertedArtisticGenre = null;
        }
    }

    @Test
    @Transactional
    void createArtisticGenre() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);
        var returnedArtisticGenreDTO = om.readValue(
            restArtisticGenreMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(artisticGenreDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ArtisticGenreDTO.class
        );

        // Validate the ArtisticGenre in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedArtisticGenre = artisticGenreMapper.toEntity(returnedArtisticGenreDTO);
        assertArtisticGenreUpdatableFieldsEquals(returnedArtisticGenre, getPersistedArtisticGenre(returnedArtisticGenre));

        insertedArtisticGenre = returnedArtisticGenre;
    }

    @Test
    @Transactional
    void createArtisticGenreWithExistingId() throws Exception {
        // Create the ArtisticGenre with an existing ID
        artisticGenre.setId(1L);
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtisticGenreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAcronymIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        artisticGenre.setAcronym(null);

        // Create the ArtisticGenre, which fails.
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        restArtisticGenreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        artisticGenre.setName(null);

        // Create the ArtisticGenre, which fails.
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        restArtisticGenreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        artisticGenre.setDescription(null);

        // Create the ArtisticGenre, which fails.
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        restArtisticGenreMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArtisticGenres() throws Exception {
        // Initialize the database
        insertedArtisticGenre = artisticGenreRepository.saveAndFlush(artisticGenre);

        // Get all the artisticGenreList
        restArtisticGenreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artisticGenre.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getArtisticGenre() throws Exception {
        // Initialize the database
        insertedArtisticGenre = artisticGenreRepository.saveAndFlush(artisticGenre);

        // Get the artisticGenre
        restArtisticGenreMockMvc
            .perform(get(ENTITY_API_URL_ID, artisticGenre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artisticGenre.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingArtisticGenre() throws Exception {
        // Get the artisticGenre
        restArtisticGenreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtisticGenre() throws Exception {
        // Initialize the database
        insertedArtisticGenre = artisticGenreRepository.saveAndFlush(artisticGenre);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artisticGenre
        ArtisticGenre updatedArtisticGenre = artisticGenreRepository.findById(artisticGenre.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtisticGenre are not directly saved in db
        em.detach(updatedArtisticGenre);
        updatedArtisticGenre.acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(updatedArtisticGenre);

        restArtisticGenreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artisticGenreDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedArtisticGenreToMatchAllProperties(updatedArtisticGenre);
    }

    @Test
    @Transactional
    void putNonExistingArtisticGenre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artisticGenre.setId(longCount.incrementAndGet());

        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtisticGenreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artisticGenreDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtisticGenre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artisticGenre.setId(longCount.incrementAndGet());

        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtisticGenreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtisticGenre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artisticGenre.setId(longCount.incrementAndGet());

        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtisticGenreMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtisticGenreWithPatch() throws Exception {
        // Initialize the database
        insertedArtisticGenre = artisticGenreRepository.saveAndFlush(artisticGenre);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artisticGenre using partial update
        ArtisticGenre partialUpdatedArtisticGenre = new ArtisticGenre();
        partialUpdatedArtisticGenre.setId(artisticGenre.getId());

        partialUpdatedArtisticGenre.description(UPDATED_DESCRIPTION);

        restArtisticGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtisticGenre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtisticGenre))
            )
            .andExpect(status().isOk());

        // Validate the ArtisticGenre in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtisticGenreUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedArtisticGenre, artisticGenre),
            getPersistedArtisticGenre(artisticGenre)
        );
    }

    @Test
    @Transactional
    void fullUpdateArtisticGenreWithPatch() throws Exception {
        // Initialize the database
        insertedArtisticGenre = artisticGenreRepository.saveAndFlush(artisticGenre);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the artisticGenre using partial update
        ArtisticGenre partialUpdatedArtisticGenre = new ArtisticGenre();
        partialUpdatedArtisticGenre.setId(artisticGenre.getId());

        partialUpdatedArtisticGenre.acronym(UPDATED_ACRONYM).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restArtisticGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtisticGenre.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedArtisticGenre))
            )
            .andExpect(status().isOk());

        // Validate the ArtisticGenre in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertArtisticGenreUpdatableFieldsEquals(partialUpdatedArtisticGenre, getPersistedArtisticGenre(partialUpdatedArtisticGenre));
    }

    @Test
    @Transactional
    void patchNonExistingArtisticGenre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artisticGenre.setId(longCount.incrementAndGet());

        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtisticGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artisticGenreDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtisticGenre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artisticGenre.setId(longCount.incrementAndGet());

        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtisticGenreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtisticGenre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        artisticGenre.setId(longCount.incrementAndGet());

        // Create the ArtisticGenre
        ArtisticGenreDTO artisticGenreDTO = artisticGenreMapper.toDto(artisticGenre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtisticGenreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(artisticGenreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtisticGenre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtisticGenre() throws Exception {
        // Initialize the database
        insertedArtisticGenre = artisticGenreRepository.saveAndFlush(artisticGenre);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the artisticGenre
        restArtisticGenreMockMvc
            .perform(delete(ENTITY_API_URL_ID, artisticGenre.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return artisticGenreRepository.count();
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

    protected ArtisticGenre getPersistedArtisticGenre(ArtisticGenre artisticGenre) {
        return artisticGenreRepository.findById(artisticGenre.getId()).orElseThrow();
    }

    protected void assertPersistedArtisticGenreToMatchAllProperties(ArtisticGenre expectedArtisticGenre) {
        assertArtisticGenreAllPropertiesEquals(expectedArtisticGenre, getPersistedArtisticGenre(expectedArtisticGenre));
    }

    protected void assertPersistedArtisticGenreToMatchUpdatableProperties(ArtisticGenre expectedArtisticGenre) {
        assertArtisticGenreAllUpdatablePropertiesEquals(expectedArtisticGenre, getPersistedArtisticGenre(expectedArtisticGenre));
    }
}
