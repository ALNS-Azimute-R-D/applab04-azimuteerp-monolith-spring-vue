package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.EventProgramDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram}.
 */
public interface EventProgramService {
    /**
     * Save a eventProgram.
     *
     * @param eventProgramDTO the entity to save.
     * @return the persisted entity.
     */
    EventProgramDTO save(EventProgramDTO eventProgramDTO);

    /**
     * Updates a eventProgram.
     *
     * @param eventProgramDTO the entity to update.
     * @return the persisted entity.
     */
    EventProgramDTO update(EventProgramDTO eventProgramDTO);

    /**
     * Partially updates a eventProgram.
     *
     * @param eventProgramDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventProgramDTO> partialUpdate(EventProgramDTO eventProgramDTO);

    /**
     * Get all the eventPrograms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventProgramDTO> findAll(Pageable pageable);

    /**
     * Get all the eventPrograms with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventProgramDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" eventProgram.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventProgramDTO> findOne(Long id);

    /**
     * Delete the "id" eventProgram.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
