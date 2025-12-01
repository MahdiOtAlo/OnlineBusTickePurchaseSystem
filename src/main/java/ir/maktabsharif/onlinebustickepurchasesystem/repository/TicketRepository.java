package ir.maktabsharif.onlinebustickepurchasesystem.repository;

import ir.maktabsharif.onlinebustickepurchasesystem.model.Ticket;
import ir.maktabsharif.onlinebustickepurchasesystem.model.TicketStatus;
import ir.maktabsharif.onlinebustickepurchasesystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserIdAndStatus(Long userId, TicketStatus status);

    List<Ticket> findByUserId(Long userId);

    boolean existsByUserIdAndTripIdAndStatus(Long userId, Long tripId, TicketStatus status);

    int countByTripId(Long tripId);

    int countByTripIdAndStatus(Long tripId, TicketStatus status);
}