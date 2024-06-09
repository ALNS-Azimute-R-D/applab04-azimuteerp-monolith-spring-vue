package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationMemberRoleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationMemberRoleService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMemberRoleDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMemberRole}.
 */
@RestController
@RequestMapping("/api/organization-member-roles")
public class OrganizationMemberRoleResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationMemberRoleResource.class);

    private static final String ENTITY_NAME = "organizationMemberRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationMemberRoleService organizationMemberRoleService;

    private final OrganizationMemberRoleRepository organizationMemberRoleRepository;

    public OrganizationMemberRoleResource(
        OrganizationMemberRoleService organizationMemberRoleService,
        OrganizationMemberRoleRepository organizationMemberRoleRepository
    ) {
        this.organizationMemberRoleService = organizationMemberRoleService;
        this.organizationMemberRoleRepository = organizationMemberRoleRepository;
    }

    /**
     * {@code POST  /organization-member-roles} : Create a new organizationMemberRole.
     *
     * @param organizationMemberRoleDTO the organizationMemberRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationMemberRoleDTO, or with status {@code 400 (Bad Request)} if the organizationMemberRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationMemberRoleDTO> createOrganizationMemberRole(
        @Valid @RequestBody OrganizationMemberRoleDTO organizationMemberRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationMemberRole : {}", organizationMemberRoleDTO);
        if (organizationMemberRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationMemberRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationMemberRoleDTO = organizationMemberRoleService.save(organizationMemberRoleDTO);
        return ResponseEntity.created(new URI("/api/organization-member-roles/" + organizationMemberRoleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationMemberRoleDTO.getId().toString()))
            .body(organizationMemberRoleDTO);
    }

    /**
     * {@code PUT  /organization-member-roles/:id} : Updates an existing organizationMemberRole.
     *
     * @param id the id of the organizationMemberRoleDTO to save.
     * @param organizationMemberRoleDTO the organizationMemberRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationMemberRoleDTO,
     * or with status {@code 400 (Bad Request)} if the organizationMemberRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationMemberRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationMemberRoleDTO> updateOrganizationMemberRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationMemberRoleDTO organizationMemberRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationMemberRole : {}, {}", id, organizationMemberRoleDTO);
        if (organizationMemberRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationMemberRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationMemberRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationMemberRoleDTO = organizationMemberRoleService.update(organizationMemberRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationMemberRoleDTO.getId().toString()))
            .body(organizationMemberRoleDTO);
    }

    /**
     * {@code PATCH  /organization-member-roles/:id} : Partial updates given fields of an existing organizationMemberRole, field will ignore if it is null
     *
     * @param id the id of the organizationMemberRoleDTO to save.
     * @param organizationMemberRoleDTO the organizationMemberRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationMemberRoleDTO,
     * or with status {@code 400 (Bad Request)} if the organizationMemberRoleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationMemberRoleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationMemberRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationMemberRoleDTO> partialUpdateOrganizationMemberRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationMemberRoleDTO organizationMemberRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationMemberRole partially : {}, {}", id, organizationMemberRoleDTO);
        if (organizationMemberRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationMemberRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationMemberRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationMemberRoleDTO> result = organizationMemberRoleService.partialUpdate(organizationMemberRoleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationMemberRoleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-member-roles} : get all the organizationMemberRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationMemberRoles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrganizationMemberRoleDTO>> getAllOrganizationMemberRoles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of OrganizationMemberRoles");
        Page<OrganizationMemberRoleDTO> page = organizationMemberRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-member-roles/:id} : get the "id" organizationMemberRole.
     *
     * @param id the id of the organizationMemberRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationMemberRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationMemberRoleDTO> getOrganizationMemberRole(@PathVariable("id") Long id) {
        log.debug("REST request to get OrganizationMemberRole : {}", id);
        Optional<OrganizationMemberRoleDTO> organizationMemberRoleDTO = organizationMemberRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationMemberRoleDTO);
    }

    /**
     * {@code DELETE  /organization-member-roles/:id} : delete the "id" organizationMemberRole.
     *
     * @param id the id of the organizationMemberRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationMemberRole(@PathVariable("id") Long id) {
        log.debug("REST request to delete OrganizationMemberRole : {}", id);
        organizationMemberRoleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
