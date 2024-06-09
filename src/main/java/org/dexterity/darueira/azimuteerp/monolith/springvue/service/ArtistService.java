package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtistDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist}.
 */
public interface ArtistService {
    /**
     * Save a artist.
     *
     * @param artistDTO the entity to save.
     * @return the persisted entity.
     */
    ArtistDTO save(ArtistDTO artistDTO);

    /**
     * Updates a artist.
     *
     * @param artistDTO the entity to update.
     * @return the persisted entity.
     */
    ArtistDTO update(ArtistDTO artistDTO);

    /**
     * Partially updates a artist.
     *
     * @param artistDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtistDTO> partialUpdate(ArtistDTO artistDTO);

    /**
     * Get all the artists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtistDTO> findAll(Pageable pageable);

    /**
     * Get all the artists with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtistDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" artist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtistDTO> findOne(Long id);

    /**
     * Delete the "id" artist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
