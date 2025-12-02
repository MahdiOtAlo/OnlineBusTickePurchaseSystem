package ir.maktabsharif.onlinebustickepurchasesystem.mapper;

import ir.maktabsharif.onlinebustickepurchasesystem.dto.UserResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.RegisterUserDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.model.User;
import ir.maktabsharif.onlinebustickepurchasesystem.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().name()
        );
    }

    public User toEntity(RegisterUserDTO dto, PasswordEncoder passwordEncoder, UserRole role) {
        return User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(role)
                .build();
    }

}