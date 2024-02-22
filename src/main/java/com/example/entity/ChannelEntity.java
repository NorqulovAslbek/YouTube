package com.example.entity;

import com.example.enums.ChannelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @OneToOne
    private AttachEntity photo;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Enumerated(EnumType.STRING)
    private ChannelStatus status;
    @OneToOne
    private AttachEntity banner;
}
