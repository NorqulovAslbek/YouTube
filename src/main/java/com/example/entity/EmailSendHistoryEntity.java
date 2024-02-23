package com.example.entity;

import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailSendHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "text")
    private String message;
    private String email;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    private LocalDateTime createdDate = LocalDateTime.now();
}
