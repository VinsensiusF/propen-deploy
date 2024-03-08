package com.unimate.unimate.entity;

import com.unimate.unimate.enums.AccountStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO create SQL Index
    private String email;

    private String password;

    private String name;

//    @Type(type = "org.hibernate.type.ImageType")
//    @Lob
//    private byte[] profilePicture;

    private String profilePicture;

    // UNVERIFIED, VERIFIED
    @Enumerated(EnumType.STRING)
    private AccountStatusEnum status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    private Instant deletedAt;
}
