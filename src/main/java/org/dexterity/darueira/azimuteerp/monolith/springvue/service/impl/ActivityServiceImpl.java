package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity}.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityDTO save(ActivityDTO activityDTO) {
        log.debug("Request to save Activity : {}", activityDTO);
        Activity activity = activityMapper.toEntity(activityDTO);
        activity = activityRepository.save(activity);
        return activityMapper.toDto(activity);
    }

    @Override
    public ActivityDTO update(ActivityDTO activityDTO) {
        log.debug("Request to update Activity : {}", activityDTO);
        Activity activity = activityMapper.toEntity(activityDTO);
        activity = activityRepository.save(activity);
        return activityMapper.toDto(activity);
    }

    @Override
    public Optional<ActivityDTO> partialUpdate(ActivityDTO activityDTO) {
        log.debug("Request to partially update Activity : {}", activityDTO);

        return activityRepository
            .findById(activityDTO.getId())
            .map(existingActivity -> {
                activityMapper.partialUpdate(existingActivity, activityDTO);

                return existingActivity;
            })
            .map(activityRepository::save)
            .map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Activities");
        return activityRepository.findAll(pageable).map(activityMapper::toDto);
    }

    public Page<ActivityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return activityRepository.findAllWithEagerRelationships(pageable).map(activityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityDTO> findOne(Long id) {
        log.debug("Request to get Activity : {}", id);
        return activityRepository.findOneWithEagerRelationships(id).map(activityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Activity : {}", id);
        activityRepository.deleteById(id);
    }
}
