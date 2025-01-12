package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CreateNewUserDataDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name,
        @NotBlank(message = "O email é obrigatório.")
        @Email
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 20, message = "A senha deve conter entre 8 e 20 caracteres.")
        String password,
        @NotBlank(message = "O CPF é obrigatório.")
        @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos.")
        String cpf,
        @NotNull(message = "A data de admissão é obrigatória.")
        LocalDateTime admissionDate,
        @NotNull(message = "A função é obrigatória.")
        @Valid
        UserRole role,
        @NotNull(message = "O setor é obrigatório.")
        Long departmentId) {
}
