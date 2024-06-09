package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.StockLevelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel}.
 */
public interface StockLevelService {
    /**
     * Save a stockLevel.
     *
     * @param stockLevelDTO the entity to save.
     * @return the persisted entity.
     */
    StockLevelDTO save(StockLevelDTO stockLevelDTO);

    /**
     * Updates a stockLevel.
     *
     * @param stockLevelDTO the entity to update.
     * @return the persisted entity.
     */
    StockLevelDTO update(StockLevelDTO stockLevelDTO);

    /**
     * Partially updates a stockLevel.
     *
     * @param stockLevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StockLevelDTO> partialUpdate(StockLevelDTO stockLevelDTO);

    /**
     * Get all the stockLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockLevelDTO> findAll(Pageable pageable);

    /**
     * Get all the stockLevels with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StockLevelDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" stockLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockLevelDTO> findOne(Long id);

    /**
     * Delete the "id" stockLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
