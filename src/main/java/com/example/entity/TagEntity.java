package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Tag")
public class TagEntity extends BaseEntity {
    @Column(name = "name",nullable = false)
    private  String name;
}
