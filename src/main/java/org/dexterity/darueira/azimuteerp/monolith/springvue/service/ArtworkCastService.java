package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkCastDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast}.
 */
public interface ArtworkCastService {
    /**
     * Save a artworkCast.
     *
     * @param artworkCastDTO the entity to save.
     * @return the persisted entity.
     */
    ArtworkCastDTO save(ArtworkCastDTO artworkCastDTO);

    /**
     * Updates a artworkCast.
     *
     * @param artworkCastDTO the entity to update.
     * @return the persisted entity.
     */
    ArtworkCastDTO update(ArtworkCastDTO artworkCastDTO);

    /**
     * Partially updates a artworkCast.
     *
     * @param artworkCastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtworkCastDTO> partialUpdate(ArtworkCastDTO artworkCastDTO);

    /**
     * Get all the artworkCasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkCastDTO> findAll(Pageable pageable);

    /**
     * Get all the artworkCasts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtworkCastDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" artworkCast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtworkCastDTO> findOne(Long id);

    /**
     * Delete the "id" artworkCast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
