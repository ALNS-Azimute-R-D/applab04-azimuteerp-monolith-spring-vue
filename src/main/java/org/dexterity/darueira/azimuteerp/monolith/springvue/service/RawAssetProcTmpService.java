package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.RawAssetProcTmpDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp}.
 */
public interface RawAssetProcTmpService {
    /**
     * Save a rawAssetProcTmp.
     *
     * @param rawAssetProcTmpDTO the entity to save.
     * @return the persisted entity.
     */
    RawAssetProcTmpDTO save(RawAssetProcTmpDTO rawAssetProcTmpDTO);

    /**
     * Updates a rawAssetProcTmp.
     *
     * @param rawAssetProcTmpDTO the entity to update.
     * @return the persisted entity.
     */
    RawAssetProcTmpDTO update(RawAssetProcTmpDTO rawAssetProcTmpDTO);

    /**
     * Partially updates a rawAssetProcTmp.
     *
     * @param rawAssetProcTmpDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RawAssetProcTmpDTO> partialUpdate(RawAssetProcTmpDTO rawAssetProcTmpDTO);

    /**
     * Get all the rawAssetProcTmps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RawAssetProcTmpDTO> findAll(Pageable pageable);

    /**
     * Get all the rawAssetProcTmps with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RawAssetProcTmpDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" rawAssetProcTmp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RawAssetProcTmpDTO> findOne(Long id);

    /**
     * Delete the "id" rawAssetProcTmp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
