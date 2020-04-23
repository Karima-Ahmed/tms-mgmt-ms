package com.isoft.tms.service;

import com.isoft.tms.service.dto.CenterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.isoft.tms.domain.Center}.
 */
public interface CenterService {

    /**
     * Save a center.
     *
     * @param centerDTO the entity to save.
     * @return the persisted entity.
     */
    CenterDTO save(CenterDTO centerDTO);

    /**
     * Get all the centers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CenterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" center.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterDTO> findOne(Long id);

    /**
     * Delete the "id" center.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
