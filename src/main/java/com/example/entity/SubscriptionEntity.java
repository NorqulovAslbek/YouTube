package com.example.entity;

import com.example.enums.SubscriptionNotificationType;
import com.example.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private ProfileEntity profile;

    @Column(name = "channel_id")
    private Integer channelId;
    @OneToOne
    @JoinColumn(name = "channel_id", updatable = false, insertable = false)
    private ChannelEntity channel;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private SubscriptionStatus status;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "notification_type")
    private SubscriptionNotificationType notificationType;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "unsubscribe_date")
    private LocalDateTime unsubscribeDate;

}
