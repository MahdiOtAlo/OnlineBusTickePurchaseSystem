package ir.maktabsharif.onlinebustickepurchasesystem.service;

import ir.maktabsharif.onlinebustickepurchasesystem.dto.LoginRequestDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.LoginResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.RegisterUserDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.dto.UserResponseDTO;
import ir.maktabsharif.onlinebustickepurchasesystem.mapper.UserMapper;
import ir.maktabsharif.onlinebustickepurchasesystem.model.User;
import ir.maktabsharif.onlinebustickepurchasesystem.model.UserRole;
import ir.maktabsharif.onlinebustickepurchasesystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("username or password is incorrect");
        }

        return new LoginResponseDTO(
                user.getUsername(),
                user.getRole().name(),
                "Welcome " + user.getUsername()
        );
    }

    @Transactional
    public UserResponseDTO registerUser(RegisterUserDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already exists!");
        }

        User user = userMapper.toEntity(request, passwordEncoder, UserRole.USER);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public UserResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return userMapper.toDto(user);
    }
}