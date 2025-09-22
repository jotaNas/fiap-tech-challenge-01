package com.fiap.postech.techchallenger.controller.v1;

import com.fiap.postech.techchallenger.dto.*;
import com.fiap.postech.techchallenger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UserControllerV1 {

    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Criar novo usuário")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody UserCreateDTO dto) {
        return userService.create(dto);
    }

    @Operation(summary = "Buscar usuário por ID")
    @GetMapping("/users/{id}")
    public UserResponseDTO get(@PathVariable Long id) {
        return userService.get(id);
    }

    @Operation(summary = "Buscar usuários por nome")
    @GetMapping("/users")
    public List<UserResponseDTO> search(@RequestParam(name = "name", required = false) String name) {
        if (name == null || name.isBlank()) {
            return userService.findAll();
        }
        return userService.searchByName(name);
    }

    @Operation(summary = "Atualizar dados do usuário (exceto senha)")
    @PutMapping("/users/{id}")
    public UserResponseDTO update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        return userService.update(id, dto);
    }

    @Operation(summary = "Alterar senha do usuário")
    @PatchMapping("/users/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long id, @Valid @RequestBody PasswordChangeDTO dto) {
        userService.changePassword(id, dto);
    }

    @Operation(summary = "Excluir usuário por ID")
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Validação de login")
    @PostMapping("/auth/login")
    public UserResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return userService.validateLogin(dto);
    }
}
