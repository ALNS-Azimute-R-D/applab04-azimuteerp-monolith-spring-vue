package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artwork}.
 */
public interface ArtworkService {
    /**
     * Save a artwork.
     *
     * @param artworkDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkDTO save(ArtworkDTO artworkDTO);

    /**
     * Updates a artwork.
     *
     * @param artworkDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkDTO update(ArtworkDTO artworkDTO);

    /**
     * Partially updates a artwork.
     *
     * @param artworkDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkDTO> partialUpdate(ArtworkDTO artworkDTO);

    /**
     * Get all the artworks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkDTO> findAll(Pageable pageable);

    /**
     * Get all the artworks with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" artwork.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkDTO> findOne(Long id);

    /**
     * Delete the "id" artwork.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
