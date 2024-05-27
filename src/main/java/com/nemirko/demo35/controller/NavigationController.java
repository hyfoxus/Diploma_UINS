package com.nemirko.demo35.controller;

import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.service.NavigationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/navigation")
@AllArgsConstructor
public class NavigationController {

    private final NavigationService navigationService;

    @GetMapping("/shortest-paths")
    public List<List<Vertex>> getThreeShortestPaths(@RequestParam long vertex1Id, @RequestParam long vertex2Id) {
        return navigationService.getThreeShortestRoutes(vertex1Id, vertex2Id);
    }
}