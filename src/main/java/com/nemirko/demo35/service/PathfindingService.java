package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.entity.Vertex;

import java.util.List;

public interface PathfindingService {
    List<Vertex> findPath(Scheme scheme, Vertex start, Vertex goal);
}