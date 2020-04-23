package com.isoft.tms.service;

import com.isoft.tms.service.dto.CenterWorkingDaysDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.CenterWorkingDays}.
 */
public interface CenterWorkingDaysService {

    /**
     * Save a centerWorkingDays.
     *
     * @param centerWorkingDaysDTO the entity to save.
     * @return the persisted entity.
     */
    CenterWorkingDaysDTO save(CenterWorkingDaysDTO centerWorkingDaysDTO);

    /**
     * Get all the centerWorkingDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CenterWorkingDaysDTO> findAll(Pageable pageable);

    /**
     * Get the "id" centerWorkingDays.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterWorkingDaysDTO> findOne(Long id);

    /**
     * Delete the "id" centerWorkingDays.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
