package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CommonLocalityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality}.
 */
public interface CommonLocalityService {
    /**
     * Save a commonLocality.
     *
     * @param commonLocalityDTO the entity to save.
     * @return the persisted entity.
     */
    CommonLocalityDTO save(CommonLocalityDTO commonLocalityDTO);

    /**
     * Updates a commonLocality.
     *
     * @param commonLocalityDTO the entity to update.
     * @return the persisted entity.
     */
    CommonLocalityDTO update(CommonLocalityDTO commonLocalityDTO);

    /**
     * Partially updates a commonLocality.
     *
     * @param commonLocalityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommonLocalityDTO> partialUpdate(CommonLocalityDTO commonLocalityDTO);

    /**
     * Get all the commonLocalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommonLocalityDTO> findAll(Pageable pageable);

    /**
     * Get all the commonLocalities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommonLocalityDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" commonLocality.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommonLocalityDTO> findOne(Long id);

    /**
     * Delete the "id" commonLocality.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
