package com.example.service;

import com.example.dto.CreateTagDTO;
import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exp.AppBadException;
import com.example.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TagService {
    @Autowired
    private TagRepository repository;

    public List<TagDTO> create(CreateTagDTO tagDTO) {
        List<TagDTO> tageNames = new LinkedList<>();
        for (String name : tagDTO.getName()) {
            TagEntity entity = new TagEntity();
            entity.setName(name);
            repository.save(entity);
            tageNames.add(toDTO(entity));
        }
        return tageNames;
    }

    public TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setVisible(entity.getVisible());
        dto.setName(entity.getName());
        return dto;
    }

    public Boolean update(TagDTO dto, Integer id) {
        Optional<TagEntity> optional = repository.findById(id);
        if (!optional.isPresent()) {
            log.warn("Tag not found");
            throw new AppBadException("Tag not found");
        }
        TagEntity entity = optional.get();
        if (!entity.getVisible().equals(true)) {
            log.warn("tag is disabled");
            throw new AppBadException("tag is disabled");
        }
        int effectRows = repository.update(dto.getName(), LocalDateTime.now(), dto.getId());
        return effectRows == 1;
    }

    public Boolean delete(Integer id) {
        Optional<TagEntity> optional = repository.findById(id);
        if (optional.isEmpty()) {
            log.warn("delete {} ", id);
            throw new AppBadException("Tag not found");
        }
        TagEntity entity = optional.get();
        entity.setVisible(false);
        repository.save(entity);
        return true;
    }

    public List<TagDTO> getList() {
        List<TagDTO> dtoList = new LinkedList<>();
        Iterable<TagEntity> all = repository.findAll();
        for (TagEntity entity : all) {
            if (entity.getVisible().equals(true)) {
                TagDTO dto = new TagDTO();
                dto.setName(entity.getName());
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

}
