package com.nemirko.demo35.controller;

import com.nemirko.demo35.entity.Edge;
import com.nemirko.demo35.entity.EdgeType;
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
    public ResponseEntity<List<Edge>> getAll() {
        List<Edge> edges = edgeService.getAll();
        return ResponseEntity.ok(edges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Edge> getById(@PathVariable Long id) {
        Edge edge = edgeService.getById(id);
        return ResponseEntity.ok(edge);
    }


    @PostMapping
    public ResponseEntity<Edge> save(@RequestParam int distance, @RequestParam long vertexFromId,
                                     @RequestParam long vertexToId, @RequestParam int direction, @RequestParam EdgeType type) {
        try {
            Edge edge = edgeService.create(distance, vertexFromId, vertexToId, direction, type);
            return ResponseEntity.ok(edge);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        edgeService.delete(id);
    }
}
