package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.BrandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Brand}.
 */
public interface BrandService {
    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save.
     * @return the persisted entity.
     */
    BrandDTO save(BrandDTO brandDTO);

    /**
     * Updates a brand.
     *
     * @param brandDTO the entity to update.
     * @return the persisted entity.
     */
    BrandDTO update(BrandDTO brandDTO);

    /**
     * Partially updates a brand.
     *
     * @param brandDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BrandDTO> partialUpdate(BrandDTO brandDTO);

    /**
     * Get all the brands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BrandDTO> findAll(Pageable pageable);

    /**
     * Get the "id" brand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BrandDTO> findOne(Long id);

    /**
     * Delete the "id" brand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
