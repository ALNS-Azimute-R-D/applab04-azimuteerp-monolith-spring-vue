package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtmediaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia}.
 */
public interface TypeOfArtmediaService {
    /**
     * Save a typeOfArtmedia.
     *
     * @param typeOfArtmediaDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfArtmediaDTO save(TypeOfArtmediaDTO typeOfArtmediaDTO);

    /**
     * Updates a typeOfArtmedia.
     *
     * @param typeOfArtmediaDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfArtmediaDTO update(TypeOfArtmediaDTO typeOfArtmediaDTO);

    /**
     * Partially updates a typeOfArtmedia.
     *
     * @param typeOfArtmediaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfArtmediaDTO> partialUpdate(TypeOfArtmediaDTO typeOfArtmediaDTO);

    /**
     * Get all the typeOfArtmedias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfArtmediaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfArtmedia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfArtmediaDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfArtmedia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
