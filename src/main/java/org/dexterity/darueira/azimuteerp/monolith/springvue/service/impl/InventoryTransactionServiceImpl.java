package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransaction;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.InventoryTransactionRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.InventoryTransactionService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.InventoryTransactionDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.InventoryTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.InventoryTransaction}.
 */
@Service
@Transactional
public class InventoryTransactionServiceImpl implements InventoryTransactionService {

    private final Logger log = LoggerFactory.getLogger(InventoryTransactionServiceImpl.class);

    private final InventoryTransactionRepository inventoryTransactionRepository;

    private final InventoryTransactionMapper inventoryTransactionMapper;

    public InventoryTransactionServiceImpl(
        InventoryTransactionRepository inventoryTransactionRepository,
        InventoryTransactionMapper inventoryTransactionMapper
    ) {
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.inventoryTransactionMapper = inventoryTransactionMapper;
    }

    @Override
    public InventoryTransactionDTO save(InventoryTransactionDTO inventoryTransactionDTO) {
        log.debug("Request to save InventoryTransaction : {}", inventoryTransactionDTO);
        InventoryTransaction inventoryTransaction = inventoryTransactionMapper.toEntity(inventoryTransactionDTO);
        inventoryTransaction = inventoryTransactionRepository.save(inventoryTransaction);
        return inventoryTransactionMapper.toDto(inventoryTransaction);
    }

    @Override
    public InventoryTransactionDTO update(InventoryTransactionDTO inventoryTransactionDTO) {
        log.debug("Request to update InventoryTransaction : {}", inventoryTransactionDTO);
        InventoryTransaction inventoryTransaction = inventoryTransactionMapper.toEntity(inventoryTransactionDTO);
        inventoryTransaction = inventoryTransactionRepository.save(inventoryTransaction);
        return inventoryTransactionMapper.toDto(inventoryTransaction);
    }

    @Override
    public Optional<InventoryTransactionDTO> partialUpdate(InventoryTransactionDTO inventoryTransactionDTO) {
        log.debug("Request to partially update InventoryTransaction : {}", inventoryTransactionDTO);

        return inventoryTransactionRepository
            .findById(inventoryTransactionDTO.getId())
            .map(existingInventoryTransaction -> {
                inventoryTransactionMapper.partialUpdate(existingInventoryTransaction, inventoryTransactionDTO);

                return existingInventoryTransaction;
            })
            .map(inventoryTransactionRepository::save)
            .map(inventoryTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryTransactions");
        return inventoryTransactionRepository.findAll(pageable).map(inventoryTransactionMapper::toDto);
    }

    public Page<InventoryTransactionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return inventoryTransactionRepository.findAllWithEagerRelationships(pageable).map(inventoryTransactionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryTransactionDTO> findOne(Long id) {
        log.debug("Request to get InventoryTransaction : {}", id);
        return inventoryTransactionRepository.findOneWithEagerRelationships(id).map(inventoryTransactionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InventoryTransaction : {}", id);
        inventoryTransactionRepository.deleteById(id);
    }
}
