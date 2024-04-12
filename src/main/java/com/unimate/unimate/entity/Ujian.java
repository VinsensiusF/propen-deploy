package com.unimate.unimate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @JsonIgnore
    @JoinColumn(name = "kelasId")
    private Kelas kelas;

    @OneToMany(mappedBy = "ujian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionContent> questionContents = new ArrayList<>();

    private String title;

    //in seconds
    private Long duration;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;

    private Date deletedAt;
}