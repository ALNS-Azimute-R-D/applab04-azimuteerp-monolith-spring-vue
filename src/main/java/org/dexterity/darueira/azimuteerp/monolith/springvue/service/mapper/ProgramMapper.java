package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProgramDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Program} and its DTO {@link ProgramDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProgramMapper extends EntityMapper<ProgramDTO, Program> {}
