package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.AssetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Asset}.
 */
@Service
@Transactional
public class AssetServiceImpl implements AssetService {

    private final Logger log = LoggerFactory.getLogger(AssetServiceImpl.class);

    private final AssetRepository assetRepository;

    private final AssetMapper assetMapper;

    public AssetServiceImpl(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    @Override
    public AssetDTO save(AssetDTO assetDTO) {
        log.debug("Request to save Asset : {}", assetDTO);
        Asset asset = assetMapper.toEntity(assetDTO);
        asset = assetRepository.save(asset);
        return assetMapper.toDto(asset);
    }

    @Override
    public AssetDTO update(AssetDTO assetDTO) {
        log.debug("Request to update Asset : {}", assetDTO);
        Asset asset = assetMapper.toEntity(assetDTO);
        asset = assetRepository.save(asset);
        return assetMapper.toDto(asset);
    }

    @Override
    public Optional<AssetDTO> partialUpdate(AssetDTO assetDTO) {
        log.debug("Request to partially update Asset : {}", assetDTO);

        return assetRepository
            .findById(assetDTO.getId())
            .map(existingAsset -> {
                assetMapper.partialUpdate(existingAsset, assetDTO);

                return existingAsset;
            })
            .map(assetRepository::save)
            .map(assetMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assets");
        return assetRepository.findAll(pageable).map(assetMapper::toDto);
    }

    public Page<AssetDTO> findAllWithEagerRelationships(Pageable pageable) {
        return assetRepository.findAllWithEagerRelationships(pageable).map(assetMapper::toDto);
    }

    /**
     *  Get all the assets where AssetMetadata is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AssetDTO> findAllWhereAssetMetadataIsNull() {
        log.debug("Request to get all assets where AssetMetadata is null");
        return StreamSupport.stream(assetRepository.findAll().spliterator(), false)
            .filter(asset -> asset.getAssetMetadata() == null)
            .map(assetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssetDTO> findOne(Long id) {
        log.debug("Request to get Asset : {}", id);
        return assetRepository.findOneWithEagerRelationships(id).map(assetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);
        assetRepository.deleteById(id);
    }
}
