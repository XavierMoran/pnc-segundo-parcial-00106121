package com.uca.pncsegundoparcialgestiontareas.service.impl;

import com.uca.pncsegundoparcialgestiontareas.dto.TaskRequestDTO;
import com.uca.pncsegundoparcialgestiontareas.dto.TaskResponseDTO;
import com.uca.pncsegundoparcialgestiontareas.entity.Task;
import com.uca.pncsegundoparcialgestiontareas.enums.Priority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;
import com.uca.pncsegundoparcialgestiontareas.exception.BusinessRuleException;
import com.uca.pncsegundoparcialgestiontareas.exception.ResourceNotFoundException;
import com.uca.pncsegundoparcialgestiontareas.mapper.TaskMapper;
import com.uca.pncsegundoparcialgestiontareas.repository.TaskRepository;
import com.uca.pncsegundoparcialgestiontareas.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {

        validateBusinessRulesForCreate(requestDTO);

        Task task = taskMapper.toEntity(requestDTO);

        task.setStatus(TaskStatus.PENDING);
        task.setActive(true);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toResponseDTO(savedTask);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks(TaskStatus status, Priority priority) {

        List<Task> tasks;

        if (status != null && priority != null) {
            tasks = taskRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority);
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(taskMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id: " + id));

        return taskMapper.toResponseDTO(task);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id: " + id));

        validateBusinessRulesForUpdate(requestDTO, task);

        taskMapper.updateEntityFromDTO(requestDTO, task);

        if (task.getStatus() == TaskStatus.DONE || task.getStatus() == TaskStatus.CANCELLED) {
            task.setActive(false);
        } else {
            task.setActive(true);
        }

        Task updatedTask = taskRepository.save(task);

        return taskMapper.toResponseDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con id: " + id));

        if (task.getStatus() == TaskStatus.IN_PROGRESS || task.getStatus() == TaskStatus.REVIEW) {
            throw new BusinessRuleException("No se puede eliminar una tarea en estado IN_PROGRESS o REVIEW");
        }

        taskRepository.delete(task);
    }

    private void validateBusinessRulesForCreate(TaskRequestDTO requestDTO) {

        if (taskRepository.existsByTitleIgnoreCase(requestDTO.getTitle())) {
            throw new BusinessRuleException("Ya existe una tarea con ese título");
        }

        if (requestDTO.getStatus() == TaskStatus.DONE || requestDTO.getStatus() == TaskStatus.CANCELLED) {
            throw new BusinessRuleException("No se puede crear una tarea directamente en estado DONE o CANCELLED");
        }

        validateCommonBusinessRules(requestDTO);
    }

    private void validateBusinessRulesForUpdate(TaskRequestDTO requestDTO, Task currentTask) {

        if (!currentTask.getTitle().equalsIgnoreCase(requestDTO.getTitle())
                && taskRepository.existsByTitleIgnoreCase(requestDTO.getTitle())) {
            throw new BusinessRuleException("Ya existe una tarea con ese título");
        }

        validateCommonBusinessRules(requestDTO);
    }

    private void validateCommonBusinessRules(TaskRequestDTO requestDTO) {

        if (requestDTO.getLoggedHours() > requestDTO.getEstimatedHours()) {
            throw new BusinessRuleException("Las horas registradas no pueden exceder las horas estimadas");
        }

        if (!requestDTO.getDueDate().isAfter(LocalDate.now())) {
            throw new BusinessRuleException("La fecha de vencimiento debe ser futura");
        }
    }
}