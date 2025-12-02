package ir.maktabsharif.onlinebustickepurchasesystem.dto;

import ir.maktabsharif.onlinebustickepurchasesystem.model.Gender;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

public record PurchaseTicketRequestDTO(
        @NotBlank
        String passengerName,
        @NotNull
        Gender gender,
        @NotNull
        Long tripId
) {
}
