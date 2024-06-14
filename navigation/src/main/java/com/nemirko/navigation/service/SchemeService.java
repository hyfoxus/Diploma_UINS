package com.nemirko.navigation.service;

import java.util.List;
import java.util.Optional;

import com.nemirko.navigation.entity.*;
import com.nemirko.navigation.repository.EdgeRepository;
import com.nemirko.navigation.repository.SchemeRepository;
import com.nemirko.navigation.repository.VertexRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor
public class SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;
    @Autowired
    private VertexRepository vertexRepository;
    @Autowired
    private EdgeRepository edgeRepository;

    public List<Scheme> getAll() {
        return schemeRepository.findAll();
    }

    public Scheme getById(Long id) {
        return schemeRepository.findById(id).orElse(null);
    }



    public Scheme create(Scheme scheme) {
        return schemeRepository.save(scheme);
    }
    @Transactional
    public Scheme create(List<Long> vertexIds, List<Long> edgeIds, long level, String description) {
        List<Vertex> vertexes = vertexRepository.findAllById(vertexIds);
        List<Edge> edges = edgeRepository.findAllById(edgeIds);

        Scheme scheme = new Scheme();
        scheme.setVertexes(vertexes);
        scheme.setEdges(edges);
        scheme.setLevel(level);
        scheme.setDescription(description);

        return schemeRepository.save(scheme);
    }

    @Transactional
    public Scheme createAndAddVertexToScheme(Long schemeId, String name, String description, VertexType type, Boolean availability) {
        Scheme scheme = schemeRepository.findById(schemeId).orElseThrow(() -> new RuntimeException("Scheme not found"));

        Vertex vertex = new Vertex();
        vertex.setName(name);
        vertex.setDescription(description);
        vertex.setType(type);
        vertex.setAvailability(availability);
        Vertex savedVertex = vertexRepository.save(vertex);

        scheme.getVertexes().add(savedVertex);
        return schemeRepository.save(scheme);
    }

    @Transactional
    public Scheme createAndAddEdgeToScheme(Long schemeId, int distance, long vertexFromId, long vertexToId, int direction, EdgeType type) {
        Scheme scheme = schemeRepository.findById(schemeId).orElseThrow(() -> new RuntimeException("Scheme not found"));
        Vertex fromVertex = vertexRepository.findById(vertexFromId).orElseThrow(() -> new RuntimeException("From Vertex not found"));
        Vertex toVertex = vertexRepository.findById(vertexToId).orElseThrow(() -> new RuntimeException("To Vertex not found"));

        Edge edge = new Edge();
        edge.setDistance(distance);
        edge.setVertexFrom(fromVertex);
        edge.setVertexTo(toVertex);
        edge.setType(type);
        edge.setDirection(direction);
        Edge savedEdge = edgeRepository.save(edge);

        fromVertex.getAngles().put(savedEdge.getId(), direction);
        toVertex.getAngles().put(savedEdge.getId(), (180 + direction) % 360);
        vertexRepository.save(fromVertex);
        vertexRepository.save(toVertex);

        scheme.getEdges().add(savedEdge);
        return schemeRepository.save(scheme);
    }


    @Transactional
    public Scheme edit(Long id, Scheme updatedScheme) {
        Optional<Scheme> schemeOptional = schemeRepository.findById(id);

        if (schemeOptional.isPresent()) {
            Scheme scheme = schemeOptional.get();

            if (updatedScheme.getVertexes() != null) {
                scheme.setVertexes(updatedScheme.getVertexes());
            }

            if (updatedScheme.getEdges() != null) {
                scheme.setEdges(updatedScheme.getEdges());
            }

            if (updatedScheme.getLevel() != null) {
                scheme.setLevel(updatedScheme.getLevel());
            }

            return schemeRepository.save(scheme);
        } else {
            throw new RuntimeException("Scheme with id " + id + " not found.");
        }
    }
}

