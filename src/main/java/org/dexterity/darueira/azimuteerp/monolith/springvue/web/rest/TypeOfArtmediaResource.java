package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfArtmediaRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfArtmediaService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtmediaDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtmedia}.
 */
@RestController
@RequestMapping("/api/type-of-artmedias")
public class TypeOfArtmediaResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfArtmediaResource.class);

    private static final String ENTITY_NAME = "typeOfArtmedia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfArtmediaService typeOfArtmediaService;

    private final TypeOfArtmediaRepository typeOfArtmediaRepository;

    public TypeOfArtmediaResource(TypeOfArtmediaService typeOfArtmediaService, TypeOfArtmediaRepository typeOfArtmediaRepository) {
        this.typeOfArtmediaService = typeOfArtmediaService;
        this.typeOfArtmediaRepository = typeOfArtmediaRepository;
    }

    /**
     * {@code POST  /type-of-artmedias} : Create a new typeOfArtmedia.
     *
     * @param typeOfArtmediaDTO the typeOfArtmediaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfArtmediaDTO, or with status {@code 400 (Bad Request)} if the typeOfArtmedia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeOfArtmediaDTO> createTypeOfArtmedia(@Valid @RequestBody TypeOfArtmediaDTO typeOfArtmediaDTO)
        throws URISyntaxException {
        log.debug("REST request to save TypeOfArtmedia : {}", typeOfArtmediaDTO);
        if (typeOfArtmediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOfArtmedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        typeOfArtmediaDTO = typeOfArtmediaService.save(typeOfArtmediaDTO);
        return ResponseEntity.created(new URI("/api/type-of-artmedias/" + typeOfArtmediaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeOfArtmediaDTO.getId().toString()))
            .body(typeOfArtmediaDTO);
    }

    /**
     * {@code PUT  /type-of-artmedias/:id} : Updates an existing typeOfArtmedia.
     *
     * @param id the id of the typeOfArtmediaDTO to save.
     * @param typeOfArtmediaDTO the typeOfArtmediaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfArtmediaDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfArtmediaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfArtmediaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfArtmediaDTO> updateTypeOfArtmedia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeOfArtmediaDTO typeOfArtmediaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfArtmedia : {}, {}", id, typeOfArtmediaDTO);
        if (typeOfArtmediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfArtmediaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfArtmediaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeOfArtmediaDTO = typeOfArtmediaService.update(typeOfArtmediaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfArtmediaDTO.getId().toString()))
            .body(typeOfArtmediaDTO);
    }

    /**
     * {@code PATCH  /type-of-artmedias/:id} : Partial updates given fields of an existing typeOfArtmedia, field will ignore if it is null
     *
     * @param id the id of the typeOfArtmediaDTO to save.
     * @param typeOfArtmediaDTO the typeOfArtmediaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfArtmediaDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfArtmediaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfArtmediaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfArtmediaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfArtmediaDTO> partialUpdateTypeOfArtmedia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeOfArtmediaDTO typeOfArtmediaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfArtmedia partially : {}, {}", id, typeOfArtmediaDTO);
        if (typeOfArtmediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfArtmediaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfArtmediaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfArtmediaDTO> result = typeOfArtmediaService.partialUpdate(typeOfArtmediaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfArtmediaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-artmedias} : get all the typeOfArtmedias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfArtmedias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeOfArtmediaDTO>> getAllTypeOfArtmedias(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TypeOfArtmedias");
        Page<TypeOfArtmediaDTO> page = typeOfArtmediaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-artmedias/:id} : get the "id" typeOfArtmedia.
     *
     * @param id the id of the typeOfArtmediaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfArtmediaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfArtmediaDTO> getTypeOfArtmedia(@PathVariable("id") Long id) {
        log.debug("REST request to get TypeOfArtmedia : {}", id);
        Optional<TypeOfArtmediaDTO> typeOfArtmediaDTO = typeOfArtmediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfArtmediaDTO);
    }

    /**
     * {@code DELETE  /type-of-artmedias/:id} : delete the "id" typeOfArtmedia.
     *
     * @param id the id of the typeOfArtmediaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeOfArtmedia(@PathVariable("id") Long id) {
        log.debug("REST request to delete TypeOfArtmedia : {}", id);
        typeOfArtmediaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
