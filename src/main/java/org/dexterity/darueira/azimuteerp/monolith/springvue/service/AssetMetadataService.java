package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetMetadataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadata}.
 */
public interface AssetMetadataService {
    /**
     * Save a assetMetadata.
     *
     * @param assetMetadataDTO the entity to save.
     * @return the persisted entity.
     */
    AssetMetadataDTO save(AssetMetadataDTO assetMetadataDTO);

    /**
     * Updates a assetMetadata.
     *
     * @param assetMetadataDTO the entity to update.
     * @return the persisted entity.
     */
    AssetMetadataDTO update(AssetMetadataDTO assetMetadataDTO);

    /**
     * Partially updates a assetMetadata.
     *
     * @param assetMetadataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetMetadataDTO> partialUpdate(AssetMetadataDTO assetMetadataDTO);

    /**
     * Get all the assetMetadata.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetMetadataDTO> findAll(Pageable pageable);

    /**
     * Get all the assetMetadata with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetMetadataDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" assetMetadata.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetMetadataDTO> findOne(Long id);

    /**
     * Delete the "id" assetMetadata.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
