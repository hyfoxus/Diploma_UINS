package com.nemirko.navigation.controller;

import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.EdgeType;
import com.nemirko.navigation.entity.Scheme;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.service.SchemeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/scheme")
@AllArgsConstructor
public class SchemeController {

    private final SchemeService schemeService;

    @GetMapping("/")
    public ResponseEntity<List<Scheme>> getAllSchemes() {
        return ResponseEntity.ok(schemeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getSchemeById(@PathVariable Long id) {
        return ResponseEntity.ok(schemeService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Scheme> createScheme(@RequestBody Scheme scheme) {
        return ResponseEntity.ok(schemeService.create(scheme));
    }

    @PostMapping("/{schemeId}/vertex")
    public ResponseEntity<Vertex> createAndAddVertexToScheme(@PathVariable Long schemeId, @RequestBody Vertex vertex) {
        return ResponseEntity.ok(schemeService.createAndAddVertexToScheme(schemeId, vertex));
    }

    @PostMapping("/{schemeId}/edge")
    public ResponseEntity<Edge> createAndAddEdgeToScheme(@PathVariable Long schemeId, @RequestParam int distance, @RequestParam long vertexFromId, @RequestParam long vertexToId, @RequestParam int direction, @RequestParam EdgeType type) {
        return ResponseEntity.ok(schemeService.createAndAddEdgeToScheme(schemeId, distance, vertexFromId, vertexToId, direction, type));
    }

}