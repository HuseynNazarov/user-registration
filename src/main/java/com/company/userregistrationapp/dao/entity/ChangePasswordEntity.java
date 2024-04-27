package com.company.userregistrationapp.dao.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "change_password")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "code")
    String code;

    @Column(name = "expire_time")
    LocalDateTime expiredTime;
}
