package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtisticGenreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre}.
 */
public interface ArtisticGenreService {
    /**
     * Save a artisticGenre.
     *
     * @param artisticGenreDTO the entity to save.
     * @return the persisted entity.
     */
    ArtisticGenreDTO save(ArtisticGenreDTO artisticGenreDTO);

    /**
     * Updates a artisticGenre.
     *
     * @param artisticGenreDTO the entity to update.
     * @return the persisted entity.
     */
    ArtisticGenreDTO update(ArtisticGenreDTO artisticGenreDTO);

    /**
     * Partially updates a artisticGenre.
     *
     * @param artisticGenreDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtisticGenreDTO> partialUpdate(ArtisticGenreDTO artisticGenreDTO);

    /**
     * Get all the artisticGenres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtisticGenreDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artisticGenre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtisticGenreDTO> findOne(Long id);

    /**
     * Delete the "id" artisticGenre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
