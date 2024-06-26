package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person}.
 */
public interface PersonService {
    /**
     * Save a person.
     *
     * @param personDTO the entity to save.
     * @return the persisted entity.
     */
    PersonDTO save(PersonDTO personDTO);

    /**
     * Updates a person.
     *
     * @param personDTO the entity to update.
     * @return the persisted entity.
     */
    PersonDTO update(PersonDTO personDTO);

    /**
     * Partially updates a person.
     *
     * @param personDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonDTO> partialUpdate(PersonDTO personDTO);

    /**
     * Get all the people.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonDTO> findAll(Pageable pageable);

    /**
     * Get all the people with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" person.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonDTO> findOne(Long id);

    /**
     * Delete the "id" person.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
