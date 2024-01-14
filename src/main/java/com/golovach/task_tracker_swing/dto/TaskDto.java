package com.golovach.task_tracker_swing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private String name;

    private String isDone;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate executionDate;
}
