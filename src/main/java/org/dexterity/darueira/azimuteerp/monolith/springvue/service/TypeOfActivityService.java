package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity}.
 */
public interface TypeOfActivityService {
    /**
     * Save a typeOfActivity.
     *
     * @param typeOfActivityDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfActivityDTO save(TypeOfActivityDTO typeOfActivityDTO);

    /**
     * Updates a typeOfActivity.
     *
     * @param typeOfActivityDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfActivityDTO update(TypeOfActivityDTO typeOfActivityDTO);

    /**
     * Partially updates a typeOfActivity.
     *
     * @param typeOfActivityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfActivityDTO> partialUpdate(TypeOfActivityDTO typeOfActivityDTO);

    /**
     * Get all the typeOfActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfActivityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfActivityDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfActivity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
