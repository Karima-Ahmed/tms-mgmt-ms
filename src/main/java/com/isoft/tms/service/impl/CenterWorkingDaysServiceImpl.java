package com.isoft.tms.service.impl;

import com.isoft.tms.service.CenterWorkingDaysService;
import com.isoft.tms.domain.CenterWorkingDays;
import com.isoft.tms.repository.CenterWorkingDaysRepository;
import com.isoft.tms.service.dto.CenterWorkingDaysDTO;
import com.isoft.tms.service.mapper.CenterWorkingDaysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CenterWorkingDays}.
 */
@Service
@Transactional
public class CenterWorkingDaysServiceImpl implements CenterWorkingDaysService {

    private final Logger log = LoggerFactory.getLogger(CenterWorkingDaysServiceImpl.class);

    private final CenterWorkingDaysRepository centerWorkingDaysRepository;

    private final CenterWorkingDaysMapper centerWorkingDaysMapper;

    public CenterWorkingDaysServiceImpl(CenterWorkingDaysRepository centerWorkingDaysRepository, CenterWorkingDaysMapper centerWorkingDaysMapper) {
        this.centerWorkingDaysRepository = centerWorkingDaysRepository;
        this.centerWorkingDaysMapper = centerWorkingDaysMapper;
    }

    /**
     * Save a centerWorkingDays.
     *
     * @param centerWorkingDaysDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CenterWorkingDaysDTO save(CenterWorkingDaysDTO centerWorkingDaysDTO) {
        log.debug("Request to save CenterWorkingDays : {}", centerWorkingDaysDTO);
        CenterWorkingDays centerWorkingDays = centerWorkingDaysMapper.toEntity(centerWorkingDaysDTO);
        centerWorkingDays = centerWorkingDaysRepository.save(centerWorkingDays);
        return centerWorkingDaysMapper.toDto(centerWorkingDays);
    }

    /**
     * Get all the centerWorkingDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CenterWorkingDaysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CenterWorkingDays");
        return centerWorkingDaysRepository.findAll(pageable)
            .map(centerWorkingDaysMapper::toDto);
    }

    /**
     * Get one centerWorkingDays by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CenterWorkingDaysDTO> findOne(Long id) {
        log.debug("Request to get CenterWorkingDays : {}", id);
        return centerWorkingDaysRepository.findById(id)
            .map(centerWorkingDaysMapper::toDto);
    }

    /**
     * Delete the centerWorkingDays by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CenterWorkingDays : {}", id);
        centerWorkingDaysRepository.deleteById(id);
    }
}
