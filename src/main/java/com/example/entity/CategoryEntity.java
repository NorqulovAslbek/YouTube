package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {
    @Column(name = "name_uz", nullable = false)
    private String nameUz;
    @Column(name = "name_ru", nullable = false)
    private String nameRU;
    @Column(name = "name_en", nullable = false)
    private String nameEn;
}
