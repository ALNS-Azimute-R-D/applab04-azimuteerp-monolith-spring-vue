package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetType}.
 */
public interface AssetTypeService {
    /**
     * Save a assetType.
     *
     * @param assetTypeDTO the entity to save.
     * @return the persisted entity.
     */
    AssetTypeDTO save(AssetTypeDTO assetTypeDTO);

    /**
     * Updates a assetType.
     *
     * @param assetTypeDTO the entity to update.
     * @return the persisted entity.
     */
    AssetTypeDTO update(AssetTypeDTO assetTypeDTO);

    /**
     * Partially updates a assetType.
     *
     * @param assetTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetTypeDTO> partialUpdate(AssetTypeDTO assetTypeDTO);

    /**
     * Get all the assetTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" assetType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetTypeDTO> findOne(Long id);

    /**
     * Delete the "id" assetType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
