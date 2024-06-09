package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtistDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist}.
 */
public interface TypeOfArtistService {
    /**
     * Save a typeOfArtist.
     *
     * @param typeOfArtistDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfArtistDTO save(TypeOfArtistDTO typeOfArtistDTO);

    /**
     * Updates a typeOfArtist.
     *
     * @param typeOfArtistDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfArtistDTO update(TypeOfArtistDTO typeOfArtistDTO);

    /**
     * Partially updates a typeOfArtist.
     *
     * @param typeOfArtistDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfArtistDTO> partialUpdate(TypeOfArtistDTO typeOfArtistDTO);

    /**
     * Get all the typeOfArtists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfArtistDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfArtist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfArtistDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfArtist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
