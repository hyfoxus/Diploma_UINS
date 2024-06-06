package com.nemirko.navigation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vertex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private Map<Long, Integer> angles = new HashMap<>(); //key is an id of the edge adjustment to this vertex and value is to where this edge goes

    private String name;

    private String description;

    private VertexType type;

    private Boolean availability;


}
