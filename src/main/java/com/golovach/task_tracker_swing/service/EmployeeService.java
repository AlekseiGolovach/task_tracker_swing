package com.golovach.task_tracker_swing.service;

import com.golovach.task_tracker_swing.store.entities.EmployeeEntity;

import java.util.List;

public interface EmployeeService {
    List<EmployeeEntity> findAll();
}
