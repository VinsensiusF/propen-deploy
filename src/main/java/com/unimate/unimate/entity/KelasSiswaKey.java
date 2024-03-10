package com.unimate.unimate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KelasSiswaKey implements Serializable {
    @Column(name = "siswa_id")
    private Long siswaId;

    @Column(name = "kelas_id")
    private Long kelasId;
}
