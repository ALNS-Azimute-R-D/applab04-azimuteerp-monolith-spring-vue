package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMembershipDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership}.
 */
public interface OrganizationMembershipService {
    /**
     * Save a organizationMembership.
     *
     * @param organizationMembershipDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationMembershipDTO save(OrganizationMembershipDTO organizationMembershipDTO);

    /**
     * Updates a organizationMembership.
     *
     * @param organizationMembershipDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationMembershipDTO update(OrganizationMembershipDTO organizationMembershipDTO);

    /**
     * Partially updates a organizationMembership.
     *
     * @param organizationMembershipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationMembershipDTO> partialUpdate(OrganizationMembershipDTO organizationMembershipDTO);

    /**
     * Get all the organizationMemberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationMembershipDTO> findAll(Pageable pageable);

    /**
     * Get all the organizationMemberships with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationMembershipDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" organizationMembership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationMembershipDTO> findOne(Long id);

    /**
     * Delete the "id" organizationMembership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
