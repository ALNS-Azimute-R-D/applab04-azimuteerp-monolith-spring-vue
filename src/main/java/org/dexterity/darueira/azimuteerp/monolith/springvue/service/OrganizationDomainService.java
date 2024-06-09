package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDomainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain}.
 */
public interface OrganizationDomainService {
    /**
     * Save a organizationDomain.
     *
     * @param organizationDomainDTO the entity to save.
     * @return the persisted entity.
     */
    OrganizationDomainDTO save(OrganizationDomainDTO organizationDomainDTO);

    /**
     * Updates a organizationDomain.
     *
     * @param organizationDomainDTO the entity to update.
     * @return the persisted entity.
     */
    OrganizationDomainDTO update(OrganizationDomainDTO organizationDomainDTO);

    /**
     * Partially updates a organizationDomain.
     *
     * @param organizationDomainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrganizationDomainDTO> partialUpdate(OrganizationDomainDTO organizationDomainDTO);

    /**
     * Get all the organizationDomains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationDomainDTO> findAll(Pageable pageable);

    /**
     * Get all the organizationDomains with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganizationDomainDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" organizationDomain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganizationDomainDTO> findOne(Long id);

    /**
     * Delete the "id" organizationDomain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
