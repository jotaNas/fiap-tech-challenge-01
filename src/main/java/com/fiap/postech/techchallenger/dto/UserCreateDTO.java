package com.fiap.postech.techchallenger.dto;

import com.fiap.postech.techchallenger.domain.Address;
import com.fiap.postech.techchallenger.domain.UserType;
import jakarta.validation.constraints.*;

public record UserCreateDTO(

        @NotBlank(message = "O nome é obrigatório.")
        String name,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Informe um e-mail válido.")
        String email,

        @NotBlank(message = "O login é obrigatório.")
        String login,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
        String password,

        @NotNull(message = "O tipo de usuário deve ser informado.")
        UserType type,

        Address address
) { }
