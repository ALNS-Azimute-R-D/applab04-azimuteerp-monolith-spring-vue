package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity}.
 */
@Service
@Transactional
public class TypeOfActivityServiceImpl implements TypeOfActivityService {

    private final Logger log = LoggerFactory.getLogger(TypeOfActivityServiceImpl.class);

    private final TypeOfActivityRepository typeOfActivityRepository;

    private final TypeOfActivityMapper typeOfActivityMapper;

    public TypeOfActivityServiceImpl(TypeOfActivityRepository typeOfActivityRepository, TypeOfActivityMapper typeOfActivityMapper) {
        this.typeOfActivityRepository = typeOfActivityRepository;
        this.typeOfActivityMapper = typeOfActivityMapper;
    }

    @Override
    public TypeOfActivityDTO save(TypeOfActivityDTO typeOfActivityDTO) {
        log.debug("Request to save TypeOfActivity : {}", typeOfActivityDTO);
        TypeOfActivity typeOfActivity = typeOfActivityMapper.toEntity(typeOfActivityDTO);
        typeOfActivity = typeOfActivityRepository.save(typeOfActivity);
        return typeOfActivityMapper.toDto(typeOfActivity);
    }

    @Override
    public TypeOfActivityDTO update(TypeOfActivityDTO typeOfActivityDTO) {
        log.debug("Request to update TypeOfActivity : {}", typeOfActivityDTO);
        TypeOfActivity typeOfActivity = typeOfActivityMapper.toEntity(typeOfActivityDTO);
        typeOfActivity = typeOfActivityRepository.save(typeOfActivity);
        return typeOfActivityMapper.toDto(typeOfActivity);
    }

    @Override
    public Optional<TypeOfActivityDTO> partialUpdate(TypeOfActivityDTO typeOfActivityDTO) {
        log.debug("Request to partially update TypeOfActivity : {}", typeOfActivityDTO);

        return typeOfActivityRepository
            .findById(typeOfActivityDTO.getId())
            .map(existingTypeOfActivity -> {
                typeOfActivityMapper.partialUpdate(existingTypeOfActivity, typeOfActivityDTO);

                return existingTypeOfActivity;
            })
            .map(typeOfActivityRepository::save)
            .map(typeOfActivityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfActivities");
        return typeOfActivityRepository.findAll(pageable).map(typeOfActivityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfActivityDTO> findOne(Long id) {
        log.debug("Request to get TypeOfActivity : {}", id);
        return typeOfActivityRepository.findById(id).map(typeOfActivityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfActivity : {}", id);
        typeOfActivityRepository.deleteById(id);
    }
}
