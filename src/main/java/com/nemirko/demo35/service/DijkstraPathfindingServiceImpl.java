package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Edge;
import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.EdgeRepository;
import com.nemirko.demo35.repository.SchemeRepository;
import com.nemirko.demo35.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraPathfindingServiceImpl implements NavigationService {

    @Autowired
    private VertexRepository vertexRepository;

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    private SchemeRepository schemeRepository;

    @Override
    public List<List<Vertex>> getShortestPaths(long schemeId, long startId, long endId, int amountRoutes) {
        Vertex startVertex = vertexRepository.findById(startId).orElse(null);
        Vertex endVertex = vertexRepository.findById(endId).orElse(null);
        Scheme scheme = schemeRepository.findById(schemeId).orElse(null);
        if (startVertex == null || endVertex == null || scheme == null) {
            throw new IllegalStateException("One or both vertices not found or actually scheme not found");
        }

        if (!scheme.getVertexes().contains(startVertex) || !scheme.getVertexes().contains(endVertex)) {
            throw new IllegalStateException("Start or end vertex not in the specified scheme");
        }

        return findPaths(scheme, startVertex, endVertex, amountRoutes);
    }

    private List<List<Vertex>> findPaths(Scheme scheme, Vertex start, Vertex end, int amountRoutes) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingInt(Path::getDistance));
        queue.add(new Path(start, 0));

        List<List<Vertex>> shortestPaths = new ArrayList<>();
        Map<Vertex, Integer> shortestDistances = new HashMap<>();
        int pathsFound = 0;

        while (!queue.isEmpty() && pathsFound < amountRoutes) {
            Path currentPath = queue.poll();
            Vertex currentVertex = currentPath.getLastVertex();

            if (shortestDistances.containsKey(currentVertex) &&
                    currentPath.getDistance() > shortestDistances.get(currentVertex)) {
                continue;
            }

            shortestDistances.put(currentVertex, currentPath.getDistance());

            if (currentVertex.equals(end)) {
                shortestPaths.add(new ArrayList<>(currentPath.getVertices()));
                pathsFound++;
                continue;
            }

            for (Map.Entry<Long, Integer> neighborEntry : currentVertex.getAngles().entrySet()) {
                Vertex neighbor = vertexRepository.findById(neighborEntry.getKey()).orElse(null);
                if (neighbor == null || !scheme.getVertexes().contains(neighbor)) {
                    continue;
                }

                // Fetch the edge to get the distance
                Edge edge = edgeRepository.findByVertexFromAndVertexTo(currentVertex, neighbor);
                if (edge == null || !scheme.getEdges().contains(edge)) {
                    continue;
                }

                int newDistance = currentPath.getDistance() + edge.getDistance();
                List<Vertex> newPath = new ArrayList<>(currentPath.getVertices());
                newPath.add(neighbor);
                queue.add(new Path(newPath, newDistance));
            }
        }

        return shortestPaths;
    }

    private static class Path {
        private final List<Vertex> vertices;
        private final int distance;

        Path(Vertex start, int distance) {
            this.vertices = new ArrayList<>();
            this.vertices.add(start);
            this.distance = distance;
        }

        Path(List<Vertex> vertices, int distance) {
            this.vertices = vertices;
            this.distance = distance;
        }

        List<Vertex> getVertices() {
            return vertices;
        }

        int getDistance() {
            return distance;
        }

        Vertex getLastVertex() {
            return vertices.get(vertices.size() - 1);
        }
    }
}