package com.isoft.tms.service.impl;

import com.isoft.tms.service.EmployeeVacationService;
import com.isoft.tms.domain.EmployeeVacation;
import com.isoft.tms.repository.EmployeeVacationRepository;
import com.isoft.tms.service.dto.EmployeeVacationDTO;
import com.isoft.tms.service.mapper.EmployeeVacationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeVacation}.
 */
@Service
@Transactional
public class EmployeeVacationServiceImpl implements EmployeeVacationService {

    private final Logger log = LoggerFactory.getLogger(EmployeeVacationServiceImpl.class);

    private final EmployeeVacationRepository employeeVacationRepository;

    private final EmployeeVacationMapper employeeVacationMapper;

    public EmployeeVacationServiceImpl(EmployeeVacationRepository employeeVacationRepository, EmployeeVacationMapper employeeVacationMapper) {
        this.employeeVacationRepository = employeeVacationRepository;
        this.employeeVacationMapper = employeeVacationMapper;
    }

    /**
     * Save a employeeVacation.
     *
     * @param employeeVacationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeVacationDTO save(EmployeeVacationDTO employeeVacationDTO) {
        log.debug("Request to save EmployeeVacation : {}", employeeVacationDTO);
        EmployeeVacation employeeVacation = employeeVacationMapper.toEntity(employeeVacationDTO);
        employeeVacation = employeeVacationRepository.save(employeeVacation);
        return employeeVacationMapper.toDto(employeeVacation);
    }

    /**
     * Get all the employeeVacations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeVacationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeVacations");
        return employeeVacationRepository.findAll(pageable)
            .map(employeeVacationMapper::toDto);
    }

    /**
     * Get one employeeVacation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeVacationDTO> findOne(Long id) {
        log.debug("Request to get EmployeeVacation : {}", id);
        return employeeVacationRepository.findById(id)
            .map(employeeVacationMapper::toDto);
    }

    /**
     * Delete the employeeVacation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeVacation : {}", id);
        employeeVacationRepository.deleteById(id);
    }
}
