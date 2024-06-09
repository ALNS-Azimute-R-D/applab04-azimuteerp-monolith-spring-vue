package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfArtistRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfArtistService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtistDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfArtistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist}.
 */
@Service
@Transactional
public class TypeOfArtistServiceImpl implements TypeOfArtistService {

    private final Logger log = LoggerFactory.getLogger(TypeOfArtistServiceImpl.class);

    private final TypeOfArtistRepository typeOfArtistRepository;

    private final TypeOfArtistMapper typeOfArtistMapper;

    public TypeOfArtistServiceImpl(TypeOfArtistRepository typeOfArtistRepository, TypeOfArtistMapper typeOfArtistMapper) {
        this.typeOfArtistRepository = typeOfArtistRepository;
        this.typeOfArtistMapper = typeOfArtistMapper;
    }

    @Override
    public TypeOfArtistDTO save(TypeOfArtistDTO typeOfArtistDTO) {
        log.debug("Request to save TypeOfArtist : {}", typeOfArtistDTO);
        TypeOfArtist typeOfArtist = typeOfArtistMapper.toEntity(typeOfArtistDTO);
        typeOfArtist = typeOfArtistRepository.save(typeOfArtist);
        return typeOfArtistMapper.toDto(typeOfArtist);
    }

    @Override
    public TypeOfArtistDTO update(TypeOfArtistDTO typeOfArtistDTO) {
        log.debug("Request to update TypeOfArtist : {}", typeOfArtistDTO);
        TypeOfArtist typeOfArtist = typeOfArtistMapper.toEntity(typeOfArtistDTO);
        typeOfArtist = typeOfArtistRepository.save(typeOfArtist);
        return typeOfArtistMapper.toDto(typeOfArtist);
    }

    @Override
    public Optional<TypeOfArtistDTO> partialUpdate(TypeOfArtistDTO typeOfArtistDTO) {
        log.debug("Request to partially update TypeOfArtist : {}", typeOfArtistDTO);

        return typeOfArtistRepository
            .findById(typeOfArtistDTO.getId())
            .map(existingTypeOfArtist -> {
                typeOfArtistMapper.partialUpdate(existingTypeOfArtist, typeOfArtistDTO);

                return existingTypeOfArtist;
            })
            .map(typeOfArtistRepository::save)
            .map(typeOfArtistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfArtistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfArtists");
        return typeOfArtistRepository.findAll(pageable).map(typeOfArtistMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfArtistDTO> findOne(Long id) {
        log.debug("Request to get TypeOfArtist : {}", id);
        return typeOfArtistRepository.findById(id).map(typeOfArtistMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfArtist : {}", id);
        typeOfArtistRepository.deleteById(id);
    }
}
