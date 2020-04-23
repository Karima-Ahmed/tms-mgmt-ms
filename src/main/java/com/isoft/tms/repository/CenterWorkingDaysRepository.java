package com.isoft.tms.repository;

import com.isoft.tms.domain.CenterWorkingDays;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CenterWorkingDays entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterWorkingDaysRepository extends JpaRepository<CenterWorkingDays, Long>, JpaSpecificationExecutor<CenterWorkingDays> {
}
