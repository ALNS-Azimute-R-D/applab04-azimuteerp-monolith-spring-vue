package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.VenueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue}.
 */
public interface VenueService {
    /**
     * Save a venue.
     *
     * @param venueDTO the entity to save.
     * @return the persisted entity.
     */
    VenueDTO save(VenueDTO venueDTO);

    /**
     * Updates a venue.
     *
     * @param venueDTO the entity to update.
     * @return the persisted entity.
     */
    VenueDTO update(VenueDTO venueDTO);

    /**
     * Partially updates a venue.
     *
     * @param venueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VenueDTO> partialUpdate(VenueDTO venueDTO);

    /**
     * Get all the venues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VenueDTO> findAll(Pageable pageable);

    /**
     * Get all the venues with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VenueDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" venue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VenueDTO> findOne(Long id);

    /**
     * Delete the "id" venue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
