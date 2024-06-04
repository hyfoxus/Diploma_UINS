package com.nemirko.navigation.repository;

import com.nemirko.navigation.entity.Scheme;
import com.nemirko.navigation.entity.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, Long> {
    @Query("SELECT s FROM Scheme s JOIN s.vertexes v WHERE v = :vertex")
    List<Scheme> findAllByVertex(@Param("vertex") Vertex vertex);
}