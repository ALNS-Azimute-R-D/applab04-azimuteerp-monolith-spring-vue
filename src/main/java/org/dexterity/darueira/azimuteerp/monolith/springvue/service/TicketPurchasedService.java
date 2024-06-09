package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TicketPurchasedDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased}.
 */
public interface TicketPurchasedService {
    /**
     * Save a ticketPurchased.
     *
     * @param ticketPurchasedDTO the entity to save.
     * @return the persisted entity.
     */
    TicketPurchasedDTO save(TicketPurchasedDTO ticketPurchasedDTO);

    /**
     * Updates a ticketPurchased.
     *
     * @param ticketPurchasedDTO the entity to update.
     * @return the persisted entity.
     */
    TicketPurchasedDTO update(TicketPurchasedDTO ticketPurchasedDTO);

    /**
     * Partially updates a ticketPurchased.
     *
     * @param ticketPurchasedDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TicketPurchasedDTO> partialUpdate(TicketPurchasedDTO ticketPurchasedDTO);

    /**
     * Get all the ticketPurchaseds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketPurchasedDTO> findAll(Pageable pageable);

    /**
     * Get all the ticketPurchaseds with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketPurchasedDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" ticketPurchased.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketPurchasedDTO> findOne(Long id);

    /**
     * Delete the "id" ticketPurchased.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
