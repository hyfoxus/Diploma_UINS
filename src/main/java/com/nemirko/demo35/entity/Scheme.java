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

    private long level;

    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL)
    private List<Vertex> vertexes;

    @OneToMany(mappedBy = "scheme", cascade = CascadeType.ALL)
    private List<Edge> edges;

}
