package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TownCityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity}.
 */
public interface TownCityService {
    /**
     * Save a townCity.
     *
     * @param townCityDTO the entity to save.
     * @return the persisted entity.
     */
    TownCityDTO save(TownCityDTO townCityDTO);

    /**
     * Updates a townCity.
     *
     * @param townCityDTO the entity to update.
     * @return the persisted entity.
     */
    TownCityDTO update(TownCityDTO townCityDTO);

    /**
     * Partially updates a townCity.
     *
     * @param townCityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TownCityDTO> partialUpdate(TownCityDTO townCityDTO);

    /**
     * Get all the townCities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TownCityDTO> findAll(Pageable pageable);

    /**
     * Get all the townCities with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TownCityDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" townCity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TownCityDTO> findOne(Long id);

    /**
     * Delete the "id" townCity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
