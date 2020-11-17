package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DynamicData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DynamicData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DynamicDataRepository extends JpaRepository<DynamicData, Long> {
}
