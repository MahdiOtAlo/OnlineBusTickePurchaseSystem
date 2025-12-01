package ir.maktabsharif.onlinebustickepurchasesystem.repository;

import ir.maktabsharif.onlinebustickepurchasesystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}