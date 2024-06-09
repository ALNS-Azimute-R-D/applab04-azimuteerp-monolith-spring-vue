package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TenantRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TenantService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TenantDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TenantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Tenant}.
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    private final Logger log = LoggerFactory.getLogger(TenantServiceImpl.class);

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    @Override
    public TenantDTO save(TenantDTO tenantDTO) {
        log.debug("Request to save Tenant : {}", tenantDTO);
        Tenant tenant = tenantMapper.toEntity(tenantDTO);
        tenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }

    @Override
    public TenantDTO update(TenantDTO tenantDTO) {
        log.debug("Request to update Tenant : {}", tenantDTO);
        Tenant tenant = tenantMapper.toEntity(tenantDTO);
        tenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }

    @Override
    public Optional<TenantDTO> partialUpdate(TenantDTO tenantDTO) {
        log.debug("Request to partially update Tenant : {}", tenantDTO);

        return tenantRepository
            .findById(tenantDTO.getId())
            .map(existingTenant -> {
                tenantMapper.partialUpdate(existingTenant, tenantDTO);

                return existingTenant;
            })
            .map(tenantRepository::save)
            .map(tenantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tenants");
        return tenantRepository.findAll(pageable).map(tenantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantDTO> findOne(Long id) {
        log.debug("Request to get Tenant : {}", id);
        return tenantRepository.findById(id).map(tenantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tenant : {}", id);
        tenantRepository.deleteById(id);
    }
}
