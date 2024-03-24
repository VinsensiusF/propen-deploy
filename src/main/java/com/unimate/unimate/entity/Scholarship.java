package com.unimate.unimate.entity;

import com.unimate.unimate.enums.ScholarshipDegree;
import com.unimate.unimate.enums.ScholarshipStatus;
import com.unimate.unimate.enums.ScholarshipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scholarship")
@Check(constraints = "ended_at > started_at")
public class Scholarship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started_at")
    private Date startedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ended_at")
    private Date endedAt;

    //OPEN, CLOSED
    @Enumerated(EnumType.STRING)
    private ScholarshipStatus scholarshipStatus;

    //FULLY_FUNDED, PARTIALLY_FUNDED, SELF_FUNDED
    @Enumerated(EnumType.STRING)
    private ScholarshipType scholarshipType;

    //D3, D4, S1, S2, S3
    @ElementCollection(targetClass = ScholarshipDegree.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "scholarship_degrees", joinColumns = @JoinColumn(name = "scholarship_id")) // Define table for the collection
    @Column(name = "degree") // Define column name for the degree
    private Set<ScholarshipDegree> scholarshipDegrees;

    @Column(name = "ipk_minimal")
    private String ipkMinimal;

    private String university;

    private long minimalAge;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date modifiedAt;

    private Date deletedAt;

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (startedAt != null && endedAt != null && startedAt.after(endedAt)) {
            throw new IllegalArgumentException("EndedAt must be after StartedAt");
        }
    }
}