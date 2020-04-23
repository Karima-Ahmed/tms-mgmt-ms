package com.isoft.tms.web.rest;

import com.isoft.tms.service.PublicHolidayService;
import com.isoft.tms.web.rest.errors.BadRequestAlertException;
import com.isoft.tms.service.dto.PublicHolidayDTO;
import com.isoft.tms.service.dto.PublicHolidayCriteria;
import com.isoft.tms.service.PublicHolidayQueryService;

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
 * REST controller for managing {@link com.isoft.tms.domain.PublicHoliday}.
 */
@RestController
@RequestMapping("/api")
public class PublicHolidayResource {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayResource.class);

    private static final String ENTITY_NAME = "stuffMgmtMsPublicHoliday";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublicHolidayService publicHolidayService;

    private final PublicHolidayQueryService publicHolidayQueryService;

    public PublicHolidayResource(PublicHolidayService publicHolidayService, PublicHolidayQueryService publicHolidayQueryService) {
        this.publicHolidayService = publicHolidayService;
        this.publicHolidayQueryService = publicHolidayQueryService;
    }

    /**
     * {@code POST  /public-holidays} : Create a new publicHoliday.
     *
     * @param publicHolidayDTO the publicHolidayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicHolidayDTO, or with status {@code 400 (Bad Request)} if the publicHoliday has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/public-holidays")
    public ResponseEntity<PublicHolidayDTO> createPublicHoliday(@Valid @RequestBody PublicHolidayDTO publicHolidayDTO) throws URISyntaxException {
        log.debug("REST request to save PublicHoliday : {}", publicHolidayDTO);
        if (publicHolidayDTO.getId() != null) {
            throw new BadRequestAlertException("A new publicHoliday cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublicHolidayDTO result = publicHolidayService.save(publicHolidayDTO);
        return ResponseEntity.created(new URI("/api/public-holidays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /public-holidays} : Updates an existing publicHoliday.
     *
     * @param publicHolidayDTO the publicHolidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHolidayDTO,
     * or with status {@code 400 (Bad Request)} if the publicHolidayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publicHolidayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/public-holidays")
    public ResponseEntity<PublicHolidayDTO> updatePublicHoliday(@Valid @RequestBody PublicHolidayDTO publicHolidayDTO) throws URISyntaxException {
        log.debug("REST request to update PublicHoliday : {}", publicHolidayDTO);
        if (publicHolidayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublicHolidayDTO result = publicHolidayService.save(publicHolidayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, publicHolidayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /public-holidays} : get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicHolidays in body.
     */
    @GetMapping("/public-holidays")
    public ResponseEntity<List<PublicHolidayDTO>> getAllPublicHolidays(PublicHolidayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PublicHolidays by criteria: {}", criteria);
        Page<PublicHolidayDTO> page = publicHolidayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /public-holidays/count} : count all the publicHolidays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/public-holidays/count")
    public ResponseEntity<Long> countPublicHolidays(PublicHolidayCriteria criteria) {
        log.debug("REST request to count PublicHolidays by criteria: {}", criteria);
        return ResponseEntity.ok().body(publicHolidayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /public-holidays/:id} : get the "id" publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicHolidayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/public-holidays/{id}")
    public ResponseEntity<PublicHolidayDTO> getPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to get PublicHoliday : {}", id);
        Optional<PublicHolidayDTO> publicHolidayDTO = publicHolidayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publicHolidayDTO);
    }

    /**
     * {@code DELETE  /public-holidays/:id} : delete the "id" publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/public-holidays/{id}")
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        log.debug("REST request to delete PublicHoliday : {}", id);
        publicHolidayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
