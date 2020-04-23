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

import com.isoft.tms.domain.EmployeeVacation;
import com.isoft.tms.domain.*; // for static metamodels
import com.isoft.tms.repository.EmployeeVacationRepository;
import com.isoft.tms.service.dto.EmployeeVacationCriteria;
import com.isoft.tms.service.dto.EmployeeVacationDTO;
import com.isoft.tms.service.mapper.EmployeeVacationMapper;

/**
 * Service for executing complex queries for {@link EmployeeVacation} entities in the database.
 * The main input is a {@link EmployeeVacationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeVacationDTO} or a {@link Page} of {@link EmployeeVacationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeVacationQueryService extends QueryService<EmployeeVacation> {

    private final Logger log = LoggerFactory.getLogger(EmployeeVacationQueryService.class);

    private final EmployeeVacationRepository employeeVacationRepository;

    private final EmployeeVacationMapper employeeVacationMapper;

    public EmployeeVacationQueryService(EmployeeVacationRepository employeeVacationRepository, EmployeeVacationMapper employeeVacationMapper) {
        this.employeeVacationRepository = employeeVacationRepository;
        this.employeeVacationMapper = employeeVacationMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeVacationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeVacationDTO> findByCriteria(EmployeeVacationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeVacation> specification = createSpecification(criteria);
        return employeeVacationMapper.toDto(employeeVacationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeVacationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeVacationDTO> findByCriteria(EmployeeVacationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeVacation> specification = createSpecification(criteria);
        return employeeVacationRepository.findAll(specification, page)
            .map(employeeVacationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeVacationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeVacation> specification = createSpecification(criteria);
        return employeeVacationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeVacationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeVacation> createSpecification(EmployeeVacationCriteria criteria) {
        Specification<EmployeeVacation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeVacation_.id));
            }
            if (criteria.getDateFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFrom(), EmployeeVacation_.dateFrom));
            }
            if (criteria.getDateTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTo(), EmployeeVacation_.dateTo));
            }
            if (criteria.getTimeFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeFrom(), EmployeeVacation_.timeFrom));
            }
            if (criteria.getTimeTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimeTo(), EmployeeVacation_.timeTo));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatus(), EmployeeVacation_.status));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), EmployeeVacation_.type));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(EmployeeVacation_.employee, JoinType.LEFT).get(Employee_.id)));
            }
        }
        return specification;
    }
}
