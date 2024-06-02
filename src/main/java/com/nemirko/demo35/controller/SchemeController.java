package com.nemirko.demo35.controller;

import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.service.SchemeService;
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
    public ResponseEntity<Scheme> createScheme(@RequestParam List<Long> vertexIds, @RequestParam List<Long> edgeIds, @RequestParam long level) {
        Scheme scheme = schemeService.create(vertexIds, edgeIds, level);
        return ResponseEntity.ok(scheme);
    }
}

