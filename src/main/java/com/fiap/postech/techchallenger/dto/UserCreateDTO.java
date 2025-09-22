package com.fiap.postech.techchallenger.dto;

import com.fiap.postech.techchallenger.domain.Address;
import com.fiap.postech.techchallenger.domain.UserType;
import jakarta.validation.constraints.*;

public record UserCreateDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String login,
        @NotBlank @Size(min = 6, max = 100) String password,
        @NotNull UserType type,
        Address address
) { }
