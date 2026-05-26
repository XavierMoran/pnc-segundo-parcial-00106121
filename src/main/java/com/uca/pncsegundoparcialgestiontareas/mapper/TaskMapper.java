package com.uca.pncsegundoparcialgestiontareas.mapper;

import com.uca.pncsegundoparcialgestiontareas.dto.TaskRequestDTO;
import com.uca.pncsegundoparcialgestiontareas.dto.TaskResponseDTO;
import com.uca.pncsegundoparcialgestiontareas.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDTO dto) {
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .estimatedHours(dto.getEstimatedHours())
                .loggedHours(dto.getLoggedHours())
                .dueDate(dto.getDueDate())
                .assignedTo(dto.getAssignedTo())
                .build();
    }

    public TaskResponseDTO toResponseDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .estimatedHours(task.getEstimatedHours())
                .loggedHours(task.getLoggedHours())
                .dueDate(task.getDueDate())
                .assignedTo(task.getAssignedTo())
                .active(task.getActive())
                .build();
    }

    public void updateEntityFromDTO(TaskRequestDTO dto, Task task) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setEstimatedHours(dto.getEstimatedHours());
        task.setLoggedHours(dto.getLoggedHours());
        task.setDueDate(dto.getDueDate());
        task.setAssignedTo(dto.getAssignedTo());
    }
}