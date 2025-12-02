package ir.maktabsharif.onlinebustickepurchasesystem.service;

import ir.maktabsharif.onlinebustickepurchasesystem.dto.SearchTripRequestDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.TripResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.TripManagementRequestDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.mapper.TripMapper;
import ir.maktabsharif.onlinebustickepurchasesystem.model.TicketStatus;
import ir.maktabsharif.onlinebustickepurchasesystem.model.Trip;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.TicketRepository;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;
    private final TripMapper tripMapper;

    @Autowired
    public TripService(TripRepository tripRepository, TicketRepository ticketRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
        this.tripMapper = tripMapper;
    }

    public List<TripResponseDTO> searchTrips(SearchTripRequestDTO request) {

        List<Trip> trips = tripRepository.searchTrips(
                request.origin(),
                request.destination(),
                request.date()
        );

        return trips.stream()
                .map(t -> {
                    int bookedSeats = ticketRepository.countByTripIdAndStatus(
                            t.getId(), TicketStatus.ACTIVE
                    );
                    int availableSeats = t.getCapacity() - bookedSeats;
                    return tripMapper.toDto(t, availableSeats);
                })
                .toList();
    }

    @Transactional
    public Trip createTrip(TripManagementRequestDTO request) {

        if (request.capacity() <= 0) {
            throw new RuntimeException("capacity must be greater than zero!");
        }

        boolean exists = tripRepository.existsByOriginAndDestinationAndDate(
                request.origin(), request.destination(), request.date()
        );
        if (exists) {
            throw new RuntimeException("this ticket is already exist!");
        }

        Trip trip = tripMapper.toEntity(request);
        return tripRepository.save(trip);
    }

    @Transactional
    public Trip updateTrip(Long tripId, TripManagementRequestDTO request) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("user not found!"));

        if (request.date().isBefore(LocalDate.now())) {
            throw new RuntimeException("trip date must be in the future!");
        }
        if (request.capacity() <= 0) {
            throw new RuntimeException("capacity must be greater than zero!");
        }

        trip.setOrigin(request.origin());
        trip.setDestination(request.destination());
        trip.setDate(request.date());
        trip.setTime(request.time());
        trip.setCapacity(request.capacity());

        return tripRepository.save(trip);
    }

    @Transactional
    public void deleteTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("trip not found!"));

        int activeTickets = ticketRepository.countByTripIdAndStatus(
                tripId, TicketStatus.ACTIVE
        );

        if (activeTickets > 0) {
            throw new RuntimeException("can not delete trip with active tickets!");
        }

        tripRepository.delete(trip);
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("trip not found!"));
    }

    public TripResponseDTO getTripWithAvailability(Long tripId) {
        Trip trip = getTripById(tripId);
        int bookedSeats = ticketRepository.countByTripIdAndStatus(
                tripId, TicketStatus.ACTIVE
        );
        int availableSeats = trip.getCapacity() - bookedSeats;

        return tripMapper.toDto(trip, availableSeats);
    }
}