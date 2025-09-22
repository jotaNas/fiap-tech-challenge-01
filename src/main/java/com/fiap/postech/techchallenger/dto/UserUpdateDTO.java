package com.fiap.postech.techchallenger.dto;

import com.fiap.postech.techchallenger.domain.Address;
import com.fiap.postech.techchallenger.domain.UserType;
import jakarta.validation.constraints.*;

public record UserUpdateDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String login,
        @NotNull UserType type,
        Address address
) { }
