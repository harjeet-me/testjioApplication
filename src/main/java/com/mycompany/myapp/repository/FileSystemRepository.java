package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FileSystem;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FileSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileSystemRepository extends JpaRepository<FileSystem, Long> {
}
