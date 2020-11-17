package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Envelope;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Envelope entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvelopeRepository extends JpaRepository<Envelope, Long> {
}
