package com.unimate.unimate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kelas")
public class Kelas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Account teacher;

    private String name;

    private Float rating;

    private String category;

    private String description;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "benefits", joinColumns = @JoinColumn(name = "kelasId"))
    @Column(name = "benefit", nullable = false)
    private List<String> benefits = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "syllabuses", joinColumns = @JoinColumn(name = "kelasId"))
    @Column(name = "syllabus", nullable = false)
    private List<String> syllabuses;

    private Long price;

    private Boolean isFinished;

    @OneToMany(mappedBy = "kelas")
    @JsonIgnore
    private List<KelasSiswa> kelasSiswa = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;

    private Date deletedAt;
}