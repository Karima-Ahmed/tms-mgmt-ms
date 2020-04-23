package com.isoft.tms.repository;

import com.isoft.tms.domain.WorkingDays;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkingDays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkingDaysRepository extends JpaRepository<WorkingDays, Long>, JpaSpecificationExecutor<WorkingDays> {
}
