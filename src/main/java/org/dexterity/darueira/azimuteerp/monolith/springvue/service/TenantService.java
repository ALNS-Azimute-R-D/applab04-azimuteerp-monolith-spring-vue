package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TenantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant}.
 */
public interface TenantService {
    /**
     * Save a tenant.
     *
     * @param tenantDTO the entity to save.
     * @return the persisted entity.
     */
    TenantDTO save(TenantDTO tenantDTO);

    /**
     * Updates a tenant.
     *
     * @param tenantDTO the entity to update.
     * @return the persisted entity.
     */
    TenantDTO update(TenantDTO tenantDTO);

    /**
     * Partially updates a tenant.
     *
     * @param tenantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TenantDTO> partialUpdate(TenantDTO tenantDTO);

    /**
     * Get all the tenants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TenantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tenant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TenantDTO> findOne(Long id);

    /**
     * Delete the "id" tenant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
