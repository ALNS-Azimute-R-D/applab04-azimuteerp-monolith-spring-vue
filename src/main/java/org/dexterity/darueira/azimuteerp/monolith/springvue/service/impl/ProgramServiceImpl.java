package org.dexterity.darueira.azimuteerp.monolith.springvue.service.impl;

import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ProgramRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ProgramService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProgramDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program}.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
    }

    @Override
    public ProgramDTO save(ProgramDTO programDTO) {
        log.debug("Request to save Program : {}", programDTO);
        Program program = programMapper.toEntity(programDTO);
        program = programRepository.save(program);
        return programMapper.toDto(program);
    }

    @Override
    public ProgramDTO update(ProgramDTO programDTO) {
        log.debug("Request to update Program : {}", programDTO);
        Program program = programMapper.toEntity(programDTO);
        program = programRepository.save(program);
        return programMapper.toDto(program);
    }

    @Override
    public Optional<ProgramDTO> partialUpdate(ProgramDTO programDTO) {
        log.debug("Request to partially update Program : {}", programDTO);

        return programRepository
            .findById(programDTO.getId())
            .map(existingProgram -> {
                programMapper.partialUpdate(existingProgram, programDTO);

                return existingProgram;
            })
            .map(programRepository::save)
            .map(programMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable).map(programMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramDTO> findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findById(id).map(programMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.deleteById(id);
    }
}
