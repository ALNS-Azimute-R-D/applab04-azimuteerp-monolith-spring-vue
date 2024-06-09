package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.OrganizationDomainRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.OrganizationDomainService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.OrganizationDomainDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.OrganizationDomain}.
 */
@RestController
@RequestMapping("/api/organization-domains")
public class OrganizationDomainResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationDomainResource.class);

    private static final String ENTITY_NAME = "organizationDomain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationDomainService organizationDomainService;

    private final OrganizationDomainRepository organizationDomainRepository;

    public OrganizationDomainResource(
        OrganizationDomainService organizationDomainService,
        OrganizationDomainRepository organizationDomainRepository
    ) {
        this.organizationDomainService = organizationDomainService;
        this.organizationDomainRepository = organizationDomainRepository;
    }

    /**
     * {@code POST  /organization-domains} : Create a new organizationDomain.
     *
     * @param organizationDomainDTO the organizationDomainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationDomainDTO, or with status {@code 400 (Bad Request)} if the organizationDomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrganizationDomainDTO> createOrganizationDomain(@Valid @RequestBody OrganizationDomainDTO organizationDomainDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrganizationDomain : {}", organizationDomainDTO);
        if (organizationDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        organizationDomainDTO = organizationDomainService.save(organizationDomainDTO);
        return ResponseEntity.created(new URI("/api/organization-domains/" + organizationDomainDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, organizationDomainDTO.getId().toString()))
            .body(organizationDomainDTO);
    }

    /**
     * {@code PUT  /organization-domains/:id} : Updates an existing organizationDomain.
     *
     * @param id the id of the organizationDomainDTO to save.
     * @param organizationDomainDTO the organizationDomainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationDomainDTO,
     * or with status {@code 400 (Bad Request)} if the organizationDomainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationDomainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDomainDTO> updateOrganizationDomain(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationDomainDTO organizationDomainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrganizationDomain : {}, {}", id, organizationDomainDTO);
        if (organizationDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationDomainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationDomainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        organizationDomainDTO = organizationDomainService.update(organizationDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationDomainDTO.getId().toString()))
            .body(organizationDomainDTO);
    }

    /**
     * {@code PATCH  /organization-domains/:id} : Partial updates given fields of an existing organizationDomain, field will ignore if it is null
     *
     * @param id the id of the organizationDomainDTO to save.
     * @param organizationDomainDTO the organizationDomainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationDomainDTO,
     * or with status {@code 400 (Bad Request)} if the organizationDomainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organizationDomainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organizationDomainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationDomainDTO> partialUpdateOrganizationDomain(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationDomainDTO organizationDomainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrganizationDomain partially : {}, {}", id, organizationDomainDTO);
        if (organizationDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organizationDomainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organizationDomainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganizationDomainDTO> result = organizationDomainService.partialUpdate(organizationDomainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationDomainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organization-domains} : get all the organizationDomains.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationDomains in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrganizationDomainDTO>> getAllOrganizationDomains(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of OrganizationDomains");
        Page<OrganizationDomainDTO> page;
        if (eagerload) {
            page = organizationDomainService.findAllWithEagerRelationships(pageable);
        } else {
            page = organizationDomainService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-domains/:id} : get the "id" organizationDomain.
     *
     * @param id the id of the organizationDomainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationDomainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDomainDTO> getOrganizationDomain(@PathVariable("id") Long id) {
        log.debug("REST request to get OrganizationDomain : {}", id);
        Optional<OrganizationDomainDTO> organizationDomainDTO = organizationDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationDomainDTO);
    }

    /**
     * {@code DELETE  /organization-domains/:id} : delete the "id" organizationDomain.
     *
     * @param id the id of the organizationDomainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationDomain(@PathVariable("id") Long id) {
        log.debug("REST request to delete OrganizationDomain : {}", id);
        organizationDomainService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
