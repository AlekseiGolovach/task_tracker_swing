package com.golovach.task_tracker_swing.dto.facade;

import com.golovach.task_tracker_swing.dto.TaskDto;
import com.golovach.task_tracker_swing.store.entities.TaskEntity;

public class TaskFacade {
    public TaskDto TaskToTaskDto(TaskEntity task) {
        return TaskDto.builder()
                .isDone(String.valueOf(task.getIsDone()))
                .name(task.getName())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .executionDate(task.getExecutionDate())
                .build();
    }


}
