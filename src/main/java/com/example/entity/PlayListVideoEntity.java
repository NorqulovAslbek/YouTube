package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "play_list_video")
public class PlayListVideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "playlist_id")
    private Integer playListId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id",insertable = false,updatable = false)
    private PlaylistEntity playlist;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id",insertable = false,updatable = false)
    private VideoEntity video;

    @Column(name = "order_num")
    private Integer orderNum;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
