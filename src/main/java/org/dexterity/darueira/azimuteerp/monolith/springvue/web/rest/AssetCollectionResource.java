package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.AssetCollectionRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.AssetCollectionService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.AssetCollectionDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection}.
 */
@RestController
@RequestMapping("/api/asset-collections")
public class AssetCollectionResource {

    private final Logger log = LoggerFactory.getLogger(AssetCollectionResource.class);

    private static final String ENTITY_NAME = "assetCollection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetCollectionService assetCollectionService;

    private final AssetCollectionRepository assetCollectionRepository;

    public AssetCollectionResource(AssetCollectionService assetCollectionService, AssetCollectionRepository assetCollectionRepository) {
        this.assetCollectionService = assetCollectionService;
        this.assetCollectionRepository = assetCollectionRepository;
    }

    /**
     * {@code POST  /asset-collections} : Create a new assetCollection.
     *
     * @param assetCollectionDTO the assetCollectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetCollectionDTO, or with status {@code 400 (Bad Request)} if the assetCollection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssetCollectionDTO> createAssetCollection(@Valid @RequestBody AssetCollectionDTO assetCollectionDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssetCollection : {}", assetCollectionDTO);
        if (assetCollectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetCollection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assetCollectionDTO = assetCollectionService.save(assetCollectionDTO);
        return ResponseEntity.created(new URI("/api/asset-collections/" + assetCollectionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assetCollectionDTO.getId().toString()))
            .body(assetCollectionDTO);
    }

    /**
     * {@code PUT  /asset-collections/:id} : Updates an existing assetCollection.
     *
     * @param id the id of the assetCollectionDTO to save.
     * @param assetCollectionDTO the assetCollectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetCollectionDTO,
     * or with status {@code 400 (Bad Request)} if the assetCollectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetCollectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssetCollectionDTO> updateAssetCollection(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssetCollectionDTO assetCollectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssetCollection : {}, {}", id, assetCollectionDTO);
        if (assetCollectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetCollectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetCollectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assetCollectionDTO = assetCollectionService.update(assetCollectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetCollectionDTO.getId().toString()))
            .body(assetCollectionDTO);
    }

    /**
     * {@code PATCH  /asset-collections/:id} : Partial updates given fields of an existing assetCollection, field will ignore if it is null
     *
     * @param id the id of the assetCollectionDTO to save.
     * @param assetCollectionDTO the assetCollectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetCollectionDTO,
     * or with status {@code 400 (Bad Request)} if the assetCollectionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assetCollectionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assetCollectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetCollectionDTO> partialUpdateAssetCollection(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssetCollectionDTO assetCollectionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssetCollection partially : {}, {}", id, assetCollectionDTO);
        if (assetCollectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assetCollectionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assetCollectionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssetCollectionDTO> result = assetCollectionService.partialUpdate(assetCollectionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetCollectionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /asset-collections} : get all the assetCollections.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetCollections in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssetCollectionDTO>> getAllAssetCollections(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of AssetCollections");
        Page<AssetCollectionDTO> page;
        if (eagerload) {
            page = assetCollectionService.findAllWithEagerRelationships(pageable);
        } else {
            page = assetCollectionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asset-collections/:id} : get the "id" assetCollection.
     *
     * @param id the id of the assetCollectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetCollectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssetCollectionDTO> getAssetCollection(@PathVariable("id") Long id) {
        log.debug("REST request to get AssetCollection : {}", id);
        Optional<AssetCollectionDTO> assetCollectionDTO = assetCollectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetCollectionDTO);
    }

    /**
     * {@code DELETE  /asset-collections/:id} : delete the "id" assetCollection.
     *
     * @param id the id of the assetCollectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetCollection(@PathVariable("id") Long id) {
        log.debug("REST request to delete AssetCollection : {}", id);
        assetCollectionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
