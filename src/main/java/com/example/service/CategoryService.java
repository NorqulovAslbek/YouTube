package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.CreateCategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.enums.AppLanguage;
import com.example.exp.AppBadException;
import com.example.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    public Object create(CreateCategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findByNameUzAndNameRUAndNameEn(dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        if (optional.isPresent()){
            log.warn("This category exists");
           throw new AppBadException("This category exists");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRU(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());

        categoryRepository.save(entity);

        return toDTO(entity);


    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRU(entity.getNameRU());
        dto.setNameEn(entity.getNameEn());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}
