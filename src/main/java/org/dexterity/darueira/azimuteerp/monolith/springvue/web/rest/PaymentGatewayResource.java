package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.PaymentGatewayRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.PaymentGatewayService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.PaymentGatewayDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PaymentGateway}.
 */
@RestController
@RequestMapping("/api/payment-gateways")
public class PaymentGatewayResource {

    private final Logger log = LoggerFactory.getLogger(PaymentGatewayResource.class);

    private static final String ENTITY_NAME = "paymentGateway";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentGatewayService paymentGatewayService;

    private final PaymentGatewayRepository paymentGatewayRepository;

    public PaymentGatewayResource(PaymentGatewayService paymentGatewayService, PaymentGatewayRepository paymentGatewayRepository) {
        this.paymentGatewayService = paymentGatewayService;
        this.paymentGatewayRepository = paymentGatewayRepository;
    }

    /**
     * {@code POST  /payment-gateways} : Create a new paymentGateway.
     *
     * @param paymentGatewayDTO the paymentGatewayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentGatewayDTO, or with status {@code 400 (Bad Request)} if the paymentGateway has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PaymentGatewayDTO> createPaymentGateway(@Valid @RequestBody PaymentGatewayDTO paymentGatewayDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentGateway : {}", paymentGatewayDTO);
        if (paymentGatewayDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentGateway cannot already have an ID", ENTITY_NAME, "idexists");
        }
        paymentGatewayDTO = paymentGatewayService.save(paymentGatewayDTO);
        return ResponseEntity.created(new URI("/api/payment-gateways/" + paymentGatewayDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, paymentGatewayDTO.getId().toString()))
            .body(paymentGatewayDTO);
    }

    /**
     * {@code PUT  /payment-gateways/:id} : Updates an existing paymentGateway.
     *
     * @param id the id of the paymentGatewayDTO to save.
     * @param paymentGatewayDTO the paymentGatewayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentGatewayDTO,
     * or with status {@code 400 (Bad Request)} if the paymentGatewayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentGatewayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PaymentGatewayDTO> updatePaymentGateway(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentGatewayDTO paymentGatewayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentGateway : {}, {}", id, paymentGatewayDTO);
        if (paymentGatewayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentGatewayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentGatewayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        paymentGatewayDTO = paymentGatewayService.update(paymentGatewayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentGatewayDTO.getId().toString()))
            .body(paymentGatewayDTO);
    }

    /**
     * {@code PATCH  /payment-gateways/:id} : Partial updates given fields of an existing paymentGateway, field will ignore if it is null
     *
     * @param id the id of the paymentGatewayDTO to save.
     * @param paymentGatewayDTO the paymentGatewayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentGatewayDTO,
     * or with status {@code 400 (Bad Request)} if the paymentGatewayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentGatewayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentGatewayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentGatewayDTO> partialUpdatePaymentGateway(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentGatewayDTO paymentGatewayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentGateway partially : {}, {}", id, paymentGatewayDTO);
        if (paymentGatewayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentGatewayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentGatewayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentGatewayDTO> result = paymentGatewayService.partialUpdate(paymentGatewayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentGatewayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-gateways} : get all the paymentGateways.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentGateways in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PaymentGatewayDTO>> getAllPaymentGateways(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of PaymentGateways");
        Page<PaymentGatewayDTO> page = paymentGatewayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-gateways/:id} : get the "id" paymentGateway.
     *
     * @param id the id of the paymentGatewayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentGatewayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentGatewayDTO> getPaymentGateway(@PathVariable("id") Long id) {
        log.debug("REST request to get PaymentGateway : {}", id);
        Optional<PaymentGatewayDTO> paymentGatewayDTO = paymentGatewayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentGatewayDTO);
    }

    /**
     * {@code DELETE  /payment-gateways/:id} : delete the "id" paymentGateway.
     *
     * @param id the id of the paymentGatewayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentGateway(@PathVariable("id") Long id) {
        log.debug("REST request to delete PaymentGateway : {}", id);
        paymentGatewayService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
