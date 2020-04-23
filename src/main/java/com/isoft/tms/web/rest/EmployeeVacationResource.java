package com.isoft.tms.web.rest;

import com.isoft.tms.service.EmployeeVacationService;
import com.isoft.tms.web.rest.errors.BadRequestAlertException;
import com.isoft.tms.service.dto.EmployeeVacationDTO;
import com.isoft.tms.service.dto.EmployeeVacationCriteria;
import com.isoft.tms.service.EmployeeVacationQueryService;

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
 * REST controller for managing {@link com.isoft.tms.domain.EmployeeVacation}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeVacationResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeVacationResource.class);

    private static final String ENTITY_NAME = "stuffMgmtMsEmployeeVacation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeVacationService employeeVacationService;

    private final EmployeeVacationQueryService employeeVacationQueryService;

    public EmployeeVacationResource(EmployeeVacationService employeeVacationService, EmployeeVacationQueryService employeeVacationQueryService) {
        this.employeeVacationService = employeeVacationService;
        this.employeeVacationQueryService = employeeVacationQueryService;
    }

    /**
     * {@code POST  /employee-vacations} : Create a new employeeVacation.
     *
     * @param employeeVacationDTO the employeeVacationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeVacationDTO, or with status {@code 400 (Bad Request)} if the employeeVacation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-vacations")
    public ResponseEntity<EmployeeVacationDTO> createEmployeeVacation(@Valid @RequestBody EmployeeVacationDTO employeeVacationDTO) throws URISyntaxException {
        log.debug("REST request to save EmployeeVacation : {}", employeeVacationDTO);
        if (employeeVacationDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeVacation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeVacationDTO result = employeeVacationService.save(employeeVacationDTO);
        return ResponseEntity.created(new URI("/api/employee-vacations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-vacations} : Updates an existing employeeVacation.
     *
     * @param employeeVacationDTO the employeeVacationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeVacationDTO,
     * or with status {@code 400 (Bad Request)} if the employeeVacationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeVacationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-vacations")
    public ResponseEntity<EmployeeVacationDTO> updateEmployeeVacation(@Valid @RequestBody EmployeeVacationDTO employeeVacationDTO) throws URISyntaxException {
        log.debug("REST request to update EmployeeVacation : {}", employeeVacationDTO);
        if (employeeVacationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeVacationDTO result = employeeVacationService.save(employeeVacationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeVacationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-vacations} : get all the employeeVacations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeVacations in body.
     */
    @GetMapping("/employee-vacations")
    public ResponseEntity<List<EmployeeVacationDTO>> getAllEmployeeVacations(EmployeeVacationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmployeeVacations by criteria: {}", criteria);
        Page<EmployeeVacationDTO> page = employeeVacationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employee-vacations/count} : count all the employeeVacations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/employee-vacations/count")
    public ResponseEntity<Long> countEmployeeVacations(EmployeeVacationCriteria criteria) {
        log.debug("REST request to count EmployeeVacations by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeVacationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-vacations/:id} : get the "id" employeeVacation.
     *
     * @param id the id of the employeeVacationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeVacationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-vacations/{id}")
    public ResponseEntity<EmployeeVacationDTO> getEmployeeVacation(@PathVariable Long id) {
        log.debug("REST request to get EmployeeVacation : {}", id);
        Optional<EmployeeVacationDTO> employeeVacationDTO = employeeVacationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeVacationDTO);
    }

    /**
     * {@code DELETE  /employee-vacations/:id} : delete the "id" employeeVacation.
     *
     * @param id the id of the employeeVacationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-vacations/{id}")
    public ResponseEntity<Void> deleteEmployeeVacation(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeVacation : {}", id);
        employeeVacationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
