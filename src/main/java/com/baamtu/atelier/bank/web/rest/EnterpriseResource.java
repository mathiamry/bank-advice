package com.baamtu.atelier.bank.web.rest;

import com.baamtu.atelier.bank.repository.EnterpriseRepository;
import com.baamtu.atelier.bank.service.EnterpriseService;
import com.baamtu.atelier.bank.service.dto.EnterpriseDTO;
import com.baamtu.atelier.bank.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.baamtu.atelier.bank.domain.Enterprise}.
 */
@RestController
@RequestMapping("/api")
public class EnterpriseResource {

    private final Logger log = LoggerFactory.getLogger(EnterpriseResource.class);

    private static final String ENTITY_NAME = "enterprise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnterpriseService enterpriseService;

    private final EnterpriseRepository enterpriseRepository;

    public EnterpriseResource(EnterpriseService enterpriseService, EnterpriseRepository enterpriseRepository) {
        this.enterpriseService = enterpriseService;
        this.enterpriseRepository = enterpriseRepository;
    }

    /**
     * {@code POST  /enterprises} : Create a new enterprise.
     *
     * @param enterpriseDTO the enterpriseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enterpriseDTO, or with status {@code 400 (Bad Request)} if the enterprise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enterprises")
    public ResponseEntity<EnterpriseDTO> createEnterprise(@Valid @RequestBody EnterpriseDTO enterpriseDTO) throws URISyntaxException {
        log.debug("REST request to save Enterprise : {}", enterpriseDTO);
        if (enterpriseDTO.getId() != null) {
            throw new BadRequestAlertException("A new enterprise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnterpriseDTO result = enterpriseService.save(enterpriseDTO);
        return ResponseEntity
            .created(new URI("/api/enterprises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enterprises/:id} : Updates an existing enterprise.
     *
     * @param id the id of the enterpriseDTO to save.
     * @param enterpriseDTO the enterpriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enterpriseDTO,
     * or with status {@code 400 (Bad Request)} if the enterpriseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enterpriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enterprises/{id}")
    public ResponseEntity<EnterpriseDTO> updateEnterprise(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnterpriseDTO enterpriseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Enterprise : {}, {}", id, enterpriseDTO);
        if (enterpriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enterpriseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enterpriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnterpriseDTO result = enterpriseService.save(enterpriseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enterpriseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /enterprises/:id} : Partial updates given fields of an existing enterprise, field will ignore if it is null
     *
     * @param id the id of the enterpriseDTO to save.
     * @param enterpriseDTO the enterpriseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enterpriseDTO,
     * or with status {@code 400 (Bad Request)} if the enterpriseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enterpriseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enterpriseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/enterprises/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EnterpriseDTO> partialUpdateEnterprise(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnterpriseDTO enterpriseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Enterprise partially : {}, {}", id, enterpriseDTO);
        if (enterpriseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enterpriseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enterpriseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnterpriseDTO> result = enterpriseService.partialUpdate(enterpriseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enterpriseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /enterprises} : get all the enterprises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enterprises in body.
     */
    @GetMapping("/enterprises")
    public List<EnterpriseDTO> getAllEnterprises() {
        log.debug("REST request to get all Enterprises");
        return enterpriseService.findAll();
    }

    /**
     * {@code GET  /enterprises/:id} : get the "id" enterprise.
     *
     * @param id the id of the enterpriseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enterpriseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enterprises/{id}")
    public ResponseEntity<EnterpriseDTO> getEnterprise(@PathVariable Long id) {
        log.debug("REST request to get Enterprise : {}", id);
        Optional<EnterpriseDTO> enterpriseDTO = enterpriseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enterpriseDTO);
    }

    /**
     * {@code DELETE  /enterprises/:id} : delete the "id" enterprise.
     *
     * @param id the id of the enterpriseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enterprises/{id}")
    public ResponseEntity<Void> deleteEnterprise(@PathVariable Long id) {
        log.debug("REST request to delete Enterprise : {}", id);
        enterpriseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
