package com.unimate.unimate.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Type(JsonBinaryType.class)
//    @Column(name = "content", columnDefinition = "jsonb")
//    private QuestionContent content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ujianId")
    private Ujian ujian;
}
