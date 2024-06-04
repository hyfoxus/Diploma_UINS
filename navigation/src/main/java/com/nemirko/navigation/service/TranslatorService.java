package com.nemirko.navigation.service;

import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.repository.EdgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Service
public class TranslatorService {

    @Autowired
    private EdgeRepository edgeRepository;

    public List<String> translateRoute(List<Vertex> route) {
        List<String> instructions = new ArrayList<>();
        int currentDirection = 0; // Assume starting direction is East (0 degrees)

        if (route.size() < 2) {
            return instructions;
        }

        Vertex current = route.get(0);
        Vertex next = route.get(1);

        Edge firstEdge = findEdgeBetweenVertices(current, next);

        if (firstEdge != null) {
            currentDirection = updateDirection(current, next, currentDirection);
        }

        StringBuilder currentInstruction = new StringBuilder();
        int accumulatedDistance = 0;
        String lastDirection = "";

        for (int i = 0; i < route.size() - 1; i++) {
            current = route.get(i);
            next = route.get(i + 1);

            Edge edge = findEdgeBetweenVertices(current, next);

            if (edge != null) {
                String direction = getDirection(current, next, edge, currentDirection);

                if (direction.equals(lastDirection)) {
                    accumulatedDistance += edge.getDistance();
                } else {
                    if (accumulatedDistance > 0) {
                        currentInstruction.append(String.format("%s for %d meters.", lastDirection, accumulatedDistance));
                        instructions.add(currentInstruction.toString());
                    }

                    lastDirection = direction;
                    accumulatedDistance = edge.getDistance();
                    currentInstruction = new StringBuilder();
                    currentInstruction.append(String.format("From %s, ", current.getName()));
                }

                currentDirection = updateDirection(current, next, currentDirection);
            }
        }

        if (accumulatedDistance > 0) {
            currentInstruction.append(String.format("%s for %d meters.", lastDirection, accumulatedDistance));
            instructions.add(currentInstruction.toString());
        }

        return instructions;
    }

    private Edge findEdgeBetweenVertices(Vertex current, Vertex next) {
        List<Edge> edgesFromCurrent = edgeRepository.findAllByVertexFrom(current);
        for (Edge edge : edgesFromCurrent) {
            if (edge.getVertexTo().equals(next)) {
                return edge;
            }
        }

        List<Edge> edgesToCurrent = edgeRepository.findAllByVertexTo(current);
        for (Edge edge : edgesToCurrent) {
            if (edge.getVertexFrom().equals(next)) {
                return edge;
            }
        }

        return null;
    }

    private String getDirection(Vertex current, Vertex next, Edge edge, int currentDirection) {
        Map<Long, Integer> angles = current.getAngles();
        Integer angle = angles.get(next.getId());

        if (angle == null) {
            return "go straight";
        }

        int relativeAngle = (angle - currentDirection + 360) % 360;

        if (relativeAngle < 45 || relativeAngle > 315) {
            return "go straight";
        } else if (relativeAngle < 135) {
            return "turn right";
        } else if (relativeAngle < 225) {
            return "turn back";
        } else {
            return "turn left";
        }
    }

    private int updateDirection(Vertex current, Vertex next, int currentDirection) {
        Map<Long, Integer> angles = current.getAngles();
        Integer angle = angles.get(next.getId());

        if (angle != null) {
            return (angle + 360) % 360; // Normalize to 0-359 degrees
        }

        return currentDirection; // No change if angle is null
    }
}