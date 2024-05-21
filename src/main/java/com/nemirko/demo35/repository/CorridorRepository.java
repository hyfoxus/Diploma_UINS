package com.nemirko.demo35.repository;

import com.nemirko.demo35.entity.Corridor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorridorRepository extends JpaRepository<Corridor, Long> {
    // Add any additional methods you need here
}
