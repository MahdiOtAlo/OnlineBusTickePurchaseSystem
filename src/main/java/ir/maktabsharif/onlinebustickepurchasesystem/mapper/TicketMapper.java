package ir.maktabsharif.onlinebustickepurchasesystem.mapper;

import ir.maktabsharif.onlinebustickepurchasesystem.dto.TicketResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.PurchaseTicketRequestDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.model.Ticket;
import ir.maktabsharif.onlinebustickepurchasesystem.model.TicketStatus;
import ir.maktabsharif.onlinebustickepurchasesystem.model.Trip;
import ir.maktabsharif.onlinebustickepurchasesystem.model.User;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketResponseDTO toDto(Ticket ticket) {
        Trip trip = ticket.getTrip();
        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getPassengerName(),
                ticket.getGender(),
                ticket.getStatus().name(),
                trip.getOrigin(),
                trip.getDestination(),
                trip.getDate(),
                trip.getTime(),
                trip.getId()
        );
    }


    public Ticket toEntity(PurchaseTicketRequestDTO dto, Trip trip, User user) {
        return Ticket.builder()
                .passengerName(dto.passengerName())
                .gender(dto.gender())
                .status(TicketStatus.ACTIVE)
                .trip(trip)
                .user(user)
                .build();
    }
}