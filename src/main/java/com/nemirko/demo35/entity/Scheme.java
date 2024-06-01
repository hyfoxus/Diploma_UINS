package com.nemirko.demo35.entity;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id")
    private List<Vertex> vertexes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id")
    private List<Edge> edges;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id")
    private List<Vertex> ports;
}
