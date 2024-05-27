package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Edge;
import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.EdgeRepository;
import com.nemirko.demo35.repository.VertexRepository;
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

    public List<Edge> getAllCorridors() {
        return edgeRepository.findAll();
    }

    public Edge getCorridorById(Long id) {
        return edgeRepository.findById(id).orElse(null);
    }



    @Transactional
    public Edge createCorridorWithVertices(int distance, long vertexFromId, long vertexToId, int direction) {
        Optional<Vertex> vertex1 = vertexRepository.findById(vertexFromId);
        Optional<Vertex> vertex2 = vertexRepository.findById(vertexToId);
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            throw new IllegalStateException("One or both vertices not found");
        }
        Edge edge = new Edge();
        edge.setDistance(distance);
        edge.setVertex1(vertex1.get());
        edge.setVertex2(vertex2.get());

        vertex1.get().getNeighbors().put(vertexToId, direction);
        vertex2.get().getNeighbors().put(vertexFromId, (180 + direction) % 360);
        vertexRepository.save(vertex1.get());
        vertexRepository.save(vertex2.get());
        return edgeRepository.save(edge);
    }


    public void deleteCorridor(Long id) {
        edgeRepository.deleteById(id);
    }
}
