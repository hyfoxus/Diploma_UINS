package com.nemirko.demo35.service;

import com.nemirko.demo35.entity.Vertex;
import com.nemirko.demo35.repository.VertexRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DijkstraPathfindingServiceImplTest {

    @Mock
    private VertexRepository vertexRepository;

    @InjectMocks
    private DijkstraPathfindingServiceImpl navigationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetShortestPaths_InvalidStartId_ShouldThrowException() {
        long startId = 1L;
        long endId = 2L;
        when(vertexRepository.findById(startId)).thenReturn(Optional.empty());
        when(vertexRepository.findById(endId)).thenReturn(Optional.of(new Vertex()));

        assertThrows(IllegalStateException.class, () -> navigationService.getShortestPaths(startId, endId, 1));
    }

    @Test
    public void testGetShortestPaths_ValidVertices_ShouldReturnPaths() {
        long startId = 1L;
        long endId = 2L;

        Vertex startVertex = new Vertex();
        Vertex endVertex = new Vertex();

        Map<Long, Integer> neighbors = new HashMap<>();
        neighbors.put(endId, 1);
        startVertex.setAngles(neighbors);

        endVertex.setAngles(new HashMap<>());

        when(vertexRepository.findById(startId)).thenReturn(Optional.of(startVertex));
        when(vertexRepository.findById(endId)).thenReturn(Optional.of(endVertex));

        assertEquals(1, navigationService.getShortestPaths(startId, endId, 1).size());
    }
}
