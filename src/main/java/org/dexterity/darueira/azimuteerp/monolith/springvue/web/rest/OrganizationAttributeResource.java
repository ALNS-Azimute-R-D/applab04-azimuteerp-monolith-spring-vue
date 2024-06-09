package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationAttributeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationAttributeService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationAttributeDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationAttribute}.
 */
@RestController
@RequestMapping("/api/organization-attributes")
public class OrganizationAttributeResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationAttributeResource.class);

    private static final String ENTITY_NAME = "organizationAttribute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationAttributeService organizationAttributeService;

    private final OrganizationAttributeRepository organizationAttributeRepository;

    public OrganizationAttributeResource(
        OrganizationAttributeService organizationAttributeService,
        OrganizationAttributeRepository organizationAttributeRepository
    ) {
        this.organizationAttributeService = organizationAttributeService;
        this.organizationAttributeRepository = organizationAttributeRepository;
    }

    /**
     * {@code POST  /organization-attributes} : Create a new organizationAttribute.
     *
     * @param organizationAttributeDTO the organizationAttributeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationAttributeDTO, or with status {@code 400 (Bad Request)} if the organizationAttribute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationAttributeDTO> createOrganizationAttribute(
        @Valid @RequestBody OrganizationAttributeDTO organizationAttributeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OrganizationAttribute : {}", organizationAttributeDTO);
        if (organizationAttributeDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationAttribute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationAttributeDTO = organizationAttributeService.save(organizationAttributeDTO);
        return ResponseEntity.created(new URI("/api/organization-attributes/" + organizationAttributeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationAttributeDTO.getId().toString()))
            .body(organizationAttributeDTO);
    }

    /**
     * {@code PUT  /organization-attributes/:id} : Updates an existing organizationAttribute.
     *
     * @param id the id of the organizationAttributeDTO to save.
     * @param organizationAttributeDTO the organizationAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the organizationAttributeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationAttributeDTO> updateOrganizationAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationAttributeDTO organizationAttributeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationAttribute : {}, {}", id, organizationAttributeDTO);
        if (organizationAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationAttributeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationAttributeDTO = organizationAttributeService.update(organizationAttributeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationAttributeDTO.getId().toString()))
            .body(organizationAttributeDTO);
    }

    /**
     * {@code PATCH  /organization-attributes/:id} : Partial updates given fields of an existing organizationAttribute, field will ignore if it is null
     *
     * @param id the id of the organizationAttributeDTO to save.
     * @param organizationAttributeDTO the organizationAttributeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationAttributeDTO,
     * or with status {@code 400 (Bad Request)} if the organizationAttributeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationAttributeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationAttributeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationAttributeDTO> partialUpdateOrganizationAttribute(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationAttributeDTO organizationAttributeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationAttribute partially : {}, {}", id, organizationAttributeDTO);
        if (organizationAttributeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationAttributeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationAttributeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationAttributeDTO> result = organizationAttributeService.partialUpdate(organizationAttributeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationAttributeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-attributes} : get all the organizationAttributes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationAttributes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrganizationAttributeDTO>> getAllOrganizationAttributes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of OrganizationAttributes");
        Page<OrganizationAttributeDTO> page;
        if (eagerload) {
            page = organizationAttributeService.findAllWithEagerRelationships(pageable);
        } else {
            page = organizationAttributeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-attributes/:id} : get the "id" organizationAttribute.
     *
     * @param id the id of the organizationAttributeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationAttributeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationAttributeDTO> getOrganizationAttribute(@PathVariable("id") Long id) {
        log.debug("REST request to get OrganizationAttribute : {}", id);
        Optional<OrganizationAttributeDTO> organizationAttributeDTO = organizationAttributeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationAttributeDTO);
    }

    /**
     * {@code DELETE  /organization-attributes/:id} : delete the "id" organizationAttribute.
     *
     * @param id the id of the organizationAttributeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationAttribute(@PathVariable("id") Long id) {
        log.debug("REST request to delete OrganizationAttribute : {}", id);
        organizationAttributeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
