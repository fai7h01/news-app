package com.localnews.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News extends BaseEntity{

    private String author;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
}
