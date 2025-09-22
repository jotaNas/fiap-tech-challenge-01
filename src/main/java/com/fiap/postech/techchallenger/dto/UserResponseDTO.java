package com.fiap.postech.techchallenger.dto;

import com.fiap.postech.techchallenger.domain.Address;
import com.fiap.postech.techchallenger.domain.UserType;

import java.time.OffsetDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String login,
        UserType type,
        Address address,
        OffsetDateTime lastModifiedAt
) { }