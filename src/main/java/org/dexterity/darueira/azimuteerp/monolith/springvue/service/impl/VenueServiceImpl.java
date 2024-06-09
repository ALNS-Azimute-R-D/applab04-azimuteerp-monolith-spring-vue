package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.VenueRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.VenueService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.VenueDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.VenueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Venue}.
 */
@Service
@Transactional
public class VenueServiceImpl implements VenueService {

    private final Logger log = LoggerFactory.getLogger(VenueServiceImpl.class);

    private final VenueRepository venueRepository;

    private final VenueMapper venueMapper;

    public VenueServiceImpl(VenueRepository venueRepository, VenueMapper venueMapper) {
        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
    }

    @Override
    public VenueDTO save(VenueDTO venueDTO) {
        log.debug("Request to save Venue : {}", venueDTO);
        Venue venue = venueMapper.toEntity(venueDTO);
        venue = venueRepository.save(venue);
        return venueMapper.toDto(venue);
    }

    @Override
    public VenueDTO update(VenueDTO venueDTO) {
        log.debug("Request to update Venue : {}", venueDTO);
        Venue venue = venueMapper.toEntity(venueDTO);
        venue = venueRepository.save(venue);
        return venueMapper.toDto(venue);
    }

    @Override
    public Optional<VenueDTO> partialUpdate(VenueDTO venueDTO) {
        log.debug("Request to partially update Venue : {}", venueDTO);

        return venueRepository
            .findById(venueDTO.getId())
            .map(existingVenue -> {
                venueMapper.partialUpdate(existingVenue, venueDTO);

                return existingVenue;
            })
            .map(venueRepository::save)
            .map(venueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VenueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Venues");
        return venueRepository.findAll(pageable).map(venueMapper::toDto);
    }

    public Page<VenueDTO> findAllWithEagerRelationships(Pageable pageable) {
        return venueRepository.findAllWithEagerRelationships(pageable).map(venueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VenueDTO> findOne(Long id) {
        log.debug("Request to get Venue : {}", id);
        return venueRepository.findOneWithEagerRelationships(id).map(venueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venue : {}", id);
        venueRepository.deleteById(id);
    }
}
