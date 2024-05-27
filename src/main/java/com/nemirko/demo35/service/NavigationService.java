package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Vertex;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NavigationService {

    private final DijkstraPathfindingServiceImpl pathfindingService;

    public List<List<Vertex>> getThreeShortestRoutes(long vertex1Id, long vertex2Id) {
        return pathfindingService.findThreeShortestPaths(vertex1Id, vertex2Id);
    }
}