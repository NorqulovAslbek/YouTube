package com.example.repository;

import com.example.entity.ReportEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, Integer>, PagingAndSortingRepository<ReportEntity, Integer> {

}
