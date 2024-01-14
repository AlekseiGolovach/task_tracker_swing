package com.golovach.task_tracker_swing.dto.facade;

import com.golovach.task_tracker_swing.dto.EmployeeDto;
import com.golovach.task_tracker_swing.store.entities.EmployeeEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmployeeFacade {
    public EmployeeDto employeeToEmployeeDto(EmployeeEntity employee) {
        return EmployeeDto.builder()
                .fullName(employee.getFullName())
                .tasks(employee.getTasks()
                        .stream()
                        .map(task -> new TaskFacade().TaskToTaskDto(task))
                        .collect(Collectors.toList()))
                .build();
    }
}
