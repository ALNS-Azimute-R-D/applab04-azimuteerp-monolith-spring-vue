package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfVenueRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfVenueService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfVenueDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfVenue}.
 */
@RestController
@RequestMapping("/api/type-of-venues")
public class TypeOfVenueResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfVenueResource.class);

    private static final String ENTITY_NAME = "typeOfVenue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfVenueService typeOfVenueService;

    private final TypeOfVenueRepository typeOfVenueRepository;

    public TypeOfVenueResource(TypeOfVenueService typeOfVenueService, TypeOfVenueRepository typeOfVenueRepository) {
        this.typeOfVenueService = typeOfVenueService;
        this.typeOfVenueRepository = typeOfVenueRepository;
    }

    /**
     * {@code POST  /type-of-venues} : Create a new typeOfVenue.
     *
     * @param typeOfVenueDTO the typeOfVenueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfVenueDTO, or with status {@code 400 (Bad Request)} if the typeOfVenue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeOfVenueDTO> createTypeOfVenue(@Valid @RequestBody TypeOfVenueDTO typeOfVenueDTO) throws URISyntaxException {
        log.debug("REST request to save TypeOfVenue : {}", typeOfVenueDTO);
        if (typeOfVenueDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOfVenue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        typeOfVenueDTO = typeOfVenueService.save(typeOfVenueDTO);
        return ResponseEntity.created(new URI("/api/type-of-venues/" + typeOfVenueDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeOfVenueDTO.getId().toString()))
            .body(typeOfVenueDTO);
    }

    /**
     * {@code PUT  /type-of-venues/:id} : Updates an existing typeOfVenue.
     *
     * @param id the id of the typeOfVenueDTO to save.
     * @param typeOfVenueDTO the typeOfVenueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfVenueDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfVenueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfVenueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfVenueDTO> updateTypeOfVenue(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeOfVenueDTO typeOfVenueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfVenue : {}, {}", id, typeOfVenueDTO);
        if (typeOfVenueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfVenueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfVenueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeOfVenueDTO = typeOfVenueService.update(typeOfVenueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfVenueDTO.getId().toString()))
            .body(typeOfVenueDTO);
    }

    /**
     * {@code PATCH  /type-of-venues/:id} : Partial updates given fields of an existing typeOfVenue, field will ignore if it is null
     *
     * @param id the id of the typeOfVenueDTO to save.
     * @param typeOfVenueDTO the typeOfVenueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfVenueDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfVenueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfVenueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfVenueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfVenueDTO> partialUpdateTypeOfVenue(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeOfVenueDTO typeOfVenueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfVenue partially : {}, {}", id, typeOfVenueDTO);
        if (typeOfVenueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfVenueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfVenueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfVenueDTO> result = typeOfVenueService.partialUpdate(typeOfVenueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfVenueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-venues} : get all the typeOfVenues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfVenues in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeOfVenueDTO>> getAllTypeOfVenues(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypeOfVenues");
        Page<TypeOfVenueDTO> page = typeOfVenueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-venues/:id} : get the "id" typeOfVenue.
     *
     * @param id the id of the typeOfVenueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfVenueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfVenueDTO> getTypeOfVenue(@PathVariable("id") Long id) {
        log.debug("REST request to get TypeOfVenue : {}", id);
        Optional<TypeOfVenueDTO> typeOfVenueDTO = typeOfVenueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfVenueDTO);
    }

    /**
     * {@code DELETE  /type-of-venues/:id} : delete the "id" typeOfVenue.
     *
     * @param id the id of the typeOfVenueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeOfVenue(@PathVariable("id") Long id) {
        log.debug("REST request to delete TypeOfVenue : {}", id);
        typeOfVenueService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
