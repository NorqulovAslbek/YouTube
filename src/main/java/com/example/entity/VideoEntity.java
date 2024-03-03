package com.example.entity;

import com.example.enums.VideoStatus;
import com.example.enums.VideoType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(name = "title", nullable = false, columnDefinition = "Text")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "Text")
    private String description;
    @Column(name = "category_id")
    private Integer categoryId;
    @OneToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;
    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", updatable = false, insertable = false)
    private AttachEntity attach;
    @Column(name = "preview_attach_id")
    private String previewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity preview;
    @Column(name = "channel_id")
    private Integer channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VideoStatus status;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private VideoType type;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "published_date")
    private LocalDateTime publishedDate;
    @Column(name = "view_count")
    private Long viewCount = 0l;
    @Column(name = "shared_count")
    private Long sharedCount = 0l;
    private Integer likeCount = 0;
    private Integer dislikeCount = 0;

}
