package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization}.
 */
public interface OrganizationService {
    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationDTO save(OrganizationDTO organizationDTO);

    /**
     * Updates a organization.
     *
     * @param organizationDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationDTO update(OrganizationDTO organizationDTO);

    /**
     * Partially updates a organization.
     *
     * @param organizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationDTO> partialUpdate(OrganizationDTO organizationDTO);

    /**
     * Get all the organizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationDTO> findAll(Pageable pageable);

    /**
     * Get all the organizations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" organization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" organization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
