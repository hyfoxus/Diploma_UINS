package com.nemirko.ui.dto;

import com.nemirko.navigation.entity.VertexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {
    private Long id;
    private String label;
    private String description;
    private Boolean availability;
    private VertexType type;
}
