package com.isoft.tms.web.rest;

import com.isoft.tms.service.CenterWorkingDaysService;
import com.isoft.tms.web.rest.errors.BadRequestAlertException;
import com.isoft.tms.service.dto.CenterWorkingDaysDTO;
import com.isoft.tms.service.dto.CenterWorkingDaysCriteria;
import com.isoft.tms.service.CenterWorkingDaysQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.tms.domain.CenterWorkingDays}.
 */
@RestController
@RequestMapping("/api")
public class CenterWorkingDaysResource {

    private final Logger log = LoggerFactory.getLogger(CenterWorkingDaysResource.class);

    private static final String ENTITY_NAME = "stuffMgmtMsCenterWorkingDays";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CenterWorkingDaysService centerWorkingDaysService;

    private final CenterWorkingDaysQueryService centerWorkingDaysQueryService;

    public CenterWorkingDaysResource(CenterWorkingDaysService centerWorkingDaysService, CenterWorkingDaysQueryService centerWorkingDaysQueryService) {
        this.centerWorkingDaysService = centerWorkingDaysService;
        this.centerWorkingDaysQueryService = centerWorkingDaysQueryService;
    }

    /**
     * {@code POST  /center-working-days} : Create a new centerWorkingDays.
     *
     * @param centerWorkingDaysDTO the centerWorkingDaysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centerWorkingDaysDTO, or with status {@code 400 (Bad Request)} if the centerWorkingDays has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/center-working-days")
    public ResponseEntity<CenterWorkingDaysDTO> createCenterWorkingDays(@RequestBody CenterWorkingDaysDTO centerWorkingDaysDTO) throws URISyntaxException {
        log.debug("REST request to save CenterWorkingDays : {}", centerWorkingDaysDTO);
        if (centerWorkingDaysDTO.getId() != null) {
            throw new BadRequestAlertException("A new centerWorkingDays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CenterWorkingDaysDTO result = centerWorkingDaysService.save(centerWorkingDaysDTO);
        return ResponseEntity.created(new URI("/api/center-working-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /center-working-days} : Updates an existing centerWorkingDays.
     *
     * @param centerWorkingDaysDTO the centerWorkingDaysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerWorkingDaysDTO,
     * or with status {@code 400 (Bad Request)} if the centerWorkingDaysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centerWorkingDaysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/center-working-days")
    public ResponseEntity<CenterWorkingDaysDTO> updateCenterWorkingDays(@RequestBody CenterWorkingDaysDTO centerWorkingDaysDTO) throws URISyntaxException {
        log.debug("REST request to update CenterWorkingDays : {}", centerWorkingDaysDTO);
        if (centerWorkingDaysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CenterWorkingDaysDTO result = centerWorkingDaysService.save(centerWorkingDaysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerWorkingDaysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /center-working-days} : get all the centerWorkingDays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centerWorkingDays in body.
     */
    @GetMapping("/center-working-days")
    public ResponseEntity<List<CenterWorkingDaysDTO>> getAllCenterWorkingDays(CenterWorkingDaysCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CenterWorkingDays by criteria: {}", criteria);
        Page<CenterWorkingDaysDTO> page = centerWorkingDaysQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /center-working-days/count} : count all the centerWorkingDays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/center-working-days/count")
    public ResponseEntity<Long> countCenterWorkingDays(CenterWorkingDaysCriteria criteria) {
        log.debug("REST request to count CenterWorkingDays by criteria: {}", criteria);
        return ResponseEntity.ok().body(centerWorkingDaysQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /center-working-days/:id} : get the "id" centerWorkingDays.
     *
     * @param id the id of the centerWorkingDaysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centerWorkingDaysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/center-working-days/{id}")
    public ResponseEntity<CenterWorkingDaysDTO> getCenterWorkingDays(@PathVariable Long id) {
        log.debug("REST request to get CenterWorkingDays : {}", id);
        Optional<CenterWorkingDaysDTO> centerWorkingDaysDTO = centerWorkingDaysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centerWorkingDaysDTO);
    }

    /**
     * {@code DELETE  /center-working-days/:id} : delete the "id" centerWorkingDays.
     *
     * @param id the id of the centerWorkingDaysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/center-working-days/{id}")
    public ResponseEntity<Void> deleteCenterWorkingDays(@PathVariable Long id) {
        log.debug("REST request to delete CenterWorkingDays : {}", id);
        centerWorkingDaysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
