package com.unimate.unimate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ujian")
public class Ujian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kelasId")
    private Kelas kelas;

    @OneToMany(mappedBy = "ujian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionContent> questionContents = new ArrayList<>();

    @OneToMany(mappedBy = "ujian", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UjianSiswa> ujianSiswa = new ArrayList<>();

    private String title;

    //in seconds
    private Long duration;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;
    
    private Double passingGrade;

    private Date deletedAt;
}