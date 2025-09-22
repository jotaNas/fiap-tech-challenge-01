package com.fiap.postech.techchallenger.service;

import com.fiap.postech.techchallenger.domain.User;
import com.fiap.postech.techchallenger.dto.*;
import com.fiap.postech.techchallenger.exception.EmailAlreadyUsedException;
import com.fiap.postech.techchallenger.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException(dto.email());
        }

        String hash = BCrypt.hashpw(dto.password(), BCrypt.gensalt());

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .login(dto.login())
                .passwordHash(hash)
                .type(dto.type())
                .address(dto.address())
                .build();

        user = userRepository.save(user);
        return toDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO get(Long id) {
        return toDTO(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> searchByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = findOrThrow(id);

        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.email())) {
                throw new EmailAlreadyUsedException(dto.email());
            }
            user.setEmail(dto.email());
        }

        if (dto.name() != null) {
            user.setName(dto.name());
        }

        if (dto.login() != null) {
            user.setLogin(dto.login());
        }

        if (dto.type() != null) {
            user.setType(dto.type());
        }

        if (dto.address() != null) {
            user.setAddress(dto.address());
        }

        user = userRepository.save(user);
        return toDTO(user);
    }

    private User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o id: " + id));
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getType(),
                user.getAddress(),
                user.getLastModifiedAt()
        );
    }

    public void changePassword(Long id, @Valid PasswordChangeDTO dto) {
        User user = findOrThrow(id);

        if (dto.currentPassword() == null || !BCrypt.checkpw(dto.currentPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Senha atual inválida.");
        }
        if (dto.newPassword() == null || dto.newPassword().isBlank()) {
            throw new IllegalArgumentException("Nova senha não pode ser vazia.");
        }

        String newHash = BCrypt.hashpw(dto.newPassword(), BCrypt.gensalt());
        user.setPasswordHash(newHash);
        user.setLastModifiedAt(OffsetDateTime.now());

        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findOrThrow(id);
        userRepository.deleteById(user.getId());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO validateLogin(@Valid LoginRequestDTO dto) {
        User user = userRepository.findByLogin(dto.login())
                .or(() -> userRepository.findByEmail(dto.login()))
                .orElseThrow(() -> new IllegalArgumentException("Login ou senha inválidos."));

        if (!BCrypt.checkpw(dto.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        return toDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
