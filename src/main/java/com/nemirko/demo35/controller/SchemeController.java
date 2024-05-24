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
        return ResponseEntity.ok(schemeService.getAllSchemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getSchemeById(@PathVariable Long id) {
        return ResponseEntity.ok(schemeService.getSchemeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Scheme> createScheme(@RequestBody Scheme scheme) {
        return ResponseEntity.ok(schemeService.createScheme(scheme));
    }
}

