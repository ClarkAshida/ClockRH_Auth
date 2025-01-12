package clock.rh.userManagement.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDataDTO(
        String name,
        @Email(message = "Email inválido")
        String email,
        @Size(min = 8, max = 20, message = "A senha deve conter entre 8 e 20 caracteres.")
        String password,
        Long departmentId
) {
}
