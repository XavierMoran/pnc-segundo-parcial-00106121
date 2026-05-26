package com.uca.pncsegundoparcialgestiontareas.dto;

import com.uca.pncsegundoparcialgestiontareas.enums.Priority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private Integer estimatedHours;
    private Integer loggedHours;
    private LocalDate dueDate;
    private String assignedTo;
    private Boolean active;
}