package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ScheduledActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity}.
 */
public interface ScheduledActivityService {
    /**
     * Save a scheduledActivity.
     *
     * @param scheduledActivityDTO the entity to save.
     * @return the persisted entity.
     */
    ScheduledActivityDTO save(ScheduledActivityDTO scheduledActivityDTO);

    /**
     * Updates a scheduledActivity.
     *
     * @param scheduledActivityDTO the entity to update.
     * @return the persisted entity.
     */
    ScheduledActivityDTO update(ScheduledActivityDTO scheduledActivityDTO);

    /**
     * Partially updates a scheduledActivity.
     *
     * @param scheduledActivityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ScheduledActivityDTO> partialUpdate(ScheduledActivityDTO scheduledActivityDTO);

    /**
     * Get all the scheduledActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScheduledActivityDTO> findAll(Pageable pageable);

    /**
     * Get all the scheduledActivities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScheduledActivityDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" scheduledActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduledActivityDTO> findOne(Long id);

    /**
     * Delete the "id" scheduledActivity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
