package com.uca.pncsegundoparcialgestiontareas.repository;

import com.uca.pncsegundoparcialgestiontareas.entity.Task;
import com.uca.pncsegundoparcialgestiontareas.enums.Priority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByTitleIgnoreCase(String title);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(Priority priority);

    List<Task> findByStatusAndPriority(TaskStatus status, Priority priority);
}