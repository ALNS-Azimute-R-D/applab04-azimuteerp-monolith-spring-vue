package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CustomerTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType}.
 */
public interface CustomerTypeService {
    /**
     * Save a customerType.
     *
     * @param customerTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CustomerTypeDTO save(CustomerTypeDTO customerTypeDTO);

    /**
     * Updates a customerType.
     *
     * @param customerTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CustomerTypeDTO update(CustomerTypeDTO customerTypeDTO);

    /**
     * Partially updates a customerType.
     *
     * @param customerTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustomerTypeDTO> partialUpdate(CustomerTypeDTO customerTypeDTO);

    /**
     * Get all the customerTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CustomerTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" customerType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustomerTypeDTO> findOne(Long id);

    /**
     * Delete the "id" customerType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
