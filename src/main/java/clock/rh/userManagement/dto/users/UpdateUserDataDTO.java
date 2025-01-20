package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.UserRole;
import jakarta.validation.constraints.*;

public record UpdateUserDataDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String name,
        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 20, message = "A senha deve conter entre 8 e 20 caracteres.")
        String password,
        @NotNull(message = "O setor é obrigatório")
        Long departmentId,
        @NotNull(message = "A função é obrigatória.")
        UserRole role
) {
}
