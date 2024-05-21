package com.nemirko.demo35.service;

import java.util.List;

import com.nemirko.demo35.entity.Scheme;
import com.nemirko.demo35.repository.SchemeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@AllArgsConstructor
public class SchemeService {
    private final SchemeRepository schemeRepository;

    public List<Scheme> getAllSchemes() {
        return schemeRepository.findAll();
    }

    public Scheme getSchemeById(Long id) {
        return schemeRepository.findById(id).orElse(null);
    }

    public Scheme createScheme(Scheme scheme) {
        return schemeRepository.save(scheme);
    }
}
