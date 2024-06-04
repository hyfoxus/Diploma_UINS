package com.nemirko.navigation.repository;

import com.nemirko.navigation.entity.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VertexRepository extends JpaRepository<Vertex, Long>  {

    List<Vertex> findByAvailability(boolean availability);
}
