package com.nemirko.ui.controller;

import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.ui.dto.EdgeDTO;
import com.nemirko.ui.service.SchemeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/api/scheme")
public class SchemeUIController {

    private final RestTemplate restTemplate;
    private final String navigationServiceUrl;
    private final SchemeService schemeService;

    public SchemeUIController(RestTemplate restTemplate, @Value("${navigation.service.url}") String navigationServiceUrl, SchemeService schemeService) {
        this.restTemplate = restTemplate;
        this.navigationServiceUrl = navigationServiceUrl;
        this.schemeService = schemeService;
    }

    @PostMapping("/{schemeId}/vertex")
    public ResponseEntity<Vertex> createVertex(@PathVariable Long schemeId, @RequestBody Vertex vertex) {
        String url = String.format("%s/api/scheme/%d/vertex",
                navigationServiceUrl, schemeId);
        ResponseEntity<Vertex> response = restTemplate.postForEntity(url, vertex, Vertex.class);
        return response;
    }

    @PostMapping("/{schemeId}/edge")
    public ResponseEntity<Edge> createEdge(
            @PathVariable Long schemeId, @RequestBody EdgeDTO edgeDTO) {
        String url =
                String.format(
                        "%s/api/scheme/%d/edge?distance=%d&vertexFromId=%d&vertexToId=%d&direction=%d&type=Horizontal",
                        navigationServiceUrl,
                        schemeId,
                        edgeDTO.getLength(),
                        edgeDTO.getFrom(),
                        edgeDTO.getTo(),
                        edgeDTO.getDirection());
        ResponseEntity<Edge> response = restTemplate.postForEntity(url, null, Edge.class);
        return response;
    }
}