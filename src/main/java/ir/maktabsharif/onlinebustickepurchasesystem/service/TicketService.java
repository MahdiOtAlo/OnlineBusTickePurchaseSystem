package ir.maktabsharif.onlinebustickepurchasesystem.service;

import ir.maktabsharif.onlinebustickepurchasesystem.dto.PurchaseTicketRequestDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.TicketResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.mapper.TicketMapper;
import ir.maktabsharif.onlinebustickepurchasesystem.model.*;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.TicketRepository;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.TripRepository;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TripRepository tripRepository, UserRepository userRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
    }

    @Transactional
    public TicketResponseDTO purchaseTicket(PurchaseTicketRequestDTO request, Long userId) {
        Trip trip = tripRepository.findById(request.tripId())
                .orElseThrow(() -> new RuntimeException("Trip not found!"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        int bookedSeats = ticketRepository.countByTripIdAndStatus(
                trip.getId(), TicketStatus.ACTIVE
        );

        if (bookedSeats >= trip.getCapacity()) {
            throw new RuntimeException("Trip capacity is full!");
        }

        // 4. Check for duplicate ticket
        boolean alreadyBooked = ticketRepository.existsByUserIdAndTripIdAndStatus(
                userId, trip.getId(), TicketStatus.ACTIVE
        );

        if (alreadyBooked) {
            throw new RuntimeException("You have already booked a ticket for this trip!");
        }

        // 5. Create ticket using Mapper
        Ticket ticket = ticketMapper.toEntity(request, trip, user);
        Ticket savedTicket = ticketRepository.save(ticket);

        // 6. Return DTO
        return ticketMapper.toDto(savedTicket);
    }

    public List<TicketResponseDTO> getUserTickets(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);
        return tickets.stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    public List<TicketResponseDTO> getUserActiveTickets(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserIdAndStatus(
                userId, TicketStatus.ACTIVE
        );
        return tickets.stream()
                .map(ticketMapper::toDto)
                .toList();
    }

    @Transactional
    public void cancelTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found!"));

        // Check ticket ownership
        if (!ticket.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to cancel this ticket!");
        }

        // Check ticket status
        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new RuntimeException("This ticket is already cancelled!");
        }

        ticket.setStatus(TicketStatus.CANCELLED);
        ticketRepository.save(ticket);
    }

    public TicketResponseDTO getTicketDetails(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found!"));

        // Check ticket ownership
        if (!ticket.getUser().getId().equals(userId)) {
            throw new RuntimeException("You are not authorized to view this ticket!");
        }

        return ticketMapper.toDto(ticket);
    }

    public int getAvailableSeats(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found!"));

        int bookedSeats = ticketRepository.countByTripIdAndStatus(
                tripId, TicketStatus.ACTIVE
        );

        return trip.getCapacity() - bookedSeats;
    }
}