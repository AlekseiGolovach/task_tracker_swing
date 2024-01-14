package com.golovach.task_tracker_swing.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    List<TaskEntity> tasks = new ArrayList<>();
}
