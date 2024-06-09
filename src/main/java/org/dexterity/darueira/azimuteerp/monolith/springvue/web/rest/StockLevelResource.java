package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.StockLevelRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.StockLevelService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.StockLevelDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.StockLevel}.
 */
@RestController
@RequestMapping("/api/stock-levels")
public class StockLevelResource {

    private final Logger log = LoggerFactory.getLogger(StockLevelResource.class);

    private static final String ENTITY_NAME = "stockLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockLevelService stockLevelService;

    private final StockLevelRepository stockLevelRepository;

    public StockLevelResource(StockLevelService stockLevelService, StockLevelRepository stockLevelRepository) {
        this.stockLevelService = stockLevelService;
        this.stockLevelRepository = stockLevelRepository;
    }

    /**
     * {@code POST  /stock-levels} : Create a new stockLevel.
     *
     * @param stockLevelDTO the stockLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockLevelDTO, or with status {@code 400 (Bad Request)} if the stockLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StockLevelDTO> createStockLevel(@Valid @RequestBody StockLevelDTO stockLevelDTO) throws URISyntaxException {
        log.debug("REST request to save StockLevel : {}", stockLevelDTO);
        if (stockLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        stockLevelDTO = stockLevelService.save(stockLevelDTO);
        return ResponseEntity.created(new URI("/api/stock-levels/" + stockLevelDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, stockLevelDTO.getId().toString()))
            .body(stockLevelDTO);
    }

    /**
     * {@code PUT  /stock-levels/:id} : Updates an existing stockLevel.
     *
     * @param id the id of the stockLevelDTO to save.
     * @param stockLevelDTO the stockLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockLevelDTO,
     * or with status {@code 400 (Bad Request)} if the stockLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockLevelDTO> updateStockLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StockLevelDTO stockLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StockLevel : {}, {}", id, stockLevelDTO);
        if (stockLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        stockLevelDTO = stockLevelService.update(stockLevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockLevelDTO.getId().toString()))
            .body(stockLevelDTO);
    }

    /**
     * {@code PATCH  /stock-levels/:id} : Partial updates given fields of an existing stockLevel, field will ignore if it is null
     *
     * @param id the id of the stockLevelDTO to save.
     * @param stockLevelDTO the stockLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockLevelDTO,
     * or with status {@code 400 (Bad Request)} if the stockLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the stockLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the stockLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StockLevelDTO> partialUpdateStockLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StockLevelDTO stockLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StockLevel partially : {}, {}", id, stockLevelDTO);
        if (stockLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, stockLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!stockLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StockLevelDTO> result = stockLevelService.partialUpdate(stockLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /stock-levels} : get all the stockLevels.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockLevels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StockLevelDTO>> getAllStockLevels(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of StockLevels");
        Page<StockLevelDTO> page;
        if (eagerload) {
            page = stockLevelService.findAllWithEagerRelationships(pageable);
        } else {
            page = stockLevelService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stock-levels/:id} : get the "id" stockLevel.
     *
     * @param id the id of the stockLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockLevelDTO> getStockLevel(@PathVariable("id") Long id) {
        log.debug("REST request to get StockLevel : {}", id);
        Optional<StockLevelDTO> stockLevelDTO = stockLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockLevelDTO);
    }

    /**
     * {@code DELETE  /stock-levels/:id} : delete the "id" stockLevel.
     *
     * @param id the id of the stockLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockLevel(@PathVariable("id") Long id) {
        log.debug("REST request to delete StockLevel : {}", id);
        stockLevelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
