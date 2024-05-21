package com.nemirko.demo35.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Corridor {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    private int distance; // No special annotations needed for basic fields.

    @OneToOne
    @JoinColumn(name = "vertex1_id")
    private Vertex vertex1;

    @OneToOne
    @JoinColumn(name = "vertex2_id")
    private Vertex vertex2;
}
