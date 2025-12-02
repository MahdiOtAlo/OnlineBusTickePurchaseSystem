package ir.maktabsharif.onlinebustickepurchasesystem.mapper;

import ir.maktabsharif.onlinebustickepurchasesystem.dto.TripResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.TripManagementRequestDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.model.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    public TripResponseDTO toDto(Trip trip, Integer availableSeats) {
        return new TripResponseDTO(
                trip.getId(),
                trip.getOrigin(),
                trip.getDestination(),
                trip.getDate(),
                trip.getTime(),
                trip.getCapacity(),
                availableSeats
        );
    }

    public Trip toEntity(TripManagementRequestDTO dto) {
        return Trip.builder()
                .origin(dto.origin())
                .destination(dto.destination())
                .date(dto.date())
                .time(dto.time())
                .capacity(dto.capacity())
                .build();
    }
}