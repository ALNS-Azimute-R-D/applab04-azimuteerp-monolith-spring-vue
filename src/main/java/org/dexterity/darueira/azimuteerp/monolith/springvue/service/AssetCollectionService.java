package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetCollectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection}.
 */
public interface AssetCollectionService {
    /**
     * Save a assetCollection.
     *
     * @param assetCollectionDTO the entity to save.
     * @return the persisted entity.
     */
    AssetCollectionDTO save(AssetCollectionDTO assetCollectionDTO);

    /**
     * Updates a assetCollection.
     *
     * @param assetCollectionDTO the entity to update.
     * @return the persisted entity.
     */
    AssetCollectionDTO update(AssetCollectionDTO assetCollectionDTO);

    /**
     * Partially updates a assetCollection.
     *
     * @param assetCollectionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetCollectionDTO> partialUpdate(AssetCollectionDTO assetCollectionDTO);

    /**
     * Get all the assetCollections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetCollectionDTO> findAll(Pageable pageable);

    /**
     * Get all the assetCollections with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetCollectionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" assetCollection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetCollectionDTO> findOne(Long id);

    /**
     * Delete the "id" assetCollection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
