package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.CommonLocalityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.CommonLocalityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CommonLocalityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.CommonLocalityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality}.
 */
@Service
@Transactional
public class CommonLocalityServiceImpl implements CommonLocalityService {

    private final Logger log = LoggerFactory.getLogger(CommonLocalityServiceImpl.class);

    private final CommonLocalityRepository commonLocalityRepository;

    private final CommonLocalityMapper commonLocalityMapper;

    public CommonLocalityServiceImpl(CommonLocalityRepository commonLocalityRepository, CommonLocalityMapper commonLocalityMapper) {
        this.commonLocalityRepository = commonLocalityRepository;
        this.commonLocalityMapper = commonLocalityMapper;
    }

    @Override
    public CommonLocalityDTO save(CommonLocalityDTO commonLocalityDTO) {
        log.debug("Request to save CommonLocality : {}", commonLocalityDTO);
        CommonLocality commonLocality = commonLocalityMapper.toEntity(commonLocalityDTO);
        commonLocality = commonLocalityRepository.save(commonLocality);
        return commonLocalityMapper.toDto(commonLocality);
    }

    @Override
    public CommonLocalityDTO update(CommonLocalityDTO commonLocalityDTO) {
        log.debug("Request to update CommonLocality : {}", commonLocalityDTO);
        CommonLocality commonLocality = commonLocalityMapper.toEntity(commonLocalityDTO);
        commonLocality = commonLocalityRepository.save(commonLocality);
        return commonLocalityMapper.toDto(commonLocality);
    }

    @Override
    public Optional<CommonLocalityDTO> partialUpdate(CommonLocalityDTO commonLocalityDTO) {
        log.debug("Request to partially update CommonLocality : {}", commonLocalityDTO);

        return commonLocalityRepository
            .findById(commonLocalityDTO.getId())
            .map(existingCommonLocality -> {
                commonLocalityMapper.partialUpdate(existingCommonLocality, commonLocalityDTO);

                return existingCommonLocality;
            })
            .map(commonLocalityRepository::save)
            .map(commonLocalityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommonLocalityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonLocalities");
        return commonLocalityRepository.findAll(pageable).map(commonLocalityMapper::toDto);
    }

    public Page<CommonLocalityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return commonLocalityRepository.findAllWithEagerRelationships(pageable).map(commonLocalityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommonLocalityDTO> findOne(Long id) {
        log.debug("Request to get CommonLocality : {}", id);
        return commonLocalityRepository.findOneWithEagerRelationships(id).map(commonLocalityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommonLocality : {}", id);
        commonLocalityRepository.deleteById(id);
    }
}
