package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TownCityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TownCityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TownCityDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TownCity}.
 */
@RestController
@RequestMapping("/api/town-cities")
public class TownCityResource {

    private final Logger log = LoggerFactory.getLogger(TownCityResource.class);

    private static final String ENTITY_NAME = "townCity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TownCityService townCityService;

    private final TownCityRepository townCityRepository;

    public TownCityResource(TownCityService townCityService, TownCityRepository townCityRepository) {
        this.townCityService = townCityService;
        this.townCityRepository = townCityRepository;
    }

    /**
     * {@code POST  /town-cities} : Create a new townCity.
     *
     * @param townCityDTO the townCityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new townCityDTO, or with status {@code 400 (Bad Request)} if the townCity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TownCityDTO> createTownCity(@Valid @RequestBody TownCityDTO townCityDTO) throws URISyntaxException {
        log.debug("REST request to save TownCity : {}", townCityDTO);
        if (townCityDTO.getId() != null) {
            throw new BadRequestAlertException("A new townCity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        townCityDTO = townCityService.save(townCityDTO);
        return ResponseEntity.created(new URI("/api/town-cities/" + townCityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, townCityDTO.getId().toString()))
            .body(townCityDTO);
    }

    /**
     * {@code PUT  /town-cities/:id} : Updates an existing townCity.
     *
     * @param id the id of the townCityDTO to save.
     * @param townCityDTO the townCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townCityDTO,
     * or with status {@code 400 (Bad Request)} if the townCityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the townCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TownCityDTO> updateTownCity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TownCityDTO townCityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TownCity : {}, {}", id, townCityDTO);
        if (townCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, townCityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!townCityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        townCityDTO = townCityService.update(townCityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townCityDTO.getId().toString()))
            .body(townCityDTO);
    }

    /**
     * {@code PATCH  /town-cities/:id} : Partial updates given fields of an existing townCity, field will ignore if it is null
     *
     * @param id the id of the townCityDTO to save.
     * @param townCityDTO the townCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated townCityDTO,
     * or with status {@code 400 (Bad Request)} if the townCityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the townCityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the townCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TownCityDTO> partialUpdateTownCity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TownCityDTO townCityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TownCity partially : {}, {}", id, townCityDTO);
        if (townCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, townCityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!townCityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TownCityDTO> result = townCityService.partialUpdate(townCityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, townCityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /town-cities} : get all the townCities.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of townCities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TownCityDTO>> getAllTownCities(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TownCities");
        Page<TownCityDTO> page;
        if (eagerload) {
            page = townCityService.findAllWithEagerRelationships(pageable);
        } else {
            page = townCityService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /town-cities/:id} : get the "id" townCity.
     *
     * @param id the id of the townCityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the townCityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TownCityDTO> getTownCity(@PathVariable("id") Long id) {
        log.debug("REST request to get TownCity : {}", id);
        Optional<TownCityDTO> townCityDTO = townCityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(townCityDTO);
    }

    /**
     * {@code DELETE  /town-cities/:id} : delete the "id" townCity.
     *
     * @param id the id of the townCityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTownCity(@PathVariable("id") Long id) {
        log.debug("REST request to delete TownCity : {}", id);
        townCityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
