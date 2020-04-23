package com.isoft.tms.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.isoft.tms.domain.CenterWorkingDays;
import com.isoft.tms.domain.*; // for static metamodels
import com.isoft.tms.repository.CenterWorkingDaysRepository;
import com.isoft.tms.service.dto.CenterWorkingDaysCriteria;
import com.isoft.tms.service.dto.CenterWorkingDaysDTO;
import com.isoft.tms.service.mapper.CenterWorkingDaysMapper;

/**
 * Service for executing complex queries for {@link CenterWorkingDays} entities in the database.
 * The main input is a {@link CenterWorkingDaysCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CenterWorkingDaysDTO} or a {@link Page} of {@link CenterWorkingDaysDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CenterWorkingDaysQueryService extends QueryService<CenterWorkingDays> {

    private final Logger log = LoggerFactory.getLogger(CenterWorkingDaysQueryService.class);

    private final CenterWorkingDaysRepository centerWorkingDaysRepository;

    private final CenterWorkingDaysMapper centerWorkingDaysMapper;

    public CenterWorkingDaysQueryService(CenterWorkingDaysRepository centerWorkingDaysRepository, CenterWorkingDaysMapper centerWorkingDaysMapper) {
        this.centerWorkingDaysRepository = centerWorkingDaysRepository;
        this.centerWorkingDaysMapper = centerWorkingDaysMapper;
    }

    /**
     * Return a {@link List} of {@link CenterWorkingDaysDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CenterWorkingDaysDTO> findByCriteria(CenterWorkingDaysCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CenterWorkingDays> specification = createSpecification(criteria);
        return centerWorkingDaysMapper.toDto(centerWorkingDaysRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CenterWorkingDaysDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CenterWorkingDaysDTO> findByCriteria(CenterWorkingDaysCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CenterWorkingDays> specification = createSpecification(criteria);
        return centerWorkingDaysRepository.findAll(specification, page)
            .map(centerWorkingDaysMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CenterWorkingDaysCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CenterWorkingDays> specification = createSpecification(criteria);
        return centerWorkingDaysRepository.count(specification);
    }

    /**
     * Function to convert {@link CenterWorkingDaysCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CenterWorkingDays> createSpecification(CenterWorkingDaysCriteria criteria) {
        Specification<CenterWorkingDays> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CenterWorkingDays_.id));
            }
            if (criteria.getStartWorkingHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartWorkingHour(), CenterWorkingDays_.startWorkingHour));
            }
            if (criteria.getEndWorkingHour() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndWorkingHour(), CenterWorkingDays_.endWorkingHour));
            }
            if (criteria.getWorkingDaysId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkingDaysId(),
                    root -> root.join(CenterWorkingDays_.workingDays, JoinType.LEFT).get(WorkingDays_.id)));
            }
        }
        return specification;
    }
}
