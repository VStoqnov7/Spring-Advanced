package com.example.mobilele.service.impl;

import com.example.mobilele.models.entity.Model;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.service.ModelService;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {
    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

}
