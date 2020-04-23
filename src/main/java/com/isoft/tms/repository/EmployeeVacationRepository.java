package com.isoft.tms.repository;

import com.isoft.tms.domain.EmployeeVacation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeVacation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeVacationRepository extends JpaRepository<EmployeeVacation, Long>, JpaSpecificationExecutor<EmployeeVacation> {
}
