package com.uca.pncsegundoparcialgestiontareas.service;

import com.uca.pncsegundoparcialgestiontareas.dto.TaskRequestDTO;
import com.uca.pncsegundoparcialgestiontareas.dto.TaskResponseDTO;
import com.uca.pncsegundoparcialgestiontareas.enums.Priority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO requestDTO);

    List<TaskResponseDTO> getAllTasks(TaskStatus status, Priority priority);

    TaskResponseDTO getTaskById(Long id);

    TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO);

    void deleteTask(Long id);
}