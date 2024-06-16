
package com.nemirko.ui.controller;

import com.nemirko.navigation.entity.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vertex")
public class VertexController {

    private final RestTemplate restTemplate;
    private final String navigationServiceUrl;

    @Autowired
    public VertexController(RestTemplate restTemplate, @Value("${navigation.service.url}") String navigationServiceUrl) {
        this.restTemplate = restTemplate;
        this.navigationServiceUrl = navigationServiceUrl;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Vertex>> getVerticesByComplexCondition(
            @RequestParam Map<String, String> params,
            @RequestParam(required = false) List<String> sortFields,
            @RequestParam(required = false) List<String> sortDirections) {

        StringBuilder urlBuilder = new StringBuilder(navigationServiceUrl + "/api/vertex/filter?");
        params.forEach((key, value) -> urlBuilder.append(key).append("=").append(value).append("&"));
        if (sortFields != null) {
            for (String field : sortFields) {
                urlBuilder.append("sortFields=").append(field).append("&");
            }
        }
        if (sortDirections != null) {
            for (String direction : sortDirections) {
                urlBuilder.append("sortDirections=").append(direction).append("&");
            }
        }

        String url = urlBuilder.toString();
        ResponseEntity<List<Vertex>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        return ResponseEntity.ok(response.getBody());
    }
}
