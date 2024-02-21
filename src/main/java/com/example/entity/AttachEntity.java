package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "size")
    private Long size;
    @Column(name = "type")
    private String type;
    @Column(name = "path")
    private String path;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
