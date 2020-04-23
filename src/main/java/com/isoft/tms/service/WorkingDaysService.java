package com.isoft.tms.service;

import com.isoft.tms.service.dto.WorkingDaysDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.WorkingDays}.
 */
public interface WorkingDaysService {

    /**
     * Save a workingDays.
     *
     * @param workingDaysDTO the entity to save.
     * @return the persisted entity.
     */
    WorkingDaysDTO save(WorkingDaysDTO workingDaysDTO);

    /**
     * Get all the workingDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkingDaysDTO> findAll(Pageable pageable);

    /**
     * Get the "id" workingDays.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkingDaysDTO> findOne(Long id);

    /**
     * Delete the "id" workingDays.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
