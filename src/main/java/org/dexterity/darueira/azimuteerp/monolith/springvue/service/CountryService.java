package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CountryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Country}.
 */
public interface CountryService {
    /**
     * Save a country.
     *
     * @param countryDTO the entity to save.
     * @return the persisted entity.
     */
    CountryDTO save(CountryDTO countryDTO);

    /**
     * Updates a country.
     *
     * @param countryDTO the entity to update.
     * @return the persisted entity.
     */
    CountryDTO update(CountryDTO countryDTO);

    /**
     * Partially updates a country.
     *
     * @param countryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CountryDTO> partialUpdate(CountryDTO countryDTO);

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CountryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" country.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CountryDTO> findOne(Long id);

    /**
     * Delete the "id" country.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
