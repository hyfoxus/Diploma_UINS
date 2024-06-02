package com.nemirko.navigation.service;

import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.EdgeType;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.repository.EdgeRepository;
import com.nemirko.navigation.repository.VertexRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EdgeService {

    @Autowired
    private VertexRepository vertexRepository;

    @Autowired
    private EdgeRepository edgeRepository;

    public List<Edge> getAll() {
        return edgeRepository.findAll();
    }

    public Edge getById(Long id) {
        return edgeRepository.findById(id).orElse(null);
    }



    @Transactional
    public Edge create(int distance, long vertexFromId, long vertexToId, int direction, EdgeType type) {
        Optional<Vertex> vertex1 = vertexRepository.findById(vertexFromId);
        Optional<Vertex> vertex2 = vertexRepository.findById(vertexToId);
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            throw new IllegalStateException("One or both vertices not found");
        }
        Edge edge = new Edge();
        edge.setDistance(distance);
        edge.setVertexFrom(vertex1.get());
        edge.setVertexTo(vertex2.get());
        edge.setType(type);
        edgeRepository.save(edge);
        vertex1.get().getAngles().put(edge.getId(), direction);
        vertex2.get().getAngles().put(edge.getId(), (180 + direction) % 360);

        vertexRepository.save(vertex1.get());
        vertexRepository.save(vertex2.get());

        return edge;
    }


    public void delete(Long id) {
        edgeRepository.getById(id).getVertexFrom().getAngles().remove(id);
        edgeRepository.getById(id).getVertexTo().getAngles().remove(id);
        edgeRepository.deleteById(id);

    }
}
