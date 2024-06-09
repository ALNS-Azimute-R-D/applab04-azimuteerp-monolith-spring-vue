package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset}.
 */
public interface AssetService {
    /**
     * Save a asset.
     *
     * @param assetDTO the entity to save.
     * @return the persisted entity.
     */
    AssetDTO save(AssetDTO assetDTO);

    /**
     * Updates a asset.
     *
     * @param assetDTO the entity to update.
     * @return the persisted entity.
     */
    AssetDTO update(AssetDTO assetDTO);

    /**
     * Partially updates a asset.
     *
     * @param assetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetDTO> partialUpdate(AssetDTO assetDTO);

    /**
     * Get all the assets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetDTO> findAll(Pageable pageable);

    /**
     * Get all the AssetDTO where AssetMetadata is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AssetDTO> findAllWhereAssetMetadataIsNull();

    /**
     * Get all the assets with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" asset.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetDTO> findOne(Long id);

    /**
     * Delete the "id" asset.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
