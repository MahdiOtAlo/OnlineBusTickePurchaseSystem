package ir.maktabsharif.onlinebustickepurchasesystem.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TripResponseDTO(Long id,
                              String origin,
                              String destination,
                              LocalDate date,
                              LocalTime time,
                              Integer capacity,
                              Integer availableSeats
) {
}
