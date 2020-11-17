package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Discrim;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Discrim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscrimRepository extends JpaRepository<Discrim, Long> {
}
