package com.golovach.task_tracker_swing.store.repositories;

import com.golovach.task_tracker_swing.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
