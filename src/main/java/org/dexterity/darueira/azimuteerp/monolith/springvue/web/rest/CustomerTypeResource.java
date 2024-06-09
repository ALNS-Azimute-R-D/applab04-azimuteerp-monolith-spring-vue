package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.CustomerTypeRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.CustomerTypeService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.CustomerTypeDTO;
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
 * REST controller for managing {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.CustomerType}.
 */
@RestController
@RequestMapping("/api/customer-types")
public class CustomerTypeResource {

    private final Logger log = LoggerFactory.getLogger(CustomerTypeResource.class);

    private static final String ENTITY_NAME = "customerType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerTypeService customerTypeService;

    private final CustomerTypeRepository customerTypeRepository;

    public CustomerTypeResource(CustomerTypeService customerTypeService, CustomerTypeRepository customerTypeRepository) {
        this.customerTypeService = customerTypeService;
        this.customerTypeRepository = customerTypeRepository;
    }

    /**
     * {@code POST  /customer-types} : Create a new customerType.
     *
     * @param customerTypeDTO the customerTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerTypeDTO, or with status {@code 400 (Bad Request)} if the customerType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CustomerTypeDTO> createCustomerType(@Valid @RequestBody CustomerTypeDTO customerTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomerType : {}", customerTypeDTO);
        if (customerTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        customerTypeDTO = customerTypeService.save(customerTypeDTO);
        return ResponseEntity.created(new URI("/api/customer-types/" + customerTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, customerTypeDTO.getId().toString()))
            .body(customerTypeDTO);
    }

    /**
     * {@code PUT  /customer-types/:id} : Updates an existing customerType.
     *
     * @param id the id of the customerTypeDTO to save.
     * @param customerTypeDTO the customerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the customerTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerTypeDTO> updateCustomerType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerTypeDTO customerTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerType : {}, {}", id, customerTypeDTO);
        if (customerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        customerTypeDTO = customerTypeService.update(customerTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerTypeDTO.getId().toString()))
            .body(customerTypeDTO);
    }

    /**
     * {@code PATCH  /customer-types/:id} : Partial updates given fields of an existing customerType, field will ignore if it is null
     *
     * @param id the id of the customerTypeDTO to save.
     * @param customerTypeDTO the customerTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerTypeDTO,
     * or with status {@code 400 (Bad Request)} if the customerTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customerTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomerTypeDTO> partialUpdateCustomerType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerTypeDTO customerTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerType partially : {}, {}", id, customerTypeDTO);
        if (customerTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerTypeDTO> result = customerTypeService.partialUpdate(customerTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-types} : get all the customerTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CustomerTypeDTO>> getAllCustomerTypes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CustomerTypes");
        Page<CustomerTypeDTO> page = customerTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customer-types/:id} : get the "id" customerType.
     *
     * @param id the id of the customerTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerTypeDTO> getCustomerType(@PathVariable("id") Long id) {
        log.debug("REST request to get CustomerType : {}", id);
        Optional<CustomerTypeDTO> customerTypeDTO = customerTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerTypeDTO);
    }

    /**
     * {@code DELETE  /customer-types/:id} : delete the "id" customerType.
     *
     * @param id the id of the customerTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerType(@PathVariable("id") Long id) {
        log.debug("REST request to delete CustomerType : {}", id);
        customerTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
