package com.unimate.unimate.entity;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "partnership")
public class Partnership {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String company;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;

    @CreationTimestamp
    private Date createdAt;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private Account user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PartnershipStatus status = PartnershipStatus.NEW;

  
    
}
