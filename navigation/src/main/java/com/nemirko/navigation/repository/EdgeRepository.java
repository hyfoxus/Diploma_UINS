package com.nemirko.navigation.repository;

import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.Vertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Long> {
    // Add any additional methods you need here
    Edge findByVertexFromAndVertexTo(Vertex vertexFrom, Vertex vertexTo);
    List<Edge> findAllByVertexFrom(Vertex vertexFrom);
    List<Edge> findAllByVertexTo(Vertex vertexTo);
}
