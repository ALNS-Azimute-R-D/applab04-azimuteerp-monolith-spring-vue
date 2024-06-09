package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfEventRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfEventService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfEventDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfEvent}.
 */
@RestController
@RequestMapping("/api/type-of-events")
public class TypeOfEventResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfEventResource.class);

    private static final String ENTITY_NAME = "typeOfEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfEventService typeOfEventService;

    private final TypeOfEventRepository typeOfEventRepository;

    public TypeOfEventResource(TypeOfEventService typeOfEventService, TypeOfEventRepository typeOfEventRepository) {
        this.typeOfEventService = typeOfEventService;
        this.typeOfEventRepository = typeOfEventRepository;
    }

    /**
     * {@code POST  /type-of-events} : Create a new typeOfEvent.
     *
     * @param typeOfEventDTO the typeOfEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfEventDTO, or with status {@code 400 (Bad Request)} if the typeOfEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeOfEventDTO> createTypeOfEvent(@Valid @RequestBody TypeOfEventDTO typeOfEventDTO) throws URISyntaxException {
        log.debug("REST request to save TypeOfEvent : {}", typeOfEventDTO);
        if (typeOfEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOfEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        typeOfEventDTO = typeOfEventService.save(typeOfEventDTO);
        return ResponseEntity.created(new URI("/api/type-of-events/" + typeOfEventDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeOfEventDTO.getId().toString()))
            .body(typeOfEventDTO);
    }

    /**
     * {@code PUT  /type-of-events/:id} : Updates an existing typeOfEvent.
     *
     * @param id the id of the typeOfEventDTO to save.
     * @param typeOfEventDTO the typeOfEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfEventDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfEventDTO> updateTypeOfEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeOfEventDTO typeOfEventDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfEvent : {}, {}", id, typeOfEventDTO);
        if (typeOfEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeOfEventDTO = typeOfEventService.update(typeOfEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfEventDTO.getId().toString()))
            .body(typeOfEventDTO);
    }

    /**
     * {@code PATCH  /type-of-events/:id} : Partial updates given fields of an existing typeOfEvent, field will ignore if it is null
     *
     * @param id the id of the typeOfEventDTO to save.
     * @param typeOfEventDTO the typeOfEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfEventDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfEventDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfEventDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfEventDTO> partialUpdateTypeOfEvent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeOfEventDTO typeOfEventDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfEvent partially : {}, {}", id, typeOfEventDTO);
        if (typeOfEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfEventDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfEventRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfEventDTO> result = typeOfEventService.partialUpdate(typeOfEventDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfEventDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-events} : get all the typeOfEvents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfEvents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeOfEventDTO>> getAllTypeOfEvents(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypeOfEvents");
        Page<TypeOfEventDTO> page = typeOfEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-events/:id} : get the "id" typeOfEvent.
     *
     * @param id the id of the typeOfEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfEventDTO> getTypeOfEvent(@PathVariable("id") Long id) {
        log.debug("REST request to get TypeOfEvent : {}", id);
        Optional<TypeOfEventDTO> typeOfEventDTO = typeOfEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfEventDTO);
    }

    /**
     * {@code DELETE  /type-of-events/:id} : delete the "id" typeOfEvent.
     *
     * @param id the id of the typeOfEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeOfEvent(@PathVariable("id") Long id) {
        log.debug("REST request to delete TypeOfEvent : {}", id);
        typeOfEventService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
