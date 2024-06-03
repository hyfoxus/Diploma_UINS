package com.nemirko.navigation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    private int distance; // No special annotations needed for basic fields.

    private EdgeType type;

    @ManyToOne
    @JoinColumn(name = "vertex1_id")
    private Vertex vertexFrom;

    @ManyToOne
    @JoinColumn(name = "vertex2_id")
    private Vertex vertexTo;
}
