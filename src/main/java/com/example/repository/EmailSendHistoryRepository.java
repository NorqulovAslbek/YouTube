package com.example.repository;

import com.example.entity.EmailSendHistoryEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailSendHistoryRepository extends CrudRepository<EmailSendHistoryEntity, Integer>, PagingAndSortingRepository<EmailSendHistoryEntity, Integer> {
    List<EmailSendHistoryEntity> findByEmail(String email);
    Page<EmailSendHistoryEntity> findByEmail(String email, Pageable pageable);

    List<EmailSendHistoryEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT count (s) from EmailSendHistoryEntity s where s.email =?1 and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);

}
