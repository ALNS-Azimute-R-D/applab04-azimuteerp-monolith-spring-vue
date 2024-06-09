package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtistRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtistService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist}.
 */
@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private final Logger log = LoggerFactory.getLogger(ArtistServiceImpl.class);

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Override
    public ArtistDTO save(ArtistDTO artistDTO) {
        log.debug("Request to save Artist : {}", artistDTO);
        Artist artist = artistMapper.toEntity(artistDTO);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    @Override
    public ArtistDTO update(ArtistDTO artistDTO) {
        log.debug("Request to update Artist : {}", artistDTO);
        Artist artist = artistMapper.toEntity(artistDTO);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    @Override
    public Optional<ArtistDTO> partialUpdate(ArtistDTO artistDTO) {
        log.debug("Request to partially update Artist : {}", artistDTO);

        return artistRepository
            .findById(artistDTO.getId())
            .map(existingArtist -> {
                artistMapper.partialUpdate(existingArtist, artistDTO);

                return existingArtist;
            })
            .map(artistRepository::save)
            .map(artistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artists");
        return artistRepository.findAll(pageable).map(artistMapper::toDto);
    }

    public Page<ArtistDTO> findAllWithEagerRelationships(Pageable pageable) {
        return artistRepository.findAllWithEagerRelationships(pageable).map(artistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtistDTO> findOne(Long id) {
        log.debug("Request to get Artist : {}", id);
        return artistRepository.findOneWithEagerRelationships(id).map(artistMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artist : {}", id);
        artistRepository.deleteById(id);
    }
}
