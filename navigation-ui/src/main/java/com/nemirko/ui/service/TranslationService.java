package com.nemirko.ui.service;
import com.nemirko.navigation.entity.EdgeType;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.entity.Edge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String EDGE_CONTROLLER_URL = "http://localhost:8080/api/edge/listed?ids=";

    public List<String> translateRoute(List<Vertex> route) {

        List<String> instructions = new ArrayList<>();
        int currentDirection = 0; // Assume starting direction is East (0 degrees)

        if (route.size() < 2) {
            return instructions;
        }

        // Собираем все необходимые edgeId
        List<Long> edgeIds = route.stream()
                .flatMap(vertex -> vertex.getAngles().keySet().stream())
                .distinct()
                .collect(Collectors.toList());

        // Получаем все необходимые Edge за один вызов
        List<Edge> edges = getAllEdgesByIds(edgeIds);


        Vertex current = route.get(0);
        Vertex next = route.get(1);

        Edge firstEdge = findEdgeBetweenVertices(current, next, edges);

        if (firstEdge != null) {
            currentDirection = updateDirection(current, next, currentDirection);
        }

        StringBuilder currentInstruction = new StringBuilder();
        int accumulatedDistance = 0;
        String lastDirection = "";

        for (int i = 0; i < route.size() - 1; i++) {
            current = route.get(i);
            next = route.get(i + 1);

            Edge edge = findEdgeBetweenVertices(current, next, edges);

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


    private List<Edge> getAllEdgesByIds(List<Long> ids) {
        String url = EDGE_CONTROLLER_URL + ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        ResponseEntity<List<Edge>> response = restTemplate.getForEntity(url, (Class<List<Edge>>) (Object) List.class);
        return response.getBody();
    }

    private Edge findEdgeBetweenVertices(Vertex current, Vertex next, List<Edge> edges) {
        Map<Long, Integer> angles = current.getAngles();
        Long edgeId = next.getId();

        if (angles.containsKey(edgeId)) {
            return edges.stream()
                    .filter(edge -> edge.getId().equals(edgeId))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

    private String getDirection(Vertex current, Vertex next, Edge edge, int currentDirection) {
        Map<Long, Integer> angles = current.getAngles();
        Integer angle = angles.get(next.getId());

        if (angle == null) {
            return "go straight";
        }

        // Проверка на вертикальность по типу Edge
        if (edge.getType() == EdgeType.Vertical) {
            if (angle == 90) {
                return "Go up";
            } else if (angle == 270) {
                return "Go down";
            }
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
            int newDirection = (angle + 360) % 360; // Normalize to 0-359 degrees

            // Check if the edge is vertical (assuming vertical means angle is 90 or 270 degrees)
            if (newDirection == 90 || newDirection == 270) {
                // Get the single edge from 'next'
                Map<Long, Integer> nextAngles = next.getAngles();
                if (nextAngles.size() == 1) {
                    Map.Entry<Long, Integer> entry = nextAngles.entrySet().iterator().next();
                    return (entry.getValue() + 360) % 360; // Normalize to 0-359 degrees
                }
            }

            return newDirection;
        }

        return currentDirection; // No change if angle is null
    }

}
