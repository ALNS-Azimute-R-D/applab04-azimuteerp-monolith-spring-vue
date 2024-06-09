package org.dexterity.darueira.azimuteerp.monolith.springvue.service;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PaymentGatewayDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway}.
 */
public interface PaymentGatewayService {
    /**
     * Save a paymentGateway.
     *
     * @param paymentGatewayDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentGatewayDTO save(PaymentGatewayDTO paymentGatewayDTO);

    /**
     * Updates a paymentGateway.
     *
     * @param paymentGatewayDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentGatewayDTO update(PaymentGatewayDTO paymentGatewayDTO);

    /**
     * Partially updates a paymentGateway.
     *
     * @param paymentGatewayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentGatewayDTO> partialUpdate(PaymentGatewayDTO paymentGatewayDTO);

    /**
     * Get all the paymentGateways.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentGatewayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" paymentGateway.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentGatewayDTO> findOne(Long id);

    /**
     * Delete the "id" paymentGateway.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
