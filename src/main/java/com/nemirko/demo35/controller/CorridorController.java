package com.nemirko.demo35.controller;

import com.nemirko.demo35.entity.Corridor;
import com.nemirko.demo35.service.CorridorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corridors")
public class CorridorController {

    @Autowired
    private CorridorService corridorService;

    @GetMapping
    public List<Corridor> getAllCorridors() {
        return corridorService.getAllCorridors();
    }

    @GetMapping("/{id}")
    public Corridor getCorridorById(@PathVariable Long id) {
        return corridorService.getCorridorById(id);
    }

    @PostMapping
    public Corridor saveCorridor(@RequestBody Corridor corridor) {
        return corridorService.saveCorridor(corridor);
    }
    @PostMapping("/new")
    public ResponseEntity<Corridor> createCorridor(@RequestParam int distance,@RequestParam long vertex1Id, @RequestParam long vertex2Id) {
        try {
            Corridor corridor = corridorService.createCorridorWithVertices(distance, vertex1Id, vertex2Id);
            return ResponseEntity.ok(corridor);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public void deleteCorridor(@PathVariable Long id) {
        corridorService.deleteCorridor(id);
    }
}
