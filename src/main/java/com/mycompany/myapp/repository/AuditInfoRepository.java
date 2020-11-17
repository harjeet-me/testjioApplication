package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AuditInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AuditInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditInfoRepository extends JpaRepository<AuditInfo, Long> {
}
