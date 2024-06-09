package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.BusinessUnitRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.BusinessUnitService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.BusinessUnitDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.BusinessUnit}.
 */
@RestController
@RequestMapping("/api/business-units")
public class BusinessUnitResource {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitResource.class);

    private static final String ENTITY_NAME = "businessUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessUnitService businessUnitService;

    private final BusinessUnitRepository businessUnitRepository;

    public BusinessUnitResource(BusinessUnitService businessUnitService, BusinessUnitRepository businessUnitRepository) {
        this.businessUnitService = businessUnitService;
        this.businessUnitRepository = businessUnitRepository;
    }

    /**
     * {@code POST  /business-units} : Create a new businessUnit.
     *
     * @param businessUnitDTO the businessUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessUnitDTO, or with status {@code 400 (Bad Request)} if the businessUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BusinessUnitDTO> createBusinessUnit(@Valid @RequestBody BusinessUnitDTO businessUnitDTO)
        throws URISyntaxException {
        log.debug("REST request to save BusinessUnit : {}", businessUnitDTO);
        if (businessUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        businessUnitDTO = businessUnitService.save(businessUnitDTO);
        return ResponseEntity.created(new URI("/api/business-units/" + businessUnitDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, businessUnitDTO.getId().toString()))
            .body(businessUnitDTO);
    }

    /**
     * {@code PUT  /business-units/:id} : Updates an existing businessUnit.
     *
     * @param id the id of the businessUnitDTO to save.
     * @param businessUnitDTO the businessUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnitDTO,
     * or with status {@code 400 (Bad Request)} if the businessUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnitDTO> updateBusinessUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BusinessUnitDTO businessUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessUnit : {}, {}", id, businessUnitDTO);
        if (businessUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        businessUnitDTO = businessUnitService.update(businessUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessUnitDTO.getId().toString()))
            .body(businessUnitDTO);
    }

    /**
     * {@code PATCH  /business-units/:id} : Partial updates given fields of an existing businessUnit, field will ignore if it is null
     *
     * @param id the id of the businessUnitDTO to save.
     * @param businessUnitDTO the businessUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessUnitDTO,
     * or with status {@code 400 (Bad Request)} if the businessUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessUnitDTO> partialUpdateBusinessUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BusinessUnitDTO businessUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessUnit partially : {}, {}", id, businessUnitDTO);
        if (businessUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessUnitDTO> result = businessUnitService.partialUpdate(businessUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /business-units} : get all the businessUnits.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessUnits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BusinessUnitDTO>> getAllBusinessUnits(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of BusinessUnits");
        Page<BusinessUnitDTO> page;
        if (eagerload) {
            page = businessUnitService.findAllWithEagerRelationships(pageable);
        } else {
            page = businessUnitService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /business-units/:id} : get the "id" businessUnit.
     *
     * @param id the id of the businessUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnitDTO> getBusinessUnit(@PathVariable("id") Long id) {
        log.debug("REST request to get BusinessUnit : {}", id);
        Optional<BusinessUnitDTO> businessUnitDTO = businessUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessUnitDTO);
    }

    /**
     * {@code DELETE  /business-units/:id} : delete the "id" businessUnit.
     *
     * @param id the id of the businessUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessUnit(@PathVariable("id") Long id) {
        log.debug("REST request to delete BusinessUnit : {}", id);
        businessUnitService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
