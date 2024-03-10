package com.unimate.unimate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kelasSiswa")
public class KelasSiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kelas_id", referencedColumnName = "id")
    private Kelas kelas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "siswa_id", referencedColumnName = "id")
    private Account siswa;

    @Column(name = "rating")
    private Integer rating;

}
