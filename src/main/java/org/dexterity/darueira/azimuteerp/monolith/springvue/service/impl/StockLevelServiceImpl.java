package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.StockLevelRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.StockLevelService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.StockLevelDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.StockLevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel}.
 */
@Service
@Transactional
public class StockLevelServiceImpl implements StockLevelService {

    private final Logger log = LoggerFactory.getLogger(StockLevelServiceImpl.class);

    private final StockLevelRepository stockLevelRepository;

    private final StockLevelMapper stockLevelMapper;

    public StockLevelServiceImpl(StockLevelRepository stockLevelRepository, StockLevelMapper stockLevelMapper) {
        this.stockLevelRepository = stockLevelRepository;
        this.stockLevelMapper = stockLevelMapper;
    }

    @Override
    public StockLevelDTO save(StockLevelDTO stockLevelDTO) {
        log.debug("Request to save StockLevel : {}", stockLevelDTO);
        StockLevel stockLevel = stockLevelMapper.toEntity(stockLevelDTO);
        stockLevel = stockLevelRepository.save(stockLevel);
        return stockLevelMapper.toDto(stockLevel);
    }

    @Override
    public StockLevelDTO update(StockLevelDTO stockLevelDTO) {
        log.debug("Request to update StockLevel : {}", stockLevelDTO);
        StockLevel stockLevel = stockLevelMapper.toEntity(stockLevelDTO);
        stockLevel = stockLevelRepository.save(stockLevel);
        return stockLevelMapper.toDto(stockLevel);
    }

    @Override
    public Optional<StockLevelDTO> partialUpdate(StockLevelDTO stockLevelDTO) {
        log.debug("Request to partially update StockLevel : {}", stockLevelDTO);

        return stockLevelRepository
            .findById(stockLevelDTO.getId())
            .map(existingStockLevel -> {
                stockLevelMapper.partialUpdate(existingStockLevel, stockLevelDTO);

                return existingStockLevel;
            })
            .map(stockLevelRepository::save)
            .map(stockLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockLevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockLevels");
        return stockLevelRepository.findAll(pageable).map(stockLevelMapper::toDto);
    }

    public Page<StockLevelDTO> findAllWithEagerRelationships(Pageable pageable) {
        return stockLevelRepository.findAllWithEagerRelationships(pageable).map(stockLevelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockLevelDTO> findOne(Long id) {
        log.debug("Request to get StockLevel : {}", id);
        return stockLevelRepository.findOneWithEagerRelationships(id).map(stockLevelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockLevel : {}", id);
        stockLevelRepository.deleteById(id);
    }
}
