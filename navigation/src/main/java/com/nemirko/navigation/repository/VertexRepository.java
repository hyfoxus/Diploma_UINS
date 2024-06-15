
package com.nemirko.navigation.repository;

import com.nemirko.navigation.entity.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VertexRepository extends JpaRepository<Vertex, Long>, JpaSpecificationExecutor<Vertex> {

    List<Vertex> findByAvailability(boolean availability);
}
