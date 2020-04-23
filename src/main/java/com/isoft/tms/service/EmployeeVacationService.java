package com.isoft.tms.service;

import com.isoft.tms.service.dto.EmployeeVacationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.EmployeeVacation}.
 */
public interface EmployeeVacationService {

    /**
     * Save a employeeVacation.
     *
     * @param employeeVacationDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeVacationDTO save(EmployeeVacationDTO employeeVacationDTO);

    /**
     * Get all the employeeVacations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeVacationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" employeeVacation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeVacationDTO> findOne(Long id);

    /**
     * Delete the "id" employeeVacation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
