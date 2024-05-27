package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraPathfindingServiceImpl {

    @Autowired
    private VertexRepository vertexRepository;

    public List<List<Vertex>> findThreeShortestPaths(long startId, long endId) {
        Vertex startVertex = vertexRepository.findById(startId).orElse(null);
        Vertex endVertex = vertexRepository.findById(endId).orElse(null);
        if (startVertex == null || endVertex == null) {
            throw new IllegalStateException("One or both vertices not found");
        }

        return dijkstraThreePaths(startVertex, endVertex);
    }

    private List<List<Vertex>> dijkstraThreePaths(Vertex start, Vertex end) {
        // Priority Queue to hold the paths with their distances
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.distance));
        queue.add(new Path(start, 0));

        List<List<Vertex>> shortestPaths = new ArrayList<>();
        Map<Vertex, Integer> shortestDistances = new HashMap<>();
        int pathsFound = 0;

        while (!queue.isEmpty() && pathsFound < 3) {
            Path path = queue.poll();
            Vertex currentVertex = path.vertices.get(path.vertices.size() - 1);

            if (shortestDistances.containsKey(currentVertex) &&
                    path.distance > shortestDistances.get(currentVertex)) {
                continue;
            }

            shortestDistances.put(currentVertex, path.distance);

            if (currentVertex.equals(end)) {
                shortestPaths.add(new ArrayList<>(path.vertices));
                pathsFound++;
                continue;
            }

            for (Map.Entry<Long, Integer> neighborEntry : currentVertex.getNeighbors().entrySet()) {
                Vertex neighbor = vertexRepository.findById(neighborEntry.getKey()).orElse(null);
                if (neighbor == null) {
                    continue;
                }
                int newDistance = path.distance + neighborEntry.getValue();
                List<Vertex> newPath = new ArrayList<>(path.vertices);
                newPath.add(neighbor);
                queue.add(new Path(newPath, newDistance));
            }
        }

        return shortestPaths;
    }

    private static class Path {
        List<Vertex> vertices;
        int distance;

        Path(Vertex start, int distance) {
            this.vertices = new ArrayList<>();
            this.vertices.add(start);
            this.distance = distance;
        }

        Path(List<Vertex> vertices, int distance) {
            this.vertices = vertices;
            this.distance = distance;
        }
    }
}