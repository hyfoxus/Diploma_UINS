package com.nemirko.navigation.controller;

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
    public ResponseEntity<Scheme> createScheme(@RequestParam List<Long> vertexIds, @RequestParam List<Long> edgeIds, @RequestParam long level, @RequestParam String description) {
        Scheme scheme = schemeService.create(vertexIds, edgeIds, level, description);
        return ResponseEntity.ok(scheme);
    }

    @PostMapping("/{schemeId}/createVertex")
    public ResponseEntity<Scheme> createAndAddVertexToScheme(@PathVariable Long schemeId, @RequestBody Vertex vertex) {
        Scheme updatedScheme = schemeService.createAndAddVertexToScheme(schemeId, vertex.getName(), vertex.getDescription(), vertex.getType(), vertex.getAvailability());
        return ResponseEntity.ok(updatedScheme);
    }

    @PostMapping("/{schemeId}/createEdge")
    public ResponseEntity<Scheme> createAndAddEdgeToScheme(@PathVariable Long schemeId, @RequestParam int distance, @RequestParam long vertexFromId, @RequestParam long vertexToId, @RequestParam int direction, @RequestParam EdgeType type) {
        Scheme updatedScheme = schemeService.createAndAddEdgeToScheme(schemeId, distance, vertexFromId, vertexToId, direction, type);
        return ResponseEntity.ok(updatedScheme);
    }
}

