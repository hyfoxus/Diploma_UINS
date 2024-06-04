package com.nemirko.ui.controller;

import com.nemirko.navigation.entity.Scheme;
import com.nemirko.ui.dto.GraphDTO;
import com.nemirko.ui.service.SchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/api/graph")
public class GraphEditorController {

    private final RestTemplate restTemplate;
    private final SchemeService schemeService;

    @Autowired
    public GraphEditorController(RestTemplate restTemplate, SchemeService schemeService) {
        this.restTemplate = restTemplate;
        this.schemeService = schemeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GraphDTO> getGraph(@PathVariable Long id) {
        try {
            ResponseEntity<Scheme> response = restTemplate.getForEntity("http://localhost:8080/api/scheme/" + id, Scheme.class);
            GraphDTO graphDTO = schemeService.transformToGraphDTO(response.getBody());
            return ResponseEntity.ok(graphDTO);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Логирование ошибки`
            System.err.println("Error occurred while fetching the graph: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            // Логирование ошибки
            System.err.println("Unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Scheme> updateGraph(@RequestBody Scheme scheme) {
        try {
            ResponseEntity<Scheme> response = restTemplate.postForEntity("http://localhost:8080/api/scheme", scheme, Scheme.class);
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Логирование ошибки
            System.err.println("Error occurred while updating the graph: " + e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).build();
        } catch (Exception e) {
            // Логирование ошибки
            System.err.println("Unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
