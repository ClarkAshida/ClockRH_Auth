package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CreateNewUserDataDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 8, max = 20, message = "A senha deve conter entre 8 e 20 caracteres.")
        String password,
        @NotBlank
        @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos.")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos.")
        String cpf,
        @NotNull
        @Valid
        UserRole role,
        @NotNull
        Long departmentId) {
}
