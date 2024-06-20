package com.nemirko.ui.service;
import com.nemirko.navigation.entity.EdgeType;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.entity.Edge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    private final RestTemplate restTemplate;

    private final String navigationServiceUrl;

    private static final String EDGE_CONTROLLER_URL = "http://localhost:8080/api/edge/listed?ids=";

    @Autowired
    public TranslationService(RestTemplate restTemplate, @Value("${navigation.service.url}") String navigationServiceUrl) {
        this.restTemplate = restTemplate;
        this.navigationServiceUrl = navigationServiceUrl;
    }

    public List<List<String>> translateRoutes(List<List<Vertex>> routes) {
        List<List<String>> translations = new ArrayList<>();
        for (List<Vertex> route:routes) translations.add(translateRoute(route));
        return translations;
    }


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
            currentDirection = updateDirection(current, next, currentDirection, edges);
        }

        StringBuilder currentInstruction = new StringBuilder();
        int accumulatedDistance = 0;
        String lastDirection = "";

        currentInstruction = new StringBuilder();

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

                currentDirection = updateDirection(current, next, currentDirection, edges);
            }

            // Add the final accumulated instruction if any
            if (i == route.size() - 2 && accumulatedDistance > 0) {
                currentInstruction.append(String.format("%s for %d meters.", lastDirection, accumulatedDistance));
                instructions.add(currentInstruction.toString());
            }
        }

        // Ensure the final instruction is added if not already added
        if (accumulatedDistance > 0 && !instructions.contains(currentInstruction.toString())) {
            currentInstruction.append(String.format("%s for %d meters.", lastDirection, accumulatedDistance));
            instructions.add(currentInstruction.toString());
        }

        return instructions;
    }


    private List<Edge> getAllEdgesByIds(List<Long> ids) {
        String url = String.format("%s/api/edge/listed?ids=%s",
                navigationServiceUrl, ids.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")));
        ResponseEntity<List<Edge>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Edge>>() {}
        );
        return response.getBody();
    }

    private Edge findEdgeBetweenVertices(Vertex current, Vertex next, List<Edge> edges) {
        Map<Long, Integer> angles = current.getAngles();

        // Iterate over the edges to find one that connects current and next vertices
        for (Map.Entry<Long, Integer> entry : angles.entrySet()) {
            Long edgeId = entry.getKey();
            Edge edge = edges.stream()
                    .filter(e -> e.getId().equals(edgeId))
                    .findFirst()
                    .orElse(null);

            if (edge != null && (edge.getVertexFrom().getId().equals(current.getId()) && edge.getVertexTo().getId().equals(next.getId()) ||
                    edge.getVertexFrom().getId().equals(next.getId()) && edge.getVertexTo().getId().equals(current.getId()))) {
                return edge;
            }
        }
        return null;
    }

    private String getDirection(Vertex current, Vertex next, Edge edge, int currentDirection) {
        Map<Long, Integer> angles = current.getAngles();
        Integer angle = angles.get(edge.getId());

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

        int relativeAngle = (angle - currentDirection) % 360;

        if (relativeAngle < 45 || relativeAngle > 315) {
            return "go straight";
        } else if (relativeAngle < 135 && angle - currentDirection < 0) {
            return "turn right";
        } else if (relativeAngle < 225 && relativeAngle >= 135) {
            return "turn back";
        } else {
            return "turn left";
        }
    }

    private int updateDirection(Vertex current, Vertex next, int currentDirection, List<Edge> edges) {
        Map<Long, Integer> angles = current.getAngles();
        Integer angle = angles.get(findEdgeBetweenVertices(current, next, edges).getId());

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
