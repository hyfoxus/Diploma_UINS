package com.nemirko.navigation.controller;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.service.VertexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Other CRUD endpoints can be defined here...
}

