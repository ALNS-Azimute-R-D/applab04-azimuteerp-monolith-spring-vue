package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfPersonRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TypeOfPersonService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfPersonDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson}.
 */
@RestController
@RequestMapping("/api/type-of-people")
public class TypeOfPersonResource {

    private final Logger log = LoggerFactory.getLogger(TypeOfPersonResource.class);

    private static final String ENTITY_NAME = "typeOfPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeOfPersonService typeOfPersonService;

    private final TypeOfPersonRepository typeOfPersonRepository;

    public TypeOfPersonResource(TypeOfPersonService typeOfPersonService, TypeOfPersonRepository typeOfPersonRepository) {
        this.typeOfPersonService = typeOfPersonService;
        this.typeOfPersonRepository = typeOfPersonRepository;
    }

    /**
     * {@code POST  /type-of-people} : Create a new typeOfPerson.
     *
     * @param typeOfPersonDTO the typeOfPersonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeOfPersonDTO, or with status {@code 400 (Bad Request)} if the typeOfPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeOfPersonDTO> createTypeOfPerson(@Valid @RequestBody TypeOfPersonDTO typeOfPersonDTO)
        throws URISyntaxException {
        log.debug("REST request to save TypeOfPerson : {}", typeOfPersonDTO);
        if (typeOfPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeOfPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        typeOfPersonDTO = typeOfPersonService.save(typeOfPersonDTO);
        return ResponseEntity.created(new URI("/api/type-of-people/" + typeOfPersonDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, typeOfPersonDTO.getId().toString()))
            .body(typeOfPersonDTO);
    }

    /**
     * {@code PUT  /type-of-people/:id} : Updates an existing typeOfPerson.
     *
     * @param id the id of the typeOfPersonDTO to save.
     * @param typeOfPersonDTO the typeOfPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfPersonDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfPersonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeOfPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOfPersonDTO> updateTypeOfPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TypeOfPersonDTO typeOfPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TypeOfPerson : {}, {}", id, typeOfPersonDTO);
        if (typeOfPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        typeOfPersonDTO = typeOfPersonService.update(typeOfPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfPersonDTO.getId().toString()))
            .body(typeOfPersonDTO);
    }

    /**
     * {@code PATCH  /type-of-people/:id} : Partial updates given fields of an existing typeOfPerson, field will ignore if it is null
     *
     * @param id the id of the typeOfPersonDTO to save.
     * @param typeOfPersonDTO the typeOfPersonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeOfPersonDTO,
     * or with status {@code 400 (Bad Request)} if the typeOfPersonDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeOfPersonDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeOfPersonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeOfPersonDTO> partialUpdateTypeOfPerson(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TypeOfPersonDTO typeOfPersonDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeOfPerson partially : {}, {}", id, typeOfPersonDTO);
        if (typeOfPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeOfPersonDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeOfPersonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeOfPersonDTO> result = typeOfPersonService.partialUpdate(typeOfPersonDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeOfPersonDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /type-of-people} : get all the typeOfPeople.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeOfPeople in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TypeOfPersonDTO>> getAllTypeOfPeople(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TypeOfPeople");
        Page<TypeOfPersonDTO> page = typeOfPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-of-people/:id} : get the "id" typeOfPerson.
     *
     * @param id the id of the typeOfPersonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeOfPersonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfPersonDTO> getTypeOfPerson(@PathVariable("id") Long id) {
        log.debug("REST request to get TypeOfPerson : {}", id);
        Optional<TypeOfPersonDTO> typeOfPersonDTO = typeOfPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeOfPersonDTO);
    }

    /**
     * {@code DELETE  /type-of-people/:id} : delete the "id" typeOfPerson.
     *
     * @param id the id of the typeOfPersonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeOfPerson(@PathVariable("id") Long id) {
        log.debug("REST request to delete TypeOfPerson : {}", id);
        typeOfPersonService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
