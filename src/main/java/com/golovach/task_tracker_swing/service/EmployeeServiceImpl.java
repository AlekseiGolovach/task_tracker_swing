package com.golovach.task_tracker_swing.service;

import com.golovach.task_tracker_swing.store.entities.EmployeeEntity;
import com.golovach.task_tracker_swing.store.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }
}
