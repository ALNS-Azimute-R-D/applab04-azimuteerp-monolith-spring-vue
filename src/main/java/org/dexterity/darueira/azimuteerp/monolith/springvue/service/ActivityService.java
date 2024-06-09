package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity}.
 */
public interface ActivityService {
    /**
     * Save a activity.
     *
     * @param activityDTO the entity to save.
     * @return the persisted entity.
     */
    ActivityDTO save(ActivityDTO activityDTO);

    /**
     * Updates a activity.
     *
     * @param activityDTO the entity to update.
     * @return the persisted entity.
     */
    ActivityDTO update(ActivityDTO activityDTO);

    /**
     * Partially updates a activity.
     *
     * @param activityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ActivityDTO> partialUpdate(ActivityDTO activityDTO);

    /**
     * Get all the activities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityDTO> findAll(Pageable pageable);

    /**
     * Get all the activities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" activity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActivityDTO> findOne(Long id);

    /**
     * Delete the "id" activity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
