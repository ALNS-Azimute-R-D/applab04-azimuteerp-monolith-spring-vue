package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.BusinessUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit}.
 */
public interface BusinessUnitService {
    /**
     * Save a businessUnit.
     *
     * @param businessUnitDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessUnitDTO save(BusinessUnitDTO businessUnitDTO);

    /**
     * Updates a businessUnit.
     *
     * @param businessUnitDTO the entity to update.
     * @return the persisted entity.
     */
    BusinessUnitDTO update(BusinessUnitDTO businessUnitDTO);

    /**
     * Partially updates a businessUnit.
     *
     * @param businessUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusinessUnitDTO> partialUpdate(BusinessUnitDTO businessUnitDTO);

    /**
     * Get all the businessUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessUnitDTO> findAll(Pageable pageable);

    /**
     * Get all the businessUnits with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessUnitDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" businessUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessUnitDTO> findOne(Long id);

    /**
     * Delete the "id" businessUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
