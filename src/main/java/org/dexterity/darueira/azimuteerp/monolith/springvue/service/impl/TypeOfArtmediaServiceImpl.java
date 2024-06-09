package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfArtmediaRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfArtmediaService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtmediaDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfArtmediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia}.
 */
@Service
@Transactional
public class TypeOfArtmediaServiceImpl implements TypeOfArtmediaService {

    private final Logger log = LoggerFactory.getLogger(TypeOfArtmediaServiceImpl.class);

    private final TypeOfArtmediaRepository typeOfArtmediaRepository;

    private final TypeOfArtmediaMapper typeOfArtmediaMapper;

    public TypeOfArtmediaServiceImpl(TypeOfArtmediaRepository typeOfArtmediaRepository, TypeOfArtmediaMapper typeOfArtmediaMapper) {
        this.typeOfArtmediaRepository = typeOfArtmediaRepository;
        this.typeOfArtmediaMapper = typeOfArtmediaMapper;
    }

    @Override
    public TypeOfArtmediaDTO save(TypeOfArtmediaDTO typeOfArtmediaDTO) {
        log.debug("Request to save TypeOfArtmedia : {}", typeOfArtmediaDTO);
        TypeOfArtmedia typeOfArtmedia = typeOfArtmediaMapper.toEntity(typeOfArtmediaDTO);
        typeOfArtmedia = typeOfArtmediaRepository.save(typeOfArtmedia);
        return typeOfArtmediaMapper.toDto(typeOfArtmedia);
    }

    @Override
    public TypeOfArtmediaDTO update(TypeOfArtmediaDTO typeOfArtmediaDTO) {
        log.debug("Request to update TypeOfArtmedia : {}", typeOfArtmediaDTO);
        TypeOfArtmedia typeOfArtmedia = typeOfArtmediaMapper.toEntity(typeOfArtmediaDTO);
        typeOfArtmedia = typeOfArtmediaRepository.save(typeOfArtmedia);
        return typeOfArtmediaMapper.toDto(typeOfArtmedia);
    }

    @Override
    public Optional<TypeOfArtmediaDTO> partialUpdate(TypeOfArtmediaDTO typeOfArtmediaDTO) {
        log.debug("Request to partially update TypeOfArtmedia : {}", typeOfArtmediaDTO);

        return typeOfArtmediaRepository
            .findById(typeOfArtmediaDTO.getId())
            .map(existingTypeOfArtmedia -> {
                typeOfArtmediaMapper.partialUpdate(existingTypeOfArtmedia, typeOfArtmediaDTO);

                return existingTypeOfArtmedia;
            })
            .map(typeOfArtmediaRepository::save)
            .map(typeOfArtmediaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfArtmediaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfArtmedias");
        return typeOfArtmediaRepository.findAll(pageable).map(typeOfArtmediaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfArtmediaDTO> findOne(Long id) {
        log.debug("Request to get TypeOfArtmedia : {}", id);
        return typeOfArtmediaRepository.findById(id).map(typeOfArtmediaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfArtmedia : {}", id);
        typeOfArtmediaRepository.deleteById(id);
    }
}
