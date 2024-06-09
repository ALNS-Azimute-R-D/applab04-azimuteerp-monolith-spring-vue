package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtworkCastRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtworkCastService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkCastDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtworkCastMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast}.
 */
@Service
@Transactional
public class ArtworkCastServiceImpl implements ArtworkCastService {

    private final Logger log = LoggerFactory.getLogger(ArtworkCastServiceImpl.class);

    private final ArtworkCastRepository artworkCastRepository;

    private final ArtworkCastMapper artworkCastMapper;

    public ArtworkCastServiceImpl(ArtworkCastRepository artworkCastRepository, ArtworkCastMapper artworkCastMapper) {
        this.artworkCastRepository = artworkCastRepository;
        this.artworkCastMapper = artworkCastMapper;
    }

    @Override
    public ArtworkCastDTO save(ArtworkCastDTO artworkCastDTO) {
        log.debug("Request to save ArtworkCast : {}", artworkCastDTO);
        ArtworkCast artworkCast = artworkCastMapper.toEntity(artworkCastDTO);
        artworkCast = artworkCastRepository.save(artworkCast);
        return artworkCastMapper.toDto(artworkCast);
    }

    @Override
    public ArtworkCastDTO update(ArtworkCastDTO artworkCastDTO) {
        log.debug("Request to update ArtworkCast : {}", artworkCastDTO);
        ArtworkCast artworkCast = artworkCastMapper.toEntity(artworkCastDTO);
        artworkCast = artworkCastRepository.save(artworkCast);
        return artworkCastMapper.toDto(artworkCast);
    }

    @Override
    public Optional<ArtworkCastDTO> partialUpdate(ArtworkCastDTO artworkCastDTO) {
        log.debug("Request to partially update ArtworkCast : {}", artworkCastDTO);

        return artworkCastRepository
            .findById(artworkCastDTO.getId())
            .map(existingArtworkCast -> {
                artworkCastMapper.partialUpdate(existingArtworkCast, artworkCastDTO);

                return existingArtworkCast;
            })
            .map(artworkCastRepository::save)
            .map(artworkCastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkCastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtworkCasts");
        return artworkCastRepository.findAll(pageable).map(artworkCastMapper::toDto);
    }

    public Page<ArtworkCastDTO> findAllWithEagerRelationships(Pageable pageable) {
        return artworkCastRepository.findAllWithEagerRelationships(pageable).map(artworkCastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkCastDTO> findOne(Long id) {
        log.debug("Request to get ArtworkCast : {}", id);
        return artworkCastRepository.findOneWithEagerRelationships(id).map(artworkCastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkCast : {}", id);
        artworkCastRepository.deleteById(id);
    }
}
