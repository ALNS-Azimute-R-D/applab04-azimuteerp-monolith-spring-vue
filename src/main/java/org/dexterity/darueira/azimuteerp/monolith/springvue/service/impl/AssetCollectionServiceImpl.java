package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetCollectionRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetCollectionService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetCollectionDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.AssetCollectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection}.
 */
@Service
@Transactional
public class AssetCollectionServiceImpl implements AssetCollectionService {

    private final Logger log = LoggerFactory.getLogger(AssetCollectionServiceImpl.class);

    private final AssetCollectionRepository assetCollectionRepository;

    private final AssetCollectionMapper assetCollectionMapper;

    public AssetCollectionServiceImpl(AssetCollectionRepository assetCollectionRepository, AssetCollectionMapper assetCollectionMapper) {
        this.assetCollectionRepository = assetCollectionRepository;
        this.assetCollectionMapper = assetCollectionMapper;
    }

    @Override
    public AssetCollectionDTO save(AssetCollectionDTO assetCollectionDTO) {
        log.debug("Request to save AssetCollection : {}", assetCollectionDTO);
        AssetCollection assetCollection = assetCollectionMapper.toEntity(assetCollectionDTO);
        assetCollection = assetCollectionRepository.save(assetCollection);
        return assetCollectionMapper.toDto(assetCollection);
    }

    @Override
    public AssetCollectionDTO update(AssetCollectionDTO assetCollectionDTO) {
        log.debug("Request to update AssetCollection : {}", assetCollectionDTO);
        AssetCollection assetCollection = assetCollectionMapper.toEntity(assetCollectionDTO);
        assetCollection = assetCollectionRepository.save(assetCollection);
        return assetCollectionMapper.toDto(assetCollection);
    }

    @Override
    public Optional<AssetCollectionDTO> partialUpdate(AssetCollectionDTO assetCollectionDTO) {
        log.debug("Request to partially update AssetCollection : {}", assetCollectionDTO);

        return assetCollectionRepository
            .findById(assetCollectionDTO.getId())
            .map(existingAssetCollection -> {
                assetCollectionMapper.partialUpdate(existingAssetCollection, assetCollectionDTO);

                return existingAssetCollection;
            })
            .map(assetCollectionRepository::save)
            .map(assetCollectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetCollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetCollections");
        return assetCollectionRepository.findAll(pageable).map(assetCollectionMapper::toDto);
    }

    public Page<AssetCollectionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return assetCollectionRepository.findAllWithEagerRelationships(pageable).map(assetCollectionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssetCollectionDTO> findOne(Long id) {
        log.debug("Request to get AssetCollection : {}", id);
        return assetCollectionRepository.findOneWithEagerRelationships(id).map(assetCollectionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssetCollection : {}", id);
        assetCollectionRepository.deleteById(id);
    }
}
