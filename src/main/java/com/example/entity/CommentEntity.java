package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    @Column(name = "video_id")
    private String videoId;
    @ManyToOne
    @JoinColumn(name = "video_id", insertable = false, updatable = false)
    private VideoEntity video;
    @Column(columnDefinition = "text")
    private String content;
    @Column(name = "replay_id")
    private Integer replyId;
    @ManyToOne
    @JoinColumn(name = "replay_id",insertable = false,updatable = false)
    private CommentEntity reply;
    private Integer likeCount=0;
    private Integer dislikeCount=0;
    private LocalDateTime createdDate= LocalDateTime.now();
}
