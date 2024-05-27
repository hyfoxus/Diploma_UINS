package com.nemirko.demo35.repository;

import com.nemirko.demo35.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    // Add any additional methods you need here
}