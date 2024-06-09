package org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper;

import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Organization;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Person;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMembershipDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganizationMembership} and its DTO {@link OrganizationMembershipDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganizationMembershipMapper extends EntityMapper<OrganizationMembershipDTO, OrganizationMembership> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationName")
    @Mapping(target = "person", source = "person", qualifiedByName = "personLastname")
    OrganizationMembershipDTO toDto(OrganizationMembership s);

    @Named("organizationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    OrganizationDTO toDtoOrganizationName(Organization organization);

    @Named("personLastname")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "lastname", source = "lastname")
    PersonDTO toDtoPersonLastname(Person person);
}
