package com.isoft.tms.service;

import com.isoft.tms.service.dto.EmployeeTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.EmployeeType}.
 */
public interface EmployeeTypeService {

    /**
     * Save a employeeType.
     *
     * @param employeeTypeDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeTypeDTO save(EmployeeTypeDTO employeeTypeDTO);

    /**
     * Get all the employeeTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" employeeType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeTypeDTO> findOne(Long id);

    /**
     * Delete the "id" employeeType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
