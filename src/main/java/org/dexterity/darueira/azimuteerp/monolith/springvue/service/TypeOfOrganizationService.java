package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfOrganizationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization}.
 */
public interface TypeOfOrganizationService {
    /**
     * Save a typeOfOrganization.
     *
     * @param typeOfOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    TypeOfOrganizationDTO save(TypeOfOrganizationDTO typeOfOrganizationDTO);

    /**
     * Updates a typeOfOrganization.
     *
     * @param typeOfOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    TypeOfOrganizationDTO update(TypeOfOrganizationDTO typeOfOrganizationDTO);

    /**
     * Partially updates a typeOfOrganization.
     *
     * @param typeOfOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeOfOrganizationDTO> partialUpdate(TypeOfOrganizationDTO typeOfOrganizationDTO);

    /**
     * Get all the typeOfOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeOfOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" typeOfOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeOfOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" typeOfOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
