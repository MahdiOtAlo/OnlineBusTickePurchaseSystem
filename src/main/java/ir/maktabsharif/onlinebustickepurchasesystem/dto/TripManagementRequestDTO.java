package ir.maktabsharif.onlinebustickepurchasesystem.dto;

import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record TripManagementRequestDTO(
        @NotBlank
        String origin,
        @NotBlank
        String destination,
        @NotNull
        LocalDate date,
        @NotNull
        LocalTime time,
        @NotNull
        Integer capacity) {
}
