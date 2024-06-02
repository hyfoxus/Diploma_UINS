package com.nemirko.navigation.service;

import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.repository.EdgeRepository;
import com.nemirko.navigation.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VertexService {
    private final VertexRepository vertexRepository;

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    public VertexService(VertexRepository repository) {
        this.vertexRepository = repository;
    }

    public List<Vertex> findAll() {
        return vertexRepository.findAll();
    }

    public Vertex save(Vertex vertex) {
        return vertexRepository.save(vertex);
    }

    public Vertex findById(Long id) {
        return vertexRepository.findById(id).orElse(null);
    }

    public List<Vertex> findByAvailability(boolean availability) {
        return vertexRepository.findByAvailability(availability);
    }

    public void delete(Long id) {
// Fetch the vertex by its ID
        Optional<Vertex> vertexOpt = vertexRepository.findById(id);

        if (vertexOpt.isPresent()) {
            Vertex vertex = vertexOpt.get();

            // Iterate over the neighbors and delete corresponding edges
            vertex.getAngles().forEach((edgeId, direction) -> {
                // Fetch the edge by its ID and delete it if present
                edgeRepository.findById(edgeId).ifPresent(edgeRepository::delete);
            });
        } else {
            // Handle the case where the vertex is not found
            throw new RuntimeException("Vertex with id " + id + " not found.");
        }
        vertexRepository.deleteById(id);
    }

}
