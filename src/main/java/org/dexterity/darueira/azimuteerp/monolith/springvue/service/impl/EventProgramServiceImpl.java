package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.EventProgramRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.EventProgramService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.EventProgramDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.EventProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram}.
 */
@Service
@Transactional
public class EventProgramServiceImpl implements EventProgramService {

    private final Logger log = LoggerFactory.getLogger(EventProgramServiceImpl.class);

    private final EventProgramRepository eventProgramRepository;

    private final EventProgramMapper eventProgramMapper;

    public EventProgramServiceImpl(EventProgramRepository eventProgramRepository, EventProgramMapper eventProgramMapper) {
        this.eventProgramRepository = eventProgramRepository;
        this.eventProgramMapper = eventProgramMapper;
    }

    @Override
    public EventProgramDTO save(EventProgramDTO eventProgramDTO) {
        log.debug("Request to save EventProgram : {}", eventProgramDTO);
        EventProgram eventProgram = eventProgramMapper.toEntity(eventProgramDTO);
        eventProgram = eventProgramRepository.save(eventProgram);
        return eventProgramMapper.toDto(eventProgram);
    }

    @Override
    public EventProgramDTO update(EventProgramDTO eventProgramDTO) {
        log.debug("Request to update EventProgram : {}", eventProgramDTO);
        EventProgram eventProgram = eventProgramMapper.toEntity(eventProgramDTO);
        eventProgram = eventProgramRepository.save(eventProgram);
        return eventProgramMapper.toDto(eventProgram);
    }

    @Override
    public Optional<EventProgramDTO> partialUpdate(EventProgramDTO eventProgramDTO) {
        log.debug("Request to partially update EventProgram : {}", eventProgramDTO);

        return eventProgramRepository
            .findById(eventProgramDTO.getId())
            .map(existingEventProgram -> {
                eventProgramMapper.partialUpdate(existingEventProgram, eventProgramDTO);

                return existingEventProgram;
            })
            .map(eventProgramRepository::save)
            .map(eventProgramMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventProgramDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventPrograms");
        return eventProgramRepository.findAll(pageable).map(eventProgramMapper::toDto);
    }

    public Page<EventProgramDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventProgramRepository.findAllWithEagerRelationships(pageable).map(eventProgramMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventProgramDTO> findOne(Long id) {
        log.debug("Request to get EventProgram : {}", id);
        return eventProgramRepository.findOneWithEagerRelationships(id).map(eventProgramMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventProgram : {}", id);
        eventProgramRepository.deleteById(id);
    }
}
