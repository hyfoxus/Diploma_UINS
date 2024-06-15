package com.nemirko.ui.controller;
import com.nemirko.navigation.entity.Edge;
import com.nemirko.navigation.entity.Scheme;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.ui.dto.EdgeDTO;
import com.nemirko.ui.dto.GraphDTO;
import com.nemirko.ui.dto.NodeDTO;
import com.nemirko.ui.service.SchemeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/graph")
public class GraphEditorController {

  private static final Logger logger = LoggerFactory.getLogger(GraphEditorController.class);

  private final RestTemplate restTemplate;
  private final SchemeService schemeService;

  @Value("${navigation.service.url}")
  private String navigationServiceUrl;

  @Autowired
  public GraphEditorController(RestTemplate restTemplate, SchemeService schemeService) {
    this.restTemplate = restTemplate;
    this.schemeService = schemeService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<GraphDTO> getGraph(@PathVariable Long id) {
    try {
      String url = navigationServiceUrl + "/api/scheme/" + id;
      ResponseEntity<Scheme> response = restTemplate.getForEntity(url, Scheme.class);
      GraphDTO graphDTO = schemeService.transformToGraphDTO(response.getBody());
      return ResponseEntity.ok(graphDTO);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Error occurred while processing the request: {}", e.getMessage());
      return ResponseEntity.status(e.getStatusCode()).build();
    } catch (Exception e) {
      logger.error("Unexpected error occurred: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }

  @GetMapping("/list")
  public ResponseEntity<List<Long>> listSchemes() {
    try {
      String url = navigationServiceUrl + "/api/scheme/";
      ResponseEntity<List<Scheme>> response =
              restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
      List<Long> list = schemeService.transformToListOfLongs(response.getBody());
      return ResponseEntity.ok(list);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Error occurred while processing the request: {}", e.getMessage());
      return ResponseEntity.status(e.getStatusCode()).build();
    } catch (Exception e) {
      logger.error("Unexpected error occurred: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping
  public ResponseEntity<Scheme> updateGraph(@RequestBody Scheme scheme) {
    try {
      String url = navigationServiceUrl + "/api/scheme/";
      ResponseEntity<Scheme> response = restTemplate.postForEntity(url, scheme, Scheme.class);
      return response;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Error occurred while processing the request: {}", e.getMessage());
      return ResponseEntity.status(e.getStatusCode()).build();
    } catch (Exception e) {
      logger.error("Unexpected error occurred: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/scheme")
  public ResponseEntity<Scheme> createScheme(@RequestBody GraphDTO graphDTO) {

    try {
      String url = navigationServiceUrl + "/api/scheme/";
      ResponseEntity<Scheme> response =
              restTemplate.postForEntity(url, schemeService.transformToScheme(graphDTO), Scheme.class);
      return response;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Error occurred while processing the request: {}", e.getMessage());
      return ResponseEntity.status(e.getStatusCode()).build();
    } catch (Exception e) {
      logger.error("Unexpected error occurred: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/vertex")
  public ResponseEntity<Vertex> createVertex(@RequestBody NodeDTO nodeDTO) {
    try {
      String url = navigationServiceUrl + "/api/vertex";
      ResponseEntity<Vertex> response =
              restTemplate.postForEntity(url, schemeService.transformToVertex(nodeDTO), Vertex.class);
      return response;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Error occurred while processing the request: {}", e.getMessage());
      return ResponseEntity.status(e.getStatusCode()).build();
    } catch (Exception e) {
      logger.error("Unexpected error occurred: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }

  @PostMapping("/edge")
  public ResponseEntity<Edge> createEdge(@RequestBody EdgeDTO edgeDTO) {
    try {
      String url = navigationServiceUrl + "/api/vertex";
      ResponseEntity<Edge> response =
              restTemplate.postForEntity(url, schemeService.transformToEdge(edgeDTO), Edge.class);
      return response;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      logger.error("Error occurred while processing the request: {}", e.getMessage());
      return ResponseEntity.status(e.getStatusCode()).build();
    } catch (Exception e) {
      logger.error("Unexpected error occurred: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }
}