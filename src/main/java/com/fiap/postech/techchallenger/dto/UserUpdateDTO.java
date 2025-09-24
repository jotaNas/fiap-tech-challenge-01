package com.fiap.postech.techchallenger.dto;

import com.fiap.postech.techchallenger.domain.Address;
import com.fiap.postech.techchallenger.domain.UserType;
import jakarta.validation.constraints.*;

public record UserUpdateDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email informado não é válido.")
        String email,

        @NotBlank(message = "O login é obrigatório.")
        String login,

        @NotNull(message = "O tipo de usuário é obrigatório.")
        UserType type,

        Address address
) { }
