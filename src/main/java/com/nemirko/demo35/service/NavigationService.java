package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.entity.Vertex;

import java.util.List;

public interface NavigationService {
    List<List<Vertex>> getShortestPaths(long startId, long endId, int amountRoutes);
}
