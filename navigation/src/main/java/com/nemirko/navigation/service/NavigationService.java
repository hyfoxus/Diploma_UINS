package com.nemirko.navigation.service;

import com.nemirko.navigation.entity.Vertex;

import java.util.List;

public interface NavigationService {
    List<List<Vertex>> getShortestPaths(long startId, long endId, int amountRoutes);
}
