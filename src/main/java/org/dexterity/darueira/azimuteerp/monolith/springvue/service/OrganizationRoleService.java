package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole}.
 */
public interface OrganizationRoleService {
    /**
     * Save a organizationRole.
     *
     * @param organizationRoleDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationRoleDTO save(OrganizationRoleDTO organizationRoleDTO);

    /**
     * Updates a organizationRole.
     *
     * @param organizationRoleDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationRoleDTO update(OrganizationRoleDTO organizationRoleDTO);

    /**
     * Partially updates a organizationRole.
     *
     * @param organizationRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationRoleDTO> partialUpdate(OrganizationRoleDTO organizationRoleDTO);

    /**
     * Get all the organizationRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationRoleDTO> findAll(Pageable pageable);

    /**
     * Get all the organizationRoles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationRoleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" organizationRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationRoleDTO> findOne(Long id);

    /**
     * Delete the "id" organizationRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
