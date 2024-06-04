package com.nemirko.ui.service;

import com.nemirko.navigation.entity.Scheme;
import com.nemirko.ui.dto.EdgeDTO;
import com.nemirko.ui.dto.GraphDTO;
import com.nemirko.ui.dto.NodeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    public GraphDTO transformToGraphDTO(Scheme scheme) {
        List<NodeDTO> nodes = scheme.getVertexes().stream()
                .map(vertex -> new NodeDTO(vertex.getId(), vertex.getName(),vertex.getDescription(), vertex.getAvailability()))
                .collect(Collectors.toList());

        List<EdgeDTO> edges = scheme.getEdges().stream()
                .map(edge -> new EdgeDTO(edge.getVertexFrom().getId(), edge.getVertexTo().getId(), edge.getDistance(), edge.getDirection()))
                .collect(Collectors.toList());

        return new GraphDTO(nodes, edges);
    }
    public List<Long> transformToListOfLongs(List<Scheme> list) {
        return list.stream().map(Scheme::getId).toList();
    }
}
