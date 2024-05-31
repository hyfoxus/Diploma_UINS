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

    public List<Edge> getAll() {
        return edgeRepository.findAll();
    }

    public Edge getById(Long id) {
        return edgeRepository.findById(id).orElse(null);
    }



    @Transactional
    public Edge create(int distance, long vertexFromId, long vertexToId, int direction) {
        Optional<Vertex> vertex1 = vertexRepository.findById(vertexFromId);
        Optional<Vertex> vertex2 = vertexRepository.findById(vertexToId);
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            throw new IllegalStateException("One or both vertices not found");
        }
        Edge edge = new Edge();
        edge.setDistance(distance);
        edge.setVertexFrom(vertex1.get());
        edge.setVertexTo(vertex2.get());

        vertex1.get().getAngles().put(edge.getId(), direction);
        vertex2.get().getAngles().put(edge.getId(), (180 + direction) % 360);
        vertexRepository.save(vertex1.get());
        vertexRepository.save(vertex2.get());
        return edgeRepository.save(edge);
    }


    public void delete(Long id) {
        edgeRepository.getById(id).getVertexFrom().getAngles().remove(id);
        edgeRepository.getById(id).getVertexTo().getAngles().remove(id);
        edgeRepository.deleteById(id);

    }
}
