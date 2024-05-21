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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id")
    private List<Vertex> vertexes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id")
    private List<Corridor> corridors;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "scheme_id")
    private List<Vertex> ports;
}
