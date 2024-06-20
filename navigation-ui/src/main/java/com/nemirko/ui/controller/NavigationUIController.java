package com.nemirko.ui.controller;

import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.service.TranslatorService;
import com.nemirko.ui.service.TranslationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

@Controller
@RequestMapping("/api/navigation")
public class NavigationUIController {

    private final TranslationService translationService;
    private final RestTemplate restTemplate;
    private final String navigationServiceUrl;

    public NavigationUIController(TranslationService translationService, RestTemplate restTemplate, @Value("${navigation.service.url}") String navigationServiceUrl) {
        this.translationService = translationService;
        this.restTemplate = restTemplate;
        this.navigationServiceUrl = navigationServiceUrl;
    }

    @GetMapping("/build-path")
    public ResponseEntity<List<String>> buildPath(@RequestParam long vertexFromId, @RequestParam long vertexToId, @RequestParam int amountRoutes) {
        String url = String.format("%s/api/navigation/shortest-paths?vertexFromId=%d&vertexToId=%d&amountRoutes=%d",
                navigationServiceUrl, vertexFromId, vertexToId, amountRoutes);
        ResponseEntity<List<List<Vertex>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<List<Vertex>>>() {}
        );
        List<String> translatedRoute = translationService.translateRoute(response.getBody().get(0));
        return ResponseEntity.ok(translatedRoute);

        //j
    }
}