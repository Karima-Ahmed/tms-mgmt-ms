package com.isoft.tms.repository;

import com.isoft.tms.domain.Center;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Center entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CenterRepository extends JpaRepository<Center, Long>, JpaSpecificationExecutor<Center> {
}
