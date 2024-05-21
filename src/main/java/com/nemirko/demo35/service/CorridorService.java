package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Corridor;
import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.CorridorRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorridorService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CorridorRepository corridorRepository;

    public List<Corridor> getAllCorridors() {
        return corridorRepository.findAll();
    }

    public Corridor getCorridorById(Long id) {
        return corridorRepository.findById(id).orElse(null);
    }

    public Corridor saveCorridor(Corridor corridor) {
        return corridorRepository.save(corridor);
    }



    @Transactional
    public Corridor createCorridorWithVertices(int distance, long vertex1Id, long vertex2Id) {
        Vertex vertex1 = entityManager.find(Vertex.class, vertex1Id);
        Vertex vertex2 = entityManager.find(Vertex.class, vertex2Id);

        if (vertex1 == null || vertex2 == null) {
            throw new IllegalStateException("One or both vertices not found");
        }
        Corridor corridor = new Corridor();
        corridor.setDistance(distance);
        corridor.setVertex1(vertex1);
        corridor.setVertex2(vertex2);
        //entityManager.persist(corridor);
        return corridorRepository.save(corridor);
    }


    public void deleteCorridor(Long id) {
        corridorRepository.deleteById(id);
    }
}
