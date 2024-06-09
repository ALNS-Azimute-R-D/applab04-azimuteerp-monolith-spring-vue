package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfVenueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue}.
 */
public interface TypeOfVenueService {
    /**
     * Save a typeOfVenue.
     *
     * @param typeOfVenueDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfVenueDTO save(TypeOfVenueDTO typeOfVenueDTO);

    /**
     * Updates a typeOfVenue.
     *
     * @param typeOfVenueDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfVenueDTO update(TypeOfVenueDTO typeOfVenueDTO);

    /**
     * Partially updates a typeOfVenue.
     *
     * @param typeOfVenueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfVenueDTO> partialUpdate(TypeOfVenueDTO typeOfVenueDTO);

    /**
     * Get all the typeOfVenues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfVenueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfVenue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfVenueDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfVenue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
