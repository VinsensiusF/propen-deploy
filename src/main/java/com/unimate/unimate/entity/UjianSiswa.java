package com.unimate.unimate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ujianSiswa")
public class UjianSiswa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ujian_id")
    private Ujian ujian;

    @ManyToOne
    @JoinColumn(name = "siswa_id")
    private Account siswa;


    @Column(name = "grade")
    private Double grade;
}
