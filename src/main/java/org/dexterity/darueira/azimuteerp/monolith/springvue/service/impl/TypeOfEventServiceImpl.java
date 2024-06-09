package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfEventRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfEventService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfEventDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent}.
 */
@Service
@Transactional
public class TypeOfEventServiceImpl implements TypeOfEventService {

    private final Logger log = LoggerFactory.getLogger(TypeOfEventServiceImpl.class);

    private final TypeOfEventRepository typeOfEventRepository;

    private final TypeOfEventMapper typeOfEventMapper;

    public TypeOfEventServiceImpl(TypeOfEventRepository typeOfEventRepository, TypeOfEventMapper typeOfEventMapper) {
        this.typeOfEventRepository = typeOfEventRepository;
        this.typeOfEventMapper = typeOfEventMapper;
    }

    @Override
    public TypeOfEventDTO save(TypeOfEventDTO typeOfEventDTO) {
        log.debug("Request to save TypeOfEvent : {}", typeOfEventDTO);
        TypeOfEvent typeOfEvent = typeOfEventMapper.toEntity(typeOfEventDTO);
        typeOfEvent = typeOfEventRepository.save(typeOfEvent);
        return typeOfEventMapper.toDto(typeOfEvent);
    }

    @Override
    public TypeOfEventDTO update(TypeOfEventDTO typeOfEventDTO) {
        log.debug("Request to update TypeOfEvent : {}", typeOfEventDTO);
        TypeOfEvent typeOfEvent = typeOfEventMapper.toEntity(typeOfEventDTO);
        typeOfEvent = typeOfEventRepository.save(typeOfEvent);
        return typeOfEventMapper.toDto(typeOfEvent);
    }

    @Override
    public Optional<TypeOfEventDTO> partialUpdate(TypeOfEventDTO typeOfEventDTO) {
        log.debug("Request to partially update TypeOfEvent : {}", typeOfEventDTO);

        return typeOfEventRepository
            .findById(typeOfEventDTO.getId())
            .map(existingTypeOfEvent -> {
                typeOfEventMapper.partialUpdate(existingTypeOfEvent, typeOfEventDTO);

                return existingTypeOfEvent;
            })
            .map(typeOfEventRepository::save)
            .map(typeOfEventMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfEvents");
        return typeOfEventRepository.findAll(pageable).map(typeOfEventMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfEventDTO> findOne(Long id) {
        log.debug("Request to get TypeOfEvent : {}", id);
        return typeOfEventRepository.findById(id).map(typeOfEventMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfEvent : {}", id);
        typeOfEventRepository.deleteById(id);
    }
}
