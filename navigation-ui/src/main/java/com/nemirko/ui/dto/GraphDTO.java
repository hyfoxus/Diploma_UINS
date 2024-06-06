package com.nemirko.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphDTO {
    private Long level;
    private List<NodeDTO> nodes;
    private List<EdgeDTO> edges;
    private String description;
}
