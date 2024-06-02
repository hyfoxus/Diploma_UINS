package com.nemirko.demo35.entity;

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
    private long id;

    @ElementCollection
    private Map<Long, Integer> angles = new HashMap<>();

    @ManyToMany
    @JoinColumn(name = "scheme_id")
    List<Scheme> schemes;

    private String name;

    private String description;

    private VertexType type;

    private boolean availability;

}
