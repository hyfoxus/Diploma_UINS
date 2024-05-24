package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Corridor;
import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.CorridorRepository;
import com.nemirko.demo35.repository.VertexRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CorridorService {

    @Autowired
    private VertexRepository vertexRepository;

    @Autowired
    private CorridorRepository corridorRepository;

    public List<Corridor> getAllCorridors() {
        return corridorRepository.findAll();
    }

    public Corridor getCorridorById(Long id) {
        return corridorRepository.findById(id).orElse(null);
    }



    @Transactional
    public Corridor createCorridorWithVertices(int distance, long vertex1Id, long vertex2Id) {
        Optional<Vertex> vertex1 = vertexRepository.findById(vertex1Id);
        Optional<Vertex> vertex2 = vertexRepository.findById(vertex2Id);
        if (vertex1.isEmpty() || vertex2.isEmpty()) {
            throw new IllegalStateException("One or both vertices not found");
        }
        Corridor corridor = new Corridor();
        corridor.setDistance(distance);
        corridor.setVertex1(vertex1.get());
        corridor.setVertex2(vertex2.get());
        return corridorRepository.save(corridor);
    }


    public void deleteCorridor(Long id) {
        corridorRepository.deleteById(id);
    }
}
