package com.thejoa703.repository;

import com.thejoa703.domain.Material; 
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByTitleContaining(String title, Pageable pageable);   
    long countByTitleContaining(String title);
}