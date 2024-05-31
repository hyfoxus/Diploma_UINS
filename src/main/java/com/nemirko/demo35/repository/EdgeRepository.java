package com.nemirko.demo35.repository;

import com.nemirko.demo35.entity.Edge;
import com.nemirko.demo35.entity.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    // Add any additional methods you need here
    Edge findByVertexFromAndVertexTo(Vertex vertexFrom, Vertex vertexTo);
}
