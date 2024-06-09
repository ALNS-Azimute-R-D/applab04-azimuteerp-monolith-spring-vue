package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationMembershipRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationMembershipService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMembershipDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.OrganizationMembershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership}.
 */
@Service
@Transactional
public class OrganizationMembershipServiceImpl implements OrganizationMembershipService {

    private final Logger log = LoggerFactory.getLogger(OrganizationMembershipServiceImpl.class);

    private final OrganizationMembershipRepository organizationMembershipRepository;

    private final OrganizationMembershipMapper organizationMembershipMapper;

    public OrganizationMembershipServiceImpl(
        OrganizationMembershipRepository organizationMembershipRepository,
        OrganizationMembershipMapper organizationMembershipMapper
    ) {
        this.organizationMembershipRepository = organizationMembershipRepository;
        this.organizationMembershipMapper = organizationMembershipMapper;
    }

    @Override
    public OrganizationMembershipDTO save(OrganizationMembershipDTO organizationMembershipDTO) {
        log.debug("Request to save OrganizationMembership : {}", organizationMembershipDTO);
        OrganizationMembership organizationMembership = organizationMembershipMapper.toEntity(organizationMembershipDTO);
        organizationMembership = organizationMembershipRepository.save(organizationMembership);
        return organizationMembershipMapper.toDto(organizationMembership);
    }

    @Override
    public OrganizationMembershipDTO update(OrganizationMembershipDTO organizationMembershipDTO) {
        log.debug("Request to update OrganizationMembership : {}", organizationMembershipDTO);
        OrganizationMembership organizationMembership = organizationMembershipMapper.toEntity(organizationMembershipDTO);
        organizationMembership = organizationMembershipRepository.save(organizationMembership);
        return organizationMembershipMapper.toDto(organizationMembership);
    }

    @Override
    public Optional<OrganizationMembershipDTO> partialUpdate(OrganizationMembershipDTO organizationMembershipDTO) {
        log.debug("Request to partially update OrganizationMembership : {}", organizationMembershipDTO);

        return organizationMembershipRepository
            .findById(organizationMembershipDTO.getId())
            .map(existingOrganizationMembership -> {
                organizationMembershipMapper.partialUpdate(existingOrganizationMembership, organizationMembershipDTO);

                return existingOrganizationMembership;
            })
            .map(organizationMembershipRepository::save)
            .map(organizationMembershipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationMembershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationMemberships");
        return organizationMembershipRepository.findAll(pageable).map(organizationMembershipMapper::toDto);
    }

    public Page<OrganizationMembershipDTO> findAllWithEagerRelationships(Pageable pageable) {
        return organizationMembershipRepository.findAllWithEagerRelationships(pageable).map(organizationMembershipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganizationMembershipDTO> findOne(Long id) {
        log.debug("Request to get OrganizationMembership : {}", id);
        return organizationMembershipRepository.findOneWithEagerRelationships(id).map(organizationMembershipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganizationMembership : {}", id);
        organizationMembershipRepository.deleteById(id);
    }
}
