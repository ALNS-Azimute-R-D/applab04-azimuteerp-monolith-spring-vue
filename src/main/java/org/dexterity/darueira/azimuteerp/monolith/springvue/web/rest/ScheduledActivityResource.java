package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ScheduledActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ScheduledActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ScheduledActivityDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity}.
 */
@RestController
@RequestMapping("/api/scheduled-activities")
public class ScheduledActivityResource {

    private final Logger log = LoggerFactory.getLogger(ScheduledActivityResource.class);

    private static final String ENTITY_NAME = "scheduledActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduledActivityService scheduledActivityService;

    private final ScheduledActivityRepository scheduledActivityRepository;

    public ScheduledActivityResource(
        ScheduledActivityService scheduledActivityService,
        ScheduledActivityRepository scheduledActivityRepository
    ) {
        this.scheduledActivityService = scheduledActivityService;
        this.scheduledActivityRepository = scheduledActivityRepository;
    }

    /**
     * {@code POST  /scheduled-activities} : Create a new scheduledActivity.
     *
     * @param scheduledActivityDTO the scheduledActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduledActivityDTO, or with status {@code 400 (Bad Request)} if the scheduledActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ScheduledActivityDTO> createScheduledActivity(@Valid @RequestBody ScheduledActivityDTO scheduledActivityDTO)
        throws URISyntaxException {
        log.debug("REST request to save ScheduledActivity : {}", scheduledActivityDTO);
        if (scheduledActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduledActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        scheduledActivityDTO = scheduledActivityService.save(scheduledActivityDTO);
        return ResponseEntity.created(new URI("/api/scheduled-activities/" + scheduledActivityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, scheduledActivityDTO.getId().toString()))
            .body(scheduledActivityDTO);
    }

    /**
     * {@code PUT  /scheduled-activities/:id} : Updates an existing scheduledActivity.
     *
     * @param id the id of the scheduledActivityDTO to save.
     * @param scheduledActivityDTO the scheduledActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduledActivityDTO,
     * or with status {@code 400 (Bad Request)} if the scheduledActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduledActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduledActivityDTO> updateScheduledActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ScheduledActivityDTO scheduledActivityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ScheduledActivity : {}, {}", id, scheduledActivityDTO);
        if (scheduledActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheduledActivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheduledActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        scheduledActivityDTO = scheduledActivityService.update(scheduledActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduledActivityDTO.getId().toString()))
            .body(scheduledActivityDTO);
    }

    /**
     * {@code PATCH  /scheduled-activities/:id} : Partial updates given fields of an existing scheduledActivity, field will ignore if it is null
     *
     * @param id the id of the scheduledActivityDTO to save.
     * @param scheduledActivityDTO the scheduledActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduledActivityDTO,
     * or with status {@code 400 (Bad Request)} if the scheduledActivityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the scheduledActivityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the scheduledActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ScheduledActivityDTO> partialUpdateScheduledActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ScheduledActivityDTO scheduledActivityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ScheduledActivity partially : {}, {}", id, scheduledActivityDTO);
        if (scheduledActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, scheduledActivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scheduledActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ScheduledActivityDTO> result = scheduledActivityService.partialUpdate(scheduledActivityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduledActivityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /scheduled-activities} : get all the scheduledActivities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduledActivities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ScheduledActivityDTO>> getAllScheduledActivities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ScheduledActivities");
        Page<ScheduledActivityDTO> page;
        if (eagerload) {
            page = scheduledActivityService.findAllWithEagerRelationships(pageable);
        } else {
            page = scheduledActivityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scheduled-activities/:id} : get the "id" scheduledActivity.
     *
     * @param id the id of the scheduledActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduledActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduledActivityDTO> getScheduledActivity(@PathVariable("id") Long id) {
        log.debug("REST request to get ScheduledActivity : {}", id);
        Optional<ScheduledActivityDTO> scheduledActivityDTO = scheduledActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduledActivityDTO);
    }

    /**
     * {@code DELETE  /scheduled-activities/:id} : delete the "id" scheduledActivity.
     *
     * @param id the id of the scheduledActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduledActivity(@PathVariable("id") Long id) {
        log.debug("REST request to delete ScheduledActivity : {}", id);
        scheduledActivityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
