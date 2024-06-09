package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfOrganizationRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfOrganizationService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfOrganizationDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfOrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfOrganization}.
 */
@Service
@Transactional
public class TypeOfOrganizationServiceImpl implements TypeOfOrganizationService {

    private final Logger log = LoggerFactory.getLogger(TypeOfOrganizationServiceImpl.class);

    private final TypeOfOrganizationRepository typeOfOrganizationRepository;

    private final TypeOfOrganizationMapper typeOfOrganizationMapper;

    public TypeOfOrganizationServiceImpl(
        TypeOfOrganizationRepository typeOfOrganizationRepository,
        TypeOfOrganizationMapper typeOfOrganizationMapper
    ) {
        this.typeOfOrganizationRepository = typeOfOrganizationRepository;
        this.typeOfOrganizationMapper = typeOfOrganizationMapper;
    }

    @Override
    public TypeOfOrganizationDTO save(TypeOfOrganizationDTO typeOfOrganizationDTO) {
        log.debug("Request to save TypeOfOrganization : {}", typeOfOrganizationDTO);
        TypeOfOrganization typeOfOrganization = typeOfOrganizationMapper.toEntity(typeOfOrganizationDTO);
        typeOfOrganization = typeOfOrganizationRepository.save(typeOfOrganization);
        return typeOfOrganizationMapper.toDto(typeOfOrganization);
    }

    @Override
    public TypeOfOrganizationDTO update(TypeOfOrganizationDTO typeOfOrganizationDTO) {
        log.debug("Request to update TypeOfOrganization : {}", typeOfOrganizationDTO);
        TypeOfOrganization typeOfOrganization = typeOfOrganizationMapper.toEntity(typeOfOrganizationDTO);
        typeOfOrganization = typeOfOrganizationRepository.save(typeOfOrganization);
        return typeOfOrganizationMapper.toDto(typeOfOrganization);
    }

    @Override
    public Optional<TypeOfOrganizationDTO> partialUpdate(TypeOfOrganizationDTO typeOfOrganizationDTO) {
        log.debug("Request to partially update TypeOfOrganization : {}", typeOfOrganizationDTO);

        return typeOfOrganizationRepository
            .findById(typeOfOrganizationDTO.getId())
            .map(existingTypeOfOrganization -> {
                typeOfOrganizationMapper.partialUpdate(existingTypeOfOrganization, typeOfOrganizationDTO);

                return existingTypeOfOrganization;
            })
            .map(typeOfOrganizationRepository::save)
            .map(typeOfOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeOfOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeOfOrganizations");
        return typeOfOrganizationRepository.findAll(pageable).map(typeOfOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeOfOrganizationDTO> findOne(Long id) {
        log.debug("Request to get TypeOfOrganization : {}", id);
        return typeOfOrganizationRepository.findById(id).map(typeOfOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeOfOrganization : {}", id);
        typeOfOrganizationRepository.deleteById(id);
    }
}
