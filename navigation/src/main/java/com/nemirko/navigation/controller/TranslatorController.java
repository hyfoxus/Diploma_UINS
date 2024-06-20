package com.nemirko.navigation.controller;

import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.service.NavigationService;
import com.nemirko.navigation.service.TranslatorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/translation")
@AllArgsConstructor
public class TranslatorController {

    private NavigationService navigationService;


    private TranslatorService translatorService;

    @GetMapping("/translatedRoute")
    public List<List<String>> translateRoute(@RequestParam long startId,
                                             @RequestParam long endId,
                                             @RequestParam int amountRoutes) {
        List<List<Vertex>> routes = navigationService.getShortestPaths(startId, endId, amountRoutes);
        List<List<String>> translatedRoutes = new ArrayList<>();

        for (List<Vertex> route : routes) {
            List<String> translatedRoute = translatorService.translateRoute(route);
            translatedRoutes.add(translatedRoute);
        }

        return translatedRoutes;
    }
}
