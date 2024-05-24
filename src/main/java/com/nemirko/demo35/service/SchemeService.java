package com.nemirko.demo35.service;

import java.util.List;
import java.util.Optional;

import com.nemirko.demo35.entity.Corridor;
import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.CorridorRepository;
import com.nemirko.demo35.repository.SchemeRepository;
import com.nemirko.demo35.repository.VertexRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor
public class SchemeService {

    @Autowired
    private SchemeRepository schemeRepository;
    @Autowired
    private  VertexRepository vertexRepository;
    @Autowired
    private CorridorRepository corridorRepository;

    public List<Scheme> getAllSchemes() {
        return schemeRepository.findAll();
    }

    public Scheme getSchemeById(Long id) {
        return schemeRepository.findById(id).orElse(null);
    }

//    public Scheme createScheme(List<Long> vertices, List<Long> corridors, List<Long> ports) {
//        List<Optional<Vertex>> vertexList = null;
//        for (long vertexId:vertices
//             ) {
//            vertexList.add(vertexRepository.findById(vertexId));
//        }
//        List<Optional<Corridor>> corridorList = null;
//        for (long corridorId:corridors
//        ) {
//            corridorList.add(corridorRepository.findById(corridorId));
//        }
//        List<Optional<Vertex>> portList = null;
//        for (long portId:ports
//        ) {
//            portList.add(vertexRepository.findById(portId));
//        }
//
//        if (vertexList.isEmpty() || corridorList.isEmpty()) {
//            throw new IllegalStateException("No corridors or Verte");
//        }
//        Scheme scheme = new Scheme();
//        scheme.se
//        corridor.setVertex1(vertex1.get());
//        corridor.setVertex2(vertex2.get());
//        return schemeRepository.save(scheme);
//    }
    public Scheme createScheme(Scheme scheme){
        return schemeRepository.save(scheme);
    }
}
