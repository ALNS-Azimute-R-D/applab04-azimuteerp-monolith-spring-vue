package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfEventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent}.
 */
public interface TypeOfEventService {
    /**
     * Save a typeOfEvent.
     *
     * @param typeOfEventDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfEventDTO save(TypeOfEventDTO typeOfEventDTO);

    /**
     * Updates a typeOfEvent.
     *
     * @param typeOfEventDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfEventDTO update(TypeOfEventDTO typeOfEventDTO);

    /**
     * Partially updates a typeOfEvent.
     *
     * @param typeOfEventDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfEventDTO> partialUpdate(TypeOfEventDTO typeOfEventDTO);

    /**
     * Get all the typeOfEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfEventDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfEventDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
