package com.isoft.tms.service.impl;

import com.isoft.tms.service.WorkingDaysService;
import com.isoft.tms.domain.WorkingDays;
import com.isoft.tms.repository.WorkingDaysRepository;
import com.isoft.tms.service.dto.WorkingDaysDTO;
import com.isoft.tms.service.mapper.WorkingDaysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkingDays}.
 */
@Service
@Transactional
public class WorkingDaysServiceImpl implements WorkingDaysService {

    private final Logger log = LoggerFactory.getLogger(WorkingDaysServiceImpl.class);

    private final WorkingDaysRepository workingDaysRepository;

    private final WorkingDaysMapper workingDaysMapper;

    public WorkingDaysServiceImpl(WorkingDaysRepository workingDaysRepository, WorkingDaysMapper workingDaysMapper) {
        this.workingDaysRepository = workingDaysRepository;
        this.workingDaysMapper = workingDaysMapper;
    }

    /**
     * Save a workingDays.
     *
     * @param workingDaysDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkingDaysDTO save(WorkingDaysDTO workingDaysDTO) {
        log.debug("Request to save WorkingDays : {}", workingDaysDTO);
        WorkingDays workingDays = workingDaysMapper.toEntity(workingDaysDTO);
        workingDays = workingDaysRepository.save(workingDays);
        return workingDaysMapper.toDto(workingDays);
    }

    /**
     * Get all the workingDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkingDaysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingDays");
        return workingDaysRepository.findAll(pageable)
            .map(workingDaysMapper::toDto);
    }

    /**
     * Get one workingDays by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkingDaysDTO> findOne(Long id) {
        log.debug("Request to get WorkingDays : {}", id);
        return workingDaysRepository.findById(id)
            .map(workingDaysMapper::toDto);
    }

    /**
     * Delete the workingDays by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkingDays : {}", id);
        workingDaysRepository.deleteById(id);
    }
}
