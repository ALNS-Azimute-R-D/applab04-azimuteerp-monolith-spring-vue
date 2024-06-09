package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TicketPurchasedRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.TicketPurchasedService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TicketPurchasedDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TicketPurchased}.
 */
@RestController
@RequestMapping("/api/ticket-purchaseds")
public class TicketPurchasedResource {

    private final Logger log = LoggerFactory.getLogger(TicketPurchasedResource.class);

    private static final String ENTITY_NAME = "ticketPurchased";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketPurchasedService ticketPurchasedService;

    private final TicketPurchasedRepository ticketPurchasedRepository;

    public TicketPurchasedResource(TicketPurchasedService ticketPurchasedService, TicketPurchasedRepository ticketPurchasedRepository) {
        this.ticketPurchasedService = ticketPurchasedService;
        this.ticketPurchasedRepository = ticketPurchasedRepository;
    }

    /**
     * {@code POST  /ticket-purchaseds} : Create a new ticketPurchased.
     *
     * @param ticketPurchasedDTO the ticketPurchasedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketPurchasedDTO, or with status {@code 400 (Bad Request)} if the ticketPurchased has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TicketPurchasedDTO> createTicketPurchased(@Valid @RequestBody TicketPurchasedDTO ticketPurchasedDTO)
        throws URISyntaxException {
        log.debug("REST request to save TicketPurchased : {}", ticketPurchasedDTO);
        if (ticketPurchasedDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketPurchased cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ticketPurchasedDTO = ticketPurchasedService.save(ticketPurchasedDTO);
        return ResponseEntity.created(new URI("/api/ticket-purchaseds/" + ticketPurchasedDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ticketPurchasedDTO.getId().toString()))
            .body(ticketPurchasedDTO);
    }

    /**
     * {@code PUT  /ticket-purchaseds/:id} : Updates an existing ticketPurchased.
     *
     * @param id the id of the ticketPurchasedDTO to save.
     * @param ticketPurchasedDTO the ticketPurchasedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketPurchasedDTO,
     * or with status {@code 400 (Bad Request)} if the ticketPurchasedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketPurchasedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketPurchasedDTO> updateTicketPurchased(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TicketPurchasedDTO ticketPurchasedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TicketPurchased : {}, {}", id, ticketPurchasedDTO);
        if (ticketPurchasedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketPurchasedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketPurchasedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ticketPurchasedDTO = ticketPurchasedService.update(ticketPurchasedDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketPurchasedDTO.getId().toString()))
            .body(ticketPurchasedDTO);
    }

    /**
     * {@code PATCH  /ticket-purchaseds/:id} : Partial updates given fields of an existing ticketPurchased, field will ignore if it is null
     *
     * @param id the id of the ticketPurchasedDTO to save.
     * @param ticketPurchasedDTO the ticketPurchasedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketPurchasedDTO,
     * or with status {@code 400 (Bad Request)} if the ticketPurchasedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketPurchasedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketPurchasedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TicketPurchasedDTO> partialUpdateTicketPurchased(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TicketPurchasedDTO ticketPurchasedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TicketPurchased partially : {}, {}", id, ticketPurchasedDTO);
        if (ticketPurchasedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketPurchasedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketPurchasedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TicketPurchasedDTO> result = ticketPurchasedService.partialUpdate(ticketPurchasedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ticketPurchasedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ticket-purchaseds} : get all the ticketPurchaseds.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketPurchaseds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TicketPurchasedDTO>> getAllTicketPurchaseds(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TicketPurchaseds");
        Page<TicketPurchasedDTO> page;
        if (eagerload) {
            page = ticketPurchasedService.findAllWithEagerRelationships(pageable);
        } else {
            page = ticketPurchasedService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticket-purchaseds/:id} : get the "id" ticketPurchased.
     *
     * @param id the id of the ticketPurchasedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketPurchasedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketPurchasedDTO> getTicketPurchased(@PathVariable("id") Long id) {
        log.debug("REST request to get TicketPurchased : {}", id);
        Optional<TicketPurchasedDTO> ticketPurchasedDTO = ticketPurchasedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketPurchasedDTO);
    }

    /**
     * {@code DELETE  /ticket-purchaseds/:id} : delete the "id" ticketPurchased.
     *
     * @param id the id of the ticketPurchasedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketPurchased(@PathVariable("id") Long id) {
        log.debug("REST request to delete TicketPurchased : {}", id);
        ticketPurchasedService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
