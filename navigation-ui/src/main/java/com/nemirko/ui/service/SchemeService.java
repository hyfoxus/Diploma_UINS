package com.nemirko.ui.service;

import com.nemirko.navigation.entity.Scheme;
import com.nemirko.navigation.entity.Vertex;
import com.nemirko.ui.dto.EdgeDTO;
import com.nemirko.ui.dto.GraphDTO;
import com.nemirko.ui.dto.NodeDTO;
import jakarta.persistence.Version;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    public GraphDTO transformToGraphDTO(Scheme scheme) {
        List<NodeDTO> nodes =
                scheme.getVertexes().stream()
                        .map(
                                vertex ->
                                        new NodeDTO(
                                                vertex.getId(),
                                                vertex.getName(),
                                                vertex.getDescription(),
                                                vertex.getAvailability(),
                                                vertex.getType()))
                        .collect(Collectors.toList());

        List<EdgeDTO> edges =
                scheme.getEdges().stream()
                        .map(
                                edge ->
                                        new EdgeDTO(
                                                edge.getVertexFrom().getId(),
                                                edge.getVertexTo().getId(),
                                                edge.getDistance(),
                                                edge.getDirection()))
                        .collect(Collectors.toList());

        return new GraphDTO(scheme.getLevel(), nodes, edges, scheme.getDescription());
    }

    public List<Long> transformToListOfLongs(List<Scheme> list) {
        return list.stream().map(Scheme::getId).toList();
    }

    public Scheme transformToScheme(GraphDTO graphDTO) {
        Scheme scheme = new Scheme();
        scheme.setDescription(graphDTO.getDescription());
        scheme.setLevel(graphDTO.getLevel());
        return scheme;
    }
    public Vertex transformToVertex(NodeDTO nodeDTO) {
        Vertex vertex = new Vertex();
        vertex.setName(nodeDTO.getLabel());
        vertex.setType(nodeDTO.getType());
        vertex.setAvailability(nodeDTO.getAvailability());
        vertex.setDescription(nodeDTO.getDescription());
        return vertex;
    }
}