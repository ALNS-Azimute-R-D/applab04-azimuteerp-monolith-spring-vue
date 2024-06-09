package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ArtisticGenreRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ArtisticGenreService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ArtisticGenreDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ArtisticGenre}.
 */
@RestController
@RequestMapping("/api/artistic-genres")
public class ArtisticGenreResource {

    private final Logger log = LoggerFactory.getLogger(ArtisticGenreResource.class);

    private static final String ENTITY_NAME = "artisticGenre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtisticGenreService artisticGenreService;

    private final ArtisticGenreRepository artisticGenreRepository;

    public ArtisticGenreResource(ArtisticGenreService artisticGenreService, ArtisticGenreRepository artisticGenreRepository) {
        this.artisticGenreService = artisticGenreService;
        this.artisticGenreRepository = artisticGenreRepository;
    }

    /**
     * {@code POST  /artistic-genres} : Create a new artisticGenre.
     *
     * @param artisticGenreDTO the artisticGenreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artisticGenreDTO, or with status {@code 400 (Bad Request)} if the artisticGenre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArtisticGenreDTO> createArtisticGenre(@Valid @RequestBody ArtisticGenreDTO artisticGenreDTO)
        throws URISyntaxException {
        log.debug("REST request to save ArtisticGenre : {}", artisticGenreDTO);
        if (artisticGenreDTO.getId() != null) {
            throw new BadRequestAlertException("A new artisticGenre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        artisticGenreDTO = artisticGenreService.save(artisticGenreDTO);
        return ResponseEntity.created(new URI("/api/artistic-genres/" + artisticGenreDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, artisticGenreDTO.getId().toString()))
            .body(artisticGenreDTO);
    }

    /**
     * {@code PUT  /artistic-genres/:id} : Updates an existing artisticGenre.
     *
     * @param id the id of the artisticGenreDTO to save.
     * @param artisticGenreDTO the artisticGenreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artisticGenreDTO,
     * or with status {@code 400 (Bad Request)} if the artisticGenreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artisticGenreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtisticGenreDTO> updateArtisticGenre(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArtisticGenreDTO artisticGenreDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArtisticGenre : {}, {}", id, artisticGenreDTO);
        if (artisticGenreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artisticGenreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artisticGenreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        artisticGenreDTO = artisticGenreService.update(artisticGenreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artisticGenreDTO.getId().toString()))
            .body(artisticGenreDTO);
    }

    /**
     * {@code PATCH  /artistic-genres/:id} : Partial updates given fields of an existing artisticGenre, field will ignore if it is null
     *
     * @param id the id of the artisticGenreDTO to save.
     * @param artisticGenreDTO the artisticGenreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artisticGenreDTO,
     * or with status {@code 400 (Bad Request)} if the artisticGenreDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artisticGenreDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artisticGenreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArtisticGenreDTO> partialUpdateArtisticGenre(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArtisticGenreDTO artisticGenreDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArtisticGenre partially : {}, {}", id, artisticGenreDTO);
        if (artisticGenreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artisticGenreDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artisticGenreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtisticGenreDTO> result = artisticGenreService.partialUpdate(artisticGenreDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artisticGenreDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artistic-genres} : get all the artisticGenres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artisticGenres in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArtisticGenreDTO>> getAllArtisticGenres(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ArtisticGenres");
        Page<ArtisticGenreDTO> page = artisticGenreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artistic-genres/:id} : get the "id" artisticGenre.
     *
     * @param id the id of the artisticGenreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artisticGenreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtisticGenreDTO> getArtisticGenre(@PathVariable("id") Long id) {
        log.debug("REST request to get ArtisticGenre : {}", id);
        Optional<ArtisticGenreDTO> artisticGenreDTO = artisticGenreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artisticGenreDTO);
    }

    /**
     * {@code DELETE  /artistic-genres/:id} : delete the "id" artisticGenre.
     *
     * @param id the id of the artisticGenreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtisticGenre(@PathVariable("id") Long id) {
        log.debug("REST request to delete ArtisticGenre : {}", id);
        artisticGenreService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
