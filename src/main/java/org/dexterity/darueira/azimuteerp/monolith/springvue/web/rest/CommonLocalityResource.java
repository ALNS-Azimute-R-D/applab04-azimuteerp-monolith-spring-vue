package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.CommonLocalityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.CommonLocalityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CommonLocalityDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CommonLocality}.
 */
@RestController
@RequestMapping("/api/common-localities")
public class CommonLocalityResource {

    private final Logger log = LoggerFactory.getLogger(CommonLocalityResource.class);

    private static final String ENTITY_NAME = "commonLocality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommonLocalityService commonLocalityService;

    private final CommonLocalityRepository commonLocalityRepository;

    public CommonLocalityResource(CommonLocalityService commonLocalityService, CommonLocalityRepository commonLocalityRepository) {
        this.commonLocalityService = commonLocalityService;
        this.commonLocalityRepository = commonLocalityRepository;
    }

    /**
     * {@code POST  /common-localities} : Create a new commonLocality.
     *
     * @param commonLocalityDTO the commonLocalityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commonLocalityDTO, or with status {@code 400 (Bad Request)} if the commonLocality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CommonLocalityDTO> createCommonLocality(@Valid @RequestBody CommonLocalityDTO commonLocalityDTO)
        throws URISyntaxException {
        log.debug("REST request to save CommonLocality : {}", commonLocalityDTO);
        if (commonLocalityDTO.getId() != null) {
            throw new BadRequestAlertException("A new commonLocality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        commonLocalityDTO = commonLocalityService.save(commonLocalityDTO);
        return ResponseEntity.created(new URI("/api/common-localities/" + commonLocalityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, commonLocalityDTO.getId().toString()))
            .body(commonLocalityDTO);
    }

    /**
     * {@code PUT  /common-localities/:id} : Updates an existing commonLocality.
     *
     * @param id the id of the commonLocalityDTO to save.
     * @param commonLocalityDTO the commonLocalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonLocalityDTO,
     * or with status {@code 400 (Bad Request)} if the commonLocalityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonLocalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonLocalityDTO> updateCommonLocality(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CommonLocalityDTO commonLocalityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CommonLocality : {}, {}", id, commonLocalityDTO);
        if (commonLocalityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commonLocalityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commonLocalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        commonLocalityDTO = commonLocalityService.update(commonLocalityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonLocalityDTO.getId().toString()))
            .body(commonLocalityDTO);
    }

    /**
     * {@code PATCH  /common-localities/:id} : Partial updates given fields of an existing commonLocality, field will ignore if it is null
     *
     * @param id the id of the commonLocalityDTO to save.
     * @param commonLocalityDTO the commonLocalityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonLocalityDTO,
     * or with status {@code 400 (Bad Request)} if the commonLocalityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commonLocalityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commonLocalityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommonLocalityDTO> partialUpdateCommonLocality(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CommonLocalityDTO commonLocalityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommonLocality partially : {}, {}", id, commonLocalityDTO);
        if (commonLocalityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commonLocalityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commonLocalityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommonLocalityDTO> result = commonLocalityService.partialUpdate(commonLocalityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonLocalityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /common-localities} : get all the commonLocalities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commonLocalities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CommonLocalityDTO>> getAllCommonLocalities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of CommonLocalities");
        Page<CommonLocalityDTO> page;
        if (eagerload) {
            page = commonLocalityService.findAllWithEagerRelationships(pageable);
        } else {
            page = commonLocalityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /common-localities/:id} : get the "id" commonLocality.
     *
     * @param id the id of the commonLocalityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commonLocalityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonLocalityDTO> getCommonLocality(@PathVariable("id") Long id) {
        log.debug("REST request to get CommonLocality : {}", id);
        Optional<CommonLocalityDTO> commonLocalityDTO = commonLocalityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commonLocalityDTO);
    }

    /**
     * {@code DELETE  /common-localities/:id} : delete the "id" commonLocality.
     *
     * @param id the id of the commonLocalityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommonLocality(@PathVariable("id") Long id) {
        log.debug("REST request to delete CommonLocality : {}", id);
        commonLocalityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
