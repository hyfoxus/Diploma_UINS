package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VertexService {
    private final VertexRepository repository;

    @Autowired
    public VertexService(VertexRepository repository) {
        this.repository = repository;
    }

    public List<Vertex> findAll() {
        return repository.findAll();
    }

    public Vertex save(Vertex vertex) {
        return repository.save(vertex);
    }

    public Vertex findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vertex> findByAvailability(boolean availability) {
        return repository.findByAvailability(availability);
    }

}
