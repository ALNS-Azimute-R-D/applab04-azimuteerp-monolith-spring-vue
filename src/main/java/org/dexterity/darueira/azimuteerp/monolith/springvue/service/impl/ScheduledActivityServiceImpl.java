package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ScheduledActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ScheduledActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ScheduledActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ScheduledActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity}.
 */
@Service
@Transactional
public class ScheduledActivityServiceImpl implements ScheduledActivityService {

    private final Logger log = LoggerFactory.getLogger(ScheduledActivityServiceImpl.class);

    private final ScheduledActivityRepository scheduledActivityRepository;

    private final ScheduledActivityMapper scheduledActivityMapper;

    public ScheduledActivityServiceImpl(
        ScheduledActivityRepository scheduledActivityRepository,
        ScheduledActivityMapper scheduledActivityMapper
    ) {
        this.scheduledActivityRepository = scheduledActivityRepository;
        this.scheduledActivityMapper = scheduledActivityMapper;
    }

    @Override
    public ScheduledActivityDTO save(ScheduledActivityDTO scheduledActivityDTO) {
        log.debug("Request to save ScheduledActivity : {}", scheduledActivityDTO);
        ScheduledActivity scheduledActivity = scheduledActivityMapper.toEntity(scheduledActivityDTO);
        scheduledActivity = scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivityMapper.toDto(scheduledActivity);
    }

    @Override
    public ScheduledActivityDTO update(ScheduledActivityDTO scheduledActivityDTO) {
        log.debug("Request to update ScheduledActivity : {}", scheduledActivityDTO);
        ScheduledActivity scheduledActivity = scheduledActivityMapper.toEntity(scheduledActivityDTO);
        scheduledActivity = scheduledActivityRepository.save(scheduledActivity);
        return scheduledActivityMapper.toDto(scheduledActivity);
    }

    @Override
    public Optional<ScheduledActivityDTO> partialUpdate(ScheduledActivityDTO scheduledActivityDTO) {
        log.debug("Request to partially update ScheduledActivity : {}", scheduledActivityDTO);

        return scheduledActivityRepository
            .findById(scheduledActivityDTO.getId())
            .map(existingScheduledActivity -> {
                scheduledActivityMapper.partialUpdate(existingScheduledActivity, scheduledActivityDTO);

                return existingScheduledActivity;
            })
            .map(scheduledActivityRepository::save)
            .map(scheduledActivityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduledActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduledActivities");
        return scheduledActivityRepository.findAll(pageable).map(scheduledActivityMapper::toDto);
    }

    public Page<ScheduledActivityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return scheduledActivityRepository.findAllWithEagerRelationships(pageable).map(scheduledActivityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduledActivityDTO> findOne(Long id) {
        log.debug("Request to get ScheduledActivity : {}", id);
        return scheduledActivityRepository.findOneWithEagerRelationships(id).map(scheduledActivityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduledActivity : {}", id);
        scheduledActivityRepository.deleteById(id);
    }
}
