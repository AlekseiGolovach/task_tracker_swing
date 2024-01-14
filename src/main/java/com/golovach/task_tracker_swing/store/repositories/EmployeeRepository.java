package com.golovach.task_tracker_swing.store.repositories;

import com.golovach.task_tracker_swing.store.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
