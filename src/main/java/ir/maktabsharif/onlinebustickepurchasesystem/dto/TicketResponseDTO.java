package ir.maktabsharif.onlinebustickepurchasesystem.dto;

import ir.maktabsharif.onlinebustickepurchasesystem.model.Gender;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketResponseDTO(Long id,
                                String passengerName,
                                Gender gender,
                                String status,
                                String origin,
                                String destination,
                                LocalDate date,
                                LocalTime time,
                                Long tripId) {
}
