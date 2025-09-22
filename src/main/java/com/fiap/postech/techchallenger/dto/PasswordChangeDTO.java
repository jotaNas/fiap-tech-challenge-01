package com.fiap.postech.techchallenger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeDTO(
        @NotBlank String currentPassword,
        @NotBlank @Size(min = 6, max = 100) String newPassword
) { }
