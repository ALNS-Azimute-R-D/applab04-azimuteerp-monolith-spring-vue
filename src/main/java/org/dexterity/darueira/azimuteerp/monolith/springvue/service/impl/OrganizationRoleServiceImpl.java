package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationRoleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationRoleService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationRoleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole}.
 */
@Service
@Transactional
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

    private final Logger log = LoggerFactory.getLogger(OrganizationRoleServiceImpl.class);

    private final OrganizationRoleRepository organizationRoleRepository;

    private final OrganizationRoleMapper organizationRoleMapper;

    public OrganizationRoleServiceImpl(
        OrganizationRoleRepository organizationRoleRepository,
        OrganizationRoleMapper organizationRoleMapper
    ) {
        this.organizationRoleRepository = organizationRoleRepository;
        this.organizationRoleMapper = organizationRoleMapper;
    }

    @Override
    public OrganizationRoleDTO save(OrganizationRoleDTO organizationRoleDTO) {
        log.debug("Request to save OrganizationRole : {}", organizationRoleDTO);
        OrganizationRole organizationRole = organizationRoleMapper.toEntity(organizationRoleDTO);
        organizationRole = organizationRoleRepository.save(organizationRole);
        return organizationRoleMapper.toDto(organizationRole);
    }

    @Override
    public OrganizationRoleDTO update(OrganizationRoleDTO organizationRoleDTO) {
        log.debug("Request to update OrganizationRole : {}", organizationRoleDTO);
        OrganizationRole organizationRole = organizationRoleMapper.toEntity(organizationRoleDTO);
        organizationRole = organizationRoleRepository.save(organizationRole);
        return organizationRoleMapper.toDto(organizationRole);
    }

    @Override
    public Optional<OrganizationRoleDTO> partialUpdate(OrganizationRoleDTO organizationRoleDTO) {
        log.debug("Request to partially update OrganizationRole : {}", organizationRoleDTO);

        return organizationRoleRepository
            .findById(organizationRoleDTO.getId())
            .map(existingOrganizationRole -> {
                organizationRoleMapper.partialUpdate(existingOrganizationRole, organizationRoleDTO);

                return existingOrganizationRole;
            })
            .map(organizationRoleRepository::save)
            .map(organizationRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationRoles");
        return organizationRoleRepository.findAll(pageable).map(organizationRoleMapper::toDto);
    }

    public Page<OrganizationRoleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return organizationRoleRepository.findAllWithEagerRelationships(pageable).map(organizationRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationRoleDTO> findOne(Long id) {
        log.debug("Request to get OrganizationRole : {}", id);
        return organizationRoleRepository.findOneWithEagerRelationships(id).map(organizationRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationRole : {}", id);
        organizationRoleRepository.deleteById(id);
    }
}
