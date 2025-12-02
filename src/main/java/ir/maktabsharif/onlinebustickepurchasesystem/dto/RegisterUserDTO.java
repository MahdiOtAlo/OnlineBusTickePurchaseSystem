package ir.maktabsharif.onlinebustickepurchasesystem.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}