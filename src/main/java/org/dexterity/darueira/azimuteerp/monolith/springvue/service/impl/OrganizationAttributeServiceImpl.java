package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationAttributeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationAttributeService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationAttributeDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationAttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute}.
 */
@Service
@Transactional
public class OrganizationAttributeServiceImpl implements OrganizationAttributeService {

    private final Logger log = LoggerFactory.getLogger(OrganizationAttributeServiceImpl.class);

    private final OrganizationAttributeRepository organizationAttributeRepository;

    private final OrganizationAttributeMapper organizationAttributeMapper;

    public OrganizationAttributeServiceImpl(
        OrganizationAttributeRepository organizationAttributeRepository,
        OrganizationAttributeMapper organizationAttributeMapper
    ) {
        this.organizationAttributeRepository = organizationAttributeRepository;
        this.organizationAttributeMapper = organizationAttributeMapper;
    }

    @Override
    public OrganizationAttributeDTO save(OrganizationAttributeDTO organizationAttributeDTO) {
        log.debug("Request to save OrganizationAttribute : {}", organizationAttributeDTO);
        OrganizationAttribute organizationAttribute = organizationAttributeMapper.toEntity(organizationAttributeDTO);
        organizationAttribute = organizationAttributeRepository.save(organizationAttribute);
        return organizationAttributeMapper.toDto(organizationAttribute);
    }

    @Override
    public OrganizationAttributeDTO update(OrganizationAttributeDTO organizationAttributeDTO) {
        log.debug("Request to update OrganizationAttribute : {}", organizationAttributeDTO);
        OrganizationAttribute organizationAttribute = organizationAttributeMapper.toEntity(organizationAttributeDTO);
        organizationAttribute = organizationAttributeRepository.save(organizationAttribute);
        return organizationAttributeMapper.toDto(organizationAttribute);
    }

    @Override
    public Optional<OrganizationAttributeDTO> partialUpdate(OrganizationAttributeDTO organizationAttributeDTO) {
        log.debug("Request to partially update OrganizationAttribute : {}", organizationAttributeDTO);

        return organizationAttributeRepository
            .findById(organizationAttributeDTO.getId())
            .map(existingOrganizationAttribute -> {
                organizationAttributeMapper.partialUpdate(existingOrganizationAttribute, organizationAttributeDTO);

                return existingOrganizationAttribute;
            })
            .map(organizationAttributeRepository::save)
            .map(organizationAttributeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationAttributeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationAttributes");
        return organizationAttributeRepository.findAll(pageable).map(organizationAttributeMapper::toDto);
    }

    public Page<OrganizationAttributeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return organizationAttributeRepository.findAllWithEagerRelationships(pageable).map(organizationAttributeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationAttributeDTO> findOne(Long id) {
        log.debug("Request to get OrganizationAttribute : {}", id);
        return organizationAttributeRepository.findOneWithEagerRelationships(id).map(organizationAttributeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationAttribute : {}", id);
        organizationAttributeRepository.deleteById(id);
    }
}
