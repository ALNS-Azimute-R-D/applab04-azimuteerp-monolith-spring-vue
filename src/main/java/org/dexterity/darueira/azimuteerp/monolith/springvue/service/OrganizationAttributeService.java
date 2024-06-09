package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationAttributeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute}.
 */
public interface OrganizationAttributeService {
    /**
     * Save a organizationAttribute.
     *
     * @param organizationAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationAttributeDTO save(OrganizationAttributeDTO organizationAttributeDTO);

    /**
     * Updates a organizationAttribute.
     *
     * @param organizationAttributeDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationAttributeDTO update(OrganizationAttributeDTO organizationAttributeDTO);

    /**
     * Partially updates a organizationAttribute.
     *
     * @param organizationAttributeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationAttributeDTO> partialUpdate(OrganizationAttributeDTO organizationAttributeDTO);

    /**
     * Get all the organizationAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationAttributeDTO> findAll(Pageable pageable);

    /**
     * Get all the organizationAttributes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationAttributeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" organizationAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" organizationAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
