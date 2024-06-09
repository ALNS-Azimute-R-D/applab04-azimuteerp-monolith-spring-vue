package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.EventProgramRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.EventProgramService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.EventProgramDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram}.
 */
@RestController
@RequestMapping("/api/event-programs")
public class EventProgramResource {

    private final Logger log = LoggerFactory.getLogger(EventProgramResource.class);

    private static final String ENTITY_NAME = "eventProgram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventProgramService eventProgramService;

    private final EventProgramRepository eventProgramRepository;

    public EventProgramResource(EventProgramService eventProgramService, EventProgramRepository eventProgramRepository) {
        this.eventProgramService = eventProgramService;
        this.eventProgramRepository = eventProgramRepository;
    }

    /**
     * {@code POST  /event-programs} : Create a new eventProgram.
     *
     * @param eventProgramDTO the eventProgramDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventProgramDTO, or with status {@code 400 (Bad Request)} if the eventProgram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EventProgramDTO> createEventProgram(@RequestBody EventProgramDTO eventProgramDTO) throws URISyntaxException {
        log.debug("REST request to save EventProgram : {}", eventProgramDTO);
        if (eventProgramDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventProgram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eventProgramDTO = eventProgramService.save(eventProgramDTO);
        return ResponseEntity.created(new URI("/api/event-programs/" + eventProgramDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, eventProgramDTO.getId().toString()))
            .body(eventProgramDTO);
    }

    /**
     * {@code PUT  /event-programs/:id} : Updates an existing eventProgram.
     *
     * @param id the id of the eventProgramDTO to save.
     * @param eventProgramDTO the eventProgramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventProgramDTO,
     * or with status {@code 400 (Bad Request)} if the eventProgramDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventProgramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventProgramDTO> updateEventProgram(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventProgramDTO eventProgramDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EventProgram : {}, {}", id, eventProgramDTO);
        if (eventProgramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventProgramDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventProgramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eventProgramDTO = eventProgramService.update(eventProgramDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventProgramDTO.getId().toString()))
            .body(eventProgramDTO);
    }

    /**
     * {@code PATCH  /event-programs/:id} : Partial updates given fields of an existing eventProgram, field will ignore if it is null
     *
     * @param id the id of the eventProgramDTO to save.
     * @param eventProgramDTO the eventProgramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventProgramDTO,
     * or with status {@code 400 (Bad Request)} if the eventProgramDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventProgramDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventProgramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventProgramDTO> partialUpdateEventProgram(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventProgramDTO eventProgramDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventProgram partially : {}, {}", id, eventProgramDTO);
        if (eventProgramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventProgramDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventProgramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventProgramDTO> result = eventProgramService.partialUpdate(eventProgramDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventProgramDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-programs} : get all the eventPrograms.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventPrograms in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EventProgramDTO>> getAllEventPrograms(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of EventPrograms");
        Page<EventProgramDTO> page;
        if (eagerload) {
            page = eventProgramService.findAllWithEagerRelationships(pageable);
        } else {
            page = eventProgramService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-programs/:id} : get the "id" eventProgram.
     *
     * @param id the id of the eventProgramDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventProgramDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventProgramDTO> getEventProgram(@PathVariable("id") Long id) {
        log.debug("REST request to get EventProgram : {}", id);
        Optional<EventProgramDTO> eventProgramDTO = eventProgramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventProgramDTO);
    }

    /**
     * {@code DELETE  /event-programs/:id} : delete the "id" eventProgram.
     *
     * @param id the id of the eventProgramDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventProgram(@PathVariable("id") Long id) {
        log.debug("REST request to delete EventProgram : {}", id);
        eventProgramService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
