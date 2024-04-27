package com.company.userregistrationapp.dao.entity;

import com.company.userregistrationapp.enums.TaskStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraph(
        name = "taskWithProjectAndCategory",
        attributeNodes = {
                @NamedAttributeNode("category"),
                @NamedAttributeNode("project")
        }

)
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "name")
    String name;

    @JoinColumn(name = "project_id",referencedColumnName = "id")
    @ManyToOne(fetch =FetchType.EAGER)
    ProjectEntity project;

    @JoinColumn(name = "category_id",referencedColumnName = "id")
    @ManyToOne(fetch =FetchType.EAGER)
    CategoryEntity category;

    @Column(nullable = false)
    String description;

    @Column(name = "priority")
    Integer priority;

    @Column(name = "deadline")
    LocalDateTime deadline;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    TaskStatus taskStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    LocalDateTime updatedAt;

}
