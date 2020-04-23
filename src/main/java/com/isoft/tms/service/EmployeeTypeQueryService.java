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

import com.isoft.tms.domain.EmployeeType;
import com.isoft.tms.domain.*; // for static metamodels
import com.isoft.tms.repository.EmployeeTypeRepository;
import com.isoft.tms.service.dto.EmployeeTypeCriteria;
import com.isoft.tms.service.dto.EmployeeTypeDTO;
import com.isoft.tms.service.mapper.EmployeeTypeMapper;

/**
 * Service for executing complex queries for {@link EmployeeType} entities in the database.
 * The main input is a {@link EmployeeTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeTypeDTO} or a {@link Page} of {@link EmployeeTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeTypeQueryService extends QueryService<EmployeeType> {

    private final Logger log = LoggerFactory.getLogger(EmployeeTypeQueryService.class);

    private final EmployeeTypeRepository employeeTypeRepository;

    private final EmployeeTypeMapper employeeTypeMapper;

    public EmployeeTypeQueryService(EmployeeTypeRepository employeeTypeRepository, EmployeeTypeMapper employeeTypeMapper) {
        this.employeeTypeRepository = employeeTypeRepository;
        this.employeeTypeMapper = employeeTypeMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeTypeDTO> findByCriteria(EmployeeTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeType> specification = createSpecification(criteria);
        return employeeTypeMapper.toDto(employeeTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeTypeDTO> findByCriteria(EmployeeTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeType> specification = createSpecification(criteria);
        return employeeTypeRepository.findAll(specification, page)
            .map(employeeTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeType> specification = createSpecification(criteria);
        return employeeTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeType> createSpecification(EmployeeTypeCriteria criteria) {
        Specification<EmployeeType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeType_.id));
            }
            if (criteria.getNameEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameEn(), EmployeeType_.nameEn));
            }
            if (criteria.getNameAr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameAr(), EmployeeType_.nameAr));
            }
        }
        return specification;
    }
}
