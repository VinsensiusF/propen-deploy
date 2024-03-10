package com.unimate.unimate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unimate.unimate.enums.AccountStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    @OneToMany(mappedBy = "siswa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<KelasSiswa> kelasSiswa = new ArrayList<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    private Instant deletedAt;
}
