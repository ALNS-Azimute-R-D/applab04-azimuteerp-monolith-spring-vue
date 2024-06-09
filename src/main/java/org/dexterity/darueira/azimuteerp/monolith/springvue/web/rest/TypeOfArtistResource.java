package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfArtistRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfArtistService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfArtistDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfArtist}.
 */
@RestController
@RequestMapping("/api/type-of-artists")
public class TypeOfArtistResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfArtistResource.class);

    private static final String ENTITY_NAME = "typeOfArtist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfArtistService typeOfArtistService;

    private final TypeOfArtistRepository typeOfArtistRepository;

    public TypeOfArtistResource(TypeOfArtistService typeOfArtistService, TypeOfArtistRepository typeOfArtistRepository) {
        this.typeOfArtistService = typeOfArtistService;
        this.typeOfArtistRepository = typeOfArtistRepository;
    }

    /**
     * {@code POST  /type-of-artists} : Create a new typeOfArtist.
     *
     * @param typeOfArtistDTO the typeOfArtistDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfArtistDTO, or with status {@code 400 (Bad Request)} if the typeOfArtist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeOfArtistDTO> createTypeOfArtist(@Valid @RequestBody TypeOfArtistDTO typeOfArtistDTO)
        throws URISyntaxException {
        log.debug("REST request to save TypeOfArtist : {}", typeOfArtistDTO);
        if (typeOfArtistDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOfArtist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        typeOfArtistDTO = typeOfArtistService.save(typeOfArtistDTO);
        return ResponseEntity.created(new URI("/api/type-of-artists/" + typeOfArtistDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeOfArtistDTO.getId().toString()))
            .body(typeOfArtistDTO);
    }

    /**
     * {@code PUT  /type-of-artists/:id} : Updates an existing typeOfArtist.
     *
     * @param id the id of the typeOfArtistDTO to save.
     * @param typeOfArtistDTO the typeOfArtistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfArtistDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfArtistDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfArtistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfArtistDTO> updateTypeOfArtist(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeOfArtistDTO typeOfArtistDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfArtist : {}, {}", id, typeOfArtistDTO);
        if (typeOfArtistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfArtistDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfArtistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeOfArtistDTO = typeOfArtistService.update(typeOfArtistDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfArtistDTO.getId().toString()))
            .body(typeOfArtistDTO);
    }

    /**
     * {@code PATCH  /type-of-artists/:id} : Partial updates given fields of an existing typeOfArtist, field will ignore if it is null
     *
     * @param id the id of the typeOfArtistDTO to save.
     * @param typeOfArtistDTO the typeOfArtistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfArtistDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfArtistDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfArtistDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfArtistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfArtistDTO> partialUpdateTypeOfArtist(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeOfArtistDTO typeOfArtistDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfArtist partially : {}, {}", id, typeOfArtistDTO);
        if (typeOfArtistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfArtistDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfArtistRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfArtistDTO> result = typeOfArtistService.partialUpdate(typeOfArtistDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfArtistDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-artists} : get all the typeOfArtists.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfArtists in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeOfArtistDTO>> getAllTypeOfArtists(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypeOfArtists");
        Page<TypeOfArtistDTO> page = typeOfArtistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-artists/:id} : get the "id" typeOfArtist.
     *
     * @param id the id of the typeOfArtistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfArtistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfArtistDTO> getTypeOfArtist(@PathVariable("id") Long id) {
        log.debug("REST request to get TypeOfArtist : {}", id);
        Optional<TypeOfArtistDTO> typeOfArtistDTO = typeOfArtistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfArtistDTO);
    }

    /**
     * {@code DELETE  /type-of-artists/:id} : delete the "id" typeOfArtist.
     *
     * @param id the id of the typeOfArtistDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeOfArtist(@PathVariable("id") Long id) {
        log.debug("REST request to delete TypeOfArtist : {}", id);
        typeOfArtistService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
