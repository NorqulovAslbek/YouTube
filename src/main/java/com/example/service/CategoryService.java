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

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
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
        if (optional.isPresent()) {
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

    public Boolean update(CreateCategoryDTO dto, Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findByNameUzAndNameRUAndNameEn(dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        if (optional.isPresent()) {
            log.warn("This category exists");
            throw new AppBadException("This category exists");
        }
        CategoryEntity entity = get(id);
        if (!entity.getVisible().equals(true)) {
            log.warn("category is disabled");
            throw new AppBadException("category is disabled");
        }

        int effectRows = categoryRepository.update(dto.getNameUz(), dto.getNameRu(), dto.getNameEn(), LocalDateTime.now(), id);
        return effectRows == 1;
    }


    public Boolean delete(Integer id) {
        CategoryEntity categoryEntity = get(id);
        if (categoryEntity == null) {
            log.warn("delete {}", id);
            throw new AppBadException("category not found");
        }
        categoryEntity.setVisible(false);
        categoryRepository.save(categoryEntity);
        return true;
    }

    private CategoryEntity get(Integer id) {
        return categoryRepository.getById(id).orElseThrow(() -> new AppBadException("category not found"));
    }

    public List<CategoryDTO> getList(AppLanguage lan) {
        List<CategoryDTO> dtoList = new LinkedList<>();
        Iterable<CategoryEntity> all = categoryRepository.findAll();

        for (CategoryEntity entity : all) {
            if (entity.getVisible().equals(true)) {
                CategoryDTO dto = new CategoryDTO();
                switch (lan) {
                    case UZ -> dto.setName(entity.getNameUz());
                    case RU -> dto.setName(entity.getNameRU());
                    default -> dto.setName(entity.getNameEn());
                }
                dtoList.add(dto);
            }
        }
        return dtoList;
    }
}

