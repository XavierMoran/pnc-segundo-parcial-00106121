package com.uca.pncsegundoparcialgestiontareas.dto;

import com.uca.pncsegundoparcialgestiontareas.enums.Priority;
import com.uca.pncsegundoparcialgestiontareas.enums.TaskStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    private String description;

    @NotNull(message = "El status es obligatorio")
    private TaskStatus status;

    @NotNull(message = "La prioridad es obligatoria")
    private Priority priority;

    @NotNull(message = "Las horas estimadas son obligatorias")
    @Min(value = 1, message = "Las horas estimadas deben ser mayor o igual a 1")
    private Integer estimatedHours;

    @NotNull(message = "Las horas registradas son obligatorias")
    @Min(value = 0, message = "Las horas registradas no pueden ser negativas")
    private Integer loggedHours;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate dueDate;

    @NotBlank(message = "El responsable es obligatorio")
    private String assignedTo;
}