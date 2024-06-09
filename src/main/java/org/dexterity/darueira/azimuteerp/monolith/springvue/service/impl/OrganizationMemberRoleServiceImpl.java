package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationMemberRoleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationMemberRoleService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMemberRoleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationMemberRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole}.
 */
@Service
@Transactional
public class OrganizationMemberRoleServiceImpl implements OrganizationMemberRoleService {

    private final Logger log = LoggerFactory.getLogger(OrganizationMemberRoleServiceImpl.class);

    private final OrganizationMemberRoleRepository organizationMemberRoleRepository;

    private final OrganizationMemberRoleMapper organizationMemberRoleMapper;

    public OrganizationMemberRoleServiceImpl(
        OrganizationMemberRoleRepository organizationMemberRoleRepository,
        OrganizationMemberRoleMapper organizationMemberRoleMapper
    ) {
        this.organizationMemberRoleRepository = organizationMemberRoleRepository;
        this.organizationMemberRoleMapper = organizationMemberRoleMapper;
    }

    @Override
    public OrganizationMemberRoleDTO save(OrganizationMemberRoleDTO organizationMemberRoleDTO) {
        log.debug("Request to save OrganizationMemberRole : {}", organizationMemberRoleDTO);
        OrganizationMemberRole organizationMemberRole = organizationMemberRoleMapper.toEntity(organizationMemberRoleDTO);
        organizationMemberRole = organizationMemberRoleRepository.save(organizationMemberRole);
        return organizationMemberRoleMapper.toDto(organizationMemberRole);
    }

    @Override
    public OrganizationMemberRoleDTO update(OrganizationMemberRoleDTO organizationMemberRoleDTO) {
        log.debug("Request to update OrganizationMemberRole : {}", organizationMemberRoleDTO);
        OrganizationMemberRole organizationMemberRole = organizationMemberRoleMapper.toEntity(organizationMemberRoleDTO);
        organizationMemberRole = organizationMemberRoleRepository.save(organizationMemberRole);
        return organizationMemberRoleMapper.toDto(organizationMemberRole);
    }

    @Override
    public Optional<OrganizationMemberRoleDTO> partialUpdate(OrganizationMemberRoleDTO organizationMemberRoleDTO) {
        log.debug("Request to partially update OrganizationMemberRole : {}", organizationMemberRoleDTO);

        return organizationMemberRoleRepository
            .findById(organizationMemberRoleDTO.getId())
            .map(existingOrganizationMemberRole -> {
                organizationMemberRoleMapper.partialUpdate(existingOrganizationMemberRole, organizationMemberRoleDTO);

                return existingOrganizationMemberRole;
            })
            .map(organizationMemberRoleRepository::save)
            .map(organizationMemberRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationMemberRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationMemberRoles");
        return organizationMemberRoleRepository.findAll(pageable).map(organizationMemberRoleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationMemberRoleDTO> findOne(Long id) {
        log.debug("Request to get OrganizationMemberRole : {}", id);
        return organizationMemberRoleRepository.findById(id).map(organizationMemberRoleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationMemberRole : {}", id);
        organizationMemberRoleRepository.deleteById(id);
    }
}
