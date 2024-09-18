package com.localnews.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;
}
