package com.unimate.unimate.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import java.util.UUID;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "payment_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Kelas course;
    @Column(name = "price")
    private int price;
    @Column(name = "date")
    private Date date;
    @Column(name = "payat")
    private Date payat;
    @Column(name = "status")
    private String status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "siswa_id")
    private Account siswa;

    @Column(name = "net")
    private int net;

    @Column(name = "method")
    private String method;
}
