package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationMembershipRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationMembershipService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationMembershipDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationMembership}.
 */
@RestController
@RequestMapping("/api/organization-memberships")
public class OrganizationMembershipResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationMembershipResource.class);

    private static final String ENTITY_NAME = "organizationMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationMembershipService organizationMembershipService;

    private final OrganizationMembershipRepository organizationMembershipRepository;

    public OrganizationMembershipResource(
        OrganizationMembershipService organizationMembershipService,
        OrganizationMembershipRepository organizationMembershipRepository
    ) {
        this.organizationMembershipService = organizationMembershipService;
        this.organizationMembershipRepository = organizationMembershipRepository;
    }

    /**
     * {@code POST  /organization-memberships} : Create a new organizationMembership.
     *
     * @param organizationMembershipDTO the organizationMembershipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationMembershipDTO, or with status {@code 400 (Bad Request)} if the organizationMembership has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationMembershipDTO> createOrganizationMembership(
        @Valid @RequestBody OrganizationMembershipDTO organizationMembershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationMembership : {}", organizationMembershipDTO);
        if (organizationMembershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationMembershipDTO = organizationMembershipService.save(organizationMembershipDTO);
        return ResponseEntity.created(new URI("/api/organization-memberships/" + organizationMembershipDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationMembershipDTO.getId().toString()))
            .body(organizationMembershipDTO);
    }

    /**
     * {@code PUT  /organization-memberships/:id} : Updates an existing organizationMembership.
     *
     * @param id the id of the organizationMembershipDTO to save.
     * @param organizationMembershipDTO the organizationMembershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationMembershipDTO,
     * or with status {@code 400 (Bad Request)} if the organizationMembershipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationMembershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationMembershipDTO> updateOrganizationMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationMembershipDTO organizationMembershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationMembership : {}, {}", id, organizationMembershipDTO);
        if (organizationMembershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationMembershipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationMembershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationMembershipDTO = organizationMembershipService.update(organizationMembershipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationMembershipDTO.getId().toString()))
            .body(organizationMembershipDTO);
    }

    /**
     * {@code PATCH  /organization-memberships/:id} : Partial updates given fields of an existing organizationMembership, field will ignore if it is null
     *
     * @param id the id of the organizationMembershipDTO to save.
     * @param organizationMembershipDTO the organizationMembershipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationMembershipDTO,
     * or with status {@code 400 (Bad Request)} if the organizationMembershipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationMembershipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationMembershipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationMembershipDTO> partialUpdateOrganizationMembership(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationMembershipDTO organizationMembershipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationMembership partially : {}, {}", id, organizationMembershipDTO);
        if (organizationMembershipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationMembershipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationMembershipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationMembershipDTO> result = organizationMembershipService.partialUpdate(organizationMembershipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationMembershipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-memberships} : get all the organizationMemberships.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationMemberships in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrganizationMembershipDTO>> getAllOrganizationMemberships(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of OrganizationMemberships");
        Page<OrganizationMembershipDTO> page;
        if (eagerload) {
            page = organizationMembershipService.findAllWithEagerRelationships(pageable);
        } else {
            page = organizationMembershipService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-memberships/:id} : get the "id" organizationMembership.
     *
     * @param id the id of the organizationMembershipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationMembershipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationMembershipDTO> getOrganizationMembership(@PathVariable("id") Long id) {
        log.debug("REST request to get OrganizationMembership : {}", id);
        Optional<OrganizationMembershipDTO> organizationMembershipDTO = organizationMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationMembershipDTO);
    }

    /**
     * {@code DELETE  /organization-memberships/:id} : delete the "id" organizationMembership.
     *
     * @param id the id of the organizationMembershipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationMembership(@PathVariable("id") Long id) {
        log.debug("REST request to delete OrganizationMembership : {}", id);
        organizationMembershipService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
