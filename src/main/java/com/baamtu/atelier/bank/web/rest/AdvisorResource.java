package com.baamtu.atelier.bank.web.rest;

import com.baamtu.atelier.bank.domain.Advisor;
import com.baamtu.atelier.bank.repository.AdvisorRepository;
import com.baamtu.atelier.bank.service.AdvisorService;
import com.baamtu.atelier.bank.service.dto.AdvisorDTO;
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
 * REST controller for managing {@link com.baamtu.atelier.bank.domain.Advisor}.
 */
@RestController
@RequestMapping("/api")
public class AdvisorResource {

    private final Logger log = LoggerFactory.getLogger(AdvisorResource.class);

    private static final String ENTITY_NAME = "advisor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdvisorService advisorService;

    private final AdvisorRepository advisorRepository;

    public AdvisorResource(AdvisorService advisorService, AdvisorRepository advisorRepository) {
        this.advisorService = advisorService;
        this.advisorRepository = advisorRepository;
    }

    /**
     * {@code POST  /advisors} : Create a new advisor.
     *
     * @param advisorDTO the advisorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new advisorDTO, or with status {@code 400 (Bad Request)} if the advisor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/advisors")
    public ResponseEntity<AdvisorDTO> createAdvisor(@Valid @RequestBody AdvisorDTO advisorDTO) throws URISyntaxException {
        log.debug("REST request to save Advisor : {}", advisorDTO);
        if (advisorDTO.getId() != null) {
            throw new BadRequestAlertException("A new advisor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdvisorDTO result = advisorService.save(advisorDTO);
        return ResponseEntity
            .created(new URI("/api/advisors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /advisors/:id} : Updates an existing advisor.
     *
     * @param id the id of the advisorDTO to save.
     * @param advisorDTO the advisorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advisorDTO,
     * or with status {@code 400 (Bad Request)} if the advisorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the advisorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/advisors/{id}")
    public ResponseEntity<AdvisorDTO> updateAdvisor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdvisorDTO advisorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Advisor : {}, {}", id, advisorDTO);
        if (advisorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, advisorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!advisorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdvisorDTO result = advisorService.save(advisorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advisorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /advisors/:id} : Partial updates given fields of an existing advisor, field will ignore if it is null
     *
     * @param id the id of the advisorDTO to save.
     * @param advisorDTO the advisorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated advisorDTO,
     * or with status {@code 400 (Bad Request)} if the advisorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the advisorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the advisorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/advisors/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdvisorDTO> partialUpdateAdvisor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdvisorDTO advisorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Advisor partially : {}, {}", id, advisorDTO);
        if (advisorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, advisorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!advisorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdvisorDTO> result = advisorService.partialUpdate(advisorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, advisorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /advisors} : get all the advisors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of advisors in body.
     */
    @GetMapping("/advisors")
    public List<AdvisorDTO> getAllAdvisors() {
        log.debug("REST request to get all Advisors");
        return advisorService.findAll();
    }

    @GetMapping("/advisors/user")
    public Advisor getCurrentUser() {
        log.debug("REST request to get the current User");
        return advisorRepository.findByUserIsCurrentUser();
    }

    /**
     * {@code GET  /advisors/:id} : get the "id" advisor.
     *
     * @param id the id of the advisorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the advisorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/advisors/{id}")
    public ResponseEntity<AdvisorDTO> getAdvisor(@PathVariable Long id) {
        log.debug("REST request to get Advisor : {}", id);
        Optional<AdvisorDTO> advisorDTO = advisorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(advisorDTO);
    }

    /**
     * {@code DELETE  /advisors/:id} : delete the "id" advisor.
     *
     * @param id the id of the advisorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/advisors/{id}")
    public ResponseEntity<Void> deleteAdvisor(@PathVariable Long id) {
        log.debug("REST request to delete Advisor : {}", id);
        advisorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
