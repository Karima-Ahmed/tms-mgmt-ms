package com.isoft.tms.web.rest;

import com.isoft.tms.service.WorkingDaysService;
import com.isoft.tms.web.rest.errors.BadRequestAlertException;
import com.isoft.tms.service.dto.WorkingDaysDTO;
import com.isoft.tms.service.dto.WorkingDaysCriteria;
import com.isoft.tms.service.WorkingDaysQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.tms.domain.WorkingDays}.
 */
@RestController
@RequestMapping("/api")
public class WorkingDaysResource {

    private final Logger log = LoggerFactory.getLogger(WorkingDaysResource.class);

    private static final String ENTITY_NAME = "stuffMgmtMsWorkingDays";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingDaysService workingDaysService;

    private final WorkingDaysQueryService workingDaysQueryService;

    public WorkingDaysResource(WorkingDaysService workingDaysService, WorkingDaysQueryService workingDaysQueryService) {
        this.workingDaysService = workingDaysService;
        this.workingDaysQueryService = workingDaysQueryService;
    }

    /**
     * {@code POST  /working-days} : Create a new workingDays.
     *
     * @param workingDaysDTO the workingDaysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingDaysDTO, or with status {@code 400 (Bad Request)} if the workingDays has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-days")
    public ResponseEntity<WorkingDaysDTO> createWorkingDays(@Valid @RequestBody WorkingDaysDTO workingDaysDTO) throws URISyntaxException {
        log.debug("REST request to save WorkingDays : {}", workingDaysDTO);
        if (workingDaysDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingDays cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingDaysDTO result = workingDaysService.save(workingDaysDTO);
        return ResponseEntity.created(new URI("/api/working-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /working-days} : Updates an existing workingDays.
     *
     * @param workingDaysDTO the workingDaysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingDaysDTO,
     * or with status {@code 400 (Bad Request)} if the workingDaysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingDaysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-days")
    public ResponseEntity<WorkingDaysDTO> updateWorkingDays(@Valid @RequestBody WorkingDaysDTO workingDaysDTO) throws URISyntaxException {
        log.debug("REST request to update WorkingDays : {}", workingDaysDTO);
        if (workingDaysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkingDaysDTO result = workingDaysService.save(workingDaysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingDaysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /working-days} : get all the workingDays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingDays in body.
     */
    @GetMapping("/working-days")
    public ResponseEntity<List<WorkingDaysDTO>> getAllWorkingDays(WorkingDaysCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkingDays by criteria: {}", criteria);
        Page<WorkingDaysDTO> page = workingDaysQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-days/count} : count all the workingDays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/working-days/count")
    public ResponseEntity<Long> countWorkingDays(WorkingDaysCriteria criteria) {
        log.debug("REST request to count WorkingDays by criteria: {}", criteria);
        return ResponseEntity.ok().body(workingDaysQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /working-days/:id} : get the "id" workingDays.
     *
     * @param id the id of the workingDaysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingDaysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-days/{id}")
    public ResponseEntity<WorkingDaysDTO> getWorkingDays(@PathVariable Long id) {
        log.debug("REST request to get WorkingDays : {}", id);
        Optional<WorkingDaysDTO> workingDaysDTO = workingDaysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingDaysDTO);
    }

    /**
     * {@code DELETE  /working-days/:id} : delete the "id" workingDays.
     *
     * @param id the id of the workingDaysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-days/{id}")
    public ResponseEntity<Void> deleteWorkingDays(@PathVariable Long id) {
        log.debug("REST request to delete WorkingDays : {}", id);
        workingDaysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
