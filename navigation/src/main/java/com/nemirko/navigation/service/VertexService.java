package com.nemirko.navigation.service;

import com.nemirko.navigation.entity.Vertex;
import com.nemirko.navigation.entity.VertexType;
import com.nemirko.navigation.repository.EdgeRepository;
import com.nemirko.navigation.repository.VertexRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VertexService {
  private final VertexRepository vertexRepository;

  @Autowired private EdgeRepository edgeRepository;

  @Autowired
  public VertexService(VertexRepository repository) {
    this.vertexRepository = repository;
  }

  public List<Vertex> findAll() {
    return vertexRepository.findAll();
  }

  public Vertex save(Vertex vertex) {
    return vertexRepository.save(vertex);
  }

  public Vertex findById(Long id) {
    return vertexRepository.findById(id).orElse(null);
  }

  public List<Vertex> findByAvailability(boolean availability) {
    return vertexRepository.findByAvailability(availability);
  }

  public Vertex edit(Long id, Vertex updatedVertex) {
    // Fetch the vertex by its ID
    Optional<Vertex> vertexOpt = vertexRepository.findById(id);

    if (vertexOpt.isPresent()) {
      Vertex vertex = vertexOpt.get();

      if (updatedVertex.getName() != null && !updatedVertex.getName().isEmpty()) {
        vertex.setName(updatedVertex.getName());
      }

      // Check if the updated availability field is not empty
      if (updatedVertex.getAvailability() != vertex.getAvailability()) {
        // Only update the availability if the updatedVertex's availability is not null
        if (updatedVertex.getAvailability() != null) {
          vertex.setAvailability(updatedVertex.getAvailability());
        }
      }

      if (updatedVertex.getDescription() != null && !updatedVertex.getDescription().isEmpty()) {
        vertex.setDescription(updatedVertex.getDescription());
      }
      // You can update other properties here

      // Save the updated vertex
      return vertexRepository.save(vertex);
    } else {
      // Handle the case where the vertex is not found
      throw new RuntimeException("Vertex with id " + id + " not found.");
    }
  }

  public void delete(Long id) {
    // Fetch the vertex by its ID
    Optional<Vertex> vertexOpt = vertexRepository.findById(id);

    if (vertexOpt.isPresent()) {
      Vertex vertex = vertexOpt.get();

      // Iterate over the neighbors and delete corresponding edges
      vertex
              .getAngles()
              .forEach(
                      (edgeId, direction) -> {
                        // Fetch the edge by its ID and delete it if present
                        edgeRepository.findById(edgeId).ifPresent(edgeRepository::delete);
                      });
    } else {
      // Handle the case where the vertex is not found
      throw new RuntimeException("Vertex with id " + id + " not found.");
    }
    vertexRepository.deleteById(id);
  }

  public List<Vertex> findByComplexCondition(
          Map<String, Pair<String, String>> params,
          List<String> sortFields,
          List<String> sortDirections) {

    Specification<Vertex> specification = (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      for (Map.Entry<String, Pair<String, String>> entry : params.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue().getKey();
        String operation = entry.getValue().getValue();

        if ("availability".equalsIgnoreCase(key)) {
          predicates.add(criteriaBuilder.equal(root.get(key), Boolean.parseBoolean(value)));
        } else if ("type".equalsIgnoreCase(key)) {
          predicates.add(criteriaBuilder.equal(root.get(key), VertexType.fromStringIgnoreCase(value)));
        }
        else if ("eq".equalsIgnoreCase(operation)) {
          predicates.add(criteriaBuilder.equal(root.get(key), value));
        } else if ("like".equalsIgnoreCase(operation)) {
          predicates.add(criteriaBuilder.like(root.get(key), "%" + value + "%"));
        } else if ("starts".equalsIgnoreCase(operation)) {
          predicates.add(criteriaBuilder.like(root.get(key), value + "%"));
        }
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };

    Sort sort = Sort.unsorted();
    if (sortFields != null && sortDirections != null && sortFields.size() == sortDirections.size()) {
      for (int i = 0; i < sortFields.size(); i++) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirections.get(i));
        sort = sort.and(Sort.by(direction, sortFields.get(i)));
      }
    }

    return vertexRepository.findAll(specification, sort);
  }
}