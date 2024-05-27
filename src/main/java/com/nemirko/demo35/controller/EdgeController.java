package com.nemirko.demo35.controller;

import com.nemirko.demo35.entity.Edge;
import com.nemirko.demo35.service.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {

    @Autowired
    private EdgeService edgeService;

    @GetMapping
    public ResponseEntity<List<Edge>> getAllCorridors() {
        List<Edge> edges = edgeService.getAllCorridors();
        return ResponseEntity.ok(edges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Edge> getCorridorById(@PathVariable Long id) {
        Edge edge = edgeService.getCorridorById(id);
        return ResponseEntity.ok(edge);
    }


    @PostMapping
    public ResponseEntity<Edge> saveCorridor(@RequestParam int distance, @RequestParam long vertex1Id,
                                             @RequestParam long vertex2Id, @RequestParam int direction) {
        try {
            Edge edge = edgeService.createCorridorWithVertices(distance, vertex1Id, vertex2Id, direction);
            return ResponseEntity.ok(edge);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public void deleteCorridor(@PathVariable Long id) {
        edgeService.deleteCorridor(id);
    }
}
