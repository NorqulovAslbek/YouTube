package com.example.repository;

import com.example.dto.PaginationResultDTO;
import com.example.dto.VideoFilterDTO;
import com.example.entity.VideoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VideoSearchRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<VideoEntity> filter(VideoFilterDTO filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> map = new HashMap<>();
        if (filter.getTitle() != null) {
            builder.append("and lower(title) like:title ");
            map.put("title", "%" + filter.getTitle().toLowerCase() + "%");
        }

        StringBuilder selectBuilder = new StringBuilder("from VideoEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append("order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM VideoEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page - 1) * size);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : map.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<VideoEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<VideoEntity>(entityList, totalElements);
    }

}

