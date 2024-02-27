package com.unimate.unimate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emailVerificationToken")
public class EmailVerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private Account account;

    private Instant issuedAt;

    private Instant expiredAt;
}
