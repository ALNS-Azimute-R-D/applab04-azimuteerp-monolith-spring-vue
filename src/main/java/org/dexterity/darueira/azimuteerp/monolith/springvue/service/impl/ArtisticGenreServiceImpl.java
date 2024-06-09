package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtisticGenreRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtisticGenreService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtisticGenreDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ArtisticGenreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre}.
 */
@Service
@Transactional
public class ArtisticGenreServiceImpl implements ArtisticGenreService {

    private final Logger log = LoggerFactory.getLogger(ArtisticGenreServiceImpl.class);

    private final ArtisticGenreRepository artisticGenreRepository;

    private final ArtisticGenreMapper artisticGenreMapper;

    public ArtisticGenreServiceImpl(ArtisticGenreRepository artisticGenreRepository, ArtisticGenreMapper artisticGenreMapper) {
        this.artisticGenreRepository = artisticGenreRepository;
        this.artisticGenreMapper = artisticGenreMapper;
    }

    @Override
    public ArtisticGenreDTO save(ArtisticGenreDTO artisticGenreDTO) {
        log.debug("Request to save ArtisticGenre : {}", artisticGenreDTO);
        ArtisticGenre artisticGenre = artisticGenreMapper.toEntity(artisticGenreDTO);
        artisticGenre = artisticGenreRepository.save(artisticGenre);
        return artisticGenreMapper.toDto(artisticGenre);
    }

    @Override
    public ArtisticGenreDTO update(ArtisticGenreDTO artisticGenreDTO) {
        log.debug("Request to update ArtisticGenre : {}", artisticGenreDTO);
        ArtisticGenre artisticGenre = artisticGenreMapper.toEntity(artisticGenreDTO);
        artisticGenre = artisticGenreRepository.save(artisticGenre);
        return artisticGenreMapper.toDto(artisticGenre);
    }

    @Override
    public Optional<ArtisticGenreDTO> partialUpdate(ArtisticGenreDTO artisticGenreDTO) {
        log.debug("Request to partially update ArtisticGenre : {}", artisticGenreDTO);

        return artisticGenreRepository
            .findById(artisticGenreDTO.getId())
            .map(existingArtisticGenre -> {
                artisticGenreMapper.partialUpdate(existingArtisticGenre, artisticGenreDTO);

                return existingArtisticGenre;
            })
            .map(artisticGenreRepository::save)
            .map(artisticGenreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtisticGenreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtisticGenres");
        return artisticGenreRepository.findAll(pageable).map(artisticGenreMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtisticGenreDTO> findOne(Long id) {
        log.debug("Request to get ArtisticGenre : {}", id);
        return artisticGenreRepository.findById(id).map(artisticGenreMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtisticGenre : {}", id);
        artisticGenreRepository.deleteById(id);
    }
}
