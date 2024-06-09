package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtworkCastRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtworkCastService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtworkCastDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtworkCast}.
 */
@RestController
@RequestMapping("/api/artwork-casts")
public class ArtworkCastResource {

    private final Logger log = LoggerFactory.getLogger(ArtworkCastResource.class);

    private static final String ENTITY_NAME = "artworkCast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtworkCastService artworkCastService;

    private final ArtworkCastRepository artworkCastRepository;

    public ArtworkCastResource(ArtworkCastService artworkCastService, ArtworkCastRepository artworkCastRepository) {
        this.artworkCastService = artworkCastService;
        this.artworkCastRepository = artworkCastRepository;
    }

    /**
     * {@code POST  /artwork-casts} : Create a new artworkCast.
     *
     * @param artworkCastDTO the artworkCastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artworkCastDTO, or with status {@code 400 (Bad Request)} if the artworkCast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArtworkCastDTO> createArtworkCast(@Valid @RequestBody ArtworkCastDTO artworkCastDTO) throws URISyntaxException {
        log.debug("REST request to save ArtworkCast : {}", artworkCastDTO);
        if (artworkCastDTO.getId() != null) {
            throw new BadRequestAlertException("A new artworkCast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        artworkCastDTO = artworkCastService.save(artworkCastDTO);
        return ResponseEntity.created(new URI("/api/artwork-casts/" + artworkCastDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, artworkCastDTO.getId().toString()))
            .body(artworkCastDTO);
    }

    /**
     * {@code PUT  /artwork-casts/:id} : Updates an existing artworkCast.
     *
     * @param id the id of the artworkCastDTO to save.
     * @param artworkCastDTO the artworkCastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkCastDTO,
     * or with status {@code 400 (Bad Request)} if the artworkCastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artworkCastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtworkCastDTO> updateArtworkCast(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArtworkCastDTO artworkCastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtworkCast : {}, {}", id, artworkCastDTO);
        if (artworkCastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        artworkCastDTO = artworkCastService.update(artworkCastDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artworkCastDTO.getId().toString()))
            .body(artworkCastDTO);
    }

    /**
     * {@code PATCH  /artwork-casts/:id} : Partial updates given fields of an existing artworkCast, field will ignore if it is null
     *
     * @param id the id of the artworkCastDTO to save.
     * @param artworkCastDTO the artworkCastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artworkCastDTO,
     * or with status {@code 400 (Bad Request)} if the artworkCastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artworkCastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artworkCastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtworkCastDTO> partialUpdateArtworkCast(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArtworkCastDTO artworkCastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtworkCast partially : {}, {}", id, artworkCastDTO);
        if (artworkCastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artworkCastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artworkCastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtworkCastDTO> result = artworkCastService.partialUpdate(artworkCastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artworkCastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artwork-casts} : get all the artworkCasts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artworkCasts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArtworkCastDTO>> getAllArtworkCasts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ArtworkCasts");
        Page<ArtworkCastDTO> page;
        if (eagerload) {
            page = artworkCastService.findAllWithEagerRelationships(pageable);
        } else {
            page = artworkCastService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artwork-casts/:id} : get the "id" artworkCast.
     *
     * @param id the id of the artworkCastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artworkCastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkCastDTO> getArtworkCast(@PathVariable("id") Long id) {
        log.debug("REST request to get ArtworkCast : {}", id);
        Optional<ArtworkCastDTO> artworkCastDTO = artworkCastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artworkCastDTO);
    }

    /**
     * {@code DELETE  /artwork-casts/:id} : delete the "id" artworkCast.
     *
     * @param id the id of the artworkCastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtworkCast(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArtworkCast : {}", id);
        artworkCastService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
