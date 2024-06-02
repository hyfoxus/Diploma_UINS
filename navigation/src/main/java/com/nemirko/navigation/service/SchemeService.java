package com.nemirko.navigation.service;

import java.util.List;

import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.Scheme;
import com.nemirko.navigation.entity.Vertex;
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



    @Transactional
    public Scheme create(List<Long> vertexIds, List<Long> edgeIds, long level) {
        List<Vertex> vertexes = vertexRepository.findAllById(vertexIds);
        List<Edge> edges = edgeRepository.findAllById(edgeIds);

        Scheme scheme = new Scheme();
        scheme.setVertexes(vertexes);
        scheme.setEdges(edges);
        scheme.setLevel(level);

        return schemeRepository.save(scheme);
    }
}

