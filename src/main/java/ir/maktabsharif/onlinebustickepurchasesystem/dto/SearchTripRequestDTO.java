package ir.maktabsharif.onlinebustickepurchasesystem.dto;

import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public record SearchTripRequestDTO(
        @NotBlank
        String origin,
        @NotBlank
        String destination,
        @NotNull
        LocalDate date
) {
}
