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
@Table(name = "kelasSiswa")
public class KelasSiswa {

    @EmbeddedId
    private KelasSiswaKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("kelasId")
    @JoinColumn(name = "kelas_id")
    private Kelas kelas;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("siswaId")
    @JoinColumn(name = "siswa_id")
    private Account account;

    private Integer rating;

}
