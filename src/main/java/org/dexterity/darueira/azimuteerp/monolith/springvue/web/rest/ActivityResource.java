package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ActivityDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity}.
 */
@RestController
@RequestMapping("/api/activities")
public class ActivityResource {

    private final Logger log = LoggerFactory.getLogger(ActivityResource.class);

    private static final String ENTITY_NAME = "activity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityService activityService;

    private final ActivityRepository activityRepository;

    public ActivityResource(ActivityService activityService, ActivityRepository activityRepository) {
        this.activityService = activityService;
        this.activityRepository = activityRepository;
    }

    /**
     * {@code POST  /activities} : Create a new activity.
     *
     * @param activityDTO the activityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityDTO, or with status {@code 400 (Bad Request)} if the activity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityDTO activityDTO) throws URISyntaxException {
        log.debug("REST request to save Activity : {}", activityDTO);
        if (activityDTO.getId() != null) {
            throw new BadRequestAlertException("A new activity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        activityDTO = activityService.save(activityDTO);
        return ResponseEntity.created(new URI("/api/activities/" + activityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, activityDTO.getId().toString()))
            .body(activityDTO);
    }

    /**
     * {@code PUT  /activities/:id} : Updates an existing activity.
     *
     * @param id the id of the activityDTO to save.
     * @param activityDTO the activityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityDTO,
     * or with status {@code 400 (Bad Request)} if the activityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActivityDTO activityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Activity : {}, {}", id, activityDTO);
        if (activityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        activityDTO = activityService.update(activityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityDTO.getId().toString()))
            .body(activityDTO);
    }

    /**
     * {@code PATCH  /activities/:id} : Partial updates given fields of an existing activity, field will ignore if it is null
     *
     * @param id the id of the activityDTO to save.
     * @param activityDTO the activityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityDTO,
     * or with status {@code 400 (Bad Request)} if the activityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the activityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the activityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivityDTO> partialUpdateActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActivityDTO activityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Activity partially : {}, {}", id, activityDTO);
        if (activityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivityDTO> result = activityService.partialUpdate(activityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /activities} : get all the activities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ActivityDTO>> getAllActivities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Activities");
        Page<ActivityDTO> page;
        if (eagerload) {
            page = activityService.findAllWithEagerRelationships(pageable);
        } else {
            page = activityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /activities/:id} : get the "id" activity.
     *
     * @param id the id of the activityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivity(@PathVariable("id") Long id) {
        log.debug("REST request to get Activity : {}", id);
        Optional<ActivityDTO> activityDTO = activityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activityDTO);
    }

    /**
     * {@code DELETE  /activities/:id} : delete the "id" activity.
     *
     * @param id the id of the activityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") Long id) {
        log.debug("REST request to delete Activity : {}", id);
        activityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
