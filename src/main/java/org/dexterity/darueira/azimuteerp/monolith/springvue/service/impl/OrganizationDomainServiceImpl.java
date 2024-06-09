package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationDomainRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationDomainService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDomainDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationDomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain}.
 */
@Service
@Transactional
public class OrganizationDomainServiceImpl implements OrganizationDomainService {

    private final Logger log = LoggerFactory.getLogger(OrganizationDomainServiceImpl.class);

    private final OrganizationDomainRepository organizationDomainRepository;

    private final OrganizationDomainMapper organizationDomainMapper;

    public OrganizationDomainServiceImpl(
        OrganizationDomainRepository organizationDomainRepository,
        OrganizationDomainMapper organizationDomainMapper
    ) {
        this.organizationDomainRepository = organizationDomainRepository;
        this.organizationDomainMapper = organizationDomainMapper;
    }

    @Override
    public OrganizationDomainDTO save(OrganizationDomainDTO organizationDomainDTO) {
        log.debug("Request to save OrganizationDomain : {}", organizationDomainDTO);
        OrganizationDomain organizationDomain = organizationDomainMapper.toEntity(organizationDomainDTO);
        organizationDomain = organizationDomainRepository.save(organizationDomain);
        return organizationDomainMapper.toDto(organizationDomain);
    }

    @Override
    public OrganizationDomainDTO update(OrganizationDomainDTO organizationDomainDTO) {
        log.debug("Request to update OrganizationDomain : {}", organizationDomainDTO);
        OrganizationDomain organizationDomain = organizationDomainMapper.toEntity(organizationDomainDTO);
        organizationDomain = organizationDomainRepository.save(organizationDomain);
        return organizationDomainMapper.toDto(organizationDomain);
    }

    @Override
    public Optional<OrganizationDomainDTO> partialUpdate(OrganizationDomainDTO organizationDomainDTO) {
        log.debug("Request to partially update OrganizationDomain : {}", organizationDomainDTO);

        return organizationDomainRepository
            .findById(organizationDomainDTO.getId())
            .map(existingOrganizationDomain -> {
                organizationDomainMapper.partialUpdate(existingOrganizationDomain, organizationDomainDTO);

                return existingOrganizationDomain;
            })
            .map(organizationDomainRepository::save)
            .map(organizationDomainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationDomainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationDomains");
        return organizationDomainRepository.findAll(pageable).map(organizationDomainMapper::toDto);
    }

    public Page<OrganizationDomainDTO> findAllWithEagerRelationships(Pageable pageable) {
        return organizationDomainRepository.findAllWithEagerRelationships(pageable).map(organizationDomainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationDomainDTO> findOne(Long id) {
        log.debug("Request to get OrganizationDomain : {}", id);
        return organizationDomainRepository.findOneWithEagerRelationships(id).map(organizationDomainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationDomain : {}", id);
        organizationDomainRepository.deleteById(id);
    }
}
