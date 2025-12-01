package ir.maktabsharif.onlinebustickepurchasesystem.repository;

import ir.maktabsharif.onlinebustickepurchasesystem.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t WHERE " +
            "(:origin IS NULL OR t.origin = :origin) AND " +
            "(:destination IS NULL OR t.destination = :destination) AND " +
            "(:date IS NULL OR t.date = :date) " +
            "ORDER BY t.date ASC, t.time ASC")
    List<Trip> searchTrips(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("date") LocalDate date
    );

    boolean existsByOriginAndDestinationAndDate(
            String origin,
            String destination,
            LocalDate date
    );
}