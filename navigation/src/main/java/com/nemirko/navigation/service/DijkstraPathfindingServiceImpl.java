package com.nemirko.navigation.service;

import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.Scheme;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.repository.EdgeRepository;
import com.nemirko.navigation.repository.SchemeRepository;
import com.nemirko.navigation.repository.VertexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DijkstraPathfindingServiceImpl implements NavigationService {

    @Autowired
    private VertexRepository vertexRepository;

    @Autowired
    private SchemeRepository schemeRepository;

    @Override
    public List<List<Vertex>> getShortestPaths(long startId, long endId, int amountRoutes) {
        Vertex startVertex = vertexRepository.findById(startId).orElse(null);
        Vertex endVertex = vertexRepository.findById(endId).orElse(null);

        if (startVertex == null || endVertex == null) {
            throw new IllegalStateException("One or both vertices not found");
        }

        Scheme scheme = findCommonSchemeWithLowestLevel(startVertex, endVertex);

        if (scheme == null) {
            throw new IllegalStateException("No common scheme found between the start and end vertices");
        }

        return findPaths(scheme, startVertex, endVertex, amountRoutes);
    }

    private Scheme findCommonSchemeWithLowestLevel(Vertex startVertex, Vertex endVertex) {
        List<Scheme> startSchemes = schemeRepository.findAllByVertex(startVertex);
        List<Scheme> endSchemes = schemeRepository.findAllByVertex(endVertex);

        startSchemes.retainAll(endSchemes);

        return startSchemes.stream().min(Comparator.comparingLong(Scheme::getLevel)).orElse(null);
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

            for (Edge edge : scheme.getEdges()) {
                Vertex neighbor = null;
                if (edge.getVertexFrom().equals(currentVertex)) {
                    neighbor = edge.getVertexTo();
                } else if (edge.getVertexTo().equals(currentVertex)) {
                    neighbor = edge.getVertexFrom();
                }

                if (neighbor == null || !scheme.getVertexes().contains(neighbor)) {
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