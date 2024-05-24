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
    public ResponseEntity<List<Corridor>> getAllCorridors() {
        List<Corridor> corridors = corridorService.getAllCorridors();
        return ResponseEntity.ok(corridors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corridor> getCorridorById(@PathVariable Long id) {
        Corridor corridor = corridorService.getCorridorById(id);
        return ResponseEntity.ok(corridor);
    }


    @PostMapping
    public ResponseEntity<Corridor> saveCorridor(@RequestParam int distance,@RequestParam long vertex1Id, @RequestParam long vertex2Id) {
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
