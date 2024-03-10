package com.unimate.unimate.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //Out of 100
    private Long score;

    @Type(JsonBinaryType.class)
    @Column(name = "answers", columnDefinition = "jsonb")
    private List<String> answers;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ujianId")
    private Ujian ujian;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId")
    private Account student;
}
