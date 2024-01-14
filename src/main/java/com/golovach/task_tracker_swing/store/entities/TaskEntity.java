package com.golovach.task_tracker_swing.store.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private Boolean isDone;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate executionDate;


}
