package com.unimate.unimate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "courseContent")
public class CourseContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private Integer type;

    private String description;

    private String link;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId")
    private Course course;

}
