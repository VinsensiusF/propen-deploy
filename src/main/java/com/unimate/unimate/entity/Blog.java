package com.unimate.unimate.entity;

import com.unimate.unimate.enums.AccountStatusEnum;
import com.unimate.unimate.enums.BlogType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String title;

    private String writer;

    // 5-mins reading time
    private String readingTime;

    // INFORMASI_BEASISWA, PEKERJAAN_LUAR_NEGERI, TIPS_TRIK
    @Enumerated(EnumType.STRING)
    private BlogType blogType;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;

    private Date deletedAt;

    @Lob
    private Blob image;
}