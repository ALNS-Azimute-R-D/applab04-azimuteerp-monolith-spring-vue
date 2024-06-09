package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationRoleRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationRoleService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationRoleDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationRole}.
 */
@RestController
@RequestMapping("/api/organization-roles")
public class OrganizationRoleResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationRoleResource.class);

    private static final String ENTITY_NAME = "organizationRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationRoleService organizationRoleService;

    private final OrganizationRoleRepository organizationRoleRepository;

    public OrganizationRoleResource(
        OrganizationRoleService organizationRoleService,
        OrganizationRoleRepository organizationRoleRepository
    ) {
        this.organizationRoleService = organizationRoleService;
        this.organizationRoleRepository = organizationRoleRepository;
    }

    /**
     * {@code POST  /organization-roles} : Create a new organizationRole.
     *
     * @param organizationRoleDTO the organizationRoleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationRoleDTO, or with status {@code 400 (Bad Request)} if the organizationRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationRoleDTO> createOrganizationRole(@Valid @RequestBody OrganizationRoleDTO organizationRoleDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrganizationRole : {}", organizationRoleDTO);
        if (organizationRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationRoleDTO = organizationRoleService.save(organizationRoleDTO);
        return ResponseEntity.created(new URI("/api/organization-roles/" + organizationRoleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationRoleDTO.getId().toString()))
            .body(organizationRoleDTO);
    }

    /**
     * {@code PUT  /organization-roles/:id} : Updates an existing organizationRole.
     *
     * @param id the id of the organizationRoleDTO to save.
     * @param organizationRoleDTO the organizationRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationRoleDTO,
     * or with status {@code 400 (Bad Request)} if the organizationRoleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationRoleDTO> updateOrganizationRole(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationRoleDTO organizationRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationRole : {}, {}", id, organizationRoleDTO);
        if (organizationRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationRoleDTO = organizationRoleService.update(organizationRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationRoleDTO.getId().toString()))
            .body(organizationRoleDTO);
    }

    /**
     * {@code PATCH  /organization-roles/:id} : Partial updates given fields of an existing organizationRole, field will ignore if it is null
     *
     * @param id the id of the organizationRoleDTO to save.
     * @param organizationRoleDTO the organizationRoleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationRoleDTO,
     * or with status {@code 400 (Bad Request)} if the organizationRoleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationRoleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationRoleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationRoleDTO> partialUpdateOrganizationRole(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationRoleDTO organizationRoleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationRole partially : {}, {}", id, organizationRoleDTO);
        if (organizationRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationRoleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationRoleDTO> result = organizationRoleService.partialUpdate(organizationRoleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationRoleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-roles} : get all the organizationRoles.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationRoles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrganizationRoleDTO>> getAllOrganizationRoles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of OrganizationRoles");
        Page<OrganizationRoleDTO> page;
        if (eagerload) {
            page = organizationRoleService.findAllWithEagerRelationships(pageable);
        } else {
            page = organizationRoleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-roles/:id} : get the "id" organizationRole.
     *
     * @param id the id of the organizationRoleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationRoleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationRoleDTO> getOrganizationRole(@PathVariable("id") Long id) {
        log.debug("REST request to get OrganizationRole : {}", id);
        Optional<OrganizationRoleDTO> organizationRoleDTO = organizationRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationRoleDTO);
    }

    /**
     * {@code DELETE  /organization-roles/:id} : delete the "id" organizationRole.
     *
     * @param id the id of the organizationRoleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationRole(@PathVariable("id") Long id) {
        log.debug("REST request to delete OrganizationRole : {}", id);
        organizationRoleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
