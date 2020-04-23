package com.isoft.tms.service;

import com.isoft.tms.service.dto.PublicHolidayDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.PublicHoliday}.
 */
public interface PublicHolidayService {

    /**
     * Save a publicHoliday.
     *
     * @param publicHolidayDTO the entity to save.
     * @return the persisted entity.
     */
    PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO);

    /**
     * Get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicHolidayDTO> findAll(Pageable pageable);

    /**
     * Get the "id" publicHoliday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicHolidayDTO> findOne(Long id);

    /**
     * Delete the "id" publicHoliday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
