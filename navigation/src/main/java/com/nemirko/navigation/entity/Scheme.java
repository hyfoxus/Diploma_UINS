package com.nemirko.navigation.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Scheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private long level;

    @ManyToMany
    @JoinTable(
            name = "scheme_vertexes",
            joinColumns = @JoinColumn(name = "scheme_id"),
            inverseJoinColumns = @JoinColumn(name = "vertex_id")
    )
    private List<Vertex> vertexes;

    @ManyToMany
    @JoinTable(
            name = "scheme_edges",
            joinColumns = @JoinColumn(name = "scheme_id"),
            inverseJoinColumns = @JoinColumn(name = "edge_id")
    )
    private List<Edge> edges;

}
