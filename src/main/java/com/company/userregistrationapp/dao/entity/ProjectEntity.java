package com.company.userregistrationapp.dao.entity;

import com.company.userregistrationapp.enums.ProjectStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    List<TaskEntity> tasks;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ProjectStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    LocalDateTime updatedAt;
}
