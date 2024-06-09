package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetMetadataRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetMetadataService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetMetadataDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetMetadata}.
 */
@RestController
@RequestMapping("/api/asset-metadata")
public class AssetMetadataResource {

    private final Logger log = LoggerFactory.getLogger(AssetMetadataResource.class);

    private static final String ENTITY_NAME = "assetMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetMetadataService assetMetadataService;

    private final AssetMetadataRepository assetMetadataRepository;

    public AssetMetadataResource(AssetMetadataService assetMetadataService, AssetMetadataRepository assetMetadataRepository) {
        this.assetMetadataService = assetMetadataService;
        this.assetMetadataRepository = assetMetadataRepository;
    }

    /**
     * {@code POST  /asset-metadata} : Create a new assetMetadata.
     *
     * @param assetMetadataDTO the assetMetadataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetMetadataDTO, or with status {@code 400 (Bad Request)} if the assetMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssetMetadataDTO> createAssetMetadata(@Valid @RequestBody AssetMetadataDTO assetMetadataDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssetMetadata : {}", assetMetadataDTO);
        if (assetMetadataDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assetMetadataDTO = assetMetadataService.save(assetMetadataDTO);
        return ResponseEntity.created(new URI("/api/asset-metadata/" + assetMetadataDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assetMetadataDTO.getId().toString()))
            .body(assetMetadataDTO);
    }

    /**
     * {@code PUT  /asset-metadata/:id} : Updates an existing assetMetadata.
     *
     * @param id the id of the assetMetadataDTO to save.
     * @param assetMetadataDTO the assetMetadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetMetadataDTO,
     * or with status {@code 400 (Bad Request)} if the assetMetadataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetMetadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssetMetadataDTO> updateAssetMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssetMetadataDTO assetMetadataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssetMetadata : {}, {}", id, assetMetadataDTO);
        if (assetMetadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetMetadataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assetMetadataDTO = assetMetadataService.update(assetMetadataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetMetadataDTO.getId().toString()))
            .body(assetMetadataDTO);
    }

    /**
     * {@code PATCH  /asset-metadata/:id} : Partial updates given fields of an existing assetMetadata, field will ignore if it is null
     *
     * @param id the id of the assetMetadataDTO to save.
     * @param assetMetadataDTO the assetMetadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetMetadataDTO,
     * or with status {@code 400 (Bad Request)} if the assetMetadataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetMetadataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetMetadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetMetadataDTO> partialUpdateAssetMetadata(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssetMetadataDTO assetMetadataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetMetadata partially : {}, {}", id, assetMetadataDTO);
        if (assetMetadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetMetadataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetMetadataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetMetadataDTO> result = assetMetadataService.partialUpdate(assetMetadataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetMetadataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-metadata} : get all the assetMetadata.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetMetadata in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssetMetadataDTO>> getAllAssetMetadata(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of AssetMetadata");
        Page<AssetMetadataDTO> page;
        if (eagerload) {
            page = assetMetadataService.findAllWithEagerRelationships(pageable);
        } else {
            page = assetMetadataService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-metadata/:id} : get the "id" assetMetadata.
     *
     * @param id the id of the assetMetadataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetMetadataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssetMetadataDTO> getAssetMetadata(@PathVariable("id") Long id) {
        log.debug("REST request to get AssetMetadata : {}", id);
        Optional<AssetMetadataDTO> assetMetadataDTO = assetMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetMetadataDTO);
    }

    /**
     * {@code DELETE  /asset-metadata/:id} : delete the "id" assetMetadata.
     *
     * @param id the id of the assetMetadataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetMetadata(@PathVariable("id") Long id) {
        log.debug("REST request to delete AssetMetadata : {}", id);
        assetMetadataService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
