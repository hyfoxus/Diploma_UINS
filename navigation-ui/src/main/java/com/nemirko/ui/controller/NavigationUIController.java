package com.nemirko.ui.controller;

import com.nemirko.navigation.entity.Vertex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/api/navigation")
public class NavigationUIController {

    private final RestTemplate restTemplate;
    private final String navigationServiceUrl;

    public NavigationUIController(RestTemplate restTemplate, @Value("${navigation.service.url}") String navigationServiceUrl) {
        this.restTemplate = restTemplate;
        this.navigationServiceUrl = navigationServiceUrl;
    }

    @GetMapping("/build-path")
    public ResponseEntity<List<Vertex>> buildPath(@RequestParam long vertexFromId, @RequestParam long vertexToId, @RequestParam int amountRoutes) {
        String url = String.format("%s/api/navigation/shortest-paths?vertexFromId=%d&vertexToId=%d&amountRoutes=%d",
                navigationServiceUrl, vertexFromId, vertexToId, amountRoutes);
        ResponseEntity<List<Vertex>> response = restTemplate.getForEntity(url, (Class<List<Vertex>>)(Class<?>)List.class);
        return response;
    }
}