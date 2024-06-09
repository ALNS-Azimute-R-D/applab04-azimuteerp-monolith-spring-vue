package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMemberRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole}.
 */
public interface OrganizationMemberRoleService {
    /**
     * Save a organizationMemberRole.
     *
     * @param organizationMemberRoleDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationMemberRoleDTO save(OrganizationMemberRoleDTO organizationMemberRoleDTO);

    /**
     * Updates a organizationMemberRole.
     *
     * @param organizationMemberRoleDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationMemberRoleDTO update(OrganizationMemberRoleDTO organizationMemberRoleDTO);

    /**
     * Partially updates a organizationMemberRole.
     *
     * @param organizationMemberRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationMemberRoleDTO> partialUpdate(OrganizationMemberRoleDTO organizationMemberRoleDTO);

    /**
     * Get all the organizationMemberRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationMemberRoleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" organizationMemberRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationMemberRoleDTO> findOne(Long id);

    /**
     * Delete the "id" organizationMemberRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
