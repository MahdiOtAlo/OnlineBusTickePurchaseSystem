package ir.maktabsharif.onlinebustickepurchasesystem.config;

import ir.maktabsharif.onlinebustickepurchasesystem.model.*;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.TripRepository;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, TripRepository tripRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createUsers();
        createTrips();
    }

    private void createUsers() {
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                    User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .role(UserRole.ADMIN)
                            .build(),

                    User.builder()
                            .username("user1")
                            .password(passwordEncoder.encode("user123"))
                            .role(UserRole.USER)
                            .build(),

                    User.builder()
                            .username("user2")
                            .password(passwordEncoder.encode("password123"))
                            .role(UserRole.USER)
                            .build(),

                    User.builder()
                            .username("user3")
                            .password(passwordEncoder.encode("pass1234"))
                            .role(UserRole.USER)
                            .build(),

                    User.builder()
                            .username("user4")
                            .password(passwordEncoder.encode("secret123"))
                            .role(UserRole.USER)
                            .build()
            );

            userRepository.saveAll(users);
            System.out.println("✅ 5 users created successfully!");
        }
    }

    private void createTrips() {
        if (tripRepository.count() == 0) {
            LocalDate today = LocalDate.now();

            List<Trip> trips = List.of(
                    Trip.builder()
                            .origin("Tehran")
                            .destination("Isfahan")
                            .date(today.plusDays(3))
                            .time(LocalTime.of(18, 0))
                            .capacity(30)
                            .build(),

                    Trip.builder()
                            .origin("Tehran")
                            .destination("Mashhad")
                            .date(today.plusDays(2))
                            .time(LocalTime.of(8, 30))
                            .capacity(25)
                            .build(),

                    Trip.builder()
                            .origin("Isfahan")
                            .destination("Tehran")
                            .date(today.plusDays(4))
                            .time(LocalTime.of(14, 0))
                            .capacity(20)
                            .build(),

                    Trip.builder()
                            .origin("Tehran")
                            .destination("Shiraz")
                            .date(today.plusDays(5))
                            .time(LocalTime.of(10, 15))
                            .capacity(35)
                            .build(),

                    Trip.builder()
                            .origin("Mashhad")
                            .destination("Tehran")
                            .date(today.plusDays(6))
                            .time(LocalTime.of(16, 45))
                            .capacity(28)
                            .build()
            );

            tripRepository.saveAll(trips);
            System.out.println("✅ 5 trips created successfully!");
        }
    }
}