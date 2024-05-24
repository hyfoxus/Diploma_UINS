package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Corridor;
import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.CorridorRepository;
import com.nemirko.demo35.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AStarPathfindingServiceImpl implements PathfindingService {

    @Autowired
    private CorridorRepository corridorRepository;
    @Autowired
    private VertexRepository vertexRepository;

    @Override
    public List<Vertex> findPath(Scheme scheme, Vertex start, Vertex goal) {
        // Initialize structures for A* algorithm
        // Similar to previously provided implementation...
        return new ArrayList<>(); // Ensure to replace with actual pathfinding logic
    }

    private double heuristic(Vertex a, Vertex b) {
        return 0; // This makes A* behave like Dijkstra's algorithm
    }

    private List<Vertex> reconstructPath(Map<Vertex, Vertex> cameFrom, Vertex current) {
        List<Vertex> totalPath = new ArrayList<>();
        totalPath.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            totalPath.add(current);
        }
        Collections.reverse(totalPath);
        return totalPath;
    }
}