package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.SupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Supplier}.
 */
public interface SupplierService {
    /**
     * Save a supplier.
     *
     * @param supplierDTO the entity to save.
     * @return the persisted entity.
     */
    SupplierDTO save(SupplierDTO supplierDTO);

    /**
     * Updates a supplier.
     *
     * @param supplierDTO the entity to update.
     * @return the persisted entity.
     */
    SupplierDTO update(SupplierDTO supplierDTO);

    /**
     * Partially updates a supplier.
     *
     * @param supplierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SupplierDTO> partialUpdate(SupplierDTO supplierDTO);

    /**
     * Get all the suppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupplierDTO> findAll(Pageable pageable);

    /**
     * Get all the suppliers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupplierDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" supplier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplierDTO> findOne(Long id);

    /**
     * Delete the "id" supplier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
