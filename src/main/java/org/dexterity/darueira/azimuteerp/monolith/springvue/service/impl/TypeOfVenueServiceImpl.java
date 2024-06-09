package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfVenueRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfVenueService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfVenueDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfVenueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue}.
 */
@Service
@Transactional
public class TypeOfVenueServiceImpl implements TypeOfVenueService {

    private final Logger log = LoggerFactory.getLogger(TypeOfVenueServiceImpl.class);

    private final TypeOfVenueRepository typeOfVenueRepository;

    private final TypeOfVenueMapper typeOfVenueMapper;

    public TypeOfVenueServiceImpl(TypeOfVenueRepository typeOfVenueRepository, TypeOfVenueMapper typeOfVenueMapper) {
        this.typeOfVenueRepository = typeOfVenueRepository;
        this.typeOfVenueMapper = typeOfVenueMapper;
    }

    @Override
    public TypeOfVenueDTO save(TypeOfVenueDTO typeOfVenueDTO) {
        log.debug("Request to save TypeOfVenue : {}", typeOfVenueDTO);
        TypeOfVenue typeOfVenue = typeOfVenueMapper.toEntity(typeOfVenueDTO);
        typeOfVenue = typeOfVenueRepository.save(typeOfVenue);
        return typeOfVenueMapper.toDto(typeOfVenue);
    }

    @Override
    public TypeOfVenueDTO update(TypeOfVenueDTO typeOfVenueDTO) {
        log.debug("Request to update TypeOfVenue : {}", typeOfVenueDTO);
        TypeOfVenue typeOfVenue = typeOfVenueMapper.toEntity(typeOfVenueDTO);
        typeOfVenue = typeOfVenueRepository.save(typeOfVenue);
        return typeOfVenueMapper.toDto(typeOfVenue);
    }

    @Override
    public Optional<TypeOfVenueDTO> partialUpdate(TypeOfVenueDTO typeOfVenueDTO) {
        log.debug("Request to partially update TypeOfVenue : {}", typeOfVenueDTO);

        return typeOfVenueRepository
            .findById(typeOfVenueDTO.getId())
            .map(existingTypeOfVenue -> {
                typeOfVenueMapper.partialUpdate(existingTypeOfVenue, typeOfVenueDTO);

                return existingTypeOfVenue;
            })
            .map(typeOfVenueRepository::save)
            .map(typeOfVenueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfVenueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfVenues");
        return typeOfVenueRepository.findAll(pageable).map(typeOfVenueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfVenueDTO> findOne(Long id) {
        log.debug("Request to get TypeOfVenue : {}", id);
        return typeOfVenueRepository.findById(id).map(typeOfVenueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfVenue : {}", id);
        typeOfVenueRepository.deleteById(id);
    }
}
