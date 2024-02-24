package com.example.repository;

import com.example.dto.EmailSendHistoryDTO;
import com.example.dto.FilterEmailDTO;
import com.example.dto.PaginationResultDTO;
import com.example.entity.EmailSendHistoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmailSendHistoryFilterRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<EmailSendHistoryEntity> filter(FilterEmailDTO filter, Integer page, Integer size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (filter.getEmail() != null) {
            builder.append(" and s.email=:email ");
            params.put("email", filter.getEmail());
        }

        if (filter.getFromDate() != null && filter.getToDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append(" and createdDate between :fromDate and :toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        }
        if (filter.getFromDate() != null) {
            LocalDateTime fromDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MIN);
            LocalDateTime toDate = LocalDateTime.of(filter.getFromDate(), LocalTime.MAX);
            builder.append(" and createdDate between : fromDate and : toDate ");
            params.put("fromDate", fromDate);
            params.put("toDate", toDate);
        }
        if (filter.getToDate() != null) {
            LocalDateTime time = LocalDateTime.of(filter.getToDate(), LocalTime.MAX);
            builder.append("and createdDate <= :time ");
            params.put("time", time);
        }

        String selectBuilder = "From EmailSendHistoryEntity s where 1=1 " + builder + " order by createdDate desc";

        String countBuilder = "Select count(s) from EmailSendHistoryEntity as s where 1=1 " + builder;

        Query selectQuery = entityManager.createQuery(selectBuilder);
        Query countQuery = entityManager.createQuery(countBuilder);

        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult((page - 1) * size);

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }
        List<EmailSendHistoryEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();
        return new PaginationResultDTO<>(entityList, totalElements);
    }


}
