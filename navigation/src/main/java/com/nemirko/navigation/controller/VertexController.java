
package com.nemirko.navigation.controller;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.service.Pair;
import com.nemirko.navigation.service.VertexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vertex")
public class VertexController {

    private VertexService service;

    @Autowired
    public VertexController(VertexService service) {
        this.service = service;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Vertex> getAll() {
        return service.findAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Vertex getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value="/availability", method=RequestMethod.GET)
    public List<Vertex> getByAvailability(@RequestParam boolean availability) {
        return service.findByAvailability(availability);
    }

    @RequestMapping(method=RequestMethod.POST)
    public Vertex create(@RequestBody Vertex vertex) {
        return service.save(vertex);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vertex> editVertex(@PathVariable Long id, @RequestBody Vertex updatedVertex) {
        try {
            Vertex editedVertex = service.edit(id, updatedVertex);
            return ResponseEntity.ok(editedVertex);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/filter")
    public List<Vertex> getVerticesByComplexCondition(
            @RequestParam Map<String, String> params,
            @RequestParam(required = false) List<String> sortFields,
            @RequestParam(required = false) List<String> sortDirections) {

        Map<String, Pair<String, String>> parsedParams = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String[] valueAndOperation = entry.getValue().split(",");
            if (valueAndOperation.length == 2) {
                parsedParams.put(entry.getKey(), new Pair<>(valueAndOperation[0], valueAndOperation[1]));
            } else if (valueAndOperation.length == 1) {
                parsedParams.put(entry.getKey(), new Pair<>(valueAndOperation[0], ""));
            }
        }

        return service.findByComplexCondition(parsedParams, sortFields, sortDirections);
    }
}
