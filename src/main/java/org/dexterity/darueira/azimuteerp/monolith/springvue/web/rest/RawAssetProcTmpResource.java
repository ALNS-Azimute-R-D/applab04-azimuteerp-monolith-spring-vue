package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.RawAssetProcTmpRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.RawAssetProcTmpService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.RawAssetProcTmpDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.RawAssetProcTmp}.
 */
@RestController
@RequestMapping("/api/raw-asset-proc-tmps")
public class RawAssetProcTmpResource {

    private final Logger log = LoggerFactory.getLogger(RawAssetProcTmpResource.class);

    private static final String ENTITY_NAME = "rawAssetProcTmp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RawAssetProcTmpService rawAssetProcTmpService;

    private final RawAssetProcTmpRepository rawAssetProcTmpRepository;

    public RawAssetProcTmpResource(RawAssetProcTmpService rawAssetProcTmpService, RawAssetProcTmpRepository rawAssetProcTmpRepository) {
        this.rawAssetProcTmpService = rawAssetProcTmpService;
        this.rawAssetProcTmpRepository = rawAssetProcTmpRepository;
    }

    /**
     * {@code POST  /raw-asset-proc-tmps} : Create a new rawAssetProcTmp.
     *
     * @param rawAssetProcTmpDTO the rawAssetProcTmpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rawAssetProcTmpDTO, or with status {@code 400 (Bad Request)} if the rawAssetProcTmp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RawAssetProcTmpDTO> createRawAssetProcTmp(@Valid @RequestBody RawAssetProcTmpDTO rawAssetProcTmpDTO)
        throws URISyntaxException {
        log.debug("REST request to save RawAssetProcTmp : {}", rawAssetProcTmpDTO);
        if (rawAssetProcTmpDTO.getId() != null) {
            throw new BadRequestAlertException("A new rawAssetProcTmp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rawAssetProcTmpDTO = rawAssetProcTmpService.save(rawAssetProcTmpDTO);
        return ResponseEntity.created(new URI("/api/raw-asset-proc-tmps/" + rawAssetProcTmpDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, rawAssetProcTmpDTO.getId().toString()))
            .body(rawAssetProcTmpDTO);
    }

    /**
     * {@code PUT  /raw-asset-proc-tmps/:id} : Updates an existing rawAssetProcTmp.
     *
     * @param id the id of the rawAssetProcTmpDTO to save.
     * @param rawAssetProcTmpDTO the rawAssetProcTmpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawAssetProcTmpDTO,
     * or with status {@code 400 (Bad Request)} if the rawAssetProcTmpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rawAssetProcTmpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RawAssetProcTmpDTO> updateRawAssetProcTmp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RawAssetProcTmpDTO rawAssetProcTmpDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RawAssetProcTmp : {}, {}", id, rawAssetProcTmpDTO);
        if (rawAssetProcTmpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawAssetProcTmpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawAssetProcTmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rawAssetProcTmpDTO = rawAssetProcTmpService.update(rawAssetProcTmpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawAssetProcTmpDTO.getId().toString()))
            .body(rawAssetProcTmpDTO);
    }

    /**
     * {@code PATCH  /raw-asset-proc-tmps/:id} : Partial updates given fields of an existing rawAssetProcTmp, field will ignore if it is null
     *
     * @param id the id of the rawAssetProcTmpDTO to save.
     * @param rawAssetProcTmpDTO the rawAssetProcTmpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawAssetProcTmpDTO,
     * or with status {@code 400 (Bad Request)} if the rawAssetProcTmpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rawAssetProcTmpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rawAssetProcTmpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RawAssetProcTmpDTO> partialUpdateRawAssetProcTmp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RawAssetProcTmpDTO rawAssetProcTmpDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RawAssetProcTmp partially : {}, {}", id, rawAssetProcTmpDTO);
        if (rawAssetProcTmpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawAssetProcTmpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawAssetProcTmpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RawAssetProcTmpDTO> result = rawAssetProcTmpService.partialUpdate(rawAssetProcTmpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawAssetProcTmpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /raw-asset-proc-tmps} : get all the rawAssetProcTmps.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rawAssetProcTmps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RawAssetProcTmpDTO>> getAllRawAssetProcTmps(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of RawAssetProcTmps");
        Page<RawAssetProcTmpDTO> page;
        if (eagerload) {
            page = rawAssetProcTmpService.findAllWithEagerRelationships(pageable);
        } else {
            page = rawAssetProcTmpService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /raw-asset-proc-tmps/:id} : get the "id" rawAssetProcTmp.
     *
     * @param id the id of the rawAssetProcTmpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rawAssetProcTmpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RawAssetProcTmpDTO> getRawAssetProcTmp(@PathVariable("id") Long id) {
        log.debug("REST request to get RawAssetProcTmp : {}", id);
        Optional<RawAssetProcTmpDTO> rawAssetProcTmpDTO = rawAssetProcTmpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rawAssetProcTmpDTO);
    }

    /**
     * {@code DELETE  /raw-asset-proc-tmps/:id} : delete the "id" rawAssetProcTmp.
     *
     * @param id the id of the rawAssetProcTmpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRawAssetProcTmp(@PathVariable("id") Long id) {
        log.debug("REST request to delete RawAssetProcTmp : {}", id);
        rawAssetProcTmpService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
